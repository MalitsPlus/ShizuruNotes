package com.github.malitsplus.shizurunotes.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.malitsplus.shizurunotes.ui.charalist.CharaListFragment
import com.github.malitsplus.shizurunotes.ui.clanbattle.ClanBattleFragment
import com.github.malitsplus.shizurunotes.ui.drop.DropFragment
import com.github.malitsplus.shizurunotes.ui.menu.MenuFragment
import com.github.malitsplus.shizurunotes.ui.setting.SettingContainerFragment

const val CHARA_INDEX = 1
const val DROP = 0
const val CLAN_BATTLE_INDEX = 2
const val MENU_INDEX = 3

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
        CHARA_INDEX to { CharaListFragment() },
        DROP to { DropFragment() },
        CLAN_BATTLE_INDEX to { ClanBattleFragment() },
        MENU_INDEX to { MenuFragment() }
    )

    override fun getItemCount() = tabFragmentCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}