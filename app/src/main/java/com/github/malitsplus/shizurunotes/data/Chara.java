package com.github.malitsplus.shizurunotes.data;

import android.content.pm.PackageManager;
import android.util.SparseArray;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.Statics;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Chara {

    //basic params
    public int unitId;
    public String unitName;
    public int prefabId;
    public int searchAreaWidth;
    public int atkType;

    public int moveSpeed;
    public double normalAtkCastTime;
    public String actualName;
    public String age;
    public int guildId;

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

    public String comment;
    public String selfText;
    public long startTime;

    public int charaId;
    public String iconUrl;
    public String imageUrl;
    public String position;
    public int positionIcon;
    public String sortValue;

    //preload params
    public int maxCharaLevel;
    public int maxCharaRank;
    public int maxUniqueEquipmentLevel;

    public void setBasic(int unitId, String unitName, int prefabId, int searchAreaWidth, int atkType,
                         int moveSpeed, double normalAtkCastTime, String actualName, String age,
                         int guildId, String guild, String race, String height, String weight,
                         String birthMonth, String birthDay, String bloodType, String favorite,
                         String voice, String catchCopy, String comment, String selfText, String startTime){

        this.charaId = unitId / 100;

        this.unitId = unitId;
        this.unitName = unitName;
        this.prefabId = prefabId;
        this.searchAreaWidth = searchAreaWidth;
        this.atkType = atkType;

        this.moveSpeed = moveSpeed;
        this.normalAtkCastTime = normalAtkCastTime;
        this.actualName = actualName;
        this.age = age;
        this.guildId = guildId;

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

        this.comment = comment;
        this.selfText = selfText;

        //需要处理的字串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.startTime = LocalDateTime.parse(startTime, formatter).toEpochSecond(ZoneOffset.of("+9"));
        this.iconUrl = String.format(Locale.US, Statics.ICON_URL, prefabId + 30);
        this.imageUrl = String.format(Locale.US, Statics.IMAGE_URL, prefabId + 30);

        if(searchAreaWidth < 300) {
            this.position = Statics.FILTER_FORWARD;
            this.positionIcon = R.drawable.position_forward;
        } else if(searchAreaWidth > 300 && searchAreaWidth < 600){
            this.position = Statics.FILTER_MIDDLE;
            this.positionIcon = R.drawable.position_middle;
        } else if(searchAreaWidth > 600) {
            this.position = Statics.FILTER_REAR;
            this.positionIcon = R.drawable.position_rear;
        }
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

    public List<Skill> skillList = new ArrayList<>();

}
