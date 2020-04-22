package com.github.malitsplus.shizurunotes.db

import com.github.malitsplus.shizurunotes.data.CampaignSchedule
import com.github.malitsplus.shizurunotes.data.CampaignType
import com.github.malitsplus.shizurunotes.data.EventSchedule
import com.github.malitsplus.shizurunotes.data.EventType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MasterSchedule {
    fun getSchedule(nowTime: LocalDateTime?): MutableList<EventSchedule> {
        val scheduleList = mutableListOf<EventSchedule>()
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss")
        val nowTimeString = nowTime?.format(formatter)

        DBHelper.get().getCampaignSchedule(nowTimeString)?.forEach {
            val campaignType = CampaignType.parse(it.campaign_category)
            scheduleList.add(
                CampaignSchedule(
                    it.id, "", EventType.Campaign,
                    LocalDateTime.parse(it.start_time, formatter),
                    LocalDateTime.parse(it.end_time, formatter),
                    it.campaign_category, campaignType, it.value, it.system_id
                )
            )
        }
        DBHelper.get().getHatsuneSchedule(nowTimeString)?.forEach {
            scheduleList.add(EventSchedule(
                it.event_id, it.title, EventType.Hatsune,
                LocalDateTime.parse(it.start_time, formatter),
                LocalDateTime.parse(it.end_time, formatter)
            ))
        }
        DBHelper.get().getTowerSchedule(nowTimeString)?.forEach {
            scheduleList.add(EventSchedule(it.tower_schedule_id, "", EventType.Tower,
                LocalDateTime.parse(it.start_time, formatter), LocalDateTime.parse(it.end_time, formatter)
            ))
        }

        if (nowTime == null) {
            DBHelper.get().getFreeGachaSchedule(nowTimeString)?.forEach {
                scheduleList.add(
                    EventSchedule(
                        it.campaign_id, "", EventType.Gacha,
                        LocalDateTime.parse(it.start_time, formatter),
                        LocalDateTime.parse(it.end_time, formatter)
                    )
                )
            }
            DBHelper.get().getClanBattlePeriod()?.forEach {
                scheduleList.add(
                    EventSchedule(
                        it.clan_battle_id,
                        "",
                        EventType.ClanBattle,
                        LocalDateTime.parse(it.start_time, formatter),
                        LocalDateTime.parse(it.end_time, formatter)
                    )
                )
            }
        }
        return scheduleList
    }
}