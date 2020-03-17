package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Minion;
import com.github.malitsplus.shizurunotes.data.Skill;

public class RawUnitMinion {
    public int unit_id;
    public String unit_name;
    public int prefab_id;
    public int move_speed;
    public int search_area_width;
    public int atk_type;
    public double normal_atk_cast_time;

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

    public Minion getUnitMinion(){

        Minion minion = new Minion(
                unit_id,
                unit_name,
                prefab_id,
                move_speed,
                search_area_width,
                atk_type,
                normal_atk_cast_time
        );

        if(union_burst != 0)
            minion.getSkills().add(new Skill(union_burst, Skill.SkillClass.UB));
        if(union_burst_evolution != 0)
            minion.getSkills().add(new Skill(union_burst_evolution, Skill.SkillClass.UB_EVO));
        if(main_skill_1 != 0)
            minion.getSkills().add(new Skill(main_skill_1, Skill.SkillClass.MAIN1));
        if(main_skill_evolution_1 != 0)
            minion.getSkills().add(new Skill(main_skill_evolution_1, Skill.SkillClass.MAIN1_EVO));
        if(main_skill_2 != 0)
            minion.getSkills().add(new Skill(main_skill_2, Skill.SkillClass.MAIN2));
        if(main_skill_evolution_2 != 0)
            minion.getSkills().add(new Skill(main_skill_evolution_2, Skill.SkillClass.MAIN2_EVO));
        if(main_skill_3 != 0)
            minion.getSkills().add(new Skill(main_skill_3, Skill.SkillClass.MAIN3));
        if(main_skill_4 != 0)
            minion.getSkills().add(new Skill(main_skill_4, Skill.SkillClass.MAIN4));
        if(main_skill_5 != 0)
            minion.getSkills().add(new Skill(main_skill_5, Skill.SkillClass.MAIN5));
        if(main_skill_6 != 0)
            minion.getSkills().add(new Skill(main_skill_6, Skill.SkillClass.MAIN6));
        if(main_skill_7 != 0)
            minion.getSkills().add(new Skill(main_skill_7, Skill.SkillClass.MAIN7));
        if(main_skill_8 != 0)
            minion.getSkills().add(new Skill(main_skill_8, Skill.SkillClass.MAIN8));
        if(main_skill_9 != 0)
            minion.getSkills().add(new Skill(main_skill_9, Skill.SkillClass.MAIN9));
        if(main_skill_10 != 0)
            minion.getSkills().add(new Skill(main_skill_10, Skill.SkillClass.MAIN10));
        if(sp_skill_1 != 0)
            minion.getSkills().add(new Skill(sp_skill_1, Skill.SkillClass.SP1));
        if(sp_skill_2 != 0)
            minion.getSkills().add(new Skill(sp_skill_2, Skill.SkillClass.SP2));
        if(sp_skill_3 != 0)
            minion.getSkills().add(new Skill(sp_skill_3, Skill.SkillClass.SP3));
        if(sp_skill_4 != 0)
            minion.getSkills().add(new Skill(sp_skill_4, Skill.SkillClass.SP4));
        if(sp_skill_5 != 0)
            minion.getSkills().add(new Skill(sp_skill_5, Skill.SkillClass.SP5));
        if(ex_skill_1 != 0)
            minion.getSkills().add(new Skill(ex_skill_1, Skill.SkillClass.EX1));
        if(ex_skill_evolution_1 != 0)
            minion.getSkills().add(new Skill(ex_skill_evolution_1, Skill.SkillClass.EX1_EVO));
        if(ex_skill_2 != 0)
            minion.getSkills().add(new Skill(ex_skill_2, Skill.SkillClass.EX2));
        if(ex_skill_evolution_2 != 0)
            minion.getSkills().add(new Skill(ex_skill_evolution_2, Skill.SkillClass.EX2_EVO));
        if(ex_skill_3 != 0)
            minion.getSkills().add(new Skill(ex_skill_3, Skill.SkillClass.EX3));
        if(ex_skill_evolution_3 != 0)
            minion.getSkills().add(new Skill(ex_skill_evolution_3, Skill.SkillClass.EX3_EVO));
        if(ex_skill_4 != 0)
            minion.getSkills().add(new Skill(ex_skill_4, Skill.SkillClass.EX4));
        if(ex_skill_evolution_4 != 0)
            minion.getSkills().add(new Skill(ex_skill_evolution_4, Skill.SkillClass.EX4_EVO));
        if(ex_skill_5 != 0)
            minion.getSkills().add(new Skill(ex_skill_5, Skill.SkillClass.EX5));
        if(ex_skill_evolution_5 != 0)
            minion.getSkills().add(new Skill(ex_skill_evolution_5, Skill.SkillClass.EX5_EVO));

        return minion;
    }
}
