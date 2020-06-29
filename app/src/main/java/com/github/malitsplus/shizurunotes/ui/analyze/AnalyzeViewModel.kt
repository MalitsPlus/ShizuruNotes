package com.github.malitsplus.shizurunotes.ui.analyze

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Property
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.utils.UnitUtils
import com.github.malitsplus.shizurunotes.utils.Utils

class AnalyzeViewModel(
    sharedChara: SharedViewModelChara
) : ViewModel(), OnAnalyzeActionListener {

    val chara = sharedChara.selectedChara
    var property4Analyze = MutableLiveData<Property>()
    var rarity = 1
    var rank = 1
    var enemyLevel = 1
    var enemyAccuracy = 50
    var enemyDodge = 0
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

    val enemyLevelText: String
        get() {
            return I18N.getString(R.string.enemy_level_d, enemyLevel)
        }

    val enemyAccuracyText: String
        get() {
            return I18N.getString(R.string.enemy_accuracy_d, enemyAccuracy)
        }

    val enemyDodgeText: String
        get() {
            return I18N.getString(R.string.enemy_dodge_d, enemyDodge)
        }

    val criticalRateText: String
        get() {
            val criticalRate = chara?.let {
                val critical = if (it.atkType == 1) {
                    property4Analyze.value?.getPhysicalCritical()
                } else {
                    property4Analyze.value?.getMagicCritical()
                } ?: 0
                UnitUtils.getCriticalRate(critical, it.maxCharaLevel, enemyLevel) * 100.0
            } ?: 0.0
            return I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(criticalRate))
        }

    val hpAbsorbRateText: String
        get() {
            val hpAbsorbRate = property4Analyze.value?.let {
                UnitUtils.getHpAbsorbRate(it.getLifeSteal(), enemyLevel) * 100.0
            } ?: 0.0
            return I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(hpAbsorbRate))
        }

    val physicalDamageCutText: String
        get() {
            return property4Analyze.value?.let {
                I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(it.physicalDamageCut * 100.0))
            } ?: "0%"
        }

    val magicalDamageCutText: String
        get() {
            return property4Analyze.value?.let {
                I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(it.magicalDamageCut * 100.0))
            } ?: "0%"
        }

    val hpRecoveryRateText: String
        get() {
            return property4Analyze.value?.let {
                I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(it.hpRecovery * 100.0))
            } ?: "100%"
        }

    val tpUpRateText: String
        get() {
            return property4Analyze.value?.let {
                I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(it.tpUpRate * 100.0))
            } ?: "100%"
        }

    val accuracyRateText: String
        get() {
            return if (chara?.atkType == 2) {
                "100%"
            } else {
                property4Analyze.value?.let {
                    val rate = UnitUtils.getAccuracyRate(it.getAccuracy(), enemyDodge) * 100.0
                    I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(rate))
                } ?: "100%"
            }
        }

    val dodgeRateText: String
        get() {
            return property4Analyze.value?.let {
                val rate = UnitUtils.getDodgeRate(enemyAccuracy, it.getDodge()) * 100.0
                I18N.getString(R.string.percent_modifier_s, Utils.getOneDecimalPlaces(rate))
            } ?: "0%"
        }

    val tpPerActionText: String
        get() {
            return property4Analyze.value?.let {
                UnitUtils.getActualTpRecoveryValue(UnitUtils.TpBonusType.Action, it.tpUpRate).toString()
            } ?: UnitUtils.TpBonusType.Action.getBaseValue().toString()
        }

    val tpRemainText: String
        get() {
            return property4Analyze.value?.tpRemain?.toString() ?: "0"
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