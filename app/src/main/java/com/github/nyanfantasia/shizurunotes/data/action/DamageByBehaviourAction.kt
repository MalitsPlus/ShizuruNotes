package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Ailment
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode
import java.util.*

internal class DamageByBehaviourAction : ActionParameter() {
    private var ailment: Ailment? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        ailment = Ailment(rawActionType, actionDetail1)
        actionValues.add(ActionValue(actionValue1, actionValue2, null))
        durationValues.add(ActionValue(actionValue3, actionValue4, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.s1_will_be_applied_the_s2_once_they_take_any_actions_will_take_s3_damage_every_second_lasted_4s_seconds,
            targetParameter.buildTargetClause(),
            ailment!!.description(),
            buildExpression(level, property),
            buildExpression(level, durationValues, RoundingMode.HALF_UP, property)
        )
    }
}