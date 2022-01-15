package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class UpperLimitAttackAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.s_Damage_is_reduced_on_low_level_players,
            super.localizedDetail(level, property)
        )
    }
}