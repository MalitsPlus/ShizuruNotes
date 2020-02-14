package com.github.malitsplus.shizurunotes.ui

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod
import com.github.malitsplus.shizurunotes.db.DBHelper

class SharedViewModelClanBattle : ViewModel() {

    val periodList = ArrayList<ClanBattlePeriod>()
    var selectedPeriod: ClanBattlePeriod? = null

    init {
        loadData()
    }

    fun loadData(){
        periodList.clear()
        DBHelper.get().clanBattlePeriod.forEach {
            periodList.add(it.transToClanBattlePeriod())
        }
    }
}
