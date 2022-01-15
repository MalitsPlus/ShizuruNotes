package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class ChangeEnergyRecoveryRatioByDamageAction : ActionParameter() {

    private val childrenActionString: String
        get() {
            val childrenActionString = StringBuilder()
            if (childrenAction != null) {
                for (action in childrenAction!!) {
                    childrenActionString.append(action.actionId % 10).append(", ")
                }
                childrenActionString.delete(
                    childrenActionString.lastIndexOf(", "),
                    childrenActionString.length
                )
            }
            return childrenActionString.toString()
        }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.change_energy_recovery_ratio_of_action_s1_to_s2_when_s3_get_damage,
            childrenActionString,
            actionValue1.valueString(),
            targetParameter.buildTargetClause()
        )
    }
}