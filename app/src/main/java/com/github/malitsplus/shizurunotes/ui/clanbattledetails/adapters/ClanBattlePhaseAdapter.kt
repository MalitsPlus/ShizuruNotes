package com.github.malitsplus.shizurunotes.ui.clanbattledetails.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.ClanBattleBoss
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattlePhaseBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.ClanBattleViewPagerFragmentDirections

class ClanBattlePhaseAdapter (
    private var bossList: List<ClanBattleBoss>
) : RecyclerView.Adapter<ClanBattlePhaseAdapter.ClanBattleDetailsBossHolder>() {

    private lateinit var sharedClanBattleVM: SharedViewModelClanBattle

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleDetailsBossHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattlePhaseBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_phase, parent, false
        )
        return ClanBattleDetailsBossHolder(
            binding
        )
    }


    override fun onBindViewHolder(
        holder: ClanBattleDetailsBossHolder,
        position: Int
    ) {
        holder.binding.apply {
            val thisBoss = bossList[position]
            boss = thisBoss
            clickListener = View.OnClickListener {
                if (it?.id == R.id.clan_battle_phase_boss){
                    sharedClanBattleVM.mSetSelectedBoss(thisBoss)
                    val action =
                        ClanBattleViewPagerFragmentDirections.actionNavClanBattleViewPagerToNavClanBattleBossDetails()
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return bossList.size
    }

    fun update(periodList: List<ClanBattleBoss>) {
        this.bossList = periodList
        notifyDataSetChanged()
    }

    fun setSharedClanBattleVM(clanBattle: SharedViewModelClanBattle){
        this.sharedClanBattleVM = clanBattle
    }

    class ClanBattleDetailsBossHolder internal constructor(val binding: ListItemClanBattlePhaseBinding) :
        RecyclerView.ViewHolder(binding.root)

}
