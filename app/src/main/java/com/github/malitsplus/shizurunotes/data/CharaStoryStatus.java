package com.github.malitsplus.shizurunotes.data;

import android.util.SparseArray;

import java.util.HashMap;

public class CharaStoryStatus {

    public int charaId;
    public int statusType;
    public double statusRate;

    public CharaStoryStatus(int charaId, int statusType, double statusRate){
        this.charaId = charaId;
        this.statusType = statusType;
        this.statusRate = statusRate;
    }


    public Property getProperty(){
        Property property = new Property();
        switch (statusType){
            case 1:
                property.hp = statusRate;
                break;
            case 2:
                property.atk = statusRate;
                break;
            case 3:
                property.def = statusRate;
                break;
            case 4:
                property.magicStr = statusRate;
                break;
            case 5:
                property.magicDef = statusRate;
                break;
            case 6:
                property.physicalCritical = statusRate;
                break;
            case 7:
                property.magicCritical = statusRate;
                break;
            case 8:
                property.dodge = statusRate;
                break;
            case 9:
                property.lifeSteal = statusRate;
                break;
            case 10:
                property.waveHpRecovery = statusRate;
                break;
            case 11:
                property.waveEnergyRecovery = statusRate;
                break;
            case 14:
                property.energyRecoveryRate = statusRate;
                break;
            case 15:
                property.hpRecoveryRate = statusRate;
                break;
            case 17:
                property.accuracy = statusRate;
                break;
            default:
                break;
        }
        return property;
    }

}
