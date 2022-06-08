package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.common.Statics

class SecretDungeonSchedule(
    val dungeonAreaId: Int,
    val startTime: String,
    val endTime: String
) {
    val title: String = "$dungeonAreaId"
    var enemyIcon = Statics.UNKNOWN_ICON
    val waveGroupMap = mutableMapOf<String, WaveGroup>()
    val duration: String by lazy {
        "$startTime ~ $endTime"
    }
}