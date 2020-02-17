package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.db.DBHelper

class ClanBattlePhase(
    val phase: Int,
    val waveGroupId1: Int?,
    val waveGroupId2: Int?,
    val waveGroupId3: Int?,
    val waveGroupId4: Int?,
    val waveGroupId5: Int?) {

    val bossList = mutableListOf<ClanBattleBoss>()

    init {
        val waveGroupList = mutableListOf<WaveGroup>()
        DBHelper.get().getClanBattleWaveEnemy(listOfNotNull(
            waveGroupId1, waveGroupId2, waveGroupId3, waveGroupId4, waveGroupId5
        ))?.forEach {
            waveGroupList.add(it.waveGroup)
        }

        waveGroupList.forEach{ w ->
            w.bossList.forEach { bossList.add(it) }
        }
    }

}