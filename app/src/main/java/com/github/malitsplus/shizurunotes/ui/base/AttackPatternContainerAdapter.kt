package com.github.malitsplus.shizurunotes.ui.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.AttackPattern
import com.github.malitsplus.shizurunotes.databinding.ItemHintAttackPatternBinding
import com.github.malitsplus.shizurunotes.databinding.ListItemAttackPatternBinding

class AttackPatternContainerAdapter(
    private val mContext: Context?
) : BaseHintAdapter<ListItemAttackPatternBinding, ItemHintAttackPatternBinding>(mContext, R.layout.list_item_attack_pattern, R.layout.item_hint_attack_pattern) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding as ItemHintAttackPatternBinding) {
                    num = itemList[position] as Int
                    executePendingBindings()
                }
            }
            is InstanceViewHolder -> {
                with(holder.binding as ListItemAttackPatternBinding) {
                    patternItem = itemList[position] as AttackPattern.AttackPatternItem
                    executePendingBindings()
                }
            }
        }
    }

    fun initializeItems(attackPatternList: List<AttackPattern>?) {
        if (attackPatternList != null) {
            for (i in 1..attackPatternList.size) {
                itemList.add(i)
                attackPatternList[i - 1].items.forEach {
                    itemList.add(it)
                }
            }
        }
    }
}