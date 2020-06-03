package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
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
) : Item {
    override val itemId: Int = equipmentId
    override val iconUrl = Statics.EQUIPMENT_ICON_URL.format(equipmentId)
    override val itemName: String = equipmentName
    override val itemType = ItemType.EQUIPMENT
    var craftMap: Map<Item, Int>? = null

    fun getCeiledProperty(): Property {
        return equipmentProperty.plus(equipmentEnhanceRate.multiply(maxEnhanceLevel.toDouble())).ceiled
    }

    fun getEnhancedProperty(level: Int): Property {
        return equipmentProperty.plus(equipmentEnhanceRate.multiply(level.toDouble())).ceiled
    }

    fun getLeafCraftMap(): Map<Item, Int> {
        val leafMap = mutableMapOf<Item, Int>()
        craftMap?.forEach {
            addOrCreateLeaf(leafMap, it.key, it.value)
        }
        return leafMap
    }

    private fun addOrCreateLeaf(map: MutableMap<Item, Int>, item: Item, value: Int) {
        if (item is EquipmentPiece || item is GeneralItem || (item is Equipment && item.craftFlg == 0)) {
            if (map.containsKey(item)) {
                map[item]?.plus(value)
            } else {
                map[item] = value
            }
        } else if (item is Equipment && item.craftFlg == 1) {
            item.craftMap?.forEach {
                addOrCreateLeaf(map, it.key, it.value)
            }
        }
    }

    companion object {
        val getNull = Equipment(999999,
            I18N.getString(R.string.unimplemented),
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            Property(),
            Property(),
            "",
            0)
    }
}

class EquipmentPiece(
    private val id: Int,
    private val name: String
) : Item {
    override val itemId: Int = id
    override val itemName: String = name
    override val itemType: ItemType = ItemType.EQUIPMENT_PIECE
    override val iconUrl: String = Statics.EQUIPMENT_ICON_URL.format(itemId)
}