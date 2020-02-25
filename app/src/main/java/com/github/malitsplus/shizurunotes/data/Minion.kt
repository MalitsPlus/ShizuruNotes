package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.common.Statics
import com.github.malitsplus.shizurunotes.data.action.PassiveAction

class Minion(
    val unitId: Int,
    val unitName: String,
    val prefabId: Int,
    val moveSpeed: Int,
    val searchAreaWidth: Int,
    val atkType: Int,
    val normalAttackCastTime: Double
) {

    val iconUrl: String = Statics.ICON_URL.format(prefabId)
    val attackPattern = mutableListOf<AttackPattern>()
    val skills = mutableListOf<Skill>()
    val propertyMap = mutableMapOf<Int, Property>()
    val propertyGrowthMap = mutableMapOf<Int, Property>()
    var minionProperty = Property()

    fun initialMinion(level: Int, rank: Int, rarity: Int) {
        minionProperty = Property()
            .plusEqual(propertyMap[rarity])
            .plusEqual(propertyGrowthMap[rarity]?.multiply((level + rank).toDouble()))

        skills.forEach { skill ->
            skill.actions.forEach { a ->
                if (a.parameter is PassiveAction) {
                    if (rarity >= 5 && skill.skillClass == Skill.SkillClass.EX1_EVO) {
                        minionProperty.plusEqual((a.parameter as PassiveAction).propertyItem(level))
                    } else if (rarity < 5 && skill.skillClass == Skill.SkillClass.EX1) {
                        minionProperty.plusEqual((a.parameter as PassiveAction).propertyItem(level))
                    }
                }
            }
        }

        skills.forEach {
            it.setActionDescriptions(level, minionProperty)
        }
    }

}