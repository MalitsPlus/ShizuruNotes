package com.github.nyanfantasia.shizurunotes.data

class CharaStoryStatus(var charaId: Int, var statusType: Int, var statusRate: Double) {
    val property: Property
        get() {
            val property = Property()
            when (statusType) {
                1 -> property.hp = statusRate
                2 -> property.atk = statusRate
                3 -> property.def = statusRate
                4 -> property.magicStr = statusRate
                5 -> property.magicDef = statusRate
                6 -> property.physicalCritical = statusRate
                7 -> property.magicCritical = statusRate
                8 -> property.dodge = statusRate
                9 -> property.lifeSteal = statusRate
                10 -> property.waveHpRecovery = statusRate
                11 -> property.waveEnergyRecovery = statusRate
                12 -> property.physicalPenetrate = statusRate
                13 -> property.magicPenetrate = statusRate
                14 -> property.energyRecoveryRate = statusRate
                15 -> property.hpRecoveryRate = statusRate
                16 -> property.energyReduceRate = statusRate
                17 -> property.accuracy = statusRate
                else -> {}
            }
            return property
        }
}