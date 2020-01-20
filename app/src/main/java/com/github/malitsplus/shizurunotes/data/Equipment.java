package com.github.malitsplus.shizurunotes.data;

public class Equipment {
    public int equipmentId;
    public String equipmentName;
    public int promotionLevel;
    public Property equipmentData;
    public Property equipmentEnhanceRate;

    public Equipment(){

    }

    public Equipment(int equipmentId, String equipmentName, int promotionLevel, Property equipmentData, Property equipmentEnhanceRate) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.promotionLevel = promotionLevel;
        this.equipmentData = equipmentData;
        this.equipmentEnhanceRate = equipmentEnhanceRate;
    }

    public Property getCeiledProperty(){
        return equipmentData.plus(equipmentEnhanceRate.multiply(promotionLevel));
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

    public int getPromotionLevel() {
        return promotionLevel;
    }

    public void setPromotionLevel(int promotionLevel) {
        this.promotionLevel = promotionLevel;
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
