package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class UnableStateGuardAction extends ActionParameter {

    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        String amount = buildExpression(level, property);
        try {
            int intAmount = Integer.parseInt(amount);
            if (intAmount < 0) {
                amount = String.valueOf((long)Integer.MAX_VALUE - Integer.MIN_VALUE + intAmount);
            }
        } catch (Exception ignored) { }

        return I18N.getString(R.string.Enable_s1_to_resist_all_sorts_of_incapacity_efficacies_up_to_s2_times_in_a_period_of_s3_sec,
                targetParameter.buildTargetClause(),
                amount,
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        );
    }
}
