package com.github.malitsplus.shizurunotes.ui.minion

import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.ClanBattleBoss
import com.github.malitsplus.shizurunotes.databinding.ListItemMinionBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.basic.AttackPatternContainerAdapter
import com.github.malitsplus.shizurunotes.ui.basic.BasicRecyclerAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.adapters.ClanBattleBossSkillAdapter

class EnemyMinionAdapter(
    layout: Int,
    private val sharedClanBattle: SharedViewModelClanBattle
) : BasicRecyclerAdapter<ClanBattleBoss, ListItemMinionBinding>(layout)
{
    override fun onBindViewHolder(holder: VH<ListItemMinionBinding>, position: Int) {
        holder.binding.apply {
            val item = itemList[position]

            //初始化属性，技能
            item.skills.forEach {
                it.setActionDescriptions(it.enemySkillLevel, item.property)
            }

            //获取icon，虽然多半没有
//            Glide.with(minionIcon.context)
//                .load(item.iconUrl)
//                .placeholder(R.drawable.mic_chara_icon_place_holder)
//                .error(R.drawable.mic_chara_icon_error)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(minionIcon)

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
                layoutManager = LinearLayoutManager(context)
                adapter = AttackPatternContainerAdapter().apply { itemList = item.attackPatternList }
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