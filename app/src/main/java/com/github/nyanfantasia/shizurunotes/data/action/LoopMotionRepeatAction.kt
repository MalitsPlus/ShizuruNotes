package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class LoopMotionRepeatAction : ActionParameter() {
    private var successClause: String? = null
    private var failureClause: String? = null
    override fun childInit() {
        super.childInit()
        if (actionDetail2 != 0) successClause =
            getString(R.string.use_d_after_time_up, actionDetail2 % 10)
        if (actionDetail3 != 0) failureClause =
            getString(R.string.use_d_after_break, actionDetail3 % 10)
    }

    override fun localizedDetail(level: Int, property: Property): String {
        val mainClause = getString(
            R.string.Repeat_effect_d1_every_s2_sec_up_to_s3_sec_break_if_taken_more_than_s4_damage,
            actionDetail1 % 10,
            actionValue2.valueString(),
            actionValue1.valueString(),
            actionValue3.valueString()
        )
        return if (successClause != null && failureClause != null) mainClause + successClause + failureClause else if (successClause != null) mainClause + successClause else if (failureClause != null) mainClause + failureClause else mainClause
    }
}