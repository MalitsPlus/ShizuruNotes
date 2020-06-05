package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.Enemy;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.Skill;

public class RawEnemy {
    public int enemy_id;
    public int unit_id;
    public String name;
    public int level;
    public int resist_status_id;
    public int prefab_id;
    public int atk_type;
    public int search_area_width;
    public double normal_atk_cast_time;
    public String comment;

    public int hp;
    public int atk;
    public int magic_str;
    public int def;
    public int magic_def;
    public int physical_critical;
    public int magic_critical;
    public int wave_hp_recovery;
    public int wave_energy_Recovery;
    public int dodge;
    public int physical_penetrate;
    public int magic_penetrate;
    public int life_steal;
    public int hp_recovery_rate;
    public int energy_recovery_rate;
    public int energy_reduce_rate;
    public int accuracy;
    public int union_burst_level;
    public int main_skill_lv_1;
    public int main_skill_lv_2;
    public int main_skill_lv_3;
    public int main_skill_lv_4;
    public int main_skill_lv_5;
    public int main_skill_lv_6;
    public int main_skill_lv_7;
    public int main_skill_lv_8;
    public int main_skill_lv_9;
    public int main_skill_lv_10;
    public int ex_skill_lv_1;
    public int ex_skill_lv_2;
    public int ex_skill_lv_3;
    public int ex_skill_lv_4;
    public int ex_skill_lv_5;
    public int child_enemy_parameter_1;
    public int child_enemy_parameter_2;
    public int child_enemy_parameter_3;
    public int child_enemy_parameter_4;
    public int child_enemy_parameter_5;

    public int union_burst;
    public int main_skill_1;
    public int main_skill_2;
    public int main_skill_3;
    public int main_skill_4;
    public int main_skill_5;
    public int main_skill_6;
    public int main_skill_7;
    public int main_skill_8;
    public int main_skill_9;
    public int main_skill_10;
    public int ex_skill_1;
    public int ex_skill_evolution_1;
    public int ex_skill_2;
    public int ex_skill_evolution_2;
    public int ex_skill_3;
    public int ex_skill_evolution_3;
    public int ex_skill_4;
    public int ex_skill_evolution_4;
    public int ex_skill_5;
    public int ex_skill_evolution_5;
    public int sp_skill_1;
    public int sp_skill_2;
    public int sp_skill_3;
    public int sp_skill_4;
    public int sp_skill_5;
    public int union_burst_evolution;
    public int main_skill_evolution_1;
    public int main_skill_evolution_2;

