package com.github.malitsplus.shizurunotes.data


class WaveGroup(
    val id: Int,
    val waveGroupId: Int
) {
    var enemyList = listOf<Enemy>()
    var dropGoldList: List<Int>? = null
    var dropRewardList: List<EnemyRewardData>? = null

    fun getEnemyIconUrl(position: Int): String {
        return if (position >= 0 && position < enemyList.size) {
            enemyList[position].iconUrl
        } else  ""
    }
}