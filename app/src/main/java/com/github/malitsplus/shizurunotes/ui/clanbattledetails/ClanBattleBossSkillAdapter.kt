package com.github.malitsplus.shizurunotes.ui.clanbattledetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattleBossSkillBinding

class ClanBattleBossSkillAdapter (
    private var skillList: List<Skill>
) : RecyclerView.Adapter<ClanBattleBossSkillAdapter.ClanBattleBossSkillHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleBossSkillHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattleBossSkillBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_boss_skill, parent, false
        )
        return ClanBattleBossSkillHolder(binding)
    }


    override fun onBindViewHolder(
        holder: ClanBattleBossSkillHolder,
        position: Int
    ) {
        holder.binding.skill = skillList[position]


        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return skillList.size
    }

    fun update(periodList: List<Skill>) {
        this.skillList = periodList
        notifyDataSetChanged()
    }

    class ClanBattleBossSkillHolder internal constructor(val binding: ListItemClanBattleBossSkillBinding) :
        RecyclerView.ViewHolder(binding.root)

}