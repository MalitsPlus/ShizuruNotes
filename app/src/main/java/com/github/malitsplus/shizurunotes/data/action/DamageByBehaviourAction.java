package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Ailment;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

class DamageByBehaviourAction extends ActionParameter {

    protected Ailment ailment;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        ailment = new Ailment(rawActionType, actionDetail1);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.s1_will_be_applied_the_s2_once_they_take_any_actions_will_take_s3_damage_every_second_lasted_4s_seconds,
                targetParameter.buildTargetClause(),
                ailment.description(),
                buildExpression(level, property),
                buildExpression(level, durationValues, RoundingMode.HALF_UP, property));
    }
}
