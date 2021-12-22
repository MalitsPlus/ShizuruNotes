package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AweAction extends ActionParameter {

    enum AweType{
        unknown(-1),
        ubOnly(0),
        ubAndSkill(1);

        private int value;
        AweType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static AweType parse(int value){
            for(AweType item : AweType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    protected AweType aweType;
    protected List<ActionValue> durationValues = new ArrayList<>();
    protected List<ActionValue> percentValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
        percentValues.add(new ActionValue(actionValue1, actionValue2, null));
        aweType = AweType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (aweType){
            case ubAndSkill:
                return I18N.getString(R.string.Reduce_s1_damage_or_instant_healing_effect_of_union_burst_and_main_skills_cast_by_s2_for_s3_sec,
                        buildExpression(level, percentValues, RoundingMode.UNNECESSARY, property),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            case ubOnly:
                return I18N.getString(R.string.Reduce_s1_damage_or_instant_healing_effect_of_union_burst_cast_by_s2_for_s3_sec,
                        buildExpression(level, percentValues, RoundingMode.UNNECESSARY, property),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
