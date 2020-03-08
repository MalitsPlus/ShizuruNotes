package com.github.malitsplus.shizurunotes.ui.minion

import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Minion
import com.github.malitsplus.shizurunotes.databinding.ListItemMinionBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.base.AttackPatternContainerAdapter
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.adapters.ClanBattleBossSkillAdapter

class MinionAdapter(
    layout: Int,
    private val sharedChara: SharedViewModelChara
) : BaseRecyclerAdapter<Minion, ListItemMinionBinding>(layout)
{
    override fun onBindViewHolder(holder: VH<ListItemMinionBinding>, position: Int) {
        holder.binding.apply {
            val item = itemList[position]

            //初始化属性，技能
            item.initialMinion(sharedChara.maxCharaLevel, sharedChara.maxCharaRank, sharedChara.selectedChara?.rarity ?: 5)

            //设置控件文本
            textCastTime.text = I18N.getString(R.string.text_normal_attack_cast_time).format(item.normalAttackCastTime)
            minionName.text = item.unitName
            minionId.text = "ID：" + item.unitId.toString()

            //设置属性
            with(item.minionProperty) {
                txtSearchAreaWidth.setRightString(item.searchAreaWidth.toString())
                txtHp.setRightString(getHp().toString())
                txtAtk.setRightString(getAtk().toString())
                txtDef.setRightString(getDef().toString())
                txtMagicStr.setRightString(getMagicStr().toString())
                txtMagicDef.setRightString(getMagicDef().toString())
                this
            }

            //行动顺序
            item.attackPattern.forEach {
                it.setItems(item.skills, item.atkType)
            }
            minionAttackPattern.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = AttackPatternContainerAdapter().apply { itemList = item.attackPattern }
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