package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.ItemGridIconBinding
import com.github.malitsplus.shizurunotes.databinding.ItemHintTextBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment

class GridSelectAdapter(
    private val mContext: Context,
    private val sharedEquipment: SharedViewModelEquipment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val GRID_TEXT = 1
        const val GRID_EQUIPMENT = 2
    }

    var itemList = listOf<Any>()
    private val maxSelectNum = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            GRID_TEXT -> {
                val binding = DataBindingUtil.inflate<ItemHintTextBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_hint_text, parent, false
                )
                HintTextViewHolder(binding)
            }
            GRID_EQUIPMENT -> {
                val binding = DataBindingUtil.inflate<ItemGridIconBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_grid_icon, parent, false
                )
                GridIconViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]) {
            is Int -> GRID_TEXT
            is Equipment -> GRID_EQUIPMENT
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding) {
                    holder.binding.hintText = I18N.getString(R.string.text_drop_rarity).format(itemList[position] as Int)
                    executePendingBindings()
                }
            }
            is GridIconViewHolder -> {
                with(holder.binding){
                    val thisEquipment = itemList[position] as Equipment
                    iconUrl = thisEquipment.iconUrl
                    sharedEquipment.selectedDrops.value?.let {
                        if (it.contains(thisEquipment)) {
                            setItemStatus(this, true)
                        } else {
                            setItemStatus(this, false)
                        }
                        clickListener = View.OnClickListener { _ ->
                            if (it.contains(thisEquipment)) {
                                it.remove(thisEquipment)
                                setItemStatus(this, false)
                            } else if (it.size < maxSelectNum){
                                it.add(thisEquipment)
                                setItemStatus(this, true)
                            }
                        }
                    }
                    executePendingBindings()
                }
            }
        }
    }

    private fun setItemStatus(binding: ItemGridIconBinding, selected: Boolean) {
        binding.itemGridContainer.background = if (selected) {
            mContext.getDrawable(R.drawable.color_selected_background)
        } else {
            mContext.getDrawable(R.drawable.color_unselected_background)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun update(list: List<Any>) {
        itemList = list
        notifyDataSetChanged()
    }

    class HintTextViewHolder internal constructor(
        val binding: ItemHintTextBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class GridIconViewHolder internal constructor(
        val binding: ItemGridIconBinding
    ) : RecyclerView.ViewHolder(binding.root)
}