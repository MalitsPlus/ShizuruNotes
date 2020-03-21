package com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod
import com.github.malitsplus.shizurunotes.databinding.FragmentClanBattleViewPagerBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails.adapters.ClanBattleViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ClanBattleViewPagerFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModelClanBattle
    private lateinit var period: ClanBattlePeriod

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentClanBattleViewPagerBinding.inflate(inflater, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModelClanBattle::class.java)
        period = sharedViewModel.selectedPeriod!!

        val tabLayout = binding.clanBattleViewPagerTab
        val viewPager = binding.clanBattleViewPager2
        viewPager.adapter =
            ClanBattleViewPagerAdapter(
                this,
                period.phaseList
            )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        binding.clanBattleViewPagerToolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return if (position < period.phaseList.size)
            I18N.getString(R.string.tab_phase) + period.phaseList[position].phase
        else
            null
    }

}
