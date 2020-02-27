package com.github.malitsplus.shizurunotes.ui.clanbattledetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentClanBattleBossDetailsBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.basic.AttackPatternContainerAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.adapters.ClanBattleBossChildAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.adapters.ClanBattleBossResistAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.adapters.ClanBattleBossSkillAdapter

/**
 * A simple [Fragment] subclass.
 */
class ClanBattleBossDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedClanBattleVM = ViewModelProvider(activity!!).get(SharedViewModelClanBattle::class.java)
        val binding = DataBindingUtil.inflate<FragmentClanBattleBossDetailsBinding>(
            inflater,
            R.layout.fragment_clan_battle_boss_details,
            container,
            false
        ).apply {
            boss = sharedClanBattleVM.selectedBoss

            sharedClanBattleVM.selectedBoss?.let {
                bossAttackPatternRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = AttackPatternContainerAdapter().apply { itemList = it.attackPatternList }
                    setHasFixedSize(true)
                }

                childrenRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ClanBattleBossChildAdapter(it.children)
                    setHasFixedSize(true)
                }

                bossSkillRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ClanBattleBossSkillAdapter(it.skills, sharedClanBattleVM)
                    setHasFixedSize(true)
                }

                clanBattleBossDetailsToolbar.title = it.name

//                resistRecycler.apply {
//                    layoutManager = GridLayoutManager(context, 2)
//                    adapter = ClanBattleBossResistAdapter(
//                        sharedClanBattleVM.selectedBoss?.resistName ?: listOf(),
//                        sharedClanBattleVM.selectedBoss?.resistRate ?: listOf()
//                        setHasFixedSize(true)
//                    )
//                }

                it
            }

            clanBattleBossDetailsToolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

}
