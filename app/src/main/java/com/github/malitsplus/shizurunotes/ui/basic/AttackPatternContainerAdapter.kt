package com.github.malitsplus.shizurunotes.ui.basic

import androidx.recyclerview.widget.GridLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.AttackPattern
import com.github.malitsplus.shizurunotes.databinding.ListItemAttackPatternContainerBinding
import com.github.malitsplus.shizurunotes.ui.charadetails.AttackPatternAdapter

class AttackPatternContainerAdapter : BasicRecyclerAdapter<AttackPattern, ListItemAttackPatternContainerBinding>(
    R.layout.list_item_attack_pattern_container) {

    override fun onBindViewHolder(holder: VH<ListItemAttackPatternContainerBinding>, position: Int) {
        with(holder.binding){
            textAttackPatternNum.text = I18N.getString(R.string.text_attack_pattern).format(position + 1)
            attackPatternContainerRecycler.apply {
                layoutManager = GridLayoutManager(context, 6)
                adapter = AttackPatternAdapter().apply { itemList = this@AttackPatternContainerAdapter.itemList[position].items }
            }
            this
        }
    }

}