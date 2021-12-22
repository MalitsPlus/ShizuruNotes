package com.github.nyanfantasia.shizurunotes.ui.equipment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Equipment
import com.github.nyanfantasia.shizurunotes.data.Item
import com.github.nyanfantasia.shizurunotes.ui.base.*
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipment
import com.google.android.material.slider.Slider

class EquipmentViewModel(
    val sharedEquipment: SharedViewModelEquipment
) : ViewModel() {
    val equipment = sharedEquipment.selectedEquipment ?: Equipment.getNull

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            field.clear()
            field.add(EquipmentBasicVT(equipment))
            field.addAll(getPropertyViewType())
            field.add(EquipmentLevelVT(equipment))
            field.add(TextTagVT(I18N.getString(R.string.text_equipment_craft_requirement)))
            equipment.getLeafCraftMap().forEach {
                field.add(EquipmentCraftVT(it))
            }
            return field
        }

    val selectedLevel = if (equipment.equipmentId in 130000..139999) {
        MutableLiveData(1)
    } else {
        MutableLiveData(0)
    }

    val onSliderChangeListener = Slider.OnChangeListener { _, value, _ ->
            selectedLevel.value = value.toInt()
    }

    fun getPropertyViewType(level: Int = 0): List<ViewType<*>> {
        val list = mutableListOf<ViewType<*>>()
        equipment.getEnhancedProperty(level).nonZeroPropertiesMap.forEach {
            list.add(PropertyVT(it))
        }
        return list
    }
}

interface OnEquipmentActionListener<T>: OnItemActionListener {
    fun onItemClickedListener(item: Item)
    val onSliderActionListener: Slider.OnChangeListener
}