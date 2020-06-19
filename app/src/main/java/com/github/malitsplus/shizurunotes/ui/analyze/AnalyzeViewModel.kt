package com.github.malitsplus.shizurunotes.ui.analyze

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Property
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.utils.UnitUtils
import com.github.malitsplus.shizurunotes.utils.Utils
import kotlin.math.ceil

class AnalyzeViewModel(
    sharedChara: SharedViewModelChara
) : ViewModel(), OnAnalyzeActionListener {

    val chara = sharedChara.selectedChara
    var property4Analyze = MutableLiveData<Property>()
    var rarity = 1
    var rank = 1
    var enemyLevel = 1
    val rankList = mutableListOf<Int>()

    init {
        chara?.let {
            property4Analyze.value = Property().plusEqual(it.charaProperty)
            rarity = it.rarity
            rank = it.maxCharaRank
            enemyLevel = it.maxCharaLevel
            for (i in it.maxCharaRank downTo 2) {
                rankList.add(i)
            }
        }
    }

    fun getCriticalRate(): String {
        val criticalRate = chara?.let {
            val critical = if (it.atkType == 1) {
                property4Analyze.value?.getPhysicalCritical()
            } else {
                property4Analyze.value?.getMagicCritical()
            } ?: 0
            UnitUtils.getCriticalRate(critical, it.maxCharaLevel, enemyLevel)
        } ?: 0.0
        return I18N.getString(R.string.percent_modifier_s, Utils.getTwoDecimalPlaces(criticalRate))
    }

    // 触发Property变化
    fun updateProperty(sRarity: Int = rarity, sRank: Int = rank) {
        property4Analyze.value = Property().plusEqual(chara?.getSpecificCharaProperty(sRarity, sRank))
    }

    // Rank下拉框监听器
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        rank = rankList[position]
        updateProperty()
    }

    override fun onItemClicked(position: Int) {
    }
}

interface OnAnalyzeActionListener : OnItemActionListener,
    AdapterView.OnItemClickListener {
}