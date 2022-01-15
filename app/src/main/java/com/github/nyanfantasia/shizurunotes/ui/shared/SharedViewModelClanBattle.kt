package com.github.nyanfantasia.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.data.Enemy
import com.github.nyanfantasia.shizurunotes.data.ClanBattlePeriod
import com.github.nyanfantasia.shizurunotes.data.Dungeon
import com.github.nyanfantasia.shizurunotes.data.SpEvent
import com.github.nyanfantasia.shizurunotes.db.DBHelper
import kotlin.concurrent.thread

class SharedViewModelClanBattle : ViewModel() {

    val periodList = MutableLiveData<MutableList<ClanBattlePeriod>>()
    val loadingFlag = MutableLiveData(false)
    var selectedPeriod: ClanBattlePeriod? = null
    var selectedEnemyList: List<Enemy>? = null
    var selectedMinion: MutableList<Enemy>? = null

    var dungeonList = mutableListOf<Dungeon>()
    var spEventList = mutableListOf<SpEvent>()

    /***
     * 从数据库读取所有会战数据。
     * 此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    fun loadData(){
        if (periodList.value.isNullOrEmpty()) {
            loadingFlag.value = true
            thread(start = true){
                val innerPeriodList = mutableListOf<ClanBattlePeriod>()
                DBHelper.get().getClanBattlePeriod()?.forEach {
                    innerPeriodList.add(it.transToClanBattlePeriod())
                }
                periodList.postValue(innerPeriodList)
                loadingFlag.postValue(false)
            }
        }
    }

    fun loadDungeon(){
        if (dungeonList.isNullOrEmpty()){
            thread(start = true){
                loadingFlag.postValue(true)
                DBHelper.get().getDungeons()?.forEach {
                    dungeonList.add(it.dungeon!!)
                }
                loadingFlag.postValue(false)
            }
        }
    }

    fun loadSpEvent() {
        if (spEventList.isNullOrEmpty()){
            thread(start = true){
                loadingFlag.postValue(true)
                DBHelper.get().getSpEvents()?.forEach {
                    spEventList.add(it.spEvent!!)
                }
                loadingFlag.postValue(false)
            }
        }
    }

    fun mSetSelectedBoss(enemy: Enemy){
        if (enemy.isMultiTarget) {
            enemy.skills.forEach {
                //多目标Boss技能值暂时仅供参考，非准确值
                it.setActionDescriptions(it.enemySkillLevel, enemy.children[0].property)
            }
        } else {
            enemy.skills.forEach {
                it.setActionDescriptions(it.enemySkillLevel, enemy.property)
            }
        }
        this.selectedEnemyList = listOf(enemy)
    }

    fun mSetSelectedBoss(enemyList: List<Enemy>){
        enemyList.forEach { enemy ->
            if (enemy.isMultiTarget) {
                enemy.skills.forEach {
                    //多目标Boss技能值暂时仅供参考，非准确值
                    it.setActionDescriptions(it.enemySkillLevel, enemy.children[0].property)
                }
            } else {
                enemy.skills.forEach {
                    it.setActionDescriptions(it.enemySkillLevel, enemy.property)
                }
            }
        }
        this.selectedEnemyList = enemyList
    }
}
