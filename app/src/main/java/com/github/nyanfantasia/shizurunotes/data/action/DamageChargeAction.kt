package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class DamageChargeAction : ActionParameter() {
    override fun childInit() {
        actionValues.add(ActionValue(actionValue1, actionValue2, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Charge_for_s1_sec_and_deal_s2_damage_taken_additional_damage_on_the_next_effect,
            actionValue3.valueString(), buildExpression(level, RoundingMode.UNNECESSARY, property)
        )
    }
}