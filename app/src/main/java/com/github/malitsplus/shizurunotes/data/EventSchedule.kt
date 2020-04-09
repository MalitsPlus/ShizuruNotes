package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.utils.Utils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class EventSchedule(
    val id: Int,
    val name: String,
    val type: EventType,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
) {
    open val title: String = when (type) {
        EventType.hatsune -> type.description + "：" + name
        else -> type.description
    }

    val durationString: String
        get() {
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return startTime.format(pattern) + "  -  " + endTime.format(pattern)
        }
}

class CampaignSchedule(
    id: Int,
    name: String,
    type: EventType,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    val category: Int,
    val campaignType: CampaignType,
    val value: Double,
    val systemId: Int
) : EventSchedule(id, name, type, startTime, endTime) {
    override val title: String by lazy {
        campaignType.description().format(Utils.roundIf(value / 1000.0))
    }
    val shortTitle: String = campaignType.shortDescription().format(Utils.roundIf(value / 1000.0))
}

enum class EventType {
    campaign,
    hatsune,
    clanBattle,
    tower,
    gacha;

    val description: String
        get() = when (this) {
            campaign -> I18N.getString(R.string.campaign)
            hatsune -> I18N.getString(R.string.hatsune)
            clanBattle -> I18N.getString(R.string.clanBattle)
            tower -> I18N.getString(R.string.tower)
            gacha -> I18N.getString(R.string.gacha)
//            else -> I18N.getString(R.string.unknown)
        }

    val color: Int
        get() = when (this) {
            campaign -> Sage.toInt()
            hatsune -> Tangerine.toInt()
            clanBattle -> Peacock.toInt()
            tower -> Grape.toInt()
            gacha -> Flamingo.toInt()
            else -> Graphite.toInt()
        }
}

const val Tangerine = 0xFFFFB878
const val Sage = 0xFF7AE7BF
const val Peacock = 0xFF46D6DB
const val Grape = 0xFFDBADFF
const val Flamingo = 0xFFFBD75B
const val Graphite = 0xFFE1E1E1