package com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattleBossResistBinding

class ClanBattleBossResistAdapter (
    val resistName: List<String>,
    val resistRate: List<Int>
) : RecyclerView.Adapter<ClanBattleBossResistAdapter.ClanBattleBossResistHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleBossResistHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattleBossResistBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_boss_resist, parent, false
        )
        return ClanBattleBossResistHolder(
            binding
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ClanBattleBossResistHolder,
        position: Int
    ) {
        holder.binding.resistItemText.apply {
            leftTextView.text = " ${resistName[position]} "
            rightTextView.text = "${resistRate[position]}%"
        }
    }

    override fun getItemCount(): Int {
        return resistName.size
    }

    class ClanBattleBossResistHolder internal constructor(val binding: ListItemClanBattleBossResistBinding) :
        RecyclerView.ViewHolder(binding.root)
}