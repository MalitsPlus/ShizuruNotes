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
        return new Property(
            this.hp + rProperty.hp,
            this.atk + rProperty.atk,
            this.magicStr + rProperty.magicStr,
            this.def + rProperty.def,
            this.magicDef + rProperty.magicDef,
            this.physicalCritical + rProperty.physicalCritical,
            this.magicCritical + rProperty.magicCritical,
            this.waveHpRecovery + rProperty.waveHpRecovery,
            this.waveEnergyRecovery + rProperty.waveEnergyRecovery,
            this.dodge + rProperty.dodge,
            this.physicalPenetrate + rProperty.physicalPenetrate,
            this.magicPenetrate + rProperty.magicPenetrate,
            this.lifeSteal + rProperty.lifeSteal,
            this.hpRecoveryRate + rProperty.hpRecoveryRate,
            this.energyRecoveryRate + rProperty.energyRecoveryRate,
            this.energyReduceRate + rProperty.energyReduceRate,
            this.accuracy + rProperty.accuracy
        );
    }

    public Property multiply(double multiplier){

        return new Property(
            this.hp * multiplier,
            this.atk * multiplier,
            this.magicStr * multiplier,
            this.def * multiplier,
            this.magicDef * multiplier,
            this.physicalCritical * multiplier,
            this.magicCritical * multiplier,
            this.waveHpRecovery * multiplier,
            this.waveEnergyRecovery * multiplier,
            this.dodge * multiplier,
            this.physicalPenetrate * multiplier,
            this.magicPenetrate * multiplier,
            this.lifeSteal * multiplier,
            this.hpRecoveryRate * multiplier,
            this.energyRecoveryRate * multiplier,
            this.energyReduceRate * multiplier,
            this.accuracy * multiplier
        );
    }

    //region setters and getters

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public void setMagicStr(double magicStr) {
        this.magicStr = magicStr;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public void setMagicDef(double magicDef) {
        this.magicDef = magicDef;
    }

    public void setPhysicalCritical(double physicalCritical) {
        this.physicalCritical = physicalCritical;
    }

    public void setMagicCritical(double magicCritical) {
        this.magicCritical = magicCritical;
    }

    public void setWaveHpRecovery(double waveHpRecovery) {
        this.waveHpRecovery = waveHpRecovery;
    }

    public void setWaveEnergyRecovery(double waveEnergyRecovery) {
        this.waveEnergyRecovery = waveEnergyRecovery;
    }

    public void setDodge(double dodge) {
        this.dodge = dodge;
    }

    public void setPhysicalPenetrate(double physicalPenetrate) {
        this.physicalPenetrate = physicalPenetrate;
    }

    public void setMagicPenetrate(double magicPenetrate) {
        this.magicPenetrate = magicPenetrate;
    }

    public void setLifeSteal(double lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public void setHpRecoveryRate(double hpRecoveryRate) {
        this.hpRecoveryRate = hpRecoveryRate;
    }

    public void setEnergyRecoveryRate(double energyRecoveryRate) {
        this.energyRecoveryRate = energyRecoveryRate;
    }

    public void setEnergyReduceRate(double energyReduceRate) {
        this.energyReduceRate = energyReduceRate;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getHp() {
        return (int)Math.round(this.hp);
    }

    public int getAtk() {
        return (int)Math.round(this.atk);
    }

    public int getMagicStr() {
        return (int)Math.round(this.magicStr);
    }

    public int getDef() {
        return (int)Math.round(this.def);
    }

    public int getMagicDef() {
        return (int)Math.round(this.magicDef);
    }

    public int getPhysicalCritical() {
        return (int)Math.round(this.physicalCritical);
    }

    public int getMagicCritical() {
        return (int)Math.round(this.magicCritical);
    }

    public int getWaveHpRecovery() {
        return (int)Math.round(this.waveHpRecovery);
    }

    public int getWaveEnergyRecovery() {
        return (int)Math.round(this.waveEnergyRecovery);
    }

    public int getDodge() {
        return (int)Math.round(this.dodge);
    }

    public int getPhysicalPenetrate() {
        return (int)Math.round(this.physicalPenetrate);
    }

    public int getMagicPenetrate() {
        return (int)Math.round(this.magicPenetrate);
    }

    public int getLifeSteal() {
        return (int)Math.round(this.lifeSteal);
    }

    public int getHpRecoveryRate() {
        return (int)Math.round(this.hpRecoveryRate);
    }

    public int getEnergyRecoveryRate() {
        return (int)Math.round(this.energyRecoveryRate);
    }

    public int getEnergyReduceRate() {
        return (int)Math.round(this.energyReduceRate);
    }

    public int getAccuracy() {
        return (int)Math.round(this.accuracy);
    }


    //endregion

}
