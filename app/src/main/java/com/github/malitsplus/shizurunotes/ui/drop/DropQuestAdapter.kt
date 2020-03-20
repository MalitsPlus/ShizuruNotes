package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.databinding.ItemDropEquipmentBinding
import com.github.malitsplus.shizurunotes.databinding.ItemHintTextBinding
import com.github.malitsplus.shizurunotes.databinding.ItemQuestDropBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseHintAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment

class DropQuestAdapter(
    private val mContext: Context,
    private val sharedEquipment: SharedViewModelEquipment
) : BaseHintAdapter<ItemQuestDropBinding, ItemHintTextBinding>(mContext, R.layout.item_quest_drop, R.layout.item_hint_text) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding as ItemHintTextBinding) {
                    hintText = itemList[position] as String
                    executePendingBindings()
                }
            }
            is InstanceViewHolder -> {
                val thisQuest = itemList[position] as Quest
                with(holder.binding as ItemQuestDropBinding) {
                    quest = thisQuest
                    dropIconContainer.removeAllViews()
                    thisQuest.dropList.forEach {
                        val rewardItem = DataBindingUtil.inflate<ItemDropEquipmentBinding>(
                            LayoutInflater.from(mContext), R.layout.item_drop_equipment, dropIconContainer, false
                        ).apply {
                            reward = it
                        }
                        sharedEquipment.selectedDrops.value?.let { equipmentList ->
                            for (equipment: Equipment in equipmentList) {
                                if (it.rewardId % 10000 == equipment.equipmentId % 10000) {
                                    rewardItem.root.background = mContext.getDrawable(R.drawable.shape_text_border)
                                    break
                                }
                            }
                        }
                        dropIconContainer.addView(rewardItem.root, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
                    }
                    executePendingBindings()
                }
            }
        }
    }
}