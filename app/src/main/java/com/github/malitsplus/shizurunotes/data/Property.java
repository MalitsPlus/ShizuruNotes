package com.github.malitsplus.shizurunotes.data;

public class Property {
    double atk;
    double def;
    double dodge;
    double energyRecoveryRate;
    double energyReduceRate;
    double hp;
    double hpRecoveryRate;
    double lifeSteal;
    double magicCritical;
    double magicDef;
    double magicPenetrate;
    double magicStr;
    double physicalCritical;
    double physicalPenetrate;
    double waveEnergyRecovery;
    double waveHpRecovery;
    double accuracy;

    public Property(){

    }

    public Property(double atk, double def, double dodge, double energyRecoveryRate, double energyReduceRate, double hp, double hpRecoveryRate, double lifeSteal, double magicCritical, double magicDef, double magicPenetrate, double magicStr, double physicalCritical, double physicalPenetrate, double waveEnergyRecovery, double waveHpRecovery, double accuracy) {
        this.atk = atk;
        this.def = def;
        this.dodge = dodge;
        this.energyRecoveryRate = energyRecoveryRate;
        this.energyReduceRate = energyReduceRate;
        this.hp = hp;
        this.hpRecoveryRate = hpRecoveryRate;
        this.lifeSteal = lifeSteal;
        this.magicCritical = magicCritical;
        this.magicDef = magicDef;
        this.magicPenetrate = magicPenetrate;
        this.magicStr = magicStr;
        this.physicalCritical = physicalCritical;
        this.physicalPenetrate = physicalPenetrate;
        this.waveEnergyRecovery = waveEnergyRecovery;
        this.waveHpRecovery = waveHpRecovery;
        this.accuracy = accuracy;
    }


    public Property plus(Property rProperty){
        this.atk += rProperty.atk;
        this.def += rProperty.def;
        this.dodge += rProperty.dodge;
        this.energyRecoveryRate += rProperty.energyRecoveryRate;
        this.energyReduceRate += rProperty.energyReduceRate;
        this.hp += rProperty.hp;
        this.hpRecoveryRate += rProperty.hpRecoveryRate;
        this.lifeSteal += rProperty.lifeSteal;
        this.magicCritical += rProperty.magicCritical;
        this.magicDef += rProperty.magicDef;
        this.magicPenetrate += rProperty.magicPenetrate;
        this.magicStr += rProperty.magicStr;
        this.physicalCritical += rProperty.physicalCritical;
        this.physicalPenetrate += rProperty.physicalPenetrate;
        this.waveEnergyRecovery += rProperty.waveEnergyRecovery;
        this.waveHpRecovery += rProperty.waveHpRecovery;
        this.accuracy += rProperty.accuracy;

        return  this;
    }
}
