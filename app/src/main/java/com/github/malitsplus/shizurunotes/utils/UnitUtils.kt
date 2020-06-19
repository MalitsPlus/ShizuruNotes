package com.github.malitsplus.shizurunotes.utils

class UnitUtils {
    companion object {

        fun getCriticalRate(critical: Int, lLevel: Int, rLevel: Int): Double {
            val rate = critical * 0.05 * lLevel / rLevel // * 0.01
            return if (rate > 100.00) {
                100.00
            } else {
                rate
            }
        }

    }
}