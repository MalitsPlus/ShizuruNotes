package com.github.malitsplus.shizurunotes.ui.clanbattle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod

class ClanBattleViewModel : ViewModel() {

    val periodList = MutableLiveData<List<ClanBattlePeriod>>()


}