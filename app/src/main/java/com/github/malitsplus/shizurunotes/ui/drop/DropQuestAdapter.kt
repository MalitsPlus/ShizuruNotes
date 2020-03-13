package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.databinding.ItemDropEquipmentBinding
import com.github.malitsplus.shizurunotes.databinding.ItemHintTextBinding
import com.github.malitsplus.shizurunotes.databinding.ItemQuestDropBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import java.lang.IllegalArgumentException

class DropQuestAdapter(
    private val mContext: Context,
    private val sharedEquipment: SharedViewModelEquipment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LINEAR_TEXT = 1
        const val LINEAR_QUEST = 2
    }

    var itemList = listOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            LINEAR_TEXT -> {
                val binding = DataBindingUtil.inflate<ItemHintTextBinding>(LayoutInflater.from(parent.context), R.layout.item_hint_text, parent, false)
                HintTextViewHolder(binding)
            }
            LINEAR_QUEST -> {
                val binding = DataBindingUtil.inflate<ItemQuestDropBinding>(LayoutInflater.from(parent.context), R.layout.item_quest_drop, parent, false)
                QuestDropViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]) {
            is String -> LINEAR_TEXT
            is Quest -> LINEAR_QUEST
            else -> 0
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun update(items: List<Any>) {
        itemList = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding) {
                    hintText = itemList[position] as String
                    executePendingBindings()
                }
            }
            is QuestDropViewHolder -> {
                val thisQuest = itemList[position] as Quest
                with(holder.binding) {
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

    class HintTextViewHolder internal constructor(
        val binding: ItemHintTextBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class QuestDropViewHolder internal constructor(
        val binding: ItemQuestDropBinding
    ) : RecyclerView.ViewHolder(binding.root)

}