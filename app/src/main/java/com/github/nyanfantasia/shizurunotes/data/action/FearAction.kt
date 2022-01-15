package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode
import java.util.*

class FearAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    private var chanceValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        durationValues.add(ActionValue(actionValue1, actionValue2, null))
        chanceValues.add(ActionValue(actionValue3, actionValue4, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Fear_s1_with_s2_chance_for_s3_sec,
            targetParameter.buildTargetClause(),
            buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}