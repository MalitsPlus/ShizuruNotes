package com.github.malitsplus.shizurunotes.data;

public class Property {
    double hp;
    double atk;
    double magicStr;
    double def;
    double magicDef;
    double physicalCritical;
    double magicCritical;
    double waveHpRecovery;
    double waveEnergyRecovery;
    double dodge;
    double physicalPenetrate;
    double magicPenetrate;
    double lifeSteal;
    double hpRecoveryRate;
    double energyRecoveryRate;
    double energyReduceRate;
    double accuracy;

    public Property(){

    }

    public Property(double hp, double atk, double magicStr, double def, double magicDef, double physicalCritical, double magicCritical, double waveHpRecovery, double waveEnergyRecovery, double dodge, double physicalPenetrate, double magicPenetrate, double lifeSteal, double hpRecoveryRate, double energyRecoveryRate, double energyReduceRate, double accuracy) {
        this.hp = hp;
        this.atk = atk;
        this.magicStr = magicStr;
        this.def = def;
        this.magicDef = magicDef;
        this.physicalCritical = physicalCritical;
        this.magicCritical = magicCritical;
        this.waveHpRecovery = waveHpRecovery;
        this.waveEnergyRecovery = waveEnergyRecovery;
        this.dodge = dodge;
        this.physicalPenetrate = physicalPenetrate;
        this.magicPenetrate = magicPenetrate;
        this.lifeSteal = lifeSteal;
        this.hpRecoveryRate = hpRecoveryRate;
        this.energyRecoveryRate = energyRecoveryRate;
        this.energyReduceRate = energyReduceRate;
        this.accuracy = accuracy;
    }

    public Property plus(Property rProperty){
        this.hp += rProperty.hp;
        this.atk += rProperty.atk;
        this.magicStr += rProperty.magicStr;
        this.def += rProperty.def;
        this.magicDef += rProperty.magicDef;
        this.physicalCritical += rProperty.physicalCritical;
        this.magicCritical += rProperty.magicCritical;
        this.waveHpRecovery += rProperty.waveHpRecovery;
        this.waveEnergyRecovery += rProperty.waveEnergyRecovery;
        this.dodge += rProperty.dodge;
        this.physicalPenetrate += rProperty.physicalPenetrate;
        this.magicPenetrate += rProperty.magicPenetrate;
        this.lifeSteal += rProperty.lifeSteal;
        this.hpRecoveryRate += rProperty.hpRecoveryRate;
        this.energyRecoveryRate += rProperty.energyRecoveryRate;
        this.energyReduceRate += rProperty.energyReduceRate;
        this.accuracy += rProperty.accuracy;

        return  this;
    }

    public Property multiply(double multiplier){
        this.hp *= multiplier;
        this.atk *= multiplier;
        this.magicStr *= multiplier;
        this.def *= multiplier;
        this.magicDef *= multiplier;
        this.physicalCritical *= multiplier;
        this.magicCritical *= multiplier;
        this.waveHpRecovery *= multiplier;
        this.waveEnergyRecovery *= multiplier;
        this.dodge *= multiplier;
        this.physicalPenetrate *= multiplier;
        this.magicPenetrate *= multiplier;
        this.lifeSteal *= multiplier;
        this.hpRecoveryRate *= multiplier;
        this.energyRecoveryRate *= multiplier;
        this.energyReduceRate *= multiplier;
        this.accuracy *= multiplier;

        return this;
    }
}
