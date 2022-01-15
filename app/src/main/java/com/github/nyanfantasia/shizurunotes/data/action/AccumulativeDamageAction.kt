package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AccumulativeDamageAction extends ActionParameter {

    private List<ActionValue> stackValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        actionValues.add(new ActionValue(actionValue2, actionValue3, null));
        stackValues.add(new ActionValue(actionValue4, actionValue5, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Add_additional_s1_damage_per_attack_with_max_s2_stacks_to_current_target,
                buildExpression(level, property), buildExpression(level, stackValues, RoundingMode.FLOOR, property));
    }
}
