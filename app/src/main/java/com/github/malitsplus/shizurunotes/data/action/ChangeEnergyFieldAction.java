package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ChangeEnergyFieldAction extends ActionParameter{

    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (actionDetail1) {
            case 1:
                return I18N.getString(R.string.Summon_a_field_with_radius_d1_at_position_s2_which_continuous_restore_tp_s3_of_units_within_the_field_for_s4_sec,
                        (int)actionValue5.value,
                        targetParameter.buildTargetClause(),
                        buildExpression(level, actionValues, RoundingMode.CEILING, property, false, targetParameter.targetType == TargetType.self, false),
                        buildExpression(level, durationValues, null, property));
            case 2:
                return I18N.getString(R.string.Summon_a_field_with_radius_d1_at_position_s2_which_continuous_lose_tp_s3_of_units_within_the_field_for_s4_sec,
                        (int)actionValue5.value,
                        targetParameter.buildTargetClause(),
                        buildExpression(level, actionValues, RoundingMode.CEILING, property, false, targetParameter.targetType == TargetType.self, false),
                        buildExpression(level, durationValues, null, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
