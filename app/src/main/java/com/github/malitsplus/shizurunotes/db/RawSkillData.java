package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.Skill;

public class RawSkillData {
    public int skill_id;
    public String name;
    public int skill_type;
    public int skill_area_width;
    public double skill_cast_time;
    public double boss_ub_cool_time;
    public int action_1;
    public int action_2;
    public int action_3;
    public int action_4;
    public int action_5;
    public int action_6;
    public int action_7;
    public int action_8;
    public int action_9;
    public int action_10;
    public int depend_action_1;
    public int depend_action_2;
    public int depend_action_3;
    public int depend_action_4;
    public int depend_action_5;
    public int depend_action_6;
    public int depend_action_7;
    public int depend_action_8;
    public int depend_action_9;
    public int depend_action_10;
    public String description;
    public int icon_type;

    public void setSkillData(Skill skill){
        skill.setSkillData(
                name,
                skill_type,
                skill_area_width,
                skill_cast_time,
                boss_ub_cool_time,
                description,
                icon_type
        );
        int iteration = DBHelper.get().getHasAction10() ? 10 : 7;
        for(int i = 1; i <= iteration; i++){
            int action = (int)Utils.getValueFromObject(this, "action_" + i);
            if(action != 0) {
                skill.getActions().add(skill.new Action(action, (int) Utils.getValueFromObject(this, "depend_action_" + i)));
            }
        }
    }
}
