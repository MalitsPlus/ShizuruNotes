package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class ChangeEnergyRecoveryRatioByDamageAction extends ActionParameter {
    @Override
    protected void childInit() {
        super.childInit();
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.change_energy_recovery_ratio_by_damage_to_s1_when_s2_get_damage_next_time,
                actionValue1,
                targetParameter.buildTargetClause());
    }
}
