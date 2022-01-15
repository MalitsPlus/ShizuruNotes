package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class StopFieldAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Remove_field_of_skill_d1_1_represents_the_first_skill_in_this_list_effect_d2,
            actionDetail1 / 100 % 10,
            actionDetail1 % 10
        )
    }
}