package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils

class SealAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return if (actionValue4.value >= 0) getString(
            R.string.Add_s1_mark_stacks_max_s2_ID_s3_on_s4_for_s5_sec,
            Utils.roundDownDouble(actionValue4.value),
            Utils.roundDownDouble(actionValue1.value),
            Utils.roundDownDouble(actionValue2.value),
            targetParameter.buildTargetClause(),
            Utils.roundDouble(actionValue3.value)
        ) else getString(
            R.string.Remove_s1_mark_stacks_ID_s2_on_s3,
            Utils.roundDownDouble(-actionValue4.value),
            Utils.roundDownDouble(actionValue2.value),
            targetParameter.buildTargetClause()
        )
    }
}