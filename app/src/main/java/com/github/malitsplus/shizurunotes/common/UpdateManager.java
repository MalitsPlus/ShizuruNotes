package com.github.malitsplus.shizurunotes.common;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;


import com.afollestad.materialdialogs.MaterialDialog;
import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.db.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateManager {
    private Context mContext;
    private View mView;
    private String lastVersionJson;
    private int serverVersion;
    private int progress;
    private boolean hasNewVersion;
    private boolean canceled;
    private UpdateCallBack callBack;
    private String versionInfo;
    private MaterialDialog progressDialog;
    private int maxLength;


    private static final int UPDATE_CHECK_COMPLETED = 1;
    private static final int UPDATE_DOWNLOADING = 2;
    private static final int UPDATE_DOWNLOAD_ERROR = 3;
    private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
    private static final int UPDATE_COMPLETED = 5;
    private static final int UPDATE_DOWNLOAD_CANCELED = 6;
    private static final String DB_FILE_SAVE_FOLDER = "mnt/sdcard/";
    private static final String DB_FILE_INNER_FOLDER = "data/data/com.github.malitsplus.shizurunotes/databases/";
    private static final String DB_FILE_NAME_COMPRESSED = "redive_jp.db.br";
    private static final String DB_FILE_NAME = "redive_jp.db";

    public UpdateManager(Context context,View view){
        mContext = context;
        mView = view;
        callBack = defaultCallBack;
    }

    public void checkDatabaseVersion(){
        hasNewVersion = false;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Statics.LAST_VERSION_URL)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                lastVersionJson = response.body().string();
                if(!TextUtils.isEmpty(lastVersionJson)){
                    try {
                        JSONObject obj = new JSONObject(lastVersionJson);
                        serverVersion = obj.getInt("TruthVersion");
                        if(serverVersion != DBHelper.DB_VERSION)
                            hasNewVersion = true;
                        updateHandler.sendEmptyMessage(UPDATE_CHECK_COMPLETED);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void downloadDB(){
        progressDialog = new MaterialDialog(mContext, MaterialDialog.getDEFAULT_BEHAVIOR());
        progressDialog.title(R.string.db_update_progress_title, null)
                .message(R.string.db_update_progress_text, null, null)
                .cancelable(false)
                .show();

        new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(Statics.DB_FILE_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    maxLength = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    File compressedFile = new File(DB_FILE_SAVE_FOLDER, DB_FILE_NAME_COMPRESSED);
                    if(compressedFile.exists())
                        compressedFile.delete();
                    FileOutputStream fos = new FileOutputStream(compressedFile);

                    int totalDownload = 0;
                    byte buf[] = new byte[1024*1024];
                    int numRead = 0;

                    while (true){
                        numRead = is.read(buf);
                        totalDownload += numRead;
                        progress = totalDownload;
                        updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING));
                        if(numRead <= 0){
                            updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
                            break;
                        }
                        fos.write(buf, 0, numRead);
                    }
                    fos.close();
                    is.close();
                    BrotliUtils.deCompress(DB_FILE_SAVE_FOLDER + DB_FILE_NAME_COMPRESSED, true);
                    Utils.copyFile(DB_FILE_NAME, DB_FILE_SAVE_FOLDER, DB_FILE_INNER_FOLDER, true);

                    updateHandler.sendEmptyMessage(UPDATE_COMPLETED);
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private UpdateCallBack defaultCallBack = new UpdateCallBack() {
        @Override   //检查下载完成
        public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {
            if(hasUpdate) {
                new MaterialDialog(mContext, MaterialDialog.getDEFAULT_BEHAVIOR())
                        .title(R.string.db_update_dialog_title, null)
                        .message(R.string.db_update_dialog_text, null, null)
                        .cancelOnTouchOutside(false)
                        .positiveButton(R.string.db_update_dialog_confirm, null, materialDialog -> {
                            downloadDB();
                            return null;
                        })
                        .negativeButton(R.string.db_update_dialog_cancel, null, null)
                        .show();
            }
        }

        @Override   //下载进度条变化
        public void downloadProgressChanged(int progress, int maxLength) {
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.message(null, progress / 1024 + " / " + maxLength / 1024 + " KB downloaded.", null);
            }
        }

        @Override
        public void downloadCanceled() {

        }

        @Override   //下载完成
        public void downloadCompleted(Boolean success, CharSequence errorMsg) {
            progressDialog.message(R.string.db_update_download_finished_text, null, null);
        }

        @Override   //数据库更新完成
        public void updateCompleted(){
            progressDialog.cancel();
            Snackbar.make(mView, R.string.db_update_finished_text, Snackbar.LENGTH_LONG).show();
            if(iFragmentCallBack != null)
                iFragmentCallBack.dbUpdateFinished();
        }
    };



    private Handler updateHandler = new Handler(msg -> {
        switch (msg.what) {
            case UPDATE_CHECK_COMPLETED:
                callBack.checkUpdateCompleted(hasNewVersion, versionInfo);
                break;
            case UPDATE_DOWNLOADING:
                callBack.downloadProgressChanged(progress, maxLength);
                break;
            case UPDATE_DOWNLOAD_ERROR:
                break;
            case UPDATE_DOWNLOAD_COMPLETED:
                callBack.downloadCompleted(true, "");
                break;
            case UPDATE_COMPLETED:
                callBack.updateCompleted();
                break;
            case UPDATE_DOWNLOAD_CANCELED:
                break;
            default:
                break;
        }
        return true;
    });

    public interface UpdateCallBack {
        void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo);
        void downloadProgressChanged(int progress, int maxLength);
        void downloadCanceled();
        void downloadCompleted(Boolean success, CharSequence errorMsg);
        void updateCompleted();
    }

    private IFragmentCallBack iFragmentCallBack;
    public void setIFragmentCallBack(IFragmentCallBack callBack){
        this.iFragmentCallBack = callBack;
    }

    public interface IFragmentCallBack{
        void dbUpdateFinished();
    }

}
