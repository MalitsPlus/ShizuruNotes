package com.github.malitsplus.shizurunotes.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
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
import com.github.malitsplus.shizurunotes.db.MasterQuest
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelQuest
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),
    UpdateManager.IActivityCallBack,
    SharedViewModelChara.MasterCharaCallBack
{
    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedClanBattle: SharedViewModelClanBattle
    private lateinit var sharedQuest: SharedViewModelQuest
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
    }

    private fun initSingletonClass() {
        DBHelper.with(application)
        UserSettings.with(application)
        UpdateManager.with(this).setIActivityCallBack(this)
        I18N.application = application
    }

    private fun setSharedViewModels() {
        sharedEquipment = ViewModelProvider(this)[SharedViewModelEquipment::class.java].apply {
            equipmentMap.observe(this@MainActivity, Observer {
                if (!it.isNullOrEmpty()) {
                    sharedChara.loadData(it)
                }
            })
        }
        sharedChara = ViewModelProvider(this)[SharedViewModelChara::class.java].apply {
            callBack = this@MainActivity
        }
        sharedClanBattle = ViewModelProvider(this)[SharedViewModelClanBattle::class.java]
        sharedQuest = ViewModelProvider(this)[SharedViewModelQuest::class.java]
    }

    private fun loadData() {
        sharedEquipment.loadData()
    }

    override fun charaLoadFinished() {
        UpdateManager.get().checkAppVersion(true)
    }

    override fun dbDownloadFinished() {
        thread(start = true) {
            for (i in 1..50) {
                if (sharedEquipment.loadingFlag.value == false
                    && sharedChara.loadingFlag.value == false
                    && sharedClanBattle.loadingFlag.value == false
                    && sharedQuest.loadingFlag.value == false) {
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
        clearData()
        loadData()
    }

    override fun showSnackBar(@StringRes messageRes: Int) {
        Snackbar.make(binding.activityFrame, messageRes, Snackbar.LENGTH_LONG).show()
    }

    private fun clearData() {
        sharedEquipment.equipmentMap.value?.clear()
        sharedChara.charaList.value?.clear()
        sharedClanBattle.periodList.value?.clear()
        sharedClanBattle.dungeonList.clear()
        sharedQuest.questList.value?.clear()
    }
}
