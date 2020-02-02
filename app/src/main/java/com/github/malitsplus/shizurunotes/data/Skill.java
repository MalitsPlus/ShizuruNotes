package com.github.malitsplus.shizurunotes.data;


import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.data.action.ActionParameter;
import com.github.malitsplus.shizurunotes.data.action.ActionRaw;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class Skill {
    public enum SkillClass {
        UB,
        UB_EVO,
        MAIN1,
        MAIN1_EVO,
        MAIN2,
        MAIN2_EVO,
        MAIN3,
        MAIN4,
        MAIN5,
        MAIN6,
        MAIN7,
        MAIN8,
        MAIN9,
        MAIN10,
        SP1,
        SP2,
        SP3,
        SP4,
        SP5,
        EX1,
        EX1_EVO,
        EX2,
        EX2_EVO,
        EX3,
        EX3_EVO,
        EX4,
        EX4_EVO,
        EX5,
        EX5_EVO
    }

    public List<ActionRaw> actionRawList = new ArrayList<>();

    public int skillId;
    public String skillName;
    public int skillType;
    public int skillAreaWidth;
    public double skillCastTime;
    public String description;
    public int iconType;

    public String iconUrl;

    public Skill(int skillId){
        this.skillId = skillId;
    }

    public void setSkillData(String skillName, int skillType, int skillAreaWidth, double skillCastTime, String description, int iconType){
        this.skillName = skillName;
        this.skillType = skillType;
        this.skillAreaWidth = skillAreaWidth;
        this.skillCastTime = skillCastTime;
        this.description = description;
        this.iconType = iconType;
        this.iconUrl = String.format(Locale.US, Statics.SKILL_ICON_URL, iconType);
    }


    public List<Action> actions = new ArrayList<>();

    public class Action{
        public int dependActionId;

        public int actionId;
        public int classId;
        public int actionType;
        public int actionDetail1;
        public int actionDetail2;
        public int actionDetail3;

        public double actionValue1;
        public double actionValue2;
        public double actionValue3;
        public double actionValue4;
        public double actionValue5;
        public double actionValue6;
        public double actionValue7;

        public int targetAssignment;
        public int targetArea;
        public int targetRange;
        public int targetType;
        public int targetNumber;
        public int targetCount;

        public Action(int actionId, int dependActionId){
            this.actionId = actionId;
            this.dependActionId = dependActionId;
        }

        public void setActionData(int classId, int actionType, int actionDetail1, int actionDetail2, int actionDetail3, double actionValue1, double actionValue2, double actionValue3, double actionValue4, double actionValue5, double actionValue6, double actionValue7, int targetAssignment, int targetArea, int targetRange, int targetType, int targetNumber, int targetCount){
            this.classId = classId;
            this.actionType = actionType;
            this.actionDetail1 = actionDetail1;
            this.actionDetail2 = actionDetail2;
            this.actionDetail3 = actionDetail3;
            this.actionValue1 = actionValue1;
            this.actionValue2 = actionValue2;
            this.actionValue3 = actionValue3;
            this.actionValue4 = actionValue4;
            this.actionValue5 = actionValue5;
            this.actionValue6 = actionValue6;
            this.actionValue7 = actionValue7;
            this.targetAssignment = targetAssignment;
            this.targetArea = targetArea;
            this.targetRange = targetRange;
            this.targetType = targetType;
            this.targetNumber = targetNumber;
            this.targetCount = targetCount;
        }

        public Action dependAction;
        public ActionParameter parameter;
        public void buildParameter(){
            parameter = ActionParameter.type(actionType).init(
                    actionId,
                    dependActionId,
                    classId,
                    actionType,
                    actionDetail1,
                    actionDetail2,
                    actionDetail3,
                    actionValue1,
                    actionValue2,
                    actionValue3,
                    actionValue4,
                    actionValue5,
                    actionValue6,
                    actionValue7,
                    targetAssignment,
                    targetArea,
                    targetRange,
                    targetType,
                    targetNumber,
                    targetCount,
                    dependAction
            );
        }

    }
}
