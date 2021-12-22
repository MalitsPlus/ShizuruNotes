package com.github.nyanfantasia.shizurunotes.ui.charadetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Chara
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelChara

class CharaDetailsViewModel(private val sharedViewModelChara: SharedViewModelChara) : ViewModel() {

    val mutableChara = MutableLiveData<Chara>()

    fun changeRank(rankString: String){
        val rank = rankString.toInt()
        val chara = mutableChara.value?.shallowCopy()
        chara?.apply {
            setCharaProperty(rank = rank)
            skills.forEach {
                it.setActionDescriptions(maxCharaLevel, charaProperty)
            }
        }?.also {
            mutableChara.value = it
        }
    }

    fun setChara(chara: Chara?) {
        chara?.let {
            mutableChara.value = it
        }
    }

    fun getChara(): Chara?{
        return mutableChara.value
    }

    val levelUndRankString: String
        get() {
            return mutableChara.value?.let {
                 I18N.getString(R.string.level_d1_rank_d2, it.maxCharaLevel, it.maxCharaRank)
            } ?: ""
        }

    fun getAttackPatternList(): List<Any> {
        val list = mutableListOf<Any>()
        mutableChara.value?.let { chara ->
            for (i in 1..chara.attackPatternList.size) {
                list.add(i)
                chara.attackPatternList[i - 1].items.forEach {
                    list.add(it)
                }
            }
        }
        return list
    }

    init {
        setChara(sharedViewModelChara.selectedChara)
    }
}