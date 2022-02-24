@file:Suppress("EnumEntryName")

package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import kotlin.math.roundToInt

class TriggerAction : ActionParameter() {
    internal enum class TriggerType(val value: Int) {
        unknown(0),
        dodge(1),
        damage(2),
        hp(3),
        dead(4),
        critical(5),
        criticalWithSummon(6),
        limitTime(7),
        stealthFree(8),
        Break(9),
        dot(10),
        allBreak(11);

        companion object {
            fun parse(value: Int): TriggerType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    private var triggerType: TriggerType? = null
    override fun childInit() {
        triggerType = TriggerType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return when (triggerType) {
            TriggerType.hp -> getString(
                R.string.Trigger_HP_is_below_d,
                actionValue3.value.roundToInt()
            )
            TriggerType.limitTime -> getString(
                R.string.Trigger_Left_time_is_below_s_sec,
                actionValue3.value.roundToInt()
            )
            TriggerType.damage -> getString(
                R.string.Trigger_d_on_damaged,
                actionValue1.value.roundToInt()
            )
            TriggerType.dead -> getString(
                R.string.Trigger_d_on_dead,
                actionValue1.value.roundToInt()
            )
            TriggerType.critical -> getString(
                R.string.Trigger_d_on_critical_damaged,
                actionValue1.value.roundToInt()
            )
            TriggerType.stealthFree -> getString(
                R.string.Trigger_d_on_stealth,
                actionValue1.value.roundToInt()
            )
            TriggerType.Break -> getString(
                R.string.Trigger_d1_on_break_and_last_for_s2_sec,
                actionValue1.value.roundToInt(),
                actionValue3.value
            )
            TriggerType.dot -> getString(
                R.string.Trigger_d_on_dot_damaged,
                actionValue1.value.roundToInt()
            )
            TriggerType.allBreak -> getString(
                R.string.Trigger_d_on_all_targets_break,
                actionValue1.value.roundToInt()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}