package com.github.malitsplus.shizurunotes.data;

import androidx.annotation.Nullable;

import com.github.malitsplus.shizurunotes.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Property {
    public double hp;
    public double atk;
    public double magicStr;
    public double def;
    public double magicDef;
    public double physicalCritical;
    public double magicCritical;
    public double waveHpRecovery;
    public double waveEnergyRecovery;
    public double dodge;
    public double physicalPenetrate;
    public double magicPenetrate;
    public double lifeSteal;
    public double hpRecoveryRate;
    public double energyRecoveryRate;
    public double energyReduceRate;
    public double accuracy;

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

    public Property reverse() {
        return new Property(
                -this.hp,
                -this.atk,
                -this.magicStr,
                -this.def,
                -this.magicDef,
                -this.physicalCritical,
                -this.magicCritical,
                -this.waveHpRecovery,
                -this.waveEnergyRecovery,
                -this.dodge,
                -this.physicalPenetrate,
                -this.magicPenetrate,
                -this.lifeSteal,
                -this.hpRecoveryRate,
                -this.energyRecoveryRate,
                -this.energyReduceRate,
                -this.accuracy
        );
    }

    public Property plus(@Nullable Property rProperty){
        if (rProperty == null)
            return this;
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

    public Property plusEqual(@Nullable Property rProperty){
        if (rProperty == null)
            return this;
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
        return this;
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

    public Property multiplyEqual(double multiplier){
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

    public Property getCeiled() {
        return new Property(
                Math.ceil(hp),
                Math.ceil(atk),
                Math.ceil(magicStr),
                Math.ceil(def),
                Math.ceil(magicDef),
                Math.ceil(physicalCritical),
                Math.ceil(magicCritical),
                Math.ceil(waveHpRecovery),
                Math.ceil(waveEnergyRecovery),
                Math.ceil(dodge),
                Math.ceil(physicalPenetrate),
                Math.ceil(magicPenetrate),
                Math.ceil(lifeSteal),
                Math.ceil(hpRecoveryRate),
                Math.ceil(energyRecoveryRate),
                Math.ceil(energyReduceRate),
                Math.ceil(accuracy)
        );
    }

    public Property roundThenSubtract(Property rProperty) {
        return new Property(
                this.getHp() - rProperty.getHp(),
                this.getAtk() - rProperty.getAtk(),
                this.getMagicStr() - rProperty.getMagicStr(),
                this.getDef() - rProperty.getDef(),
                this.getMagicDef() - rProperty.getMagicDef(),
                this.getPhysicalCritical() - rProperty.getPhysicalCritical(),
                this.getMagicCritical() - rProperty.getMagicCritical(),
                this.getWaveHpRecovery() - rProperty.getWaveHpRecovery(),
                this.getWaveEnergyRecovery() - rProperty.getWaveEnergyRecovery(),
                this.getDodge() - rProperty.getDodge(),
                this.getPhysicalPenetrate() - rProperty.getPhysicalPenetrate(),
                this.getMagicPenetrate() - rProperty.getMagicPenetrate(),
                this.getLifeSteal() - rProperty.getLifeSteal(),
                this.getHpRecoveryRate() - rProperty.getHpRecoveryRate(),
                this.getEnergyRecoveryRate() - rProperty.getEnergyRecoveryRate(),
                this.getEnergyReduceRate() - rProperty.getEnergyReduceRate(),
                this.getAccuracy() - rProperty.getAccuracy()
        );
    }

    public static Property getPropertyWithKeyAndValue(Property property, PropertyKey key, double value){
        if(property == null)
            property = new Property();

        switch (key){
            case atk:
                property.atk += value;
                return property;
            case def:
                property.def += value;
                return property;
            case dodge:
                property.dodge += value;
                return property;
            case energyRecoveryRate:
                property.energyRecoveryRate += value;
                return property;
            case energyReduceRate:
                property.energyReduceRate += value;
                return property;
            case hp:
                property.hp += value;
                return property;
            case hpRecoveryRate:
                property.hpRecoveryRate += value;
                return property;
            case lifeSteal:
                property.lifeSteal += value;
                return property;
            case magicCritical:
                property.magicCritical += value;
                return property;
            case magicDef:
                property.magicDef += value;
                return property;
            case magicPenetrate:
                property.magicPenetrate += value;
                return property;
            case magicStr:
                property.magicStr += value;
                return property;
            case physicalCritical:
                property.physicalCritical += value;
                return property;
            case physicalPenetrate:
                property.physicalPenetrate += value;
                return property;
            case waveEnergyRecovery:
                property.waveEnergyRecovery += value;
                return property;
            case waveHpRecovery:
                property.waveHpRecovery += value;
                return property;
            case accuracy:
                property.accuracy += value;
                return property;
            default:
                return property;
        }
    }

    public double getItem(PropertyKey key){
        switch (key){
            case atk:
                return atk;
            case def:
                return def;
            case dodge:
                return dodge;
            case energyRecoveryRate:
                return energyRecoveryRate;
            case energyReduceRate:
                return energyReduceRate;
            case hp:
                return hp;
            case hpRecoveryRate:
                return hpRecoveryRate;
            case lifeSteal:
                return lifeSteal;
            case magicCritical:
                return magicCritical;
            case magicDef:
                return magicDef;
            case magicPenetrate:
                return magicPenetrate;
            case magicStr:
                return magicStr;
            case physicalCritical:
                return physicalCritical;
            case physicalPenetrate:
                return physicalPenetrate;
            case waveEnergyRecovery:
                return waveEnergyRecovery;
            case waveHpRecovery:
                return waveHpRecovery;
            case accuracy:
                return accuracy;
            default:
                return 0;
        }
    }

    public Map<PropertyKey, Integer> getNonZeroPropertiesMap() {
        HashMap<PropertyKey, Integer> map = new HashMap<>();
        for (PropertyKey key : PropertyKey.values()) {
            int value = (int) Math.ceil(getItem(key));
            if (value != 0.0) {
                map.put(key, value);
            }
        }
        return map;
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
