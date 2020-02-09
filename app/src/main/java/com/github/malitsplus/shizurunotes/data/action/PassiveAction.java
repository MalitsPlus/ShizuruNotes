package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.PropertyKey;

public class PassiveAction extends ActionParameter {

    protected PropertyKey propertyKey;

    @Override
    protected void childInit() {
        switch (actionDetail1){
            case 1:
                propertyKey = PropertyKey.hp; break;
            case 2:
                propertyKey = PropertyKey.atk; break;
            case 3:
                propertyKey = PropertyKey.def; break;
            case 4:
                propertyKey = PropertyKey.magicStr; break;
            case 5:
                propertyKey = PropertyKey.magicDef; break;
            default:
                propertyKey = PropertyKey.unknown; break;
        }
        actionValues.add(new ActionValue(actionValue2, actionValue3, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Raise_s1_s2, buildExpression(level, property), propertyKey.description());
    }

    public Property propertyItem(int level){
        return Property.getPropertyWithKeyAndValue(null, propertyKey, actionValue2 + actionValue3 * level);
    }
}
