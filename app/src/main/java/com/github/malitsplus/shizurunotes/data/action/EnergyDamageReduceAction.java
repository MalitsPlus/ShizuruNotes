package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EnergyDamageReduceAction extends ActionParameter {
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        durationValues.add(new ActionValue(actionValue2, actionValue3, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Reduces_incoming_energy_damage_down_to_s1_percent_of_s2_for_s3_sec,
                actionValue1.valueString(),
                targetParameter.buildTargetClause(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
    }
}
