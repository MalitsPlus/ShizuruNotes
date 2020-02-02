package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Skill;
import com.github.malitsplus.shizurunotes.data.action.ActionRaw;

public class RawSkillAction {

    //public String description;
    //public String level_up_disp;

    public int action_id;
    public int class_id;
    public int action_type;
    public int action_detail_1;
    public int action_detail_2;
    public int action_detail_3;
    public double action_value_1;
    public double action_value_2;
    public double action_value_3;
    public double action_value_4;
    public double action_value_5;
    public double action_value_6;
    public double action_value_7;
    public int target_assignment;
    public int target_area;
    public int target_range;
    public int target_type;
    public int target_number;
    public int target_count;

    public void setActionData(Skill.Action action){
        action.setActionData(
                class_id,
                action_type,
                action_detail_1,
                action_detail_2,
                action_detail_3,
                action_value_1,
                action_value_2,
                action_value_3,
                action_value_4,
                action_value_5,
                action_value_6,
                action_value_7,
                target_assignment,
                target_area,
                target_range,
                target_type,
                target_number,
                target_count
        );
    }
}
