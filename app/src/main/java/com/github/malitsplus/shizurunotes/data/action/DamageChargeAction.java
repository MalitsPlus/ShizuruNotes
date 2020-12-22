package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;

public class DamageChargeAction extends ActionParameter {
    @Override
    protected void childInit() {
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Charge_for_s1_sec_and_deal_s2_damage_taken_additional_damage_on_the_next_effect,
                actionValue3.valueString(), buildExpression(level, RoundingMode.UNNECESSARY, property));
    }
}
