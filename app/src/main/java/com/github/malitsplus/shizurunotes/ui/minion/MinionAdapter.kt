package com.github.malitsplus.shizurunotes.ui.minion

import com.github.malitsplus.shizurunotes.data.Minion
import com.github.malitsplus.shizurunotes.databinding.ListItemMinionBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.basic.BasicRecyclerAdapter

class MinionAdapter(layout: Int, private val sharedChara: SharedViewModelChara) : BasicRecyclerAdapter<Minion, ListItemMinionBinding>(layout) {

    override fun onBindViewHolder(holder: VH<ListItemMinionBinding>, position: Int) {
        holder.binding.apply {
            val item = itemList[position]
            minionName.text = item.unitName
            minionId.text = item.unitId.toString()
            val property = item.getMinionProperty(sharedChara.maxCharaLevel, 5)
            txtHp.setRightString(property.getHp().toString())
            txtAtk.setRightString(property.getAtk().toString())
            txtDef.setRightString(property.getDef().toString())
            txtMagicStr.setRightString(property.getMagicStr().toString())
            txtMagicDef.setRightString(property.getMagicDef().toString())

            executePendingBindings()
        }
    }

}