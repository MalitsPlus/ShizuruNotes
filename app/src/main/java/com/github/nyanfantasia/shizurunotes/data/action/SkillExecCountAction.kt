package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class SkillExecCountAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Add_d1_to_the_counter_d2,
            actionDetail1,
            actionValue1.value.toInt()
        )
    }
}