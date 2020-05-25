package com.github.malitsplus.shizurunotes.ui.base

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.data.Equipment

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