package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;


import java.util.HashMap;
import java.util.Map;

public class RawResistData {
    public int resist_status_id;
    public int ailment_1;
    public int ailment_2;
    public int ailment_3;
    public int ailment_4;
    public int ailment_5;
    public int ailment_6;
    public int ailment_7;
    public int ailment_8;
    public int ailment_9;
    public int ailment_10;
    public int ailment_11;
    public int ailment_12;
    public int ailment_13;
    public int ailment_14;
    public int ailment_15;
    public int ailment_16;
    public int ailment_17;
    public int ailment_18;
    public int ailment_19;
    public int ailment_20;
    public int ailment_21;
    public int ailment_22;
    public int ailment_23;
    public int ailment_24;
    public int ailment_25;
    public int ailment_26;
    public int ailment_27;
    public int ailment_28;
    public int ailment_29;
    public int ailment_30;
    public int ailment_31;
    public int ailment_32;
    public int ailment_33;
    public int ailment_34;
    public int ailment_35;
    public int ailment_36;
    public int ailment_37;
    public int ailment_38;
    public int ailment_39;
    public int ailment_40;
    public int ailment_41;
    public int ailment_42;
    public int ailment_43;
    public int ailment_44;
    public int ailment_45;
    public int ailment_46;
    public int ailment_47;
    public int ailment_48;
    public int ailment_49;
    public int ailment_50;

    public static Map<Integer, String> ailmentMap;

    public Map<String, Integer> getResistData(){
        if (ailmentMap == null){
            ailmentMap = DBHelper.get().getAilmentMap();
        }

        Map<String, Integer> resultMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry: ailmentMap.entrySet()){
            resultMap.put(
                    entry.getValue(),
                    (Integer) Utils.getValueFromObject(this, "ailment_" + entry.getKey())
            );
        }
        return resultMap;
    }
}
