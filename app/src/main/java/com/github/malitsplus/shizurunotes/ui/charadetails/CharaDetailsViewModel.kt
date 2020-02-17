package com.github.malitsplus.shizurunotes.ui.charadetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara

class CharaDetailsViewModel(private val sharedViewModelChara: SharedViewModelChara) : ViewModel() {

    val mutableChara = MutableLiveData<Chara>()

    fun setChara(chara: Chara?) {
        mutableChara.value = chara
    }

    fun getMutableChara(): LiveData<Chara> {
        return mutableChara
    }

    init {
        setChara(sharedViewModelChara.selectedChara)
    }
}