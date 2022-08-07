package com.github.malitsplus.shizurunotes.data

import android.text.format.DateFormat
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.db.DBHelper
import com.github.malitsplus.shizurunotes.user.UserSettings
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ClanBattlePeriod(
    val clanBattleId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
) {
    val phaseList = mutableListOf<ClanBattlePhase>().apply {
        DBHelper.get().getClanBattlePhase(clanBattleId)?.forEach {
            this.add(it.clanBattlePhase)
        }
    }
    var iconBoss1: String = ""
    var iconBoss2: String = ""
    var iconBoss3: String = ""
    var iconBoss4: String = ""
    var iconBoss5: String = ""
    var zodiacImage: Int

    init {
        if (phaseList.size > 0) {
            iconBoss1 = phaseList[0].bossList[0].iconUrl
            iconBoss2 = phaseList[0].bossList[1].iconUrl
            iconBoss3 = phaseList[0].bossList[2].iconUrl
            iconBoss4 = phaseList[0].bossList[3].iconUrl
            iconBoss5 = phaseList[0].bossList[4].iconUrl
        }
        zodiacImage = when(startTime.monthValue){
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

    val periodText: String by lazy {
        val locale = Locale(UserSettings.get().getLanguage())
        val format = DateFormat.getBestDateTimePattern(locale, "MMM yyyy")
        startTime.format(DateTimeFormatter.ofPattern(format))
    }
}