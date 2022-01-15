package com.github.nyanfantasia.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.common.Statics
import com.github.nyanfantasia.shizurunotes.data.*
import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import com.github.nyanfantasia.shizurunotes.db.MasterUniqueEquipment
import com.github.nyanfantasia.shizurunotes.utils.LogUtils
import kotlin.concurrent.thread

class SharedViewModelChara : ViewModel() {

    val loadingFlag = MutableLiveData(false)
    val charaList = MutableLiveData<MutableList<Chara>>()

    var maxCharaLevel: Int = 0
    var maxCharaRank: Int = 0
    var maxUniqueEquipmentLevel: Int = 0
    var maxEnemyLevel: Int = 0

    var selectedChara: Chara? = null
    var selectedMinion: MutableList<Minion>? = null
    var backFlag = false

    var rankComparisonFrom: Int = 0
    var rankComparisonTo: Int = 0

    /***
     * 从数据库读取所有角色数据。
     * 此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    fun loadData(equipmentMap: Map<Int, Equipment>) {
        var succeeded: Boolean
        if (charaList.value.isNullOrEmpty()) {
            loadingFlag.postValue(true)
            thread(start = true) {
                val innerCharaList = mutableListOf<Chara>()
                try {
                    loadBasic(innerCharaList)
                    innerCharaList.forEach {
                        setCharaMaxData(it)
                        setCharaRarity(it)
                        setCharaStoryStatus(it)
                        setCharaPromotionStatus(it)
                        setCharaPromotionBonus(it)
                        setCharaEquipments(it, equipmentMap)
                        setUniqueEquipment(it)
                        setUnitSkillData(it)
                        setUnitAttackPattern(it)
                        it.setCharaProperty()
                    }
                    succeeded = true
                } catch (ex: Exception) {
                    succeeded = false
                    innerCharaList.clear()
                    LogUtils.file(LogUtils.E, "loadCharaData", ex.message)
                }
                charaList.postValue(innerCharaList)
                loadingFlag.postValue(false)
                callBack?.charaLoadFinished(succeeded)
            }
        }
    }

    private fun loadBasic(innerCharaList: MutableList<Chara>) {
        get().getCharaBase()?.forEach {
            val chara = Chara()
            it.setCharaBasic(chara)
            innerCharaList.add(chara)
        }
    }

    private fun setCharaMaxData(chara: Chara) {
        this.maxCharaLevel = get().maxCharaLevel - 1
        chara.maxCharaLevel = this.maxCharaLevel
        this.maxCharaRank = get().maxCharaRank
        chara.maxCharaRank = this.maxCharaRank
        this.maxUniqueEquipmentLevel = get().maxUniqueEquipmentLevel
        chara.maxUniqueEquipmentLevel = this.maxUniqueEquipmentLevel

        maxEnemyLevel = get().maxEnemyLevel
    }

    private fun setCharaRarity(chara: Chara) {
        get().getUnitRarityList(chara.unitId)?.forEach {
            if (it.rarity == 6) {
                chara.maxRarity = 6
                chara.rarity = 6
                chara.iconUrl = Statics.ICON_URL.format(chara.prefabId + 60)
                chara.imageUrl = Statics.IMAGE_URL.format(chara.prefabId + 60)
            }
            chara.rarityProperty[it.rarity] = it.property
            chara.rarityPropertyGrowth[it.rarity] = it.propertyGrowth
        }
    }

    private fun setCharaStoryStatus(chara: Chara) {
        get().getCharaStoryStatus(chara.charaId)?.forEach {
            chara.storyStatusList.add(it.getCharaStoryStatus(chara))
        }
    }

    private fun setCharaPromotionStatus(chara: Chara) {
        val promotionStatus = mutableMapOf<Int, Property>()
        get().getCharaPromotionStatus(chara.unitId)?.forEach {
            promotionStatus[it.promotion_level] = it.promotionStatus
        }
        chara.promotionStatus = promotionStatus
    }

    private fun setCharaPromotionBonus(chara: Chara) {
        val promotionBonus = mutableMapOf<Int, Property>()
        get().getCharaPromotionBonus(chara.unitId)?.forEach {
            promotionBonus[it.promotion_level] = it.promotionBonus
        }
        chara.promotionBonus = promotionBonus
    }

    private fun setCharaEquipments(chara: Chara, equipmentMap: Map<Int, Equipment>) {
        val rankEquipments = mutableMapOf<Int, List<Equipment>>()
        get().getCharaPromotion(chara.unitId)?.forEach { slots ->
            val equipmentList = mutableListOf<Equipment>()
            slots.charaSlots.forEach { id ->
                equipmentMap[id]?.let {
                    equipmentList.add(it)
                }
            }
            rankEquipments[slots.promotion_level] = equipmentList
        }
        chara.rankEquipments = rankEquipments
    }

    private fun setUniqueEquipment(chara: Chara) {
        chara.uniqueEquipment = MasterUniqueEquipment().getCharaUniqueEquipment(chara)
    }

    private fun setUnitSkillData(chara: Chara) {
        if (chara.unitConversionId == 0) {
            get().getUnitSkillData(chara.unitId)?.setCharaSkillList(chara)
        } else {
            get().getUnitSkillData(chara.unitConversionId)?.setCharaSkillList(chara)
        }
    }

    private fun setUnitAttackPattern(chara: Chara) {
        if (chara.unitConversionId == 0) {
            get().getUnitAttackPattern(chara.unitId)?.forEach {
                chara.attackPatternList.add(
                    it.attackPattern.setItems(
                        chara.skills,
                        chara.atkType
                    )
                )
            }
        } else {
            get().getUnitAttackPattern(chara.unitConversionId)?.forEach {
                chara.attackPatternList.add(
                    it.attackPattern.setItems(
                        chara.skills,
                        chara.atkType
                    )
                )
            }
        }
    }

    fun mSetSelectedChara(chara: Chara?){
        chara?.apply {
            skills.forEach {
                it.setActionDescriptions(chara.maxCharaLevel, chara.charaProperty)
            }
        }
        this.selectedChara = chara
    }

    var callBack: MasterCharaCallBack? = null

    interface MasterCharaCallBack {
        fun charaLoadFinished(succeeded: Boolean)
    }
}