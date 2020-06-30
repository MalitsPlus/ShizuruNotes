package com.github.malitsplus.shizurunotes.db

import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.EquipmentPiece
import com.github.malitsplus.shizurunotes.data.Item
import com.github.malitsplus.shizurunotes.data.Property
import com.github.malitsplus.shizurunotes.utils.Utils

class MasterEquipment {
    fun getEquipmentMap(): MutableMap<Int, Equipment> {
        val equipmentMap = mutableMapOf<Int, Equipment>()
        val rawEquipmentList = DBHelper.get().getEquipmentAll()
        val rawEquipmentEnhanceList = DBHelper.get().getEquipmentEnhance()

        val equipmentPieceMap = mutableMapOf<Int, EquipmentPiece>()
        val rawEquipmentPieceList = DBHelper.get().getEquipmentPiece()

        rawEquipmentPieceList?.forEach {
            equipmentPieceMap[it.equipment_id] = it.equipmentPiece
        }

        rawEquipmentList?.forEach {
            var enhanceProperty: Property = Property()
            if (rawEquipmentEnhanceList != null) {
                for (enhance in rawEquipmentEnhanceList) {
                    if (it.equipment_id == enhance.equipment_id) {
                        enhanceProperty = enhance.property
                        break
                    }
                }
            }

            equipmentMap[it.equipment_id] = Equipment(
                it.equipment_id,
                it.equipment_name,
                it.description.replace("\\n", ""),
                it.promotion_level,
                it.craft_flg,
                it.equipment_enhance_point,
                it.sale_price,
                it.require_level,
                it.max_equipment_enhance_level,
                it.property,
                enhanceProperty,
                it.catalog,
                it.rarity
            )
        }

        //填充装备构造树
        rawEquipmentList?.forEach { raw ->
            if (raw.craft_flg == 1) {
                val craftMap = mutableMapOf<Item, Int>()
                equipmentMap[raw.equipment_id]?.let { equipment ->
                    for (i in 1..10) {
                        val itemId = Utils.getValueFromObject(raw, "condition_equipment_id_$i") as Int
                        if (itemId != 0) {
                            when(itemId) {
                                in 101000..112999 -> {
                                    equipmentMap[itemId]?.let {
                                        craftMap[it] = Utils.getValueFromObject(raw, "consume_num_$i") as Int
                                    }
                                }
                                in 113000..139999 -> {
                                    equipmentPieceMap[itemId]?.let { piece ->
                                        craftMap[piece] = Utils.getValueFromObject(raw, "consume_num_$i") as Int
                                    }
                                }
                            }
                        }
                    }
                    equipment.craftMap = craftMap
                }
            }
        }
        //增加一个通用的未实装装备
        equipmentMap[999999] = Equipment.getNull
        return equipmentMap
    }
}