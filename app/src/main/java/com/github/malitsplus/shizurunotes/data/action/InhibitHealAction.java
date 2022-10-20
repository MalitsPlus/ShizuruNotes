package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InhibitHealAction extends ActionParameter {

    enum InhibitType{
        inhibit(0),
        decrease(1);

        private int value;
        InhibitType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static InhibitType parse(int value){
            for(InhibitType item : InhibitType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return inhibit;
        }
    }

    protected List<ActionValue> durationValues = new ArrayList<>();
    protected InhibitType inhibitType;

    @Override
    protected void childInit() {
        super.childInit();
        actionValues.add(new ActionValue(actionValue1, actionValue4, null));
        durationValues.add(new ActionValue(actionValue2, actionValue3, null));
        inhibitType = InhibitType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (inhibitType) {
            case inhibit:
                return I18N.getString(R.string.When_s1_receive_healing_deal_s2_healing_amount_damage_instead_last_for_s3_sec_or_unlimited_time_if_triggered_by_field,
                        targetParameter.buildTargetClause(),
                        actionValue1.valueString(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            case decrease:
                return I18N.getString(R.string.Decreases_s1_healing_received_by_s2_last_for_s3_sec_or_unlimited_time_if_triggered_by_field,
                        Utils.roundIfNeed(actionValue1.value * 100),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
