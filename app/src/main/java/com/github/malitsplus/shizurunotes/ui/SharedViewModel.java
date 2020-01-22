package com.github.malitsplus.shizurunotes.ui;

import android.app.Application;
import android.database.Cursor;
import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.CharaStoryStatus;
import com.github.malitsplus.shizurunotes.data.Equipment;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.db.DBHelper;


import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private List<Chara> charaList = new ArrayList<>();
    private DBHelper dbHelper;
    private Chara selectedChara;

    public void init(Application application){
        dbHelper = new DBHelper(application);
        loadData();
    }

    /***
     * 从数据库读取所有角色数据。
     * 注意：此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    public void loadData(){
        charaList.clear();
        loadBasic();
        for(Chara chara : charaList){
            setCharaMaxData(chara);
            setCharaRarity(chara);
            setCharaStoryStatus(chara);
            setCharaPromotionStatus(chara);
            setCharaEquipments(chara);
            setUniqueEquipment(chara);
            chara.setCharaProperty();
        }
    }

    private void loadBasic(){

        Cursor cursor = dbHelper.getCharaBase();
        if(cursor == null)
            return;
        while (cursor.moveToNext()){
            Chara chara = new Chara();
            chara.setBasic(
                    cursor.getInt(cursor.getColumnIndex("unit_id")),
                    cursor.getString(cursor.getColumnIndex("unit_name")),
                    cursor.getInt(cursor.getColumnIndex("prefab_id")),
                    cursor.getInt(cursor.getColumnIndex("search_area_width")),
                    cursor.getInt(cursor.getColumnIndex("atk_type")),
                    cursor.getInt(cursor.getColumnIndex("move_speed")),
                    cursor.getDouble(cursor.getColumnIndex("normal_atk_cast_time")),
                    cursor.getString(cursor.getColumnIndex("actual_name")),
                    cursor.getString(cursor.getColumnIndex("age")),
                    cursor.getInt(cursor.getColumnIndex("guild_id")),
                    cursor.getString(cursor.getColumnIndex("guild")),
                    cursor.getString(cursor.getColumnIndex("race")),
                    cursor.getString(cursor.getColumnIndex("height")),
                    cursor.getString(cursor.getColumnIndex("weight")),
                    cursor.getString(cursor.getColumnIndex("birth_month")),
                    cursor.getString(cursor.getColumnIndex("birth_day")),
                    cursor.getString(cursor.getColumnIndex("blood_type")),
                    cursor.getString(cursor.getColumnIndex("favorite")),
                    cursor.getString(cursor.getColumnIndex("voice")),
                    cursor.getString(cursor.getColumnIndex("catch_copy")),
                    cursor.getString(cursor.getColumnIndex("comment")),
                    cursor.getString(cursor.getColumnIndex("self_text")),
                    cursor.getString(cursor.getColumnIndex("start_time"))
            );
            charaList.add(chara);
        }
        cursor.close();
    }
    private void setCharaMaxData(Chara chara){
        chara.maxCharaLevel = dbHelper.getMaxCharaLevel();
        chara.maxCharaRank = dbHelper.getMaxCharaRank();
        chara.maxUniqueEquipmentLevel = dbHelper.getMaxUniqueEquipmentLevel();
    }
    private void setCharaRarity(Chara chara){
        Cursor cursor = dbHelper.getCharaRarity(chara.unitId);
        if(cursor.moveToNext()) {
            chara.setRarityProperty(getPropertyFromCursor(cursor));
            chara.setRarityPropertyGrowth(getPropertyGrowthFromCursor(cursor));
        }
        cursor.close();
    }
    private void setCharaStoryStatus(Chara chara){
        Cursor cursor = dbHelper.getCharaStoryStatus(chara.charaId);
        Property storyStatus = new Property();
        while (cursor.moveToNext()){
            for(int i = 1; i <= 5; i++) {
                int statusType = cursor.getInt(cursor.getColumnIndex("status_type_" + i));
                //如果为0直接跳过
                if (statusType == 0)
                    continue;
                storyStatus.plusEqual(new CharaStoryStatus(
                        chara.charaId,
                        statusType,
                        cursor.getDouble(cursor.getColumnIndex("status_rate_" + i)))
                        .getProperty()
                );
            }
        }
        cursor.close();
        chara.setStoryProperty(storyStatus);
    }
    private void setCharaPromotionStatus(Chara chara){
        Cursor cursor = dbHelper.getCharaPromotionStatus(chara.unitId);
        if(cursor.moveToNext())
            chara.setPromotionStatus(getPropertyFromCursor(cursor));
        cursor.close();
    }
    private void setCharaEquipments(Chara chara){
        ArrayList<Integer> slots = new ArrayList<>();

        Cursor slotsCursor = dbHelper.getCharaEquipmentSlots(chara.unitId);
        if(slotsCursor.moveToNext()) {

            for (int i = 1; i <= 6; i++) {
                int slot = slotsCursor.getInt(slotsCursor.getColumnIndex("equip_slot_" + i));
                if (slot != 999999)
                    slots.add(slot);
            }
        }
        slotsCursor.close();

        SparseArray<Equipment> equipments = new SparseArray<>();
        Cursor equipmentsCursor = dbHelper.getEquipments(slots);
        Cursor enhanceCursor = dbHelper.getEquipmentEnhance(slots);

        while(equipmentsCursor.moveToNext()){
            Equipment equipment = new Equipment();
            int equipmentId = equipmentsCursor.getInt(equipmentsCursor.getColumnIndex("equipment_id"));
            equipment.setEquipmentId(equipmentId);
            equipment.setEquipmentName(equipmentsCursor.getString(equipmentsCursor.getColumnIndex("equipment_name")));
            equipment.setEquipmentData(getPropertyFromCursor(equipmentsCursor));
            equipments.put(equipmentId, equipment);
        }
        while(enhanceCursor.moveToNext()){
            equipments.get(enhanceCursor.getInt(enhanceCursor.getColumnIndex("equipment_id")))
                    .setEquipmentEnhanceRateChain(getPropertyFromCursor(enhanceCursor))
                    .setPromotionLevel(enhanceCursor.getInt(enhanceCursor.getColumnIndex("promotion_level")));
        }
        equipmentsCursor.close();
        enhanceCursor.close();

        chara.setEquipments(equipments);

    }
    private void setUniqueEquipment(Chara chara){
        Equipment equipment = new Equipment();

        Cursor equipCursor = dbHelper.getUniqueEquipment(chara.unitId);
        if(equipCursor.moveToNext()){
            equipment.setEquipmentId(equipCursor.getInt(equipCursor.getColumnIndex("equipment_id")));
            equipment.setEquipmentName(equipCursor.getString(equipCursor.getColumnIndex("equipment_name")));
            equipment.setEquipmentData(getPropertyFromCursor(equipCursor));
        } else {
            equipment.setEquipmentData(new Property());
        }
        equipCursor.close();

        Cursor enhanceCursor = dbHelper.getUniqueEquipmentEnhance(chara.unitId);
        if(enhanceCursor.moveToNext()) {
            equipment.setEquipmentEnhanceRate(getPropertyFromCursor(enhanceCursor));
        } else {
            equipment.setEquipmentEnhanceRate(new Property());
        }
        enhanceCursor.close();

        chara.setUniqueEquipment(equipment);
    }

    private Property getPropertyFromCursor(Cursor cursor){
        return new Property(
                cursor.getDouble(cursor.getColumnIndex("hp")),
                cursor.getDouble(cursor.getColumnIndex("atk")),
                cursor.getDouble(cursor.getColumnIndex("magic_str")),
                cursor.getDouble(cursor.getColumnIndex("def")),
                cursor.getDouble(cursor.getColumnIndex("magic_def")),
                cursor.getDouble(cursor.getColumnIndex("physical_critical")),
                cursor.getDouble(cursor.getColumnIndex("magic_critical")),
                cursor.getDouble(cursor.getColumnIndex("wave_hp_recovery")),
                cursor.getDouble(cursor.getColumnIndex("wave_energy_recovery")),
                cursor.getDouble(cursor.getColumnIndex("dodge")),
                cursor.getDouble(cursor.getColumnIndex("physical_penetrate")),
                cursor.getDouble(cursor.getColumnIndex("magic_penetrate")),
                cursor.getDouble(cursor.getColumnIndex("life_steal")),
                cursor.getDouble(cursor.getColumnIndex("hp_recovery_rate")),
                cursor.getDouble(cursor.getColumnIndex("energy_recovery_rate")),
                cursor.getDouble(cursor.getColumnIndex("energy_reduce_rate")),
                cursor.getDouble(cursor.getColumnIndex("accuracy")));
    }
    private Property getPropertyGrowthFromCursor(Cursor cursor){
        return new Property(
                cursor.getDouble(cursor.getColumnIndex("hp_growth")),
                cursor.getDouble(cursor.getColumnIndex("atk_growth")),
                cursor.getDouble(cursor.getColumnIndex("magic_str_growth")),
                cursor.getDouble(cursor.getColumnIndex("def_growth")),
                cursor.getDouble(cursor.getColumnIndex("magic_def_growth")),
                cursor.getDouble(cursor.getColumnIndex("physical_critical_growth")),
                cursor.getDouble(cursor.getColumnIndex("magic_critical_growth")),
                cursor.getDouble(cursor.getColumnIndex("wave_hp_recovery_growth")),
                cursor.getDouble(cursor.getColumnIndex("wave_energy_recovery_growth")),
                cursor.getDouble(cursor.getColumnIndex("dodge_growth")),
                cursor.getDouble(cursor.getColumnIndex("physical_penetrate_growth")),
                cursor.getDouble(cursor.getColumnIndex("magic_penetrate_growth")),
                cursor.getDouble(cursor.getColumnIndex("life_steal_growth")),
                cursor.getDouble(cursor.getColumnIndex("hp_recovery_rate_growth")),
                cursor.getDouble(cursor.getColumnIndex("energy_recovery_rate_growth")),
                cursor.getDouble(cursor.getColumnIndex("energy_reduce_rate_growth")),
                cursor.getDouble(cursor.getColumnIndex("accuracy_growth")));
    }


    public Chara getSelectedChara() {
        return selectedChara;
    }
    public void setSelectedChara(Chara selectedChara) {
        this.selectedChara = selectedChara;
    }
    public List<Chara> getCharaList() {
        return charaList;
    }
}
