package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class ChangeEnergyAction extends ActionParameter {

    @Override
    protected void childInit() {
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (actionDetail1){
            case 1:
                if (targetParameter.targetType == TargetType.self){
                    return I18N.getString(R.string.Restore_s1_s2_TP, targetParameter.buildTargetClause(), buildExpression(level, null, null, property, false, true, false));
                } else {
                    return I18N.getString(R.string.Restore_s1_s2_TP, targetParameter.buildTargetClause(), buildExpression(level, property));
                }
            default:
                return I18N.getString(R.string.Make_s1_lose_s2_TP, targetParameter.buildTargetClause(), buildExpression(level, property));
        }
    }
}
