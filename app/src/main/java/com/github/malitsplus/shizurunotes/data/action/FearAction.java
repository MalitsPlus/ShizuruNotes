package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FearAction extends ActionParameter {

    protected List<ActionValue> durationValues = new ArrayList<>();
    protected List<ActionValue> chanceValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        durationValues.add(new ActionValue(actionValue1, actionValue2, null));
        chanceValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Fear_s1_with_s2_chance_for_s3_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
    }
}
