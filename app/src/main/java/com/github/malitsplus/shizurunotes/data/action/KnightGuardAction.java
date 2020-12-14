package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.PropertyKey;

import java.util.ArrayList;
import java.util.List;

public class KnightGuardAction extends ActionParameter {

    enum GuardType{
        physics(1),
        magic(2);

        private int value;
        GuardType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static GuardType parse(int value){
            for(GuardType item : GuardType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return physics;
        }
    }

    protected GuardType guardType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        guardType = GuardType.parse((int)actionValue1.value);
        switch (guardType){
            case magic:
                actionValues.add(new ActionValue(actionValue4, actionValue5, PropertyKey.magicStr));
                actionValues.add(new ActionValue(actionValue2, actionValue3, null));
                break;
            case physics:
                actionValues.add(new ActionValue(actionValue4, actionValue5, PropertyKey.atk));
                actionValues.add(new ActionValue(actionValue2, actionValue3, null));
                break;
        }
        durationValues.add(new ActionValue(actionValue6, actionValue7, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.When_s1_HP_reaches_0_restore_s2_HP_once_in_next_s3_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, property),
                buildExpression(level, durationValues, null, property));
    }
}
