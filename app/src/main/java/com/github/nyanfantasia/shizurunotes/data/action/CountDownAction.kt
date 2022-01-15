package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class CountDownAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Set_a_countdown_timer_on_s1_trigger_effect_d2_after_s3_sec,
            targetParameter.buildTargetClause(), actionDetail1 % 10, actionValue1.valueString()
        )
    }
}