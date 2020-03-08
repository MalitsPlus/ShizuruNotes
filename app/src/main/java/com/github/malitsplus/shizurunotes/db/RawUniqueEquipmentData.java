package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.Equipment;
import com.github.malitsplus.shizurunotes.data.Property;

public class RawUniqueEquipmentData {


    public int equipment_id;
    public String equipment_name;
    public String description;
    public int promotion_level;
    public int craft_flg;
    public int equipment_enhance_point;
    public int sale_price;
    public int require_level;
    public double hp;
    public double atk;
    public double magic_str;
    public double def;
    public double magic_def;
    public double physical_critical;
    public double magic_critical;
    public double wave_hp_recovery;
    public double wave_energy_recovery;
    public double dodge;
    public double physical_penetrate;
    public double magic_penetrate;
    public double life_steal;
    public double hp_recovery_rate;
    public double energy_recovery_rate;
    public double energy_reduce_rate;
    public double accuracy;

    public void setCharaUniqueEquipment(Chara chara){
        chara.setUniqueEquipment(new Equipment(
                equipment_id,
                equipment_name,
                description,
                promotion_level,
                craft_flg,
                equipment_enhance_point,
                sale_price,
                require_level,
                chara.getMaxUniqueEquipmentLevel(),
                this.getProperty(),
                DBHelper.get().getUniqueEquipmentEnhance(chara.getUnitId()).getProperty()
        ));
    }

    public Property getProperty(){
        return new Property(
                hp,
                atk,
                magic_str,
                def,
                magic_def,
                physical_critical,
                magic_critical,
                wave_hp_recovery,
                wave_energy_recovery,
                dodge,
                physical_penetrate,
                magic_penetrate,
                life_steal,
                hp_recovery_rate,
                energy_recovery_rate,
                energy_reduce_rate,
                accuracy
        );
    }
}
