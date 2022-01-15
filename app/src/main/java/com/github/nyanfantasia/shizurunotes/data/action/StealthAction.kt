package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class StealthAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(R.string.Stealth_for_s_sec, actionValue1.valueString())
    }
}