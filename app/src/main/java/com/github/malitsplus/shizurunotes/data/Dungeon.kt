package com.github.malitsplus.shizurunotes.data

class Dungeon(
    val dungeonAreaId: Int,
    val waveGroupId: Int,
    val enemyId: Int,
    val mode: Int,
    val dungeonName: String,
    val description: String,
    val dungeonBoss: List<Enemy>
) {
    val modeText: String by lazy {
        if (mode == 0) {
            ""
        } else {
            "MODE$mode"
        }
    }
}