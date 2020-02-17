package com.github.malitsplus.shizurunotes.data;

import android.provider.Telephony;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;

import java.util.ArrayList;
import java.util.List;

public enum PropertyKey {
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

    public List<PropertyKey> getKeys(){
        List<PropertyKey> all = new ArrayList<>();
        all.add(atk);
        all.add(def);
        all.add(dodge);
        all.add(energyRecoveryRate);
        all.add(energyReduceRate);
        all.add(hp);
        all.add(hpRecoveryRate);
        all.add(lifeSteal);
        all.add(magicCritical);
        all.add(magicDef);
        all.add(magicPenetrate);
        all.add(magicStr);
        all.add(physicalCritical);
        all.add(physicalPenetrate);
        all.add(waveEnergyRecovery);
        all.add(waveHpRecovery);
        all.add(accuracy);
        return all;
    }


    public String description(){
        switch (this){
            case atk: return I18N.getString(R.string.ATK);
            case def: return I18N.getString(R.string.DEF);
            case dodge: return I18N.getString(R.string.Dodge);
            case energyRecoveryRate: return I18N.getString(R.string.Energy_Recovery_Rate);
            case energyReduceRate: return I18N.getString(R.string.Energy_Reduce_Rate);
            case hp: return I18N.getString(R.string.HP);
            case hpRecoveryRate: return I18N.getString(R.string.HP_Recovery_Rate);
            case lifeSteal: return I18N.getString(R.string.Life_Steal);
            case magicCritical: return I18N.getString(R.string.Magic_Critical);
            case magicDef: return I18N.getString(R.string.Magic_DEF);
            case magicPenetrate: return I18N.getString(R.string.Magic_Penetrate);
            case magicStr: return I18N.getString(R.string.Magic_STR);
            case physicalCritical: return I18N.getString(R.string.Physical_Critical);
            case physicalPenetrate: return I18N.getString(R.string.Physical_Penetrate);
            case waveEnergyRecovery: return I18N.getString(R.string.Wave_Energy_Recovery);
            case waveHpRecovery: return I18N.getString(R.string.Wave_HP_Recovery);
            case accuracy: return I18N.getString(R.string.Accuracy);
            default: return I18N.getString(R.string.Unknown);
        }
    }
}
