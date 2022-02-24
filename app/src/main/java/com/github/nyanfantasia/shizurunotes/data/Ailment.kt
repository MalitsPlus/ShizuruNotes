package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString

@Suppress("EnumEntryName")
class Ailment(type: Int, detail: Int) {
    inner class AilmentDetail {
        @JvmField
        var detail: Any? = null
        fun setDetail(obj: Any?) {
            detail = obj
        }

        fun description(): String {
            return when (detail) {
                is DotDetail -> {
                    (detail as DotDetail).description()
                }
                is ActionDetail -> {
                    (detail as ActionDetail).description()
                }
                is CharmDetail -> {
                    (detail as CharmDetail).description()
                }
                else -> {
                    getString(R.string.Unknown)
                }
            }
        }
    }

    enum class DotDetail(val value: Int) {
        detain(0),
        poison(1),
        burn(2),
        curse(3),
        violentPoison(4),
        hex(5),
        compensation(6),
        unknown(-1);

        fun description(): String {
            return when (this) {
                detain -> getString(R.string.Detain_Damage)
                poison -> getString(R.string.Poison)
                burn -> getString(R.string.Burn)
                curse -> getString(R.string.Curse)
                violentPoison -> getString(R.string.Violent_Poison)
                hex -> getString(R.string.Hex)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): DotDetail {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    enum class CharmDetail(val value: Int) {
        charm(0), confuse(1);

        fun description(): String {
            return when {
                this == charm -> getString(R.string.Charm)
                this == confuse -> getString(R.string.Confuse)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): CharmDetail? {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return null
            }
        }
    }

    enum class ActionDetail(val value: Int) {
        slow(1),
        haste(2),
        paralyse(3),
        freeze(4),
        bind(5),
        sleep(6),
        stun(7),
        petrify(8),
        detain(9),
        faint(10),
        timeStop(11),
        unknown(12);

        fun description(): String {
            return when (this) {
                slow -> getString(R.string.Slow)
                haste -> getString(R.string.Haste)
                paralyse -> getString(R.string.Paralyse)
                freeze -> getString(R.string.Freeze)
                bind -> getString(R.string.Bind)
                sleep -> getString(R.string.Sleep)
                stun -> getString(R.string.Stun)
                petrify -> getString(R.string.Petrify)
                detain -> getString(R.string.Detain)
                faint -> getString(R.string.Faint)
                timeStop -> getString(R.string.time_stop)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): ActionDetail {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    enum class AilmentType(val value: Int) {
        knockBack(3),
        action(8),
        dot(9),
        charm(11),
        darken(12),
        silence(13),
        confuse(19),
        instantDeath(30),
        countBlind(56),
        inhibitHeal(59),
        attackSeal(60),
        fear(61),
        awe(62),
        toad(69),
        maxHP(70),
        hPRegenerationDown(76),
        damageTakenIncreased(78),
        damageByBehaviour(79),
        unknown(80);

        fun description(): String {
            return when (this) {
                knockBack -> getString(R.string.Knock_Back)
                action -> getString(R.string.Action)
                dot -> getString(R.string.Dot)
                charm -> getString(R.string.Charm)
                darken -> getString(R.string.Blind)
                silence -> getString(R.string.Silence)
                instantDeath -> getString(R.string.Instant_Death)
                confuse -> getString(R.string.Confuse)
                countBlind -> getString(R.string.Count_Blind)
                inhibitHeal -> getString(R.string.Inhibit_Heal)
                fear -> getString(R.string.Fear)
                attackSeal -> getString(R.string.Seal)
                awe -> getString(R.string.Awe)
                toad -> getString(R.string.Polymorph)
                maxHP -> getString(R.string.Changing_Max_HP)
                hPRegenerationDown -> getString(
                    R.string.HP_Regeneration_Down
                )
                damageTakenIncreased -> getString(
                    R.string.Damage_Taken_Increased
                )
                damageByBehaviour -> getString(
                    R.string.Damage_By_Behaviour
                )
                else -> getString(R.string.Unknown_Effect)
            }
        }

        companion object {
            fun parse(value: Int): AilmentType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    @JvmField
    var ailmentType: AilmentType = AilmentType.parse(type)

    @JvmField
    var ailmentDetail: AilmentDetail?
    fun description(): String {
        return if (ailmentDetail != null) ailmentDetail!!.description() else ailmentType.description()
    }

    init {
        ailmentDetail = AilmentDetail()
        when (ailmentType) {
            AilmentType.action -> ailmentDetail!!.setDetail(ActionDetail.parse(detail))
            AilmentType.dot, AilmentType.damageByBehaviour -> ailmentDetail!!.setDetail(
                DotDetail.parse(
                    detail
                )
            )
            AilmentType.charm -> ailmentDetail!!.setDetail(CharmDetail.parse(detail))
            else -> ailmentDetail = null
        }
    }
}