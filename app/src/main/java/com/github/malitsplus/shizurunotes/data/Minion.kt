package com.github.malitsplus.shizurunotes.data

class Minion(
    val unitId: Int,
    val unitName: String,
    val prefabId: Int,
    val moveSpeed: Int,
    val searchAreaWidth: Int,
    val atkType: Int,
    val normalAttackCastTime: Double
) {

    val skills = mutableListOf<Skill>()
    val propertyMap = mutableMapOf<Int, Property>()
    val propertyGrowthMap = mutableMapOf<Int, Property>()

    fun getMinionProperty(level: Int, rarity: Int): Property{
        return Property()
            .plusEqual(propertyMap[rarity])
            .plusEqual(propertyGrowthMap[rarity]?.multiply(level.toDouble()))
    }

}