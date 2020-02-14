package com.github.malitsplus.shizurunotes.data;


import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.BackgroundSpan;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.data.action.ActionParameter;
import com.github.malitsplus.shizurunotes.data.action.ActionRaw;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class Skill {
    public enum SkillClass {
        UB("UB"),
        UB_EVO("UB+"),
        MAIN1("M1"),
        MAIN1_EVO("M1+"),
        MAIN2("M2"),
        MAIN2_EVO("M2+"),
        MAIN3("M3"),
        MAIN4("M4"),
        MAIN5("M5"),
        MAIN6("M6"),
        MAIN7("M7"),
        MAIN8("M8"),
        MAIN9("M9"),
        MAIN10("M10"),
        SP1("S1"),
        SP2("S2"),
        SP3("S3"),
        SP4("S4"),
        SP5("S5"),
        EX1("E1"),
        EX1_EVO("E1+"),
        EX2("E2"),
        EX2_EVO("E2+"),
        EX3("E3"),
        EX3_EVO("E3+"),
        EX4("E4"),
        EX4_EVO("E4+"),
        EX5("E5"),
        EX5_EVO("E5+"),
        UNKNOWN("");

        private String value;
        SkillClass(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }

        public static SkillClass parse(String value){
            for(SkillClass item : SkillClass.values()){
                if(item.getValue().equals(value))
                    return item;
            }
            return UNKNOWN;
        }

        public String description(){
            switch (this){
                case UB:
                    return I18N.getString(R.string.union_burst);
                case UB_EVO:
                    return I18N.getString(R.string.union_burst_evo);
                case MAIN1:
                    return I18N.getString(R.string.main_skill_1);
                case MAIN2:
                    return I18N.getString(R.string.main_skill_2);
                case MAIN3:
                    return I18N.getString(R.string.main_skill_3);
                case MAIN4:
                    return I18N.getString(R.string.main_skill_4);
                case MAIN5:
                    return I18N.getString(R.string.main_skill_5);
                case MAIN6:
                    return I18N.getString(R.string.main_skill_6);
                case MAIN7:
                    return I18N.getString(R.string.main_skill_7);
                case MAIN8:
                    return I18N.getString(R.string.main_skill_8);
                case MAIN9:
                    return I18N.getString(R.string.main_skill_9);
                case MAIN10:
                    return I18N.getString(R.string.main_skill_10);
                case MAIN1_EVO:
                    return I18N.getString(R.string.main_skill_1_evo);
                case MAIN2_EVO:
                    return I18N.getString(R.string.main_skill_2_evo);
                case SP1:
                    return I18N.getString(R.string.sp_skill_1);
                case SP2:
                    return I18N.getString(R.string.sp_skill_2);
                case SP3:
                    return I18N.getString(R.string.sp_skill_3);
                case SP4:
                    return I18N.getString(R.string.sp_skill_4);
                case SP5:
                    return I18N.getString(R.string.sp_skill_5);
                case EX1:
                    return I18N.getString(R.string.ex_skill_1);
                case EX2:
                    return I18N.getString(R.string.ex_skill_2);
                case EX3:
                    return I18N.getString(R.string.ex_skill_3);
                case EX4:
                    return I18N.getString(R.string.ex_skill_4);
                case EX5:
                    return I18N.getString(R.string.ex_skill_5);
                case EX1_EVO:
                    return I18N.getString(R.string.ex_skill_1_evo);
                case EX2_EVO:
                    return I18N.getString(R.string.ex_skill_2_evo);
                case EX3_EVO:
                    return I18N.getString(R.string.ex_skill_3_evo);
                case EX4_EVO:
                    return I18N.getString(R.string.ex_skill_4_evo);
                case EX5_EVO:
                    return I18N.getString(R.string.ex_skill_5_evo);
                default:
                    return I18N.getString(R.string.unknown);
            }
        }
    }

    public List<ActionRaw> actionRawList = new ArrayList<>();

    public int skillId;
    public String skillName;
    public int skillType;
    public int skillAreaWidth;
    public double skillCastTime;
    public String description;
    public int iconType;
    public SkillClass skillClass;
    public String iconUrl;

    //敌人用
    public int enemySkillLevel;


    public Skill(int skillId, SkillClass skillClass){
        this.skillId = skillId;
        this.skillClass = skillClass;
    }

    public Skill(int skillId, SkillClass skillClass, int enemySkillLevel){
        this.skillId = skillId;
        this.skillClass = skillClass;
        this.enemySkillLevel = enemySkillLevel;
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


    public SpannableStringBuilder actionDescriptions;
    public void setActionDescriptions(int level, Property property){

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 1; i <= actions.size(); i++){
            builder.append("  ").append(String.valueOf(i)).append("  ");
            builder.setSpan(new BackgroundSpan(BackgroundSpan.BORDER_RECT), builder.length() - 4, builder.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(actions.get(i - 1).parameter.localizedDetail(level, property)).append("\n");
        }
        actionDescriptions = builder;
    }

    public SpannableStringBuilder getActionDescriptions(){
        return this.actionDescriptions;
    }

    public String getCastTimeText(){
        return I18N.getString(R.string.text_cast_time) + this.skillCastTime + "s";
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
