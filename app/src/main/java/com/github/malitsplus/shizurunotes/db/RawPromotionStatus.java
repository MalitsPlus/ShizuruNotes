package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.Property;

public class RawPromotionStatus {
    //public int unit_id;
    //public int promotion_level;
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

    public void setPromotionStatus(Chara chara){
        Property property = new Property(
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
        chara.promotionStatus = property;
    }
}
