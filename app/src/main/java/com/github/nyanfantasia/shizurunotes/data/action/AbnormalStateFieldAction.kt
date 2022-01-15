package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode
import java.util.*

class AbnormalStateFieldAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        durationValues.add(ActionValue(actionValue1, actionValue2, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Summon_a_field_of_radius_d1_on_s2_to_cast_effect_d3_for_s4_sec,
            actionValue3.value.toInt(),
            targetParameter.buildTargetClause(),
            actionDetail1 % 10,
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}