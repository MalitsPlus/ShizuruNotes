package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AbnormalStateFieldAction extends ActionParameter {

    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        durationValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Summon_a_field_of_radius_d1_on_s2_to_cast_effect_d3_for_s4_sec,
                (int)actionValue3.value,
                targetParameter.buildTargetClause(),
                getActionNum(actionDetail1),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
    }
}
