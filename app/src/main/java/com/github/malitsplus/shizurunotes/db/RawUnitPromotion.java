package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.common.Utils;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.Equipment;

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

    public void setCharaEquipments(Chara chara){
        ArrayList<Integer> slotList = new ArrayList<>();
        for(int i = 1; i <= 6; i++){
            int equip_id = (int)Utils.getValueFromObject(this, "equip_slot_" + i);
            if(equip_id != 999999)
                slotList.add(equip_id);
        }
        List<RawEquipmentData> equipmentDataList = DBHelper.get().getEquipments(slotList);
        List<RawEquipmentEnhanceData> enhanceDataList = DBHelper.get().getEquipmentEnhance(slotList);

        List<Equipment> equipmentList = new ArrayList<>();
        for(RawEquipmentData data : equipmentDataList){
            for(RawEquipmentEnhanceData enhanceData : enhanceDataList){
                if(enhanceData.equipment_id == data.equipment_id){
                    equipmentList.add(new Equipment(data.equipment_id, data.equipment_name, data.max_equipment_enhance_level, data.getProperty(), enhanceData.getProperty()));
                    break;
                }
            }
        }

        chara.setEquipments(equipmentList);
    }
}
