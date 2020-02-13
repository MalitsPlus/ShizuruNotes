package com.github.malitsplus.shizurunotes.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.malitsplus.shizurunotes.ui.charalist.CharaListFragment
import com.github.malitsplus.shizurunotes.ui.clanbattle.ClanbattleFragment
import com.github.malitsplus.shizurunotes.ui.setting.SettingContainerFragment
import com.github.malitsplus.shizurunotes.ui.setting.SettingFragment

const val CHARA_INDEX = 0
const val CLAN_BATTLE_INDEX = 1
const val SETTING_INDEX = 2

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
        CHARA_INDEX to { CharaListFragment() },
        CLAN_BATTLE_INDEX to { ClanbattleFragment() },
        SETTING_INDEX to { SettingContainerFragment() }
    )

    override fun getItemCount() = tabFragmentCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}