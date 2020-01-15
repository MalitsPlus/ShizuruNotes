package com.github.malitsplus.shizurunotes.data;

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


    public String iconUrl;
    public String imageUrl;
    public int positionIcon;

    public String sortValue;

    public void setBasic(int unitId, String unitName, int prefabId, int searchAreaWidth, int atkType,
                         String actualName, String age, String guild, String race, String height,
                         String weight, String birthMonth, String birthDay, String bloodType, String favorite,
                         String voice, String catchCopy, String sortValue){

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

}
