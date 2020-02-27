package com.github.malitsplus.shizurunotes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.ClanBattleBoss
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod
import com.github.malitsplus.shizurunotes.data.Dungeon
import com.github.malitsplus.shizurunotes.db.DBHelper
import kotlin.concurrent.thread

class SharedViewModelClanBattle : ViewModel() {

    val periodList = MutableLiveData<MutableList<ClanBattlePeriod>>()
    val loadingFlag = MutableLiveData<Boolean>(false)
    var selectedPeriod: ClanBattlePeriod? = null
    var selectedBoss: ClanBattleBoss? = null
    var selectedMinion: MutableList<ClanBattleBoss>? = null

    var dungeonList = mutableListOf<Dungeon>()

    /***
     * 从数据库读取所有会战数据。
     * 此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    fun loadData(){
        thread(start = true){
            periodList.postValue(mutableListOf())
            loadingFlag.postValue(true)
            val innerPeriodList = mutableListOf<ClanBattlePeriod>()
            DBHelper.get().getClanBattlePeriod()?.forEach {
                innerPeriodList.add(it.transToClanBattlePeriod())
            }
            periodList.postValue(innerPeriodList)
            loadingFlag.postValue(false)
        }
    }

    fun loadDungeon(){
        if (dungeonList.isEmpty()){
            thread(start = true){
                loadingFlag.postValue(true)
                DBHelper.get().getDungeons()?.forEach {
                    dungeonList.add(it.dungeon)
                }
                loadingFlag.postValue(false)
            }
        }
    }

//    fun mSetSelectedPeriod(period: ClanBattlePeriod?){
//        period?.phaseList?.forEach { p ->
//            p.bossList.forEach { b ->
//                b.skills.forEach { s ->
//                    s.setActionDescriptions(s.enemySkillLevel, b.property)
//                }
//            }
//        }
//        this.selectedPeriod = period
//    }

    fun mSetSelectedBoss(clanBattleBoss: ClanBattleBoss){
        clanBattleBoss.skills.forEach {
            it.setActionDescriptions(it.enemySkillLevel, clanBattleBoss.property)
        }
        this.selectedBoss = clanBattleBoss
    }

}
