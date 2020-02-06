package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.data.Chara;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RawUnitBasic {
    public int unit_id;
    public String unit_name;
    public int prefab_id;
    public int move_speed;
    public int search_area_width;
    public int atk_type;
    public double normal_atk_cast_time;
    public int guild_id;
    public String comment;
    public String start_time;
    public String age;
    public String guild;
    public String race;
    public String height;
    public String weight;
    public String birth_month;
    public String birth_day;
    public String blood_type;
    public String favorite;
    public String voice;
    public String catch_copy;
    public String self_text;
    public String actual_name;
    public String kana;

    public void setCharaBasic(Chara chara){
        chara.charaId = unit_id / 100;

        chara.unitId = unit_id;
        chara.unitName = unit_name;
        chara.prefabId = prefab_id;
        chara.searchAreaWidth = search_area_width;
        chara.atkType = atk_type;

        chara.moveSpeed = move_speed;
        chara.normalAtkCastTime = normal_atk_cast_time;
        chara.actualName = actual_name;
        chara.age = age;
        chara.guildId = guild_id;

        chara.guild = guild;
        chara.race = race;
        chara.height = height;
        chara.weight = weight;
        chara.birthMonth = birth_month;

        chara.birthDay = birth_day;
        chara.bloodType = blood_type;
        chara.favorite = favorite;
        chara.voice = voice;
        chara.catchCopy = catch_copy;

        chara.comment = comment;
        chara.kana = kana;
        chara.selfText = self_text.replaceAll("\\\\n", "\n") ;

        //需要处理的字串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        chara.startTime = LocalDateTime.parse(start_time, formatter).toEpochSecond(ZoneOffset.of("+9"));
        chara.iconUrl = String.format(Locale.US, Statics.ICON_URL, prefab_id + 30);
        chara.imageUrl = String.format(Locale.US, Statics.IMAGE_URL, prefab_id + 30);

        if(search_area_width < 300) {
            chara.position = Statics.FILTER_FORWARD;
            chara.positionIcon = R.drawable.position_forward;
        } else if(search_area_width > 300 && search_area_width < 600){
            chara.position = Statics.FILTER_MIDDLE;
            chara.positionIcon = R.drawable.position_middle;
        } else if(search_area_width > 600) {
            chara.position = Statics.FILTER_REAR;
            chara.positionIcon = R.drawable.position_rear;
        }
    }
}
