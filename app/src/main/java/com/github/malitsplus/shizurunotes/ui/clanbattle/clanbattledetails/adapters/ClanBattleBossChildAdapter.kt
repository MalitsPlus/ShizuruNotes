package com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Enemy
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattleBossChildBinding

class ClanBattleBossChildAdapter (
    private var bossChildList: List<Enemy>
) : RecyclerView.Adapter<ClanBattleBossChildAdapter.ClanBattleBossChildHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleBossChildHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattleBossChildBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_boss_child, parent, false
        )
        return ClanBattleBossChildHolder(
            binding
        )
    }


    override fun onBindViewHolder(
        holder: ClanBattleBossChildHolder,
        position: Int
    ) {
        holder.binding.bossChild = bossChildList[position]
    }

    override fun getItemCount(): Int {
        return bossChildList.size
    }

    fun update(periodList: List<Enemy>) {
        this.bossChildList = periodList
        notifyDataSetChanged()
    }

    class ClanBattleBossChildHolder internal constructor(val binding: ListItemClanBattleBossChildBinding) :
        RecyclerView.ViewHolder(binding.root)

}