package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LogBarrierAction extends ActionParameter {

    enum BarrierType{
        physics(1),
        magic(2),
        all(3);

        private int value;
        BarrierType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static BarrierType parse(int value){
            for(BarrierType item : BarrierType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return all;
        }

    }

    protected BarrierType barrierType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        barrierType = BarrierType.parse((int) actionValue1.value);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Cast_a_barrier_on_s1_to_reduce_damage_over_s2_with_coefficient_s3_the_greater_the_less_reduced_amount_for_s4_s,
                targetParameter.buildTargetClause(),
                Utils.roundDouble(actionValue5.value),
                buildExpression(level, RoundingMode.UNNECESSARY, property),
                buildExpression(level, durationValues, null, property));
    }
}
