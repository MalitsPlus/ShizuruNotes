package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import java.math.RoundingMode
import java.util.*

class EnchantLifeStealAction : ActionParameter() {
    private val stackValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        actionValues.add(ActionValue(actionValue1, actionValue2, null))
        stackValues.add(ActionValue(actionValue3, actionValue4, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Add_additional_s1_s2_to_s3_for_next_s4_attacks,
            buildExpression(level, property),
            PropertyKey.lifeSteal.description(),
            targetParameter.buildTargetClause(),
            buildExpression(level, stackValues, RoundingMode.FLOOR, property)
        )
    }
}