package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class CountDownAction extends ActionParameter {
    @Override
    protected void childInit() {
        super.childInit();
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Set_a_countdown_timer_on_s1_trigger_effect_d2_after_s3_sec,
                targetParameter.buildTargetClause(), actionDetail1 % 10, actionValue1.valueString());
    }
}
