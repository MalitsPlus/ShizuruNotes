package com.github.malitsplus.shizurunotes.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.BR
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.ItemAnalyzeAdjustBinding
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
            (binding as ItemEquipmentLevelBinding).equipmentEnhanceLevelSlider.apply {
                if (item.data.maxEnhanceLevel == 0) {
                    isEnabled = false
                } else {
                    valueFrom = 0f
                    valueTo = item.data.maxEnhanceLevel.toFloat()
                    addOnChangeListener((onItemActionListener as OnEquipmentActionListener<*>).onSliderActionListener)
                }
            }
        }
    }

//    /***
//     * 角色星级、Rank调整面板
//     * TODO: 1、增加装备、个人剧情调整选项？
//     * TODO: 2、在ViewHolder中处理一堆监听总觉得不对劲，寻找更好的处理方式？
//     */
//    class AnalyzeViewHolder(
//        binding: ViewDataBinding,
//        onItemActionListener: OnItemActionListener?
//    ): ViewTypeHolder(binding, onItemActionListener) {
//        override fun bindItem(item: ViewType<*>) {
//            super.bindItem(item)
//            item as AnalyzePanelVT
//            with (binding as ItemAnalyzeAdjustBinding) {
//                rankDropdown.apply {
//                    setAdapter(
//                        MaterialSpinnerAdapter(
//                            context,
//                            R.layout.dropdown_item_chara_list,
//                            item.data.rankList.toTypedArray()
//                        )
//                    )
//                    setText(item.data.rank.toString())
//                    onItemClickListener = item.data
//                }
//                val starViewList = listOf(
//                    charaStar1, charaStar2, charaStar3, charaStar4, charaStar5, charaStar6
//                )
//                for (i in 1..6) {
//                    starViewList[i - 1].setOnClickListener {
//                        changeStarImage(i)
//                        item.data.rarity = i
//                        item.data.updateProperty()
//                    }
//                }
//                if (item.data.chara?.maxRarity == 5) {
//                    charaStar6.visibility = View.GONE
//                }
//            }
//        }
//
//        private fun changeStarImage(rarity: Int) {
//            with(binding as ItemAnalyzeAdjustBinding) {
//                val starViewList = listOf(
//                    charaStar1, charaStar2, charaStar3, charaStar4, charaStar5, charaStar6
//                )
//                for (i in 1..6) {
//                    if (i <= rarity) {
//                        starViewList[i - 1].setImageResource(R.drawable.mic_star_filled)
//                    } else {
//                        starViewList[i - 1].setImageResource(R.drawable.mic_star_blank)
//                    }
//                }
//            }
//        }
//    }

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
//                R.layout.item_analyze_adjust -> { AnalyzeViewHolder(binding, listener) }
                else -> { GeneralViewHolder(binding, listener) }
            }
        }
    }
}

interface OnItemActionListener {
    fun onItemClicked(position: Int)
}