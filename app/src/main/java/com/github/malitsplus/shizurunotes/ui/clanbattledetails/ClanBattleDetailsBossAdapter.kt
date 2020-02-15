package com.github.malitsplus.shizurunotes.ui.clanbattledetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.ClanBattleBoss
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattleBossBinding

class ClanBattleDetailsBossAdapter (
    private val mContext: Context,
    private var bossList: List<ClanBattleBoss>
) : RecyclerView.Adapter<ClanBattleDetailsBossAdapter.ClanBattleDetailsBossHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleDetailsBossHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattleBossBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_boss, parent, false
        )
        return ClanBattleDetailsBossHolder(binding)
    }


    override fun onBindViewHolder(
        holder: ClanBattleDetailsBossHolder,
        position: Int
    ) {
        holder.binding.apply {
            boss = bossList[position]
            childrenRecycler.layoutManager = LinearLayoutManager(childrenRecycler.context)
            childrenRecycler.adapter = ClanBattleBossChildAdapter(bossList[position].children)

            bossSkillRecycler.layoutManager = LinearLayoutManager(childrenRecycler.context)
            bossSkillRecycler.adapter = ClanBattleBossSkillAdapter(bossList[position].skills)

        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return bossList.size
    }

    fun update(periodList: List<ClanBattleBoss>) {
        this.bossList = periodList
        notifyDataSetChanged()
    }

    class ClanBattleDetailsBossHolder internal constructor(val binding: ListItemClanBattleBossBinding) :
        RecyclerView.ViewHolder(binding.root)

}
