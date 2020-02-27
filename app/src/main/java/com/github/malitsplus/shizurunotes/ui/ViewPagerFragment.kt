package com.github.malitsplus.shizurunotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        viewPager.adapter =ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            CHARA_INDEX -> R.drawable.mic_queen
            CLAN_BATTLE_INDEX -> R.drawable.mic_defend
            MENU_INDEX -> R.drawable.mic_menu
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            CHARA_INDEX -> getString(R.string.title_chara_list)
            CLAN_BATTLE_INDEX -> getString(R.string.title_clan_battle)
            MENU_INDEX -> getString(R.string.title_menu)
            else -> null
        }
    }

}
