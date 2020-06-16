package com.github.malitsplus.shizurunotes.ui.enemy

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

class EnemyViewModel(
    val sharedClanBattle: SharedViewModelClanBattle
) : ViewModel() {

    val enemyList = sharedClanBattle.selectedEnemyList

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            return with(field) {
                clear()
                enemyList?.forEach { enemy ->
                    add(EnemyBasicVT(enemy))
                    enemy.children.forEach {
                        add(EnemyChildVT(it))
                    }
                    for (i in enemy.attackPatternList.indices) {
                        add(TextTagVT(I18N.getString(R.string.text_attack_pattern, i + 1)))
                        enemy.attackPatternList[i].items.forEach {
                            add(AttackPatternVT(it))
                        }
                    }
                    enemy.skills.forEach {
                        add(EnemySkillVT(it))
                    }
                    add(TextTagVT(I18N.getString(R.string.text_resist_data)))
                    enemy.resistMap?.forEach {
                        add(StringIntVT(it))
                    }
                    add(SpaceVT())
                }
                field
            }
        }
}

interface OnEnemyActionListener: OnItemActionListener {
    fun onMinionClicked(skill: Skill)
}