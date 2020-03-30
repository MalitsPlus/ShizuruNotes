package com.github.malitsplus.shizurunotes.ui.clanbattle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattlePeriodBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.BottomNaviFragmentDirections

class ClanBattleAdapter (
    private val mContext: Context,
    private val sharedViewModelClanBattle: SharedViewModelClanBattle
) : RecyclerView.Adapter<ClanBattleAdapter.ClanBattleViewHolder>() {

    private var periodList: List<ClanBattlePeriod> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClanBattleViewHolder {
        val binding = DataBindingUtil.inflate<ListItemClanBattlePeriodBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_clan_battle_period, parent, false
        )
        //向每个item设置点击监听事件
        binding.clickListener = View.OnClickListener { v: View ->
            sharedViewModelClanBattle.selectedPeriod = binding.period

            val action: NavDirections =
                BottomNaviFragmentDirections.actionNavBottomNavigationToNavClanBattleViewPager()
            Navigation.findNavController(v).navigate(action)
        }

        return ClanBattleViewHolder(binding)
    }



    override fun onBindViewHolder(
        holder: ClanBattleViewHolder,
        position: Int
    ) {
        holder.binding.period = periodList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return periodList.size
    }

    fun update(periodList: List<ClanBattlePeriod>) {
        this.periodList = periodList
        notifyDataSetChanged()
    }

    class ClanBattleViewHolder internal constructor(val binding: ListItemClanBattlePeriodBinding) :
        RecyclerView.ViewHolder(binding.root)
}