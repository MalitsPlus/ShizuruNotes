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

    private var serverVersion = 0
    private var progress = 0
    private var hasNewVersion = false
    private val canceled = false
    private val callBack: UpdateCallBack
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
                    val log = when (UserSettings.get().preference.getString("language", "ja")){
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
                                //checkDatabaseVersion()
                            }
                            negativeButton(res = R.string.db_update_dialog_cancel) {
                                checkDatabaseVersion(false)
                            }
                        }
                } else {
                    checkDatabaseVersion(false)
                }
            }

            /***
             * 数据库检查更新完成，弹出更新确认对话框
             */
            override fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: CharSequence?) {
                if (hasUpdate) {
                    MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR)
                        .title(res = R.string.db_update_dialog_title)
                        .message(res = R.string.db_update_dialog_text)
                        .cancelOnTouchOutside(false)
                        .show {
                            positiveButton(res = R.string.db_update_dialog_confirm) {
                                downloadDB()
                            }
                            negativeButton(res = R.string.db_update_dialog_cancel)
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
                progressDialog?.message(R.string.db_update_download_finished_text, null, null)
            }

            /***
             * 数据库更新整个流程结束
             */
            override fun dbUpdateCompleted() {
                iActivityCallBack?.dbUpdateFinished()
                UserSettings.get().preference.edit().putInt("dbVersion", serverVersion).apply()
                progressDialog?.cancel()
                iActivityCallBack?.showSnackBar(R.string.db_update_finished_text)
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
                //iActivityCallBack?.showSnackBar(R.string.app_update_check_failed)
                if (checkDb) checkDatabaseVersion(false)
            }

            override fun onResponse(call: Call, response: Response) {
                val lastVersionJson = response.body?.string()
                try {
                    if (lastVersionJson.isNullOrEmpty())
                        throw Exception("No response from server.")
                    if (response.code != 200)
                        throw Exception("Abnormal connection state code: %d.".format(response.code))

                    appVersionJsonInstance = JsonUtils.getBeanFromJson<AppVersionJson>(lastVersionJson, AppVersionJson::class.java)
                    appVersionJsonInstance?.versionCode?.let {
                        if (it > getAppVersionCode()){
                            appHasNewVersion = true
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    iActivityCallBack?.showSnackBar(R.string.app_update_check_failed)
                } finally {
                    updateHandler.sendEmptyMessage(APP_UPDATE_CHECK_COMPLETED)
                }
            }
        })
    }

    fun checkDatabaseVersion(forceDownload: Boolean) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Statics.LAST_VERSION_URL)
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
                    serverVersion = obj.getInt("TruthVersion")
                    hasNewVersion = if (forceDownload) {
                        true
                    } else {
                        serverVersion != UserSettings.get().preference.getInt("dbVersion", 0)
                    }
                    updateHandler.sendEmptyMessage(UPDATE_CHECK_COMPLETED)
                } catch (ex: Exception) {
                    ex.printStackTrace()
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
            setTitle(I18N.getString(R.string.app_name))
            setDestinationInExternalFilesDir(mContext, null, Statics.APK_NAME)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }
        Utils.checkFileAndDeleteIfExists(File(mContext.getExternalFilesDir(null), Statics.APK_NAME))
        downloadId = downloadManager.enqueue(request)
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        mContext.registerReceiver(broadcastReceiver, intentFilter)
    }

    private val broadcastReceiver = object: BroadcastReceiver(){
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

    fun downloadDB() {
        progressDialog = MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR).apply {
            this.title(R.string.db_update_progress_title, null)
            .message(R.string.db_update_progress_text, null, null)
            .cancelable(false)
            .show()
        }
        thread(start = true){
            try {
                val conn = URL(Statics.DB_FILE_URL).openConnection() as HttpURLConnection
                maxLength = conn.contentLength
                val inputStream = conn.inputStream
                if (!File(Statics.DB_PATH).exists()) {
                    if (!File(Statics.DB_PATH).mkdirs()) throw Exception("Cannot create DB path.")
                }
                val compressedFile = File(Statics.DB_PATH, Statics.DB_FILE_COMPRESSED)
                if (compressedFile.exists()) Utils.deleteFile(compressedFile)
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
                e.printStackTrace()
                updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_ERROR)
            }
        }
    }

    fun doDecompress(){
        BrotliUtils.deCompress(Statics.DB_PATH + Statics.DB_FILE_COMPRESSED, true)
        updateHandler.sendEmptyMessage(UPDATE_COMPLETED)
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
