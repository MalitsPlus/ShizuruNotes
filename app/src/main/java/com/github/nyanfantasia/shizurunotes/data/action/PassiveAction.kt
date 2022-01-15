package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey

class PassiveAction : ActionParameter() {
    private var propertyKey: PropertyKey? = null
    override fun childInit() {
        propertyKey = when (actionDetail1) {
            1 -> PropertyKey.hp
            2 -> PropertyKey.atk
            3 -> PropertyKey.def
            4 -> PropertyKey.magicStr
            5 -> PropertyKey.magicDef
            else -> PropertyKey.unknown
        }
        actionValues.add(ActionValue(actionValue2, actionValue3, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Raise_s1_s2,
            buildExpression(level, property),
            propertyKey!!.description()
        )
    }

    fun propertyItem(level: Int): Property {
        return Property.getPropertyWithKeyAndValue(
            null,
            propertyKey,
            actionValue2.value + actionValue3.value * level
        )
    }
}