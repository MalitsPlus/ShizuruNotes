package com.github.malitsplus.shizurunotes.ui.equipment

import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Item
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment

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

    val selectedLevel = MutableLiveData(0)

    val onSeekBarChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            selectedLevel.value = progress
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
        }
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
    val onSeekBarActionListener: SeekBar.OnSeekBarChangeListener
}