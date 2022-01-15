package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode
import java.util.*

class InhibitHealAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        durationValues.add(ActionValue(actionValue2, actionValue3, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.When_s1_receive_healing_deal_s2_healing_amount_damage_instead_last_for_s3_sec_or_unlimited_time_if_triggered_by_field,
            targetParameter.buildTargetClause(),
            actionValue1.valueString(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}