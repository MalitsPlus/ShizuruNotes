package com.github.malitsplus.shizurunotes.ui

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod
import com.github.malitsplus.shizurunotes.db.DBHelper
import com.github.malitsplus.shizurunotes.db.RawClanBattlePeriod

class SharedViewModelClanBattle : ViewModel() {

    val periodList = ArrayList<ClanBattlePeriod>()
    var selectedPeriod: ClanBattlePeriod? = null

    init {
        loadData()
    }

    fun loadData(){
        periodList.clear()
        DBHelper.get().clanBattlePeriod?.forEach {
            periodList.add(it.transToClanBattlePeriod())
        }

        periodList.forEach { period ->
            period.phaseList.forEach { p ->
                p.bossList.forEach { b ->
                    b.skills.forEach { s ->
                        DBHelper.get().getSkillData(s.skillId).setSkillData(s)
                        //向actionList中填入其他具体值
                        s.actions.forEach {
                            DBHelper.get().getSkillAction(it.actionId).setActionData(it)
                        }
                        s.actions.forEach {
                            if (it.dependActionId != 0) {
                                for (searched in s.actions) {
                                    if (searched.actionId == it.dependActionId) { //需要先建立params
                                        searched.buildParameter()
                                        it.dependAction = searched
                                        break
                                    }
                                }
                            }
                            it.buildParameter()
                        }
                        s.setActionDescriptions(s.enemySkillLevel, b.property)
                    }
                }
            }
        }
    }

    fun mSetSelectedPeriod(period: ClanBattlePeriod?){
        selectedPeriod = period
    }
}
