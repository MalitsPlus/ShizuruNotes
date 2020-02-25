package com.github.malitsplus.shizurunotes.ui.minion

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Minion
import com.github.malitsplus.shizurunotes.databinding.ListItemMinionBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.basic.BasicRecyclerAdapter
import com.github.malitsplus.shizurunotes.ui.charadetails.AttackPatternAdapter
import com.github.malitsplus.shizurunotes.ui.charadetails.SkillAdapter
import com.github.malitsplus.shizurunotes.ui.clanbattledetails.ClanBattleBossSkillAdapter

class MinionAdapter(layout: Int, private val sharedChara: SharedViewModelChara) : BasicRecyclerAdapter<Minion, ListItemMinionBinding>(layout) {

    override fun onBindViewHolder(holder: VH<ListItemMinionBinding>, position: Int) {
        holder.binding.apply {
            val item = itemList[position]

            //初始化属性，技能
            item.initialMinion(sharedChara.maxCharaLevel, sharedChara.maxCharaRank, sharedChara.selectedChara?.rarity ?: 5)

            //获取icon，虽然多半没有
            Glide.with(minionIcon.context)
                .load(item.iconUrl)
                .placeholder(R.drawable.mic_chara_icon_place_holder)
                .error(R.drawable.mic_chara_icon_error)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(minionIcon)

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

            //只取一个应该够了（懒
            minionAttackPattern.apply {
                layoutManager = GridLayoutManager(context, 6)
                val attackPatternItems = item.attackPattern[0].setItems(item.skills, item.atkType)
                adapter = AttackPatternAdapter().apply { itemList = attackPatternItems.items }
            }

            //技能部分
            minionSkillRecycler.apply {
                layoutManager = LinearLayoutManager(minionSkillRecycler.context)
                adapter = ClanBattleBossSkillAdapter(item.skills)
            }

            executePendingBindings()
        }
    }
}