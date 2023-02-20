package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ChangeSpeedOverlapAction extends ActionParameter {

    protected enum SpeedChangeType{
        slow(1),
        haste(2);

        private int value;
        SpeedChangeType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static SpeedChangeType parse(int value){
            for(SpeedChangeType item : SpeedChangeType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return haste;
        }
    }

    protected SpeedChangeType speedChangeType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        speedChangeType = SpeedChangeType.parse(actionDetail1);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        try {
            if (speedChangeType == SpeedChangeType.slow) {
                return I18N.getString(R.string.Decrease_s1_ATK_SPD_by_s2_for_s3_sec,
                        targetParameter.buildTargetClause(),
                        BigDecimal.valueOf(Double.parseDouble(buildExpression(level, RoundingMode.UNNECESSARY, property))).multiply(BigDecimal.valueOf(100.0)).stripTrailingZeros().toPlainString(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            } else if (speedChangeType == SpeedChangeType.haste) {
                return I18N.getString(R.string.Increase_s1_ATK_SPD_by_s2_for_s3_sec,
                        targetParameter.buildTargetClause(),
                        BigDecimal.valueOf(Double.parseDouble(buildExpression(level, RoundingMode.UNNECESSARY, property))).multiply(BigDecimal.valueOf(100.0)).stripTrailingZeros().toPlainString(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            } else {
                return super.localizedDetail(level, property);
            }
        } catch (Exception ex) {
            return super.localizedDetail(level, property);
        }
    }
}
