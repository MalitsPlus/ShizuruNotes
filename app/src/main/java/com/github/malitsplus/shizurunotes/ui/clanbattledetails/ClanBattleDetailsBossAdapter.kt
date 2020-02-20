package com.github.malitsplus.shizurunotes.ui.clanbattledetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.ClanBattleBoss
import com.github.malitsplus.shizurunotes.databinding.ListItemClanBattleBossBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle

class ClanBattleDetailsBossAdapter (
    private val mContext: Context,
    private var bossList: List<ClanBattleBoss>
) : RecyclerView.Adapter<ClanBattleDetailsBossAdapter.ClanBattleDetailsBossHolder>() {

    private lateinit var sharedClanBattleVM: SharedViewModelClanBattle

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

            bossAttackPattern.text = bossList[position].attackPatternList[0]
                .getEnemyPatternText(I18N.getString(R.string.text_attack_pattern_prefix))
            if (bossList[position].attackPatternList.size > 1)
                bossAttackPattern2.text = bossList[position].attackPatternList[1]
                    .getEnemyPatternText(I18N.getString(R.string.text_attack_pattern_prefix2))
            else
                bossAttackPattern2.visibility = View.GONE

            if (bossList[position].attackPatternList.size > 2)
                bossAttackPattern3.text = bossList[position].attackPatternList[2]
                    .getEnemyPatternText(I18N.getString(R.string.text_attack_pattern_prefix3))
            else
                bossAttackPattern3.visibility = View.GONE

            childrenRecycler.layoutManager = LinearLayoutManager(childrenRecycler.context)
            childrenRecycler.adapter = ClanBattleBossChildAdapter(bossList[position].children)

            bossSkillRecycler.layoutManager = LinearLayoutManager(childrenRecycler.context)
            bossSkillRecycler.adapter = ClanBattleBossSkillAdapter(bossList[position].skills)

            clickListener = View.OnClickListener {
                if (it?.id == R.id.boss_title_constraint){
                    sharedClanBattleVM.selectedBoss = boss
                    val action = ClanBattleViewPagerFragmentDirections.actionNavClanBattleViewPagerToNavClanBattleResist()
                    it.findNavController().navigate(action)
                }
            }

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

    fun setSharedClanBattleVM(clanBattle: SharedViewModelClanBattle){
        this.sharedClanBattleVM = clanBattle
    }

    class ClanBattleDetailsBossHolder internal constructor(val binding: ListItemClanBattleBossBinding) :
        RecyclerView.ViewHolder(binding.root)

}
