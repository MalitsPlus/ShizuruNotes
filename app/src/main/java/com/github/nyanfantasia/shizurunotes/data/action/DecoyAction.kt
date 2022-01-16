package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class DecoyAction : ActionParameter() {
    override fun childInit() {
        actionValues.add(ActionValue(actionValue1, actionValue2, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Make_s1_attract_enemy_attacks_last_for_s2_sec,
            targetParameter.buildTargetClause(), buildExpression(level, property)
        )
    }
}