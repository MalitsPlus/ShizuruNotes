package com.github.malitsplus.shizurunotes.ui.base

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Item
import com.github.malitsplus.shizurunotes.data.PropertyKey

data class CharaProfileVT(
    private val chara: Chara
) : ViewType<Chara> {
    override val layoutId = R.layout.item_chara_profile
    override val isUserInteractionEnabled = false
    override val data = chara
}

data class CharaUniqueEquipmentVT(
    private val equipment: Equipment
) : ViewType<Equipment> {
    override val layoutId = R.layout.item_chara_unique_equipment
    override val isUserInteractionEnabled = true
    override val data = equipment
}

data class CharaRankEquipmentVT(
    private val equipmentMapEntry: Map.Entry<Int, List<Equipment>>
) : ViewType<Map.Entry<Int, List<Equipment>>> {
    override val layoutId = R.layout.item_chara_rank_equipment
    override val isUserInteractionEnabled = true
    override val data = equipmentMapEntry
}

data class EquipmentBasicVT(
    private val equipment: Equipment
) : ViewType<Equipment> {
    override val layoutId = R.layout.item_equipment_basic
    override val isUserInteractionEnabled = true
    override val data = equipment
}

data class PropertyVT(
    private val propertyMapEntry: Map.Entry<PropertyKey, Int>
) : ViewType<Map.Entry<PropertyKey, Int>> {
    override val layoutId = R.layout.item_property
    override val isUserInteractionEnabled = false
    override val data = propertyMapEntry
}

data class EquipmentLevelVT(
    private val equipment: Equipment
) : ViewType<Equipment> {
    override val layoutId = R.layout.item_equipment_level
    override val isUserInteractionEnabled = true
    override val data = equipment
}

data class EquipmentCraftVT(
    private val equipmentCraftMap: Map.Entry<Item, Int>
) : ViewType<Map.Entry<Item, Int>> {
    override val layoutId = R.layout.item_equipment_craft_num
    override val isUserInteractionEnabled = true
    override val data = equipmentCraftMap
}

data class TextTagVT(
    private val text: String
) : ViewType<String> {
    override val layoutId = R.layout.item_text_tag
    override val isUserInteractionEnabled = false
    override val data = text
}