    public Enemy getEnemy(){
        Enemy boss = new Enemy(enemy_id);

        Property property = new Property(
                hp, atk, magic_str, def,
                magic_def, physical_critical, magic_critical, wave_hp_recovery, wave_energy_Recovery,
                dodge, physical_penetrate, magic_penetrate, life_steal, hp_recovery_rate,
                energy_recovery_rate, energy_reduce_rate, accuracy
        );
        if (comment != null) {
            comment = comment.replace("\\n　", "").replace("\\n", "").replace("・", "\n・");
        } else {
            comment = "";
        }
        boss.setBasic(unit_id, name, comment, level, prefab_id, atk_type, search_area_width, normal_atk_cast_time, resist_status_id, property);

        //children part
        for (int i = 1; i <= 5; i++){
            int childId = (int)Utils.getValueFromObject(this, "child_enemy_parameter_" + i);
            if (childId != 0){
                Enemy child = DBHelper.get().getEnemy(childId).getEnemy();
                boss.getChildren().add(child);
                boss.setMultiTarget(true);
            }
        }

        if(union_burst != 0)
            boss.getSkills().add(new Skill(union_burst, Skill.SkillClass.UB, union_burst_level));
        if(union_burst_evolution != 0)
            boss.getSkills().add(new Skill(union_burst_evolution, Skill.SkillClass.UB_EVO));
        if(main_skill_1 != 0)
            boss.getSkills().add(new Skill(main_skill_1, Skill.SkillClass.MAIN1, main_skill_lv_1));
        if(main_skill_evolution_1 != 0)
            boss.getSkills().add(new Skill(main_skill_evolution_1, Skill.SkillClass.MAIN1_EVO));
        if(main_skill_2 != 0)
            boss.getSkills().add(new Skill(main_skill_2, Skill.SkillClass.MAIN2, main_skill_lv_2));
        if(main_skill_evolution_2 != 0)
            boss.getSkills().add(new Skill(main_skill_evolution_2, Skill.SkillClass.MAIN2_EVO));
        if(main_skill_3 != 0)
            boss.getSkills().add(new Skill(main_skill_3, Skill.SkillClass.MAIN3, main_skill_lv_3));
        if(main_skill_4 != 0)
            boss.getSkills().add(new Skill(main_skill_4, Skill.SkillClass.MAIN4, main_skill_lv_4));
        if(main_skill_5 != 0)
            boss.getSkills().add(new Skill(main_skill_5, Skill.SkillClass.MAIN5, main_skill_lv_5));
        if(main_skill_6 != 0)
            boss.getSkills().add(new Skill(main_skill_6, Skill.SkillClass.MAIN6, main_skill_lv_6));
        if(main_skill_7 != 0)
            boss.getSkills().add(new Skill(main_skill_7, Skill.SkillClass.MAIN7, main_skill_lv_7));
        if(main_skill_8 != 0)
            boss.getSkills().add(new Skill(main_skill_8, Skill.SkillClass.MAIN8, main_skill_lv_8));
        if(main_skill_9 != 0)
            boss.getSkills().add(new Skill(main_skill_9, Skill.SkillClass.MAIN9, main_skill_lv_9));
        if(main_skill_10 != 0)
            boss.getSkills().add(new Skill(main_skill_10, Skill.SkillClass.MAIN10, main_skill_lv_10));
        if(sp_skill_1 != 0)
            boss.getSkills().add(new Skill(sp_skill_1, Skill.SkillClass.SP1));
        if(sp_skill_2 != 0)
            boss.getSkills().add(new Skill(sp_skill_2, Skill.SkillClass.SP2));
        if(sp_skill_3 != 0)
            boss.getSkills().add(new Skill(sp_skill_3, Skill.SkillClass.SP3));
        if(sp_skill_4 != 0)
            boss.getSkills().add(new Skill(sp_skill_4, Skill.SkillClass.SP4));
        if(sp_skill_5 != 0)
            boss.getSkills().add(new Skill(sp_skill_5, Skill.SkillClass.SP5));
        if(ex_skill_1 != 0)
            boss.getSkills().add(new Skill(ex_skill_1, Skill.SkillClass.EX1, ex_skill_lv_1));
        if(ex_skill_evolution_1 != 0)
            boss.getSkills().add(new Skill(ex_skill_evolution_1, Skill.SkillClass.EX1_EVO));
        if(ex_skill_2 != 0)
            boss.getSkills().add(new Skill(ex_skill_2, Skill.SkillClass.EX2, ex_skill_lv_2));
        if(ex_skill_evolution_2 != 0)
            boss.getSkills().add(new Skill(ex_skill_evolution_2, Skill.SkillClass.EX2_EVO));
        if(ex_skill_3 != 0)
            boss.getSkills().add(new Skill(ex_skill_3, Skill.SkillClass.EX3, ex_skill_lv_3));
        if(ex_skill_evolution_3 != 0)
            boss.getSkills().add(new Skill(ex_skill_evolution_3, Skill.SkillClass.EX3_EVO));
        if(ex_skill_4 != 0)
            boss.getSkills().add(new Skill(ex_skill_4, Skill.SkillClass.EX4, ex_skill_lv_4));
        if(ex_skill_evolution_4 != 0)
            boss.getSkills().add(new Skill(ex_skill_evolution_4, Skill.SkillClass.EX4_EVO));
        if(ex_skill_5 != 0)
            boss.getSkills().add(new Skill(ex_skill_5, Skill.SkillClass.EX5, ex_skill_lv_5));
        if(ex_skill_evolution_5 != 0)
            boss.getSkills().add(new Skill(ex_skill_evolution_5, Skill.SkillClass.EX5_EVO));

        return boss;
    }
}
