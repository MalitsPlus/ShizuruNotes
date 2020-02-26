package com.github.malitsplus.shizurunotes.ui.charadetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.AttackPattern.AttackPatternItem
import com.github.malitsplus.shizurunotes.databinding.ListItemAttackPatternBinding
import java.util.*

class AttackPatternAdapter :
    RecyclerView.Adapter<AttackPatternAdapter.AttackPatternViewHolder>() {

    var itemList = mutableListOf<AttackPatternItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttackPatternViewHolder {
        val binding =
            DataBindingUtil.inflate<ListItemAttackPatternBinding>(
                LayoutInflater.from(parent.context),
                R.layout.list_item_attack_pattern, parent, false
            )
        return AttackPatternViewHolder(
            binding
        )
    }

    //填充每个item的视图
    override fun onBindViewHolder(
        holder: AttackPatternViewHolder,
        position: Int
    ) {
        holder.binding.patternItem = itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun update(itemList: MutableList<AttackPatternItem>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class AttackPatternViewHolder internal constructor(val binding: ListItemAttackPatternBinding) :
        RecyclerView.ViewHolder(binding.root)

}