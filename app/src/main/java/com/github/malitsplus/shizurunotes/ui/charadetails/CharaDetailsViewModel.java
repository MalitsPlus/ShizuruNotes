package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.app.Application;
import android.database.Cursor;
import android.util.SparseArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.common.JsonUtils;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.CharaStoryStatus;
import com.github.malitsplus.shizurunotes.data.Equipment;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.db.DBHelper;

import java.util.ArrayList;

public class CharaDetailsViewModel extends ViewModel {

    private Chara chara;
    private DBHelper dbHelper;
    public MutableLiveData<Chara> mutableChara = new MutableLiveData<>();



    public CharaDetailsViewModel(Application application, String charaJson){
        dbHelper = new DBHelper(application);
        chara = JsonUtils.getBeanFromJson(charaJson, Chara.class);
        initProperties();
        mutableChara.setValue(chara);
    }

    public CharaDetailsViewModel(Chara chara){
        this.mutableChara.setValue(chara);
    }

    private void initProperties(){
        setCharaMaxData();
        setCharaRarity();
        setCharaStoryStatus();
        setCharaPromotionStatus();
        setCharaEquipments();
        setUniqueEquipment();

        chara.setCharaProperty();
    }


    private void setCharaMaxData(){
        chara.maxCharaLevel = dbHelper.getMaxCharaLevel();
        chara.maxCharaRank = dbHelper.getMaxCharaRank();
        chara.maxUniqueEquipmentLevel = dbHelper.getMaxUniqueEquipmentLevel();
    }
    private void setCharaRarity(){
        Cursor cursor = dbHelper.getCharaRarity(chara.unitId);
        if(cursor.moveToNext()) {
            chara.setRarityProperty(getPropertyFromCursor(cursor));
            chara.setRarityPropertyGrowth(getPropertyGrowthFromCursor(cursor));
        }
        cursor.close();
    }
    private void setCharaStoryStatus(){
        Cursor cursor = dbHelper.getCharaStoryStatus(chara.charaId);
        Property storyStatus = new Property();
        while (cursor.moveToNext()){
            for(int i = 1; i <= 5; i++) {
                int statusType = cursor.getInt(cursor.getColumnIndex("status_type_" + i));
                //如果为0直接跳过
                if (statusType == 0)
                    continue;
                storyStatus = storyStatus.plus(new CharaStoryStatus(
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
    private void setCharaPromotionStatus(){
        Cursor cursor = dbHelper.getCharaPromotionStatus(chara.unitId);
        if(cursor.moveToNext())
            chara.setPromotionStatus(getPropertyFromCursor(cursor));
        cursor.close();
    }
    private void setCharaEquipments(){
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
    private void setUniqueEquipment(){
        Equipment equipment = new Equipment();
        Cursor equipCursor = dbHelper.getUniqueEquipment(chara.unitId);

        if(equipCursor.moveToNext()){
            equipment.setEquipmentId(equipCursor.getInt(equipCursor.getColumnIndex("equipment_id")));
            equipment.setEquipmentName(equipCursor.getString(equipCursor.getColumnIndex("equipment_name")));
            equipment.setEquipmentData(getPropertyFromCursor(equipCursor));
        }
        equipCursor.close();

        Cursor enhanceCursor = dbHelper.getUniqueEquipmentEnhance(chara.unitId);
        if(enhanceCursor.moveToNext())
            equipment.setEquipmentEnhanceRate(getPropertyFromCursor(enhanceCursor));
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

    public void setChara(Chara chara){
        this.mutableChara.setValue(chara);
    }

    public LiveData<Chara> getMutableChara(){
        return mutableChara;
    }
}
