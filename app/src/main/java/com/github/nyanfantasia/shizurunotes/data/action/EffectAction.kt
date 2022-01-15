package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class EffectAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Implement_some_visual_effects_to_s1,
            targetParameter.buildTargetClause()
        )
    }
}