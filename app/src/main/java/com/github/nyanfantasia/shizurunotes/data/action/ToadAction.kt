package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode
import java.util.*

class ToadAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        durationValues.add(ActionValue(actionValue1, actionValue2, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Polymorph_s1_for_s2_sec,
            targetParameter.buildTargetClause(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}