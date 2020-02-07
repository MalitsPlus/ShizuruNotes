package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.PropertyKey;

public class HealAction extends ActionParameter {

    private ClassModifier healClass;
    private PercentModifier percentModifier;

    @Override
    protected void childInit() {
        healClass = ClassModifier.parse(actionDetail1);
        percentModifier = PercentModifier.parse((int)actionValue1);
        switch (healClass){
            case magical:
                actionValues.add(new ActionValue(actionValue4, actionValue5, PropertyKey.magicStr));
                actionValues.add(new ActionValue(actionValue2, actionValue3, null));
                break;
            case physical:
                actionValues.add(new ActionValue(actionValue4, actionValue5, PropertyKey.atk));
                actionValues.add(new ActionValue(actionValue2, actionValue3, null));
                break;
            default:
                return;
        }
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Restore_s1_s2_s3_HP, targetParameter.buildTargetClause(), buildExpression(level, null, null, property, true, false, false), percentModifier.description());
    }
}
