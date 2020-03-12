package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.databinding.ItemDropEquipmentBinding
import com.github.malitsplus.shizurunotes.databinding.ItemQuestDropBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter

class DropQuestAdapter(
    private val mContext: Context
) : BaseRecyclerAdapter<Quest, ItemQuestDropBinding>(R.layout.item_quest_drop) {

    override fun onBindViewHolder(holder: VH<ItemQuestDropBinding>, position: Int) {
        val thisQuest = itemList[position]
        with(holder.binding) {
            quest = thisQuest
            dropIconContainer.removeAllViews()
            thisQuest.dropList.forEach {
                val rewardItem = DataBindingUtil.inflate<ItemDropEquipmentBinding>(
                    LayoutInflater.from(mContext), R.layout.item_drop_equipment, dropIconContainer, false
                ).apply {
                    reward = it
                }
                dropIconContainer.addView(rewardItem.root, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            }
            executePendingBindings()
        }
    }

}