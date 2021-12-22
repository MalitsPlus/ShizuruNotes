package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;

import java.math.RoundingMode;

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
                    return I18N.getString(R.string.Restore_s1_s2_TP, targetParameter.buildTargetClause(), buildExpression(level, null, RoundingMode.CEILING, property, false, true, false));
                } else {
                    return I18N.getString(R.string.Restore_s1_s2_TP, targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property));
                }
            default:
                return I18N.getString(R.string.Make_s1_lose_s2_TP, targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property));
        }
    }
}
