package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.db.DBHelper
import java.time.LocalDateTime

class ClanBattlePeriod(
    val clanBattleId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
) {

    val periodText: String = startTime.year.toString() +
            I18N.getString(R.string.text_year) +
            startTime.monthValue.toString() +
            I18N.getString(R.string.text_month)

    val phaseList = mutableListOf<ClanBattlePhase>().apply {
        DBHelper.get().getClanBattlePhase(clanBattleId)?.forEach {
            this.add(it.clanBattlePhase)
        }
    }

    val iconBoss1 = phaseList[0].bossList[0].iconUrl
    val iconBoss2 = phaseList[0].bossList[1].iconUrl
    val iconBoss3 = phaseList[0].bossList[2].iconUrl
    val iconBoss4 = phaseList[0].bossList[3].iconUrl
    val iconBoss5 = phaseList[0].bossList[4].iconUrl

    val zodiacImage: Int? = when(startTime.monthValue){
        1 -> R.drawable.zodiac_aquarious
        2 -> R.drawable.zodiac_pisces
        3 -> R.drawable.zodiac_aries
        4 -> R.drawable.zodiac_taurus
        5 -> R.drawable.zodiac_gemini
        6 -> R.drawable.zodiac_cancer
        7 -> R.drawable.zodiac_leo
        8 -> R.drawable.zodiac_virgo
        9 -> R.drawable.zodiac_libra
        10 -> R.drawable.zodiac_scorpio
        11 -> R.drawable.zodiac_sagittarious
        12 -> R.drawable.zodiac_capricorn
        else -> R.drawable.mic_chara_icon_place_holder
    }

}