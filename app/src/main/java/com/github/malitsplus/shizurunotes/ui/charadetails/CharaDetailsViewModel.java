package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.common.JsonUtils;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.CharaStoryStatus;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.db.DBHelper;

import java.util.List;

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
    }


    private void setCharaMaxData(){
        chara.maxCharaLevel = dbHelper.getMaxCharaLevel();
        chara.maxCharaRank = dbHelper.getMaxCharaRank();
        chara.maxUniqueEquipmentLevel = dbHelper.getMaxUniqueEquipmentLevel();
    }

    private void setCharaRarity(){
        Cursor rarityCursor = dbHelper.getCharaRarity(chara.unitId);
        rarityCursor.moveToNext();
        chara.setRarityProperty(new Property(
                rarityCursor.getInt(rarityCursor.getColumnIndex("rarity")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("hp")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("atk")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_str")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("def")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_def")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("physical_critical")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_critical")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("wave_hp_recovery")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("wave_energy_recovery")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("dodge")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("physical_penetrate")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_penetrate")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("life_steal")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("hp_recovery_rate")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("energy_recovery_rate")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("energy_reduce_rate")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("accuracy"))
        ));
        chara.setRarityPropertyGrowth(new Property(
                rarityCursor.getInt(rarityCursor.getColumnIndex("rarity")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("hp_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("atk_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_str_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("def_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_def_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("physical_critical_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_critical_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("wave_hp_recovery_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("wave_energy_recovery_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("dodge_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("physical_penetrate_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("magic_penetrate_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("life_steal_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("hp_recovery_rate_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("energy_recovery_rate_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("energy_reduce_rate_growth")),
                rarityCursor.getDouble(rarityCursor.getColumnIndex("accuracy_growth"))
        ));
    }

    private void setCharaStoryStatus(){
        Cursor storyCursor = dbHelper.getCharaStoryStatus(chara.charaId);
        Property storyStatus = new Property();
        while (storyCursor.moveToNext()){
            for(int i = 1; i <= 5; i++) {
                int statusType = storyCursor.getInt(storyCursor.getColumnIndex("status_type_" + i));
                //如果为0直接跳过节省资源
                if (statusType == 0)
                    continue;
                storyStatus.plus(new CharaStoryStatus(
                        chara.charaId,
                        statusType,
                        storyCursor.getDouble(storyCursor.getColumnIndex("status_rate_" + i)))
                        .getProperty()
                );
            }
        }
        chara.setStoryProperty(storyStatus);
    }

    public void setChara(Chara chara){
        this.mutableChara.setValue(chara);
    }

    public LiveData<Chara> getMutableChara(){
        return mutableChara;
    }
}
