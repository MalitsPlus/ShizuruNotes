package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.common.Statics

class Equipment(
    val equipmentId: Int,
    val equipmentName: String,
    val description: String,
    val promotionLevel: Int,
    val craftFlg: Int,
    val equipmentEnhancePoint: Int,
    val salePrice: Int,
    val requireLevel: Int,
    val maxEnhanceLevel: Int,
    val equipmentProperty: Property,
    var equipmentEnhanceRate: Property,
    val catalog: String,
    val rarity: Int
) {
    val iconUrl = Statics.EQUIPMENT_ICON_URL.format(equipmentId)

    fun getCeiledProperty(): Property {
        return if (craftFlg == 1) {
            equipmentProperty.plus(equipmentEnhanceRate.multiply(maxEnhanceLevel.toDouble()))
        } else {
            equipmentProperty
        }
    }

}