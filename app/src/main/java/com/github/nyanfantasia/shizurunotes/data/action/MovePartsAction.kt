package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class MovePartsAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Move_Part_d1_d2_forward_then_return,
            actionValue4.value.toInt(), (-actionValue1.value).toInt()
        )
    }
}