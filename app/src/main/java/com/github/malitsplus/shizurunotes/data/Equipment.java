package com.github.malitsplus.shizurunotes.data;

public class Equipment {
    public int equipmentId;
    public String equipmentName;
    public int maxEnhanceLevel;
    public Property equipmentData;
    public Property equipmentEnhanceRate;

    public Equipment(){

    }

    public Equipment(int equipmentId, String equipmentName, int maxEnhanceLevel, Property equipmentData, Property equipmentEnhanceRate) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.maxEnhanceLevel = maxEnhanceLevel;
        this.equipmentData = equipmentData;
        this.equipmentEnhanceRate = equipmentEnhanceRate;
    }

    public Property getCeiledProperty(){
        return equipmentData.plus(equipmentEnhanceRate.multiply(maxEnhanceLevel));
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getMaxEnhanceLevel() {
        return maxEnhanceLevel;
    }

    public void setMaxEnhanceLevel(int maxEnhanceLevel) {
        this.maxEnhanceLevel = maxEnhanceLevel;
    }

    public Property getEquipmentData() {
        return equipmentData;
    }

    public void setEquipmentData(Property equipmentData) {
        this.equipmentData = equipmentData;
    }

    public Property getEquipmentEnhanceRate() {
        return equipmentEnhanceRate;
    }

    public void setEquipmentEnhanceRate(Property equipmentEnhanceRate) {
        this.equipmentEnhanceRate = equipmentEnhanceRate;
    }

    public Equipment setEquipmentEnhanceRateChain(Property equipmentEnhanceRate) {
        this.equipmentEnhanceRate = equipmentEnhanceRate;
        return this;
    }
}
