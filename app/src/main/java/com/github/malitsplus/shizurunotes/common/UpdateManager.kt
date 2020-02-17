package com.github.malitsplus.shizurunotes.common

import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.github.malitsplus.shizurunotes.R
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class UpdateManager(
    private val mContext: Context,
    private val mView: View?)
{
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

            //数据库检查更新完成后调用
            override fun checkUpdateCompleted(hasUpdate: Boolean, updateInfo: CharSequence?) {
                if (hasUpdate) {
                    MaterialDialog(mContext, MaterialDialog.DEFAULT_BEHAVIOR)
                        .title(R.string.db_update_dialog_title, null)
                        .message(R.string.db_update_dialog_text, null, null)
                        .cancelOnTouchOutside(false)
                        .positiveButton(R.string.db_update_dialog_confirm, null) {
                            downloadDB()
                        }
                        .negativeButton(R.string.db_update_dialog_cancel, null, null)
                        .show()
                }
            }

            //数据库下载进度条变化
            override fun downloadProgressChanged(progress: Int, maxLength: Int) {
                if (progressDialog?.isShowing == true) {
                    progressDialog?.message(
                        null,
                        "%d / %d KB download.".format((progress / 1024), maxLength / 1024),
                        null
                    )
                }
            }

            //数据库下载取消，备用
            override fun downloadCanceled() {}

            //数据库下载完成
            override fun downloadCompleted(success: Boolean, errorMsg: CharSequence?) {
                progressDialog?.message(R.string.db_update_download_finished_text, null, null)
            }

            //数据库更新完成
            override fun updateCompleted() {
                iFragmentCallBack?.dbUpdateFinished()
                UserSettings.get().preference.edit().putInt("dbVersion", serverVersion).apply()
                progressDialog?.cancel()
                if (mView != null)
                    Snackbar.make(mView, R.string.db_update_finished_text, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun checkDatabaseVersion() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Statics.LAST_VERSION_URL)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (mView != null)
                    Snackbar.make(mView, R.string.db_update_check_failed, Snackbar.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val lastVersionJson = response.body?.string()
                try {
                    if (lastVersionJson.isNullOrEmpty())
                        throw Exception("No response from server.")
                    val obj = JSONObject(lastVersionJson)
                    serverVersion = obj.getInt("TruthVersion")
                    if (serverVersion != UserSettings.get().preference.getInt("dbVersion", 0))
                        hasNewVersion = true
                    updateHandler.sendEmptyMessage(UPDATE_CHECK_COMPLETED)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    if (mView != null)
                        Snackbar.make(mView, R.string.db_update_check_failed, Snackbar.LENGTH_LONG).show()
                }
            }
        })
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
                BrotliUtils.deCompress(Statics.DB_PATH + Statics.DB_FILE_COMPRESSED, true)
                updateHandler.sendEmptyMessage(UPDATE_COMPLETED)
            } catch (e: Exception) {
                e.printStackTrace()
                progressDialog?.cancel()
                if (mView != null)
                    Snackbar.make(mView, R.string.db_update_check_failed, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private val updateHandler = Handler(Handler.Callback { msg: Message ->
        when (msg.what) {
            UPDATE_CHECK_COMPLETED ->
                callBack.checkUpdateCompleted(hasNewVersion, versionInfo)
            UPDATE_DOWNLOADING ->
                callBack.downloadProgressChanged(progress, maxLength)
            UPDATE_DOWNLOAD_ERROR -> {
            }
            UPDATE_DOWNLOAD_COMPLETED ->
                callBack.downloadCompleted(true, "")
            UPDATE_COMPLETED ->
                callBack.updateCompleted()
            UPDATE_DOWNLOAD_CANCELED -> {
            }
            else -> {
            }
        }
        true
    })

    interface UpdateCallBack {
        fun checkUpdateCompleted(hasUpdate: Boolean, updateInfo: CharSequence?)
        fun downloadProgressChanged(progress: Int, maxLength: Int)
        fun downloadCanceled()
        fun downloadCompleted(success: Boolean, errorMsg: CharSequence?)
        fun updateCompleted()
    }

    interface IFragmentCallBack {
        fun dbUpdateFinished()
    }

    private var iFragmentCallBack: IFragmentCallBack? = null
    fun setIFragmentCallBack(callBack: IFragmentCallBack?) {
        iFragmentCallBack = callBack
    }

    companion object {
        private const val UPDATE_CHECK_COMPLETED = 1
        private const val UPDATE_DOWNLOADING = 2
        private const val UPDATE_DOWNLOAD_ERROR = 3
        private const val UPDATE_DOWNLOAD_COMPLETED = 4
        private const val UPDATE_COMPLETED = 5
        private const val UPDATE_DOWNLOAD_CANCELED = 6
    }
}