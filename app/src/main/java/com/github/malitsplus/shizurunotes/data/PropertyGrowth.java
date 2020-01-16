package com.github.malitsplus.shizurunotes.data;

public class PropertyGrowth {
    int rarity;
    double atkGrowth;
    double defGrowth;
    double dodgeGrowth;
    double energyRecoveryRateGrowth;
    double energyReduceRateGrowth;
    double hpGrowth;
    double hpRecoveryRateGrowth;
    double lifeStealGrowth;
    double magicCriticalGrowth;
    double magicDefGrowth;
    double magicPenetrateGrowth;
    double magicStrGrowth;
    double physicalCriticalGrowth;
    double physicalPenetrateGrowth;
    double waveEnergyRecoveryGrowth;
    double waveHpRecoveryGrowth;
    double accuracyGrowth;

    public PropertyGrowth(){

    }

    public PropertyGrowth(int rarity, double atkGrowth, double defGrowth, double dodgeGrowth, double energyRecoveryRateGrowth, double energyReduceRateGrowth, double hpGrowth, double hpRecoveryRateGrowth, double lifeStealGrowth, double magicCriticalGrowth, double magicDefGrowth, double magicPenetrateGrowth, double magicStrGrowth, double physicalCriticalGrowth, double physicalPenetrateGrowth, double waveEnergyRecoveryGrowth, double waveHpRecoveryGrowth, double accuracyGrowth) {
        this.rarity = rarity;
        this.atkGrowth = atkGrowth;
        this.defGrowth = defGrowth;
        this.dodgeGrowth = dodgeGrowth;
        this.energyRecoveryRateGrowth = energyRecoveryRateGrowth;
        this.energyReduceRateGrowth = energyReduceRateGrowth;
        this.hpGrowth = hpGrowth;
        this.hpRecoveryRateGrowth = hpRecoveryRateGrowth;
        this.lifeStealGrowth = lifeStealGrowth;
        this.magicCriticalGrowth = magicCriticalGrowth;
        this.magicDefGrowth = magicDefGrowth;
        this.magicPenetrateGrowth = magicPenetrateGrowth;
        this.magicStrGrowth = magicStrGrowth;
        this.physicalCriticalGrowth = physicalCriticalGrowth;
        this.physicalPenetrateGrowth = physicalPenetrateGrowth;
        this.waveEnergyRecoveryGrowth = waveEnergyRecoveryGrowth;
        this.waveHpRecoveryGrowth = waveHpRecoveryGrowth;
        this.accuracyGrowth = accuracyGrowth;
    }

    public Property multiply(double multiplier){
        return new Property(
                this.atkGrowth * multiplier,
                this.defGrowth * multiplier,
                this.dodgeGrowth * multiplier,
                this.energyRecoveryRateGrowth * multiplier,
                this.energyReduceRateGrowth * multiplier,
                this.hpGrowth * multiplier,
                this.hpRecoveryRateGrowth * multiplier,
                this.lifeStealGrowth * multiplier,
                this.magicCriticalGrowth * multiplier,
                this.magicDefGrowth * multiplier,
                this.magicPenetrateGrowth * multiplier,
                this.magicStrGrowth * multiplier,
                this.physicalCriticalGrowth * multiplier,
                this.physicalPenetrateGrowth * multiplier,
                this.waveEnergyRecoveryGrowth * multiplier,
                this.waveHpRecoveryGrowth * multiplier,
                this.accuracyGrowth * multiplier
        );
    }
}
