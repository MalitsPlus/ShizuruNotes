package com.github.nyanfantasia.shizurunotes.ui.clanbattle.clanbattledetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.ClanBattlePhase
import com.github.nyanfantasia.shizurunotes.databinding.FragmentClanBattleDetailsBinding
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.nyanfantasia.shizurunotes.ui.clanbattle.clanbattledetails.adapters.ClanBattlePhaseAdapter

class ClanBattleDetailsFragment(
    val phase: ClanBattlePhase
) : Fragment() {

    lateinit var sharedClanBattle: SharedViewModelClanBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentClanBattleDetailsBinding>(
            inflater, R.layout.fragment_clan_battle_details, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner

            clanBattleBossRecycler.layoutManager = LinearLayoutManager(context)
            clanBattleBossRecycler.adapter = ClanBattlePhaseAdapter(
                phase.bossList
            ).apply {
                setSharedClanBattleVM(sharedClanBattle)
            }
        }
        return binding.root
    }
}
