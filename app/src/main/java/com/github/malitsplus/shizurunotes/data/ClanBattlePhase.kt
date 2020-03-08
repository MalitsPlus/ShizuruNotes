package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.db.DBHelper

class ClanBattlePhase(
    val phase: Int,
    val waveGroupId1: Int?,
    val waveGroupId2: Int?,
    val waveGroupId3: Int?,
    val waveGroupId4: Int?,
    val waveGroupId5: Int?) {

    val bossList = mutableListOf<Enemy>()

    init {
        val waveGroupList = mutableListOf<WaveGroup>()
        DBHelper.get().getWaveGroupData(listOfNotNull(
            waveGroupId1, waveGroupId2, waveGroupId3, waveGroupId4, waveGroupId5
        ))?.forEach {
            waveGroupList.add(it.getWaveGroup(true))
        }

        waveGroupList.forEach{ w ->
            w.enemyList?.forEach { bossList.add(it) }
        }
    }

}