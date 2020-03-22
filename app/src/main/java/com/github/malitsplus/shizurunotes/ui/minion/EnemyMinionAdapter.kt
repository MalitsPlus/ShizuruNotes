package com.github.malitsplus.shizurunotes.ui.minion

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Enemy
import com.github.malitsplus.shizurunotes.databinding.ListItemMinionBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.base.AttackPatternContainerAdapter
import com.github.malitsplus.shizurunotes.ui.base.BaseHintAdapter
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattle.clanbattledetails.adapters.ClanBattleBossSkillAdapter

class EnemyMinionAdapter(
    layout: Int,
    private val sharedClanBattle: SharedViewModelClanBattle
) : BaseRecyclerAdapter<Enemy, ListItemMinionBinding>(layout)
{
    override fun onBindViewHolder(holder: VH<ListItemMinionBinding>, position: Int) {
        holder.binding.apply {
            val item = itemList[position]

            //初始化属性，技能
            item.skills.forEach {
                it.setActionDescriptions(it.enemySkillLevel, item.property)
            }

            //设置控件文本
            textCastTime.text = I18N.getString(R.string.text_normal_attack_cast_time).format(item.normalAtkCastTime)
            minionName.text = item.name
            minionId.text = "ID：" + item.enemyId.toString()

            //设置属性
            with(item.property) {
                txtSearchAreaWidth.setRightString(item.searchAreaWidth.toString())
                txtHp.setRightString(getHp().toString())
                txtAtk.setRightString(getAtk().toString())
                txtDef.setRightString(getDef().toString())
                txtMagicStr.setRightString(getMagicStr().toString())
                txtMagicDef.setRightString(getMagicDef().toString())
                this
            }

            //行动顺序
            item.attackPatternList.forEach {
                it.setItems(item.skills, item.atkType)
            }

            minionAttackPattern.apply {
                val attackPatternAdapter = AttackPatternContainerAdapter(context).apply {
                    initializeItems(item.attackPatternList)
                }
                layoutManager = GridLayoutManager(context, 6).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return when(attackPatternAdapter.getItemViewType(position)) {
                                BaseHintAdapter.HINT_TEXT -> 6
                                else -> 1
                            }
                        }
                    }
                }
                adapter = attackPatternAdapter
            }

            //技能部分
            minionSkillRecycler.apply {
                layoutManager = LinearLayoutManager(minionSkillRecycler.context)
                adapter =
                    ClanBattleBossSkillAdapter(
                        item.skills,
                        null
                    )
            }

            executePendingBindings()
        }
    }
}