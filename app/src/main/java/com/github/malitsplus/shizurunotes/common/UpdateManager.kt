package com.github.malitsplus.shizurunotes.common

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Handler
import android.os.Message
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.github.malitsplus.shizurunotes.BuildConfig
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.user.UserSettings
import com.github.malitsplus.shizurunotes.utils.BrotliUtils
import com.github.malitsplus.shizurunotes.utils.FileUtils
import com.github.malitsplus.shizurunotes.utils.JsonUtils
import com.github.malitsplus.shizurunotes.utils.LogUtils
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class UpdateManager private constructor(
    private val mContext: Context)
{

    companion object {
        private const val UPDATE_CHECK_COMPLETED = 1
        private const val UPDATE_DOWNLOADING = 2
        private const val UPDATE_DOWNLOAD_ERROR = 3
        private const val UPDATE_DOWNLOAD_COMPLETED = 4
        private const val UPDATE_COMPLETED = 5
        private const val UPDATE_DOWNLOAD_CANCELED = 6
        private const val APP_UPDATE_CHECK_COMPLETED = 11
        private lateinit var updateManager: UpdateManager

        fun with(context: Context): UpdateManager{
            updateManager = UpdateManager(context)
            return updateManager
        }

        fun get(): UpdateManager{
            return updateManager
        }
    }

    private var appHasNewVersion = false
    private var appVersionJsonInstance: AppVersionJson? = null
    private var serverVersion: Long = 0
    private var progress = 0
    private var hasNewVersion = false
    private val canceled = false
    val callBack: UpdateCallBack
    private val versionInfo: String? = null
    private var progressDialog: MaterialDialog? = null
    private var maxLength = 0

    init {
        callBack = object: UpdateCallBack {

            /***
             * APP更新检查完成，弹出更新确认对话框
             */
            override fun appCheckUpdateCompleted() {
                if (appHasNewVersion) {
                    val log = when (UserSettings.get().preference.getString(UserSettings.LANGUAGE_KEY, "ja")){
                        "zh" -> appVersionJsonInstance?.messageZh
                        else -> appVersionJsonInstance?.messageJa
                    }
                    MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR)
                        .title(text = I18N.getString(R.string.app_full_name) + "v" + appVersionJsonInstance?.versionName)
                        .message(text = log)
                        .cancelOnTouchOutside(false)
                        .show {
                            positiveButton(res = R.string.db_update_dialog_confirm) {
                                downloadApp()
                            }
                            negativeButton(res = R.string.db_update_dialog_cancel) {
                                checkDatabaseVersion()
                            }
                        }
                } else {
                    checkDatabaseVersion()
                }
            }

            /***
             * 数据库检查更新完成，弹出更新确认对话框
             */
            override fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: CharSequence?) {
                if (hasUpdate) {
                    LogUtils.file(LogUtils.I, "New db version$serverVersion determined.")
                    MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR)
                        .title(res = R.string.db_update_dialog_title)
                        .message(res = R.string.db_update_dialog_text)
                        .cancelOnTouchOutside(false)
                        .show {
                            positiveButton(res = R.string.db_update_dialog_confirm) {
                                downloadDB(false)
                            }
                            negativeButton(res = R.string.db_update_dialog_cancel) {
                                LogUtils.file(LogUtils.I, "Canceled download db version$serverVersion.")
                            }
                        }
                }
            }

            /***
             * 更新数据库时进度条变化
             */
            override fun dbDownloadProgressChanged(progress: Int, maxLength: Int) {
                if (progressDialog?.isShowing == true) {
                    progressDialog?.message(
                        null,
                        "%d / %d KB download.".format((progress / 1024), maxLength / 1024),
                        null
                    )
                }
            }

            /***
             * 取消数据库更新
             */
            override fun dbDownloadCanceled() {}

            /***
             * 数据库下载完成
             */
            override fun dbDownloadCompleted(success: Boolean, errorMsg: CharSequence?) {
                LogUtils.file(LogUtils.I, "DB download finished.")
                progressDialog?.message(R.string.db_update_download_finished_text, null, null)
            }

            /***
             * 数据库更新整个流程结束
             */
            override fun dbUpdateCompleted() {
                LogUtils.file(LogUtils.I, "DB update finished.")
                UserSettings.get().setDbVersion(serverVersion)
                progressDialog?.cancel()
                iActivityCallBack?.showSnackBar(R.string.db_update_finished_text)
                iActivityCallBack?.dbUpdateFinished()
            }

            /***
             * 更新失败
             */
            override fun dbUpdateError() {
                progressDialog?.cancel()
                iActivityCallBack?.showSnackBar(R.string.db_update_failed)
            }
        }
    }

    class AppVersionJson{
        var versionCode: Int? = null
        var versionName: String? = null
        var recommend: Boolean? = null
        var messageJa: String? = null
        var messageZh: String? =null
    }

    fun checkAppVersion(checkDb: Boolean) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Statics.APP_UPDATE_LOG)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (checkDb) checkDatabaseVersion()
            }

            override fun onResponse(call: Call, response: Response) {
                val lastVersionJson = response.body?.string()
                try {
                    if (lastVersionJson.isNullOrEmpty())
                        throw Exception("No response from server.")
                    if (response.code != 200)
                        throw Exception("Abnormal connection state code: ${response.code}")

                    appVersionJsonInstance = JsonUtils.getBeanFromJson<AppVersionJson>(lastVersionJson, AppVersionJson::class.java)
                    appVersionJsonInstance?.versionCode?.let {
                        if (it > getAppVersionCode()){
                            appHasNewVersion = true
                        }
                    }
                } catch (e: Exception) {
                    LogUtils.file(LogUtils.E, "checkAppVersion", e.message)
                    iActivityCallBack?.showSnackBar(R.string.app_update_check_failed)
                } finally {
                    updateHandler.sendEmptyMessage(APP_UPDATE_CHECK_COMPLETED)
                }
            }
        })
    }

    fun checkDatabaseVersion() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Statics.LATEST_VERSION_URL)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_ERROR)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val lastVersionJson = response.body?.string()
                try {
                    if (lastVersionJson.isNullOrEmpty())
                        throw Exception("No response from server.")
                    val obj = JSONObject(lastVersionJson)
                    serverVersion = obj.getLong("TruthVersion")
//                    hasNewVersion = true
                    hasNewVersion = serverVersion != UserSettings.get().getDbVersion()
                    updateHandler.sendEmptyMessage(UPDATE_CHECK_COMPLETED)
                } catch (e: Exception) {
                    LogUtils.file(LogUtils.E, "checkDatabaseVersion", e.message)
                    updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_ERROR)
                }
            }
        })
    }

    var downloadId: Long? = null
    fun downloadApp(){
        val downloadManager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(Statics.APP_PACKAGE)).apply {
            setMimeType("application/vnd.android.package-archive")
            setTitle(I18N.getString(R.string.app_full_name))
            setDestinationInExternalFilesDir(mContext, null, Statics.APK_NAME)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }
        FileUtils.checkFileAndDeleteIfExists(File(mContext.getExternalFilesDir(null), Statics.APK_NAME))
        downloadId = downloadManager.enqueue(request)
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        mContext.registerReceiver(broadcastReceiver, intentFilter)
    }

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE){
                if (intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId){
                    installApp()
                }
            }
        }
    }

    fun installApp() {
        val contentUri = FileProvider.getUriForFile(
            mContext,
            BuildConfig.APPLICATION_ID + ".provider",
            File(mContext.getExternalFilesDir(null), Statics.APK_NAME)
        )
        val install = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
            data = contentUri
        }
        mContext.unregisterReceiver(broadcastReceiver)
        mContext.startActivity(install)
    }

    fun downloadDB(forceDownload: Boolean) {
        LogUtils.file(LogUtils.I, "Start download DB ver$serverVersion.")
        progressDialog = MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR).apply {
            this.title(R.string.db_update_progress_title, null)
            .message(R.string.db_update_progress_text, null, null)
            .cancelable(false)
            .show()
        }
        thread(start = true){
            try {
                if (forceDownload) {
                    FileUtils.deleteDirectory(File(FileUtils.getDbDirectoryPath()))
                }
                val conn = URL(Statics.DB_FILE_URL).openConnection() as HttpURLConnection
                maxLength = conn.contentLength
                val inputStream = conn.inputStream
                if (!File(FileUtils.getDbDirectoryPath()).exists()) {
                    if (!File(FileUtils.getDbDirectoryPath()).mkdirs()) throw Exception("Cannot create DB path.")
                }
                val compressedFile = File(FileUtils.getCompressedDbFilePath())
                if (compressedFile.exists()) {
                    FileUtils.deleteFile(compressedFile)
                }
                val fileOutputStream = FileOutputStream(compressedFile)
                var totalDownload = 0
                val buf = ByteArray(1024 * 1024)
                var numRead: Int
                while (true) {
                    numRead = inputStream.read(buf)
                    totalDownload += numRead
                    progress = totalDownload
                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING))
                    if (numRead <= 0) {
                        updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED)
                        break
                    }
                    fileOutputStream.write(buf, 0, numRead)
                }
                inputStream.close()
                fileOutputStream.close()
                iActivityCallBack?.dbDownloadFinished()
            } catch (e: Exception) {
                LogUtils.file(LogUtils.E, "downloadDB", e.message)
                updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_ERROR)
            }
        }
    }

    fun doDecompress(){
        FileUtils.deleteFile(FileUtils.getDbFilePath())
        LogUtils.file(LogUtils.I, "Start decompress DB.")
        BrotliUtils.deCompress(FileUtils.getCompressedDbFilePath(), true)
        updateHandler.sendEmptyMessage(UPDATE_COMPLETED)
    }

    fun forceDownloadDb() {
        MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR)
            .title(res = R.string.db_update_dialog_title)
            .message(res = R.string.db_update_dialog_text)
            .cancelOnTouchOutside(false)
            .show {
                positiveButton(res = R.string.db_update_dialog_confirm) {
                    downloadDB(true)
                }
                negativeButton(res = R.string.db_update_dialog_cancel) {
                    LogUtils.file(LogUtils.I, "Canceled download db version$serverVersion.")
                }
            }
    }

    fun updateFailed(){
        updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_ERROR)
    }

    fun getAppVersionCode(): Int{
        return BuildConfig.VERSION_CODE
    }

    val updateHandler = Handler(Handler.Callback { msg: Message ->
        when (msg.what) {
            APP_UPDATE_CHECK_COMPLETED ->
                callBack.appCheckUpdateCompleted()
            UPDATE_CHECK_COMPLETED ->
                callBack.dbCheckUpdateCompleted(hasNewVersion, versionInfo)
            UPDATE_DOWNLOADING ->
                callBack.dbDownloadProgressChanged(progress, maxLength)
            UPDATE_DOWNLOAD_ERROR ->
                callBack.dbUpdateError()
            UPDATE_DOWNLOAD_COMPLETED ->
                callBack.dbDownloadCompleted(true, "")
            UPDATE_COMPLETED ->
                callBack.dbUpdateCompleted()
            UPDATE_DOWNLOAD_CANCELED ->
                TODO()
            else -> {
            }
        }
        true
    })

    interface UpdateCallBack {
        fun appCheckUpdateCompleted()
        fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: CharSequence?)
        fun dbDownloadProgressChanged(progress: Int, maxLength: Int)
        fun dbDownloadCanceled()
        fun dbUpdateError()
        fun dbDownloadCompleted(success: Boolean, errorMsg: CharSequence?)
        fun dbUpdateCompleted()
    }

    interface IActivityCallBack {
        fun showSnackBar(@StringRes messageRes: Int)
        fun dbDownloadFinished()
        fun dbUpdateFinished()
    }

    private var iActivityCallBack: IActivityCallBack? = null
    fun setIActivityCallBack(callBack: IActivityCallBack) {
        iActivityCallBack = callBack
    }
}
