package com.github.malitsplus.shizurunotes.data;

import android.util.SparseArray;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.Statics;

import java.util.List;
import java.util.Locale;

public class Chara {

    //basic params
    public int unitId;
    public String unitName;
    public int prefabId;
    public int searchAreaWidth;
    public int atkType;

    public String actualName;
    public String age;
    public String guild;
    public String race;
    public String height;

    public String weight;
    public String birthMonth;
    public String birthDay;
    public String bloodType;
    public String favorite;

    public String voice;
    public String catchCopy;



    public int charaId;
    public String iconUrl;
    public String imageUrl;
    public int positionIcon;

    //preload params
    public int maxCharaLevel;
    public int maxCharaRank;
    public int maxUniqueEquipmentLevel;

    public String sortValue;

    public void setBasic(int unitId, String unitName, int prefabId, int searchAreaWidth, int atkType,
                         String actualName, String age, String guild, String race, String height,
                         String weight, String birthMonth, String birthDay, String bloodType, String favorite,
                         String voice, String catchCopy, String sortValue){

        this.charaId = unitId / 100;

        this.unitId = unitId;
        this.unitName = unitName;
        this.prefabId = prefabId;
        this.searchAreaWidth = searchAreaWidth;
        this.atkType = atkType;

        this.actualName = actualName;
        this.age = age;
        this.guild = guild;
        this.race = race;
        this.height = height;

        this.weight = weight;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.bloodType = bloodType;
        this.favorite = favorite;

        this.voice = voice;
        this.catchCopy = catchCopy;
        this.sortValue = sortValue;

        //需要处理的字串
        this.iconUrl = String.format(Locale.US, Statics.ICON_URL, prefabId + 30);
        this.imageUrl = String.format(Locale.US, Statics.IMAGE_URL, prefabId + 30);

        if(searchAreaWidth < 300)
            this.positionIcon = R.drawable.position_forward;
        else if(searchAreaWidth > 300 && searchAreaWidth < 600)
            this.positionIcon = R.drawable.position_middle;
        else if(searchAreaWidth > 600)
            this.positionIcon = R.drawable.position_rear;
        else
            this.positionIcon = R.drawable.mic_chara_icon_place_holder;
    }

    public Property charaProperty;
    public Property rarityProperty;
    public Property rarityPropertyGrowth;
    public Property storyProperty;
    public Property promotionStatus;
    public SparseArray<Equipment> equipments;
    public Equipment uniqueEquipment;

    public void setRarityProperty(Property rarityProperty){
        this.rarityProperty = rarityProperty;
    }
    public void setRarityPropertyGrowth(Property rarityPropertyGrowth) {
        this.rarityPropertyGrowth = rarityPropertyGrowth;
    }
    public void setStoryProperty(Property storyProperty){
        this.storyProperty = storyProperty;
    }
    public void setPromotionStatus(Property promotionStatus){
        this.promotionStatus = promotionStatus;
    }
    public void setEquipments(SparseArray<Equipment> equipments){
        this.equipments = equipments;
    }
    public void setUniqueEquipment(Equipment uniqueEquipment){
        this.uniqueEquipment = uniqueEquipment;
    }

    public Property getRarityGrowthProperty(){
        return rarityPropertyGrowth.multiply(maxCharaLevel + maxCharaRank);
    }
    public Property getAllEquipmentProperty(){
        Property property = new Property();
        for(int i = 0; i < equipments.size(); i++){
            property.plusEqual(equipments.valueAt(i).getCeiledProperty());
        }
        return property;
    }
    public Property getUniqueEquipmentProperty(){
        Property property = new Property();
        return property
                .plusEqual(uniqueEquipment.getEquipmentData())
                .plusEqual(uniqueEquipment.getEquipmentEnhanceRate().multiply(maxUniqueEquipmentLevel - 1));
    }

    public void setCharaProperty() {
        charaProperty = new Property();
        charaProperty
                .plusEqual(rarityProperty)
                .plusEqual(getRarityGrowthProperty())
                .plusEqual(storyProperty)
                .plusEqual(promotionStatus)
                .plusEqual(getAllEquipmentProperty())
                .plusEqual(getUniqueEquipmentProperty());
    }
}
