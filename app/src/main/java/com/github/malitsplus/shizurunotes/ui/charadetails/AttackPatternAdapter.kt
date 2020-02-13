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

class AttackPatternAdapter(private val mContext: Context) :
    RecyclerView.Adapter<AttackPatternAdapter.AttackPatternViewHolder>() {

    private var itemList: List<AttackPatternItem> = ArrayList()

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
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun update(itemList: List<AttackPatternItem>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class AttackPatternViewHolder internal constructor(val binding: ListItemAttackPatternBinding) :
        RecyclerView.ViewHolder(binding.root)

}