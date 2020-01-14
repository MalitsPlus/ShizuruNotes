package com.github.malitsplus.shizurunotes.data;

import androidx.core.content.res.ResourcesCompat;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.Statics;

import java.util.Locale;

public class Chara {

    public int unitId;
    public String unitName;
    public int prefabId;
    public int searchAreaWidth;
    public int atkType;
    public String actualName;
    public String age;
    public String height;
    public String weight;
    public String iconUrl;
    public int positionIcon;
    public String sortValue;

    public void setBasic(int unitId, String unitName, int prefabId, int searchAreaWidth, int atkType, String actualName, String age, String height, String weight, String sortValue){
        this.unitId = unitId;
        this.unitName = unitName;
        this.prefabId = prefabId;
        this.searchAreaWidth = searchAreaWidth;
        this.atkType = atkType;
        this.actualName = actualName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sortValue = sortValue;

        this.iconUrl = String.format(Locale.US, Statics.ICON_URL, prefabId + 30);

        if(searchAreaWidth < 300)
            this.positionIcon = R.drawable.position_forward;
        else if(searchAreaWidth > 300 && searchAreaWidth < 600)
            this.positionIcon = R.drawable.position_middle;
        else if(searchAreaWidth > 600)
            this.positionIcon = R.drawable.position_rear;
        else
            this.positionIcon = R.drawable.mic_chara_icon_place_holder;
    }
/*
    public class Base {
        public int unitId;
        public String unitName;
        public String kana;
        public int prefabId;
        public int rarity;
        public int searchAreaWidth;
        public int atkType;
        public double normalAtkCastTime;
        public int guildId;
        public String comment;
        public String iconUrl;
        public int positionIcon;
        public String actualName;

        //region getters and setters

        public int getUnitId() {
            return unitId;
        }

        public String getUnitName() {
            return unitName;
        }

        public String getKana() {
            return kana;
        }

        public int getPrefabId() {
            return prefabId;
        }

        public int getRarity() {
            return rarity;
        }

        public int getSearchAreaWidth() {
            return searchAreaWidth;
        }

        public int getAtkType() {
            return atkType;
        }

        public double getNormalAtkCastTime() {
            return normalAtkCastTime;
        }

        public int getGuildId() {
            return guildId;
        }

        public String getComment() {
            return comment;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public int getPositionIcon() {
            return positionIcon;
        }

        //endregion

        public Base(int unitId, String unitName, String kana, int prefabId, int rarity, int searchAreaWidth,
                    int atkType, double normalAtkCastTime, int guildId, String comment){
            this.unitId = unitId;
            this.unitName = unitName;
            //this.kana = kana;
            this.prefabId = prefabId;
            //this.rarity = rarity;
            this.searchAreaWidth = searchAreaWidth;
            this.atkType = atkType;
            //this.normalAtkCastTime = normalAtkCastTime;
            //this.guildId = guildId;
            //this.comment = comment;

            this.iconUrl = String.format(Locale.US, Statics.ICON_URL, prefabId + 30);
            if(searchAreaWidth < 300)
                this.positionIcon = R.drawable.position_forward;
            else if(searchAreaWidth > 300 && searchAreaWidth < 600)
                this.positionIcon = R.drawable.position_middle;
            else if(searchAreaWidth > 600 && searchAreaWidth < 900)
                this.positionIcon = R.drawable.position_rear;
            else
                this.positionIcon = R.drawable.mic_chara_icon_place_holder;
        }
    }
    */
    public Chara(){
    }
}
