package com.github.malitsplus.shizurunotes.db

import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Property

class MasterEquipment {
    fun getEquipmentMap(): MutableMap<Int, Equipment> {
        val equipmentMap = mutableMapOf<Int, Equipment>()
        val rawEquipmentList = DBHelper.get().getEquipmentAll()
        val rawEquipmentEnhanceList = DBHelper.get().getEquipmentEnhance()
        rawEquipmentList?.forEach {
            var enhanceProperty: Property = Property()
            if (it.craft_flg == 1 && rawEquipmentEnhanceList != null) {
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
                it.description,
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
        return equipmentMap
    }
}