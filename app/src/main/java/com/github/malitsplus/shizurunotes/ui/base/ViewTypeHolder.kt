package com.github.malitsplus.shizurunotes.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.BR
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.ItemEnemySkillBinding
import com.github.malitsplus.shizurunotes.databinding.ItemEquipmentBasicBinding
import com.github.malitsplus.shizurunotes.databinding.ItemEquipmentLevelBinding
import com.github.malitsplus.shizurunotes.ui.equipment.OnEquipmentActionListener

sealed class ViewTypeHolder(
    val binding: ViewDataBinding,
    val onItemActionListener: OnItemActionListener?
) : RecyclerView.ViewHolder(binding.root) {

    /***
     * 通用ViewHolder
     */
    class GeneralViewHolder(
        binding: ViewDataBinding,
        onItemActionListener: OnItemActionListener?
    ): ViewTypeHolder(binding, onItemActionListener)

    /***
     * 装备强化等级选择ViewHolder，由于包含SeekBar监听事件，不能使用通用ViewHolder
     */
    class EquipmentLevelViewHolder(
        binding: ViewDataBinding,
        onItemActionListener: OnItemActionListener?
    ): ViewTypeHolder(binding, onItemActionListener) {
        override fun bindItem(item: ViewType<*>) {
            super.bindItem(item)
            item as EquipmentLevelVT
            (binding as ItemEquipmentLevelBinding).equipmentEnhanceLevelSeekBar.apply {
                if (item.data.maxEnhanceLevel == 0) {
                    isEnabled = false
                } else {
                    min = 0
                    max = item.data.maxEnhanceLevel
                    setOnSeekBarChangeListener((onItemActionListener as OnEquipmentActionListener<*>).onSeekBarActionListener)
                }
            }
        }
    }

    open fun bindItem(item: ViewType<*>) {
        binding.setVariable(BR.itemModel, item.data)
        if (item.isUserInteractionEnabled) {
            binding.setVariable(BR.itemPosition, adapterPosition)
            binding.setVariable(BR.itemActionListener, onItemActionListener)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            viewType: Int,
            listener: OnItemActionListener?
        ): ViewTypeHolder {
            val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
            return when (viewType) {
                R.layout.item_equipment_level -> { EquipmentLevelViewHolder(binding, listener) }
                else -> { GeneralViewHolder(binding, listener) }
            }
        }
    }
}

interface OnItemActionListener {
    fun onItemClicked(position: Int)
}