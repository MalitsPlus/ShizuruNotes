package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.R
import java.util.ArrayList

@Suppress("EnumEntryName")
enum class PropertyKey {
    atk,
    def,
    dodge,
    energyRecoveryRate,
    energyReduceRate,
    hp,
    hpRecoveryRate,
    lifeSteal,
    magicCritical,
    magicDef,
    magicPenetrate,
    magicStr,
    physicalCritical,
    physicalPenetrate,
    waveEnergyRecovery,
    waveHpRecovery,
    accuracy,
    unknown;

    val keys: List<PropertyKey>
        get() {
            val all: MutableList<PropertyKey> = ArrayList()
            all.add(atk)
            all.add(def)
            all.add(dodge)
            all.add(energyRecoveryRate)
            all.add(energyReduceRate)
            all.add(hp)
            all.add(hpRecoveryRate)
            all.add(lifeSteal)
            all.add(magicCritical)
            all.add(magicDef)
            all.add(magicPenetrate)
            all.add(magicStr)
            all.add(physicalCritical)
            all.add(physicalPenetrate)
            all.add(waveEnergyRecovery)
            all.add(waveHpRecovery)
            all.add(accuracy)
            return all
        }

    fun description(): String {
        return when (this) {
            atk -> getString(
                R.string.ATK
            )
            def -> getString(
                R.string.DEF
            )
            dodge -> getString(
                R.string.Dodge
            )
            energyRecoveryRate -> getString(
                R.string.Energy_Recovery_Rate
            )
            energyReduceRate -> getString(
                R.string.Energy_Reduce_Rate
            )
            hp -> getString(
                R.string.HP
            )
            hpRecoveryRate -> getString(
                R.string.HP_Recovery_Rate
            )
            lifeSteal -> getString(
                R.string.Life_Steal
            )
            magicCritical -> getString(
                R.string.Magic_Critical
            )
            magicDef -> getString(
                R.string.Magic_DEF
            )
            magicPenetrate -> getString(
                R.string.Magic_Penetrate
            )
            magicStr -> getString(
                R.string.Magic_STR
            )
            physicalCritical -> getString(
                R.string.Physical_Critical
            )
            physicalPenetrate -> getString(
                R.string.Physical_Penetrate
            )
            waveEnergyRecovery -> getString(
                R.string.Wave_Energy_Recovery
            )
            waveHpRecovery -> getString(
                R.string.Wave_HP_Recovery
            )
            accuracy -> getString(
                R.string.Accuracy
            )
            else -> getString(R.string.Unknown)
        }
    }
}