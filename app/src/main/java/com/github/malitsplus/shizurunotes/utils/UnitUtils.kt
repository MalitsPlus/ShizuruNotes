package com.github.malitsplus.shizurunotes.utils

import android.icu.text.RelativeDateTimeFormatter
import kotlin.math.roundToInt

class UnitUtils {
    companion object {


        fun getCriticalRate(critical: Int, lLevel: Int, rLevel: Int): Double {
            val rate = critical * 0.05 * lLevel / rLevel * 0.01
            return if (rate > 1.0) {
                1.0
            } else {
                rate
            }
        }

        fun getHpAbsorbRate(lifeSteal: Int, rLevel: Int): Double {
            return lifeSteal / (100.0 + rLevel + lifeSteal)
        }

        fun getAccuracyRate(acc: Int, dod: Int): Double {
            val rate = 100.0 / (dod - acc + 100.0)
            return if (rate > 1.0) {
                1.0
            } else {
                rate
            }
        }

        fun getDodgeRate(acc: Int, dod: Int): Double {
            return 1 - getAccuracyRate(acc, dod)
        }

        fun getActualTpRecoveryValue(bonusType: TpBonusType, rate: Double, value: Double = 0.0): Int {
            val base = bonusType.getBaseValue(value)
            return (base * rate).roundToInt()
        }
    }

    enum class TpBonusType {
        Action,
        Kill,
        Recovery,
        ContinuousRecovery,
        Damaged;

        fun getBaseValue(value: Double = 0.0): Double {
            return when (this) {
                Action -> 90.0
                Kill -> 200.0
                else -> value
            }
        }
    }
}