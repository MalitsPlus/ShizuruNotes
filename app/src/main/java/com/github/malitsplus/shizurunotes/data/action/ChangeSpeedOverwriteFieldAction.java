package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ChangeSpeedOverwriteFieldAction extends ActionParameter {

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

        public static ChangeSpeedOverwriteFieldAction.SpeedChangeType parse(int value){
            for(ChangeSpeedOverwriteFieldAction.SpeedChangeType item : ChangeSpeedOverwriteFieldAction.SpeedChangeType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return haste;
        }

        public String description() {
            if (value == 1) {
                return I18N.getString(R.string.Reduce);
            } else {
                return I18N.getString(R.string.Raise);
            }
        }
    }

    protected List<ActionValue> durationValues = new ArrayList<>();
    protected SpeedChangeType speedChangeType;

    @Override
    protected void childInit() {
        super.childInit();
        speedChangeType = ChangeSpeedOverwriteFieldAction.SpeedChangeType.parse(actionDetail1);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Deploys_a_filed_of_radius_d1_which_s2_attack_speed_of_s3_for_s4_sec,
                (int)actionValue5.value,
                speedChangeType.description(),
                Utils.roundDouble(Double.parseDouble(buildExpression(level, RoundingMode.UNNECESSARY, property)) * 100),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        );
    }
}
