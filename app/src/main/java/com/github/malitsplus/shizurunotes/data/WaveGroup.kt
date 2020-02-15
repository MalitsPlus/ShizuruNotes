package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.db.DBHelper

class WaveGroup(
    val id: Int,
    val waveGroupId: Int,
    val enemy_id_1: Int?,
    val enemy_id_2: Int?,
    val enemy_id_3: Int?,
    val enemy_id_4: Int?,
    val enemy_id_5: Int?
    ) {

    val bossIdList: List<Int> = listOfNotNull(
        enemy_id_1, enemy_id_2, enemy_id_3, enemy_id_4, enemy_id_5
    )

    val bossList = mutableListOf<ClanBattleBoss>().apply {
        DBHelper.get().getClanBattleBoss(bossIdList).forEach {
            this.add(it.clanBattleBoss)
        }
    }


/*
    init {
        DBHelper.get().getClanBattleBoss(bossIdList).forEach {
            bossList.add(it.clanBattleBoss)
        }
    }

 */
}