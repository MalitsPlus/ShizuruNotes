package com.github.malitsplus.shizurunotes.data;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.action.PassiveAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public LocalDateTime startTime;
    public String kana;

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

    public Property charaProperty;
    public Property rarityProperty;
    public Property rarityPropertyGrowth;
    public Property storyProperty;
    public Property promotionStatus;
    public List<Equipment> equipments;
    public Equipment uniqueEquipment;

    public String getBirthDate(){
        return birthMonth + I18N.getString(R.string.text_month) + birthDay + I18N.getString(R.string.text_day);
    }

    public void setCharaProperty() {
        charaProperty = new Property();
        charaProperty
                .plusEqual(rarityProperty)
                .plusEqual(getRarityGrowthProperty())
                .plusEqual(storyProperty)
                .plusEqual(promotionStatus)
                .plusEqual(getAllEquipmentProperty())
                .plusEqual(getUniqueEquipmentProperty())
                .plusEqual(getPassiveSkillProperty());
    }

    public Property getRarityGrowthProperty(){
        return rarityPropertyGrowth.multiply(maxCharaLevel + maxCharaRank);
    }
    public Property getAllEquipmentProperty(){
        Property property = new Property();
        for (Equipment equipment : equipments){
            property.plusEqual(equipment.getCeiledProperty());
        }
        return property;
    }
    public Property getUniqueEquipmentProperty(){
        Property property = new Property();
        if(uniqueEquipment != null) {
            property
                .plusEqual(uniqueEquipment.getEquipmentData())
                .plusEqual(uniqueEquipment.getEquipmentEnhanceRate().multiply(maxUniqueEquipmentLevel - 1));
        }
        return property;
    }
    public Property getPassiveSkillProperty(){
        Property property = new Property();
        for (Skill skill: skills){
            if (skill.skillClass == Skill.SkillClass.EX1_EVO){
                for (Skill.Action action: skill.actions){
                    if (action.parameter instanceof PassiveAction)
                        property.plusEqual(((PassiveAction)action.parameter).propertyItem(maxCharaLevel));
                }
            }
        }
        return property;
    }

    public List<AttackPattern> attackPatternList = new ArrayList<>();

    public List<Skill> skills = new ArrayList<>();
    //public Map<Skill.SkillClass, Skill> skillMap = new HashMap<>();

}
