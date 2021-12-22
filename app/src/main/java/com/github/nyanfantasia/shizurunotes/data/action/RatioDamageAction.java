package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;
import com.github.nyanfantasia.shizurunotes.user.UserSettings;

import java.math.RoundingMode;

public class RatioDamageAction extends ActionParameter {

    enum HPtype{
        unknown(0),
        max(1),
        current(2),
        originalMax(3);

        private int value;
        HPtype(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static HPtype parse(int value){
            for(HPtype item : HPtype.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    protected HPtype hptype;

    @Override
    protected void childInit() {
        super.childInit();
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        hptype = HPtype.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        String r = buildExpression(level, RoundingMode.UNNECESSARY, property);
        if (UserSettings.get().getExpression() != UserSettings.EXPRESSION_VALUE) {
            r = String.format("(%s)", r);
        }
        switch (hptype){
            case max:
                return I18N.getString(R.string.Deal_damage_equal_to_s1_of_target_max_HP_to_s2,
                        r, targetParameter.buildTargetClause());
            case current:
                return I18N.getString(R.string.Deal_damage_equal_to_s1_of_target_current_HP_to_s2,
                        r, targetParameter.buildTargetClause());
            case originalMax:
                return I18N.getString(R.string.Deal_damage_equal_to_s1_of_targets_original_max_HP_to_s2,
                        r, targetParameter.buildTargetClause());
            default:
                return super.localizedDetail(level, property);
        }
    }
}
