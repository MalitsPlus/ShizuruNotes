package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

class HealDownAction extends ActionParameter {

    protected PercentModifier percentModifier;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        percentModifier = PercentModifier.parse((int)actionValue1.value);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Multiple_heal_effects_from_s1_with_s2_for_s3_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
    }
}
