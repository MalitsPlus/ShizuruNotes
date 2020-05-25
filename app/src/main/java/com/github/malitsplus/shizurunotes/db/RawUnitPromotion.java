package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RawUnitPromotion {
    public int unit_id;
    public int promotion_level;
    public int equip_slot_1;
    public int equip_slot_2;
    public int equip_slot_3;
    public int equip_slot_4;
    public int equip_slot_5;
    public int equip_slot_6;

    public List<Integer> getCharaSlots(){
        ArrayList<Integer> slotList = new ArrayList<>();
        for(int i = 1; i <= 6; i++){
            int equip_id = (int)Utils.getValueFromObject(this, "equip_slot_" + i);
//            if(equip_id != 999999)
            slotList.add(equip_id);
        }
        return slotList;
    }
}
