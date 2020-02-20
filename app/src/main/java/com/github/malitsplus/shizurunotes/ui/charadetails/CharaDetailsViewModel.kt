package com.github.malitsplus.shizurunotes.ui.charadetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara

class CharaDetailsViewModel(private val sharedViewModelChara: SharedViewModelChara) : ViewModel() {

    val mutableChara = MutableLiveData<Chara>()

    fun changeRank(rankString: String){
        val rank = rankString.toInt()
        mutableChara.value = mutableChara.value?.apply {
            setCharaProperty(rank = rank)
            skills.forEach {
                it.setActionDescriptions(maxCharaLevel, charaProperty)
            }
        }
    }

    fun setChara(chara: Chara?) {
        mutableChara.value = chara
    }

    fun getChara(): Chara?{
        return mutableChara.value
    }

    init {
        setChara(sharedViewModelChara.selectedChara)
    }
}