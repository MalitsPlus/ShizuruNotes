package com.github.malitsplus.shizurunotes.ui.clanbattledetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.ClanBattlePhase
import com.github.malitsplus.shizurunotes.databinding.FragmentClanBattleDetailsBinding

class ClanBattleDetailsFragment(
    val phase: ClanBattlePhase
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentClanBattleDetailsBinding>(
            inflater, R.layout.fragment_clan_battle_details, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            clanBattleBossRecycler.layoutManager = LinearLayoutManager(context)
            clanBattleBossRecycler.adapter = ClanBattleDetailsBossAdapter(context!!, phase.bossList)
        }

        return binding.root
    }

}
