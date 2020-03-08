package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.db.DBHelper

class WaveGroup(
    val id: Int,
    val waveGroupId: Int
) {
    var enemyList: List<Enemy>? = null
    var dropGoldList: List<Int>? = null
    var dropRewardList: List<EnemyRewardData>? = null

}