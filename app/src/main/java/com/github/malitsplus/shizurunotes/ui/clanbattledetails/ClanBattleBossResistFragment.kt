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
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentClanBattleBossResistBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle

/**
 * A simple [Fragment] subclass.
 */
class ClanBattleBossResistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedClanBattleVM = ViewModelProvider(activity!!).get(SharedViewModelClanBattle::class.java)
        val binding = DataBindingUtil.inflate<FragmentClanBattleBossResistBinding>(
            inflater,
            R.layout.fragment_clan_battle_boss_resist,
            container,
            false
        ).apply {
            boss = sharedClanBattleVM.selectedBoss
            toolbarBossResistFragment.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            resistRecycler.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = ClanBattleBossResistAdapter(
                    sharedClanBattleVM.selectedBoss?.resistName ?: listOf(),
                    sharedClanBattleVM.selectedBoss?.resistRate ?: listOf()
                )
            }
        }



        return binding.root
    }

}
