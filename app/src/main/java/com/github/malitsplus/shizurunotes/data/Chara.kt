package com.github.malitsplus.shizurunotes.data

import android.text.format.DateFormat
import androidx.annotation.DrawableRes
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.action.PassiveAction
import com.github.malitsplus.shizurunotes.user.UserSettings
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class Chara: Cloneable {

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Chara {
        return super.clone() as Chara
    }

    fun shallowCopy(): Chara {
        return clone()
    }

    var unitId: Int = 0
    var charaId: Int = 0
    var prefabId: Int = 0
    var searchAreaWidth: Int = 0
    var atkType: Int = 0
    var moveSpeed: Int = 0
    var guildId: Int = 0
    var normalAtkCastTime: Double = 0.0
    @DrawableRes var positionIcon: Int = 0
    var maxCharaLevel: Int = 0
    var maxCharaRank: Int = 0
    var maxUniqueEquipmentLevel: Int = 0
    var maxRarity: Int = 5
    var rarity: Int = 5

    lateinit var actualName: String
    lateinit var age: String
    lateinit var unitName: String
    lateinit var guild: String
    lateinit var race: String
    lateinit var height: String
    lateinit var weight: String
    lateinit var birthMonth: String
    lateinit var birthDay: String
    lateinit var bloodType: String
    lateinit var favorite: String
    lateinit var voice: String
    lateinit var kana: String
    lateinit var catchCopy: String
    lateinit var iconUrl: String
    lateinit var imageUrl: String
    lateinit var position: String
    var comment: String? = null
    var selfText: String? = null
    var sortValue: String? = null

    lateinit var startTime: LocalDateTime

    lateinit var charaProperty: Property
    val rarityProperty = mutableMapOf<Int, Property>()
    val rarityPropertyGrowth = mutableMapOf<Int, Property>()
    lateinit var storyProperty: Property
    lateinit var promotionStatus: Map<Int, Property>
    lateinit var rankEquipments: Map<Int, List<Equipment>>
    var uniqueEquipment: Equipment? = null

    var attackPatternList = mutableListOf<AttackPattern>()
    var skills = mutableListOf<Skill>()

    val birthDate: String by lazy {
        if (birthMonth.contains("?") || birthDay.contains("?")) {
            birthMonth + I18N.getString(R.string.text_month) + birthDay + I18N.getString(R.string.text_day)
        } else {
            val calendar = Calendar.getInstance()
            calendar.set(calendar.get(Calendar.YEAR), birthMonth.toInt() - 1, birthDay.toInt())
            val locale =  Locale(UserSettings.get().getLanguage())
            val format = DateFormat.getBestDateTimePattern(locale, "d MMM")
            SimpleDateFormat(format, locale).format(calendar.time)
        }
    }

    fun setCharaProperty(
        rarity: Int = maxRarity,
        rank: Int = maxCharaRank,
        hasUnique: Boolean = true
    ) {
        charaProperty = Property().apply {
            plusEqual(rarityProperty[rarity])
            plusEqual(getRarityGrowthProperty(rarity, rank))
            plusEqual(storyProperty)
            plusEqual(promotionStatus[rank])
            plusEqual(getAllEquipmentProperty(rank))
            plusEqual(getPassiveSkillProperty(rarity))
            if (hasUnique) {
                plusEqual(uniqueEquipmentProperty)
            }
        }
    }

    fun getSpecificCharaProperty(
        rarity: Int = maxRarity,
        rank: Int = maxCharaRank,
        hasUnique: Boolean = true
    ): Property {
        return Property().apply {
            plusEqual(rarityProperty[rarity])
            plusEqual(getRarityGrowthProperty(rarity, rank))
            plusEqual(storyProperty)
            plusEqual(promotionStatus[rank])
            plusEqual(getAllEquipmentProperty(rank))
            plusEqual(getPassiveSkillProperty(rarity))
            if (hasUnique) {
                plusEqual(uniqueEquipmentProperty)
            }
        }
    }

    private fun getRarityGrowthProperty(rarity: Int, rank: Int): Property{
        return rarityPropertyGrowth[rarity]?.multiply(maxCharaLevel.toDouble() + rank) ?: Property()
    }

    fun getAllEquipmentProperty(rank: Int): Property {
        val property = Property()
        rankEquipments[rank]?.forEach {
            property.plusEqual(it.getCeiledProperty())
        }
        return property
    }

    val uniqueEquipmentProperty: Property
        get() {
            return uniqueEquipment?.getCeiledProperty() ?: Property()
        }

    fun getPassiveSkillProperty(rarity: Int): Property {
        val property = Property()
        skills.forEach { skill ->
            if (rarity >= 5 && skill.skillClass == Skill.SkillClass.EX1_EVO) {
                skill.actions.forEach {
                    if (it.parameter is PassiveAction)
                        property.plusEqual((it.parameter as PassiveAction).propertyItem(maxCharaLevel))
                }
            } else if (rarity < 5 && skill.skillClass == Skill.SkillClass.EX1) {
                skill.actions.forEach {
                    if (it.parameter is PassiveAction)
                        property.plusEqual((it.parameter as PassiveAction).propertyItem(maxCharaLevel))
                }
            }
        }
        return property
    }
}