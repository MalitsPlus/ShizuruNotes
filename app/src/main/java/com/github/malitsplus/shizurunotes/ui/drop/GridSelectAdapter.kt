package com.github.malitsplus.shizurunotes.ui.drop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.ItemGridIconBinding

class GridSelectAdapter(val itemList: List<Equipment>) : RecyclerView.Adapter<GridSelectAdapter.GridSelectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridSelectViewHolder {
        val binding = DataBindingUtil.inflate<ItemGridIconBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_grid_icon, parent, false
        )
        return GridSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridSelectViewHolder, position: Int) {
        with(holder.binding){
            iconUrl = itemList[position].iconUrl
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class GridSelectViewHolder internal constructor(val binding: ItemGridIconBinding) :
        RecyclerView.ViewHolder(binding.root)
}