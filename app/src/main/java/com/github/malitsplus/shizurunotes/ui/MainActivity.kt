package com.github.malitsplus.shizurunotes.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.UpdateManager
import com.github.malitsplus.shizurunotes.common.UserSettings
import com.github.malitsplus.shizurunotes.databinding.ActivityMainBinding
import com.github.malitsplus.shizurunotes.db.DBHelper
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), UpdateManager.IFragmentCallBack, OnRequestPermissionsResultCallback {

    companion object{
        const val REQUEST_EXTERNAL_STORAGE = 1
        val STORAGE_PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private lateinit var updateManager: UpdateManager
    private lateinit var sharedViewModelChara: SharedViewModelChara
    private lateinit var sharedViewModelClanBattle: SharedViewModelClanBattle

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(App.localeManager.setLocale(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        DBHelper.with(application)
        UserSettings.with(application)
        I18N.application = application

        updateManager = UpdateManager(this, findViewById(R.id.nav_host_fragment))
        updateManager.setIFragmentCallBack(this)

        sharedViewModelChara = ViewModelProvider(this).get(SharedViewModelChara::class.java)
        sharedViewModelClanBattle = ViewModelProvider(this).get(SharedViewModelClanBattle::class.java)

        updateManager.checkDatabaseVersion()



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

    override fun dbUpdateFinished() {
        sharedViewModelChara.loadData()
        sharedViewModelClanBattle.loadData()
//        val fragment =
//            supportFragmentManager.fragments[0].childFragmentManager.fragments[0].childFragmentManager.fragments[0]
//        if (fragment is CharaListFragment) {
//            fragment.updateList()
//        }
    }
}
