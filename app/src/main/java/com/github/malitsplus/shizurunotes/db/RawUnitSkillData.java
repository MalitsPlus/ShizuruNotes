package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.Skill;

public class RawUnitSkillData {
    public int unid_id;
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


    public void setCharaSkillMap(Chara chara){
        if(union_burst != 0)
            chara.skillMap.put(Skill.SkillClass.UB, new Skill(union_burst));
        if(union_burst_evolution != 0)
            chara.skillMap.put(Skill.SkillClass.UB_EVO, new Skill(union_burst_evolution));
        if(main_skill_1 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN1, new Skill(main_skill_1));
        if(main_skill_evolution_1 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN1_EVO, new Skill(main_skill_evolution_1));
        if(main_skill_2 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN2, new Skill(main_skill_2));
        if(main_skill_evolution_2 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN2_EVO, new Skill(main_skill_evolution_2));
        if(main_skill_3 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN3, new Skill(main_skill_3));
        if(main_skill_4 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN4, new Skill(main_skill_4));
        if(main_skill_5 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN5, new Skill(main_skill_5));
        if(main_skill_6 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN6, new Skill(main_skill_6));
        if(main_skill_7 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN7, new Skill(main_skill_7));
        if(main_skill_8 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN8, new Skill(main_skill_8));
        if(main_skill_9 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN9, new Skill(main_skill_9));
        if(main_skill_10 != 0)
            chara.skillMap.put(Skill.SkillClass.MAIN10, new Skill(main_skill_10));
        if(sp_skill_1 != 0)
            chara.skillMap.put(Skill.SkillClass.SP1, new Skill(sp_skill_1));
        if(sp_skill_2 != 0)
            chara.skillMap.put(Skill.SkillClass.SP2, new Skill(sp_skill_2));
        if(sp_skill_3 != 0)
            chara.skillMap.put(Skill.SkillClass.SP3, new Skill(sp_skill_3));
        if(sp_skill_4 != 0)
            chara.skillMap.put(Skill.SkillClass.SP4, new Skill(sp_skill_4));
        if(sp_skill_5 != 0)
            chara.skillMap.put(Skill.SkillClass.SP5, new Skill(sp_skill_5));
        if(ex_skill_1 != 0)
            chara.skillMap.put(Skill.SkillClass.EX1, new Skill(ex_skill_1));
        if(ex_skill_evolution_1 != 0)
            chara.skillMap.put(Skill.SkillClass.EX1_EVO, new Skill(ex_skill_evolution_1));
        if(ex_skill_2 != 0)
            chara.skillMap.put(Skill.SkillClass.EX2, new Skill(ex_skill_2));
        if(ex_skill_evolution_2 != 0)
            chara.skillMap.put(Skill.SkillClass.EX2_EVO, new Skill(ex_skill_evolution_2));
        if(ex_skill_3 != 0)
            chara.skillMap.put(Skill.SkillClass.EX3, new Skill(ex_skill_3));
        if(ex_skill_evolution_3 != 0)
            chara.skillMap.put(Skill.SkillClass.EX3_EVO, new Skill(ex_skill_evolution_3));
        if(ex_skill_4 != 0)
            chara.skillMap.put(Skill.SkillClass.EX4, new Skill(ex_skill_4));
        if(ex_skill_evolution_4 != 0)
            chara.skillMap.put(Skill.SkillClass.EX4_EVO, new Skill(ex_skill_evolution_4));
        if(ex_skill_5 != 0)
            chara.skillMap.put(Skill.SkillClass.EX5, new Skill(ex_skill_5));
        if(ex_skill_evolution_5 != 0)
            chara.skillMap.put(Skill.SkillClass.EX5_EVO, new Skill(ex_skill_evolution_5));
    }
}
