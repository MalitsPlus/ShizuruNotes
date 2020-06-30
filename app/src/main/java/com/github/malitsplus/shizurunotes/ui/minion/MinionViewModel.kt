package com.github.malitsplus.shizurunotes.ui.minion

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Enemy
import com.github.malitsplus.shizurunotes.data.Minion
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara

class MinionViewModel(
    private val sharedChara: SharedViewModelChara
): ViewModel() {

    var minionList: List<Any>? = null

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            return with(field) {
                clear()
                minionList?.forEach { minion ->
                    if (minion is Minion) {
                        //初始化属性，技能，行动顺序
                        minion.initialMinion(sharedChara.maxCharaLevel, sharedChara.maxCharaRank, sharedChara.selectedChara?.rarity ?: 5)
                        minion.attackPattern.forEach {
                            it.setItems(minion.skills, minion.atkType)
                        }

                        add(MinionBasicVT(minion))

                        for (i in 1..minion.attackPattern.size) {
                            add(TextTagVT(I18N.getString(R.string.text_attack_pattern, i)))
                            minion.attackPattern[i - 1].items.forEach {
                                add(AttackPatternVT(it))
                            }
                        }
                        minion.skills.forEach {
                            add(EnemySkillVT(it))
                        }
                        add(DividerVT())
                        add(SpaceVT())

                    } else if (minion is Enemy) {
                        //初始化属性，技能，行动顺序
                        minion.skills.forEach {
                            it.setActionDescriptions(it.enemySkillLevel, minion.property)
                        }
                        minion.attackPatternList.forEach {
                            it.setItems(minion.skills, minion.atkType)
                        }

                        add(EnemyBasicVT(minion))
                        for (i in 1..minion.attackPatternList.size) {
                            add(TextTagVT(I18N.getString(R.string.text_attack_pattern, i)))
                            minion.attackPatternList[i - 1].items.forEach {
                                add(AttackPatternVT(it))
                            }
                        }
                        minion.skills.forEach {
                            add(EnemySkillVT(it))
                        }
                        add(DividerVT())
                        add(SpaceVT())
                    }
                }
                field
            }
        }
}