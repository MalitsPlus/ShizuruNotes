package com.github.malitsplus.shizurunotes.data

class Equipment(
    val equipmentId: Int,
    val equipmentName: String,
    val maxEnhanceLevel: Int,
    val equipmentData: Property,
    var equipmentEnhanceRate: Property = Property()
) {

    val ceiledProperty: Property
        get() = equipmentData.plus(equipmentEnhanceRate.multiply(maxEnhanceLevel.toDouble()))

    fun setEquipmentEnhanceRateChain(equipmentEnhanceRate: Property): Equipment {
        this.equipmentEnhanceRate = equipmentEnhanceRate
        return this
    }

}