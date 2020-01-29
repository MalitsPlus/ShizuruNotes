package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.Property;

public class RawUnitRarity {
    //public int unit_id;
    //public int rarity;
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

    public double hp_growth;
    public double atk_growth;
    public double magic_str_growth;
    public double def_growth;
    public double magic_def_growth;
    public double physical_critical_growth;
    public double magic_critical_growth;
    public double wave_hp_recovery_growth;
    public double wave_energy_recovery_growth;
    public double dodge_growth;
    public double physical_penetrate_growth;
    public double magic_penetrate_growth;
    public double life_steal_growth;
    public double hp_recovery_rate_growth;
    public double energy_recovery_rate_growth;
    public double energy_reduce_rate_growth;
    public double accuracy_growth;

    public void setCharaRarity(Chara chara){
        Property rarityProperty = new Property(
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
        Property rarityPropertyGrowth = new Property(
                hp_growth,
                atk_growth,
                magic_str_growth,
                def_growth,
                magic_def_growth,
                physical_critical_growth,
                magic_critical_growth,
                wave_hp_recovery_growth,
                wave_energy_recovery_growth,
                dodge_growth,
                physical_penetrate_growth,
                magic_penetrate_growth,
                life_steal_growth,
                hp_recovery_rate_growth,
                energy_recovery_rate_growth,
                energy_reduce_rate_growth,
                accuracy_growth
        );

        chara.setRarityProperty(rarityProperty);
        chara.setRarityPropertyGrowth(rarityPropertyGrowth);
    }
}
