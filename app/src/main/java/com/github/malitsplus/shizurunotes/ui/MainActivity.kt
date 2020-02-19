package com.github.malitsplus.shizurunotes.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.UpdateManager
import com.github.malitsplus.shizurunotes.common.UserSettings
import com.github.malitsplus.shizurunotes.databinding.ActivityMainBinding
import com.github.malitsplus.shizurunotes.db.DBHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),
    UpdateManager.IActivityCallBack
{
    private lateinit var sharedViewModelChara: SharedViewModelChara
    private lateinit var sharedViewModelClanBattle: SharedViewModelClanBattle
    private lateinit var binding: ActivityMainBinding
    private var isDatabaseBusy: Boolean = false

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(App.localeManager.setLocale(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        DBHelper.with(application)
        UserSettings.with(application)
        I18N.application = application

        UpdateManager.with(this).setIActivityCallBack(this)


        sharedViewModelChara = ViewModelProvider(this)
            .get(SharedViewModelChara::class.java).also {
                it.loadingFlag.observe(this, Observer {
                    synchronized(isDatabaseBusy) {
                        isDatabaseBusy = it || sharedViewModelClanBattle.loadingFlag.value?: false
                    }
                })
            }

        sharedViewModelClanBattle = ViewModelProvider(this)
            .get(SharedViewModelClanBattle::class.java).also {
                it.loadingFlag.observe(this, Observer {
                    synchronized(isDatabaseBusy) {
                        isDatabaseBusy = sharedViewModelChara.loadingFlag.value?: false || it
                    }
                })
            }

//        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainActivityViewModel::class.java)


//        sharedViewModelChara.loadingFlag.observe(this, Observer {
//            synchronized(isDatabaseBusy){
//                isDatabaseBusy = it || sharedViewModelClanBattle.loadingFlag.value!!
//            }
//        })
//        sharedViewModelClanBattle.loadingFlag.observe(this, Observer {
//            synchronized(isDatabaseBusy){
//                isDatabaseBusy = sharedViewModelChara.loadingFlag.value!! ||  it
//            }
//        })

        UpdateManager.get().checkAppVersion()

    }

    override fun dbDownloadFinished() {
        thread(start = true){
            for (i in 1..50){
                if (!isDatabaseBusy){
                    synchronized(DBHelper::class.java){
                        UpdateManager.get().doDecompress()
                    }
                    break
                }
                Thread.sleep(100)
                if (i == 50) UpdateManager.get().updateFailed()
            }
        }
    }

    override fun dbUpdateFinished() {
        sharedViewModelChara.loadData()
        sharedViewModelClanBattle.loadData()
    }

    override fun showSnackBar(@StringRes messageRes: Int) {
        Snackbar.make(binding.activityFrame, messageRes, Snackbar.LENGTH_LONG).show()
    }

    /*
    companion object{
        const val REQUEST_EXTERNAL_STORAGE = 1
        val STORAGE_PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private fun checkStoragePermission(): Boolean {
        return if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                STORAGE_PERMISSION,
                REQUEST_EXTERNAL_STORAGE
            )
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    updateManager.checkDatabaseVersion()
                } else {
                    MaterialDialog(this, MaterialDialog.DEFAULT_BEHAVIOR)
                        .title(R.string.permission_request_explanation_title, null)
                        .message(R.string.permission_request_explanation_text, null, null)
                        .cancelOnTouchOutside(false)
                        .positiveButton(R.string.text_ok, null) {
                            ActivityCompat.requestPermissions(
                                this,
                                STORAGE_PERMISSION,
                                REQUEST_EXTERNAL_STORAGE
                            )
                        }
                        .show()
                }
                return
            }
        }
    }
    */
}
