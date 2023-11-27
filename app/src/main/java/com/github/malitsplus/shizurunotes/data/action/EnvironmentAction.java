package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentAction extends ActionParameter {

    public enum EnvironmentType{
        unknown(-1),
        thundering(137);

        private int value;
        EnvironmentType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static EnvironmentType parse(int value){
            for(EnvironmentType item : EnvironmentType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description() {
            switch (this) {
                case thundering: return I18N.getString(R.string.thundering);
            }
            return I18N.getString(R.string.unknown);
        }
    }

    protected EnvironmentType environmentType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        environmentType = EnvironmentType.parse(actionDetail2);
        durationValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Summon_a_field_of_s1_environment_for_s2_for_s3_sec,
            environmentType.description(),
            targetParameter.buildTargetClause(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        );
    }
}
