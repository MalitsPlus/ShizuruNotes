package com.github.malitsplus.shizurunotes.ui.drop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.ItemGridIconBinding
import com.github.malitsplus.shizurunotes.databinding.ItemGridTextBinding

class GridSelectAdapter(
    val itemList: List<*>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val GRID_TEXT = 1
        const val GRID_EQUIPMENT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            GRID_TEXT -> {
                val binding = DataBindingUtil.inflate<ItemGridTextBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_grid_text, parent, false
                )
                GridTextViewHolder(binding)
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
            is String -> GRID_TEXT
            is Equipment -> GRID_EQUIPMENT
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is GridTextViewHolder -> {
                holder.binding.hintText = itemList[position] as String
            }
            is GridIconViewHolder -> {
                with(holder.binding){
                    iconUrl = (itemList[position] as Equipment).iconUrl
                    executePendingBindings()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class GridTextViewHolder internal constructor(
        val binding: ItemGridTextBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class GridIconViewHolder internal constructor(
        val binding: ItemGridIconBinding
    ) : RecyclerView.ViewHolder(binding.root)
}