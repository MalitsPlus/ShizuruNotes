package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InhibitHealAction extends ActionParameter {

    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        durationValues.add(new ActionValue(actionValue2, actionValue3, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.When_s1_receive_healing_deal_s2_healing_amount_damage_instead_last_for_s3_sec_or_unlimited_time_if_triggered_by_field,
                targetParameter.buildTargetClause(),
                actionValue1,
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
    }
}
