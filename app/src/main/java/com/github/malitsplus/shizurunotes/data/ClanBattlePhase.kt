package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.db.DBHelper

class ClanBattlePhase(
    val phase: Int,
    val waveGroupId1: Int?,
    val waveGroupId2: Int?,
    val waveGroupId3: Int?,
    val waveGroupId4: Int?,
    val waveGroupId5: Int?) {

    val waveGroupIdList: List<Int> = listOfNotNull(
        waveGroupId1, waveGroupId2, waveGroupId3, waveGroupId4, waveGroupId5
    )

    val waveGroupList = ArrayList<WaveGroup>()

    init {
        DBHelper.get().getClanBattleWaveEnemy(waveGroupIdList).forEach {
            waveGroupList.add(it.waveGroup)
        }
    }

}