package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class ChangeBodyWidthAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(R.string.Change_body_width_to_s, actionValue1.valueString())
    }
}