package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.db.DBHelper

class ClanBattlePhase(
    val phase: Int,
    waveGroupId1: Int?,
    waveGroupId2: Int?,
    waveGroupId3: Int?,
    waveGroupId4: Int?,
    waveGroupId5: Int?) {

    val bossList = mutableListOf<Enemy>()

    init {
        val waveGroupList = mutableListOf<WaveGroup>()
        DBHelper.get().getWaveGroupData(listOfNotNull(
            waveGroupId1, waveGroupId2, waveGroupId3, waveGroupId4, waveGroupId5
        ))?.forEach {
            waveGroupList.add(it.getWaveGroup(true))
        }

        waveGroupList.forEach{ w ->
            w.enemyList.forEach { bossList.add(it) }
        }
    }

}