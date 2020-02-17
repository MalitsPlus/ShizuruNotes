package com.github.malitsplus.shizurunotes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.data.Property
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.db.DBHelper.Companion.get
import java.util.*
import java.util.function.Consumer
import kotlin.concurrent.thread

class SharedViewModelChara : ViewModel() {

    interface CharaListLoadFinished {
        fun doCharaListLoadFinished()
    }
    var charaLoadFinishedCallBack: CharaListLoadFinished? = null

    val loadingFlag = MutableLiveData<Boolean>(false)
    val charaList = MutableLiveData<MutableList<Chara>>()
    var selectedChara: Chara? = null

    /***
     * 从数据库读取所有角色数据。
     * 注意：此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    fun loadData() {
        charaList.value = mutableListOf()
        thread(start = true){
            loadingFlag.postValue(true)
            val innerCharaList = mutableListOf<Chara>()
            loadBasic(innerCharaList)
            innerCharaList.forEach {
                setCharaMaxData(it)
                setCharaRarity(it)
                setCharaStoryStatus(it)
                setCharaPromotionStatus(it)
                setCharaEquipments(it)
                setUniqueEquipment(it)
                setUnitSkillData(it)
                setUnitAttackPattern(it)
                it.setCharaProperty()
                for (skill in it.skills) {
                    skill.setActionDescriptions(it.maxCharaLevel, it.charaProperty)
                }
            }
            charaList.postValue(innerCharaList)
            loadingFlag.postValue(false)
            //charaLoadFinishedCallBack?.doCharaListLoadFinished()
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
        chara.maxCharaLevel = get().maxCharaLevel - 1
        chara.maxCharaRank = get().maxCharaRank
        chara.maxUniqueEquipmentLevel = get().maxUniqueEquipmentLevel
    }

    private fun setCharaRarity(chara: Chara) {
        get().getCharaRarity(chara.unitId)?.setCharaRarity(chara)
    }

    private fun setCharaStoryStatus(chara: Chara) {
        chara.storyProperty = Property().apply {
            get().getCharaStoryStatus(chara.charaId)?.forEach {
                this.plusEqual(it.getCharaStoryStatus(chara))
            }
        }
    }

    private fun setCharaPromotionStatus(chara: Chara) {
        get().getCharaPromotionStatus(chara.unitId)?.setPromotionStatus(chara)
    }

    private fun setCharaEquipments(chara: Chara) {
        get().getCharaPromotion(chara.unitId)?.setCharaEquipments(chara)
    }

    private fun setUniqueEquipment(chara: Chara) {
        get().getUniqueEquipment(chara.unitId)?.setCharaUniqueEquipment(chara)
    }

    private fun setUnitSkillData(chara: Chara) {
        get().getUnitSkillData(chara.unitId)?.setCharaSkillList(chara)
        chara.skills.forEach { skill: Skill ->
            //填充Skill中只有actionId和dependActionId（可能为0）的actionList
            get().getSkillData(skill.skillId)?.setSkillData(skill)
            skill.actions.forEach { action: Skill.Action ->
                //向actionList中填入其他具体值
                get().getSkillAction(action.actionId)?.setActionData(action)
            }
            skill.actions.forEach { action: Skill.Action ->
                //先检查dependAction
                if (action.dependActionId != 0) {
                    for (searched in skill.actions) {
                        if (searched.actionId == action.dependActionId) {
                            //需要先为其建立params
                            searched.buildParameter()
                            action.dependAction = searched
                            break
                        }
                    }
                }
                action.buildParameter()
            }
        }
    }

    private fun setUnitAttackPattern(chara: Chara) {
        get().getUnitAttackPattern(chara.unitId)?.forEach {
            chara.attackPatternList.add(
                it.attackPattern.setItems(
                    chara.skills,
                    chara.atkType
                )
            )
        }
    }

}