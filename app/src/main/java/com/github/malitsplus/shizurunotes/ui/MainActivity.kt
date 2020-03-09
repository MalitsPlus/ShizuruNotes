package com.github.malitsplus.shizurunotes.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.UpdateManager
import com.github.malitsplus.shizurunotes.common.UserSettings
import com.github.malitsplus.shizurunotes.databinding.ActivityMainBinding
import com.github.malitsplus.shizurunotes.db.DBHelper
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),
    UpdateManager.IActivityCallBack,
    SharedViewModelEquipment.MasterEquipmentCallBack
{
    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedClanBattle: SharedViewModelClanBattle
    private lateinit var binding: ActivityMainBinding

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(App.localeManager.setLocale(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initSingletonClass()
        setSharedViewModels()
        loadData()

        UpdateManager.get().checkAppVersion(true)
    }

    private fun initSingletonClass() {
        DBHelper.with(application)
        UserSettings.with(application)
        UpdateManager.with(this).setIActivityCallBack(this)
        I18N.application = application
    }

    private fun setSharedViewModels() {
        sharedEquipment = ViewModelProvider(this)[SharedViewModelEquipment::class.java].apply {
            callBack = this@MainActivity
        }
        sharedChara = ViewModelProvider(this)[SharedViewModelChara::class.java]
        sharedClanBattle = ViewModelProvider(this)[SharedViewModelClanBattle::class.java]
    }

    private fun loadData() {
        sharedEquipment.loadData()
    }

    override fun equipmentLoadFinished() {
        sharedChara.loadData(sharedEquipment.equipmentMap)
    }

    override fun dbDownloadFinished() {
        thread(start = true){
            for (i in 1..50){
                if (sharedEquipment.loadingFlag.value == false
                    && sharedChara.loadingFlag.value == false
                    && sharedClanBattle.loadingFlag.value == false) {
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
        loadData()
    }

    override fun showSnackBar(@StringRes messageRes: Int) {
        Snackbar.make(binding.activityFrame, messageRes, Snackbar.LENGTH_LONG).show()
    }


//    companion object{
//        const val REQUEST_EXTERNAL_STORAGE = 1
//        val STORAGE_PERMISSION = arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//    }
//
//    private fun checkStoragePermission(): Boolean {
//        return if (
//            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                STORAGE_PERMISSION,
//                REQUEST_EXTERNAL_STORAGE
//            )
//            false
//        } else {
//            true
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQUEST_EXTERNAL_STORAGE -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty()
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    updateManager.checkDatabaseVersion()
//                } else {
//                    MaterialDialog(this, MaterialDialog.DEFAULT_BEHAVIOR)
//                        .title(R.string.permission_request_explanation_title, null)
//                        .message(R.string.permission_request_explanation_text, null, null)
//                        .cancelOnTouchOutside(false)
//                        .positiveButton(R.string.text_ok, null) {
//                            ActivityCompat.requestPermissions(
//                                this,
//                                STORAGE_PERMISSION,
//                                REQUEST_EXTERNAL_STORAGE
//                            )
//                        }
//                        .show()
//                }
//                return
//            }
//        }
//    }

}
