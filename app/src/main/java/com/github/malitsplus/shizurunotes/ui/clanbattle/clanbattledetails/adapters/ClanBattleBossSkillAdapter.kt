package com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattleBossSkillBinding
import com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails.ClanBattleBossDetailsFragmentDirections
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

class ClanBattleBossSkillAdapter (
    private var skillList: List<Skill>,
    private val sharedClanBattle: SharedViewModelClanBattle?
) : RecyclerView.Adapter<ClanBattleBossSkillAdapter.ClanBattleBossSkillHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleBossSkillHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattleBossSkillBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_boss_skill, parent, false
        )
        return ClanBattleBossSkillHolder(
            binding
        )
    }


    override fun onBindViewHolder(
        holder: ClanBattleBossSkillHolder,
        position: Int
    ) {
        holder.binding.skill
        with(holder.binding){
            skill = skillList[position].also { s ->
                if (s.enemyMinionList.isNotEmpty()){
                    enemyMinionButton.visibility = View.VISIBLE
                    enemyMinionButton.setOnClickListener {
                        sharedClanBattle?.selectedMinion = s.enemyMinionList
                        it.findNavController().navigate(
                            ClanBattleBossDetailsFragmentDirections.actionNavClanBattleBossDetailsToNavMinion()
                        )
                    }
                }
            }
            this
        }
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