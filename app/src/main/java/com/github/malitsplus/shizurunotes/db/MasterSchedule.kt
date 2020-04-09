package com.github.malitsplus.shizurunotes.db

import com.github.malitsplus.shizurunotes.data.CampaignSchedule
import com.github.malitsplus.shizurunotes.data.CampaignType
import com.github.malitsplus.shizurunotes.data.EventSchedule
import com.github.malitsplus.shizurunotes.data.EventType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MasterSchedule {
    fun getSchedule(): MutableList<EventSchedule> {
        val scheduleList = mutableListOf<EventSchedule>()
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss")
        DBHelper.get().getCampaignSchedule()?.forEach {
            val campaignType = CampaignType.parse(it.campaign_category)
            scheduleList.add(
                CampaignSchedule(
                    it.id, "", EventType.campaign,
                    LocalDateTime.parse(it.start_time, formatter),
                    LocalDateTime.parse(it.end_time, formatter),
                    it.campaign_category, campaignType, it.value, it.system_id
                )
            )
        }
        DBHelper.get().getHatsuneSchedule()?.forEach {
            scheduleList.add(EventSchedule(
                it.event_id, it.title, EventType.hatsune,
                LocalDateTime.parse(it.start_time, formatter),
                LocalDateTime.parse(it.end_time, formatter)
            ))
        }
        DBHelper.get().getFreeGachaSchedule()?.forEach {
            scheduleList.add(EventSchedule(it.campaign_id, "", EventType.gacha,
                LocalDateTime.parse(it.start_time, formatter),
                LocalDateTime.parse(it.end_time, formatter)
            ))
        }
        return scheduleList
    }
}