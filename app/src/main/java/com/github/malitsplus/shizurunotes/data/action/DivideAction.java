package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;

public class DivideAction extends ActionParameter {
    @Override
    protected void childInit() {
        actionValues.add(new ActionValue(actionValue2, actionValue3, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        if(actionValue1 == 0)
            return I18N.getString(R.string.Modifier_multiple_s1_HP_max_HP_to_value_d2_of_effect_d3,
                    buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                    actionDetail2, actionDetail1 % 10);
        else if(actionValue1 == 1)
            return I18N.getString(R.string.Modifier_multiple_s1_lost_HP_max_HP_to_value_d2_of_effect_d3,
                    buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                    actionDetail2, actionDetail1 % 10);
        else if(actionValue1 == 2)
            return I18N.getString(R.string.Modifier_multiple_s1_count_of_defeated_enemies_to_value_d2_of_effect_d3,
                    buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                    actionDetail2, actionDetail1 % 10);
        else if(actionValue1 >= 200 && actionValue1 < 300)
            return I18N.getString(R.string.Modifier_multiple_s1_stacks_of_mark_ID_d2_to_value_d3_of_effect_d4,
                    buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                    ((int)actionValue1) % 200, actionDetail2, actionDetail1 % 10);
        else
            return super.localizedDetail(level, property);
    }
}
