package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class LoopTriggerAction extends ActionParameter {

    enum TriggerType{
        unknown(0),
        dodge(1),
        damaged(2),
        hp(3),
        dead(4),
        criticalDamaged(5),
        getCriticalDamagedWithSummon(6);

        private int value;
        TriggerType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static TriggerType parse(int value){
            for(TriggerType item : TriggerType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    protected TriggerType triggerType;

    @Override
    protected void childInit() {
        super.childInit();
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        triggerType = TriggerType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (triggerType){
            case damaged:
                return I18N.getString(R.string.Condition_s1_chance_use_d2_when_takes_damage_within_s3_sec,
                        buildExpression(level, property), getActionNum(actionDetail2), actionValue4.valueString());
            default:
                return super.localizedDetail(level, property);
        }
    }
}
