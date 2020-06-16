package com.github.malitsplus.shizurunotes.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HatsuneStage(
    val eventId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val title: String
) {
    val battleWaveGroupMap = mutableMapOf<String, WaveGroup>()
//    val spBattleWaveGroupMap = mutableMapOf<String, WaveGroup>()
    val durationString: String
        get() {
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return startTime.format(pattern) + "  ~  " + endTime.format(pattern)
        }
}