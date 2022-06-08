package com.github.malitsplus.shizurunotes.data

class SecretDungeonQuest(
    val questId: Int,
    val dungeonAreaId: Int,
    val difficulty: Int,
    val floorNum: Int,
    val questType: Int,
    val waveGroupId: Int,
    val waveGroup: WaveGroup
) {
}