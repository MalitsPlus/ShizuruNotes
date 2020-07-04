package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ActionByHitCountAction extends ActionParameter {

    enum ConditionType {
        unknown(0),
        damage(1),
        target(2),
        hit(3),
        critical(4);

        private int value;
        ConditionType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static ConditionType parse(int value){
            for(ConditionType item : ConditionType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    protected ConditionType conditionType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        conditionType = ConditionType.parse(actionDetail1);
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        String limitation;
        if (actionValue5 > 0) {
            limitation = I18N.getString(R.string.max_s_times, Utils.roundIfNeed(actionValue5));
        } else {
            limitation = "";
        }
        switch (conditionType) {
            case hit:
                return I18N.getString(R.string.Use_d1_s2_every_s3_hits_in_next_s4_sec,
                    actionDetail2 % 10,
                    limitation,
                    Utils.roundIfNeed(actionValue1),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
                );
            default:
                return super.localizedDetail(level, property);
        }
    }
}
