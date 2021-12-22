package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;

import java.math.RoundingMode;

public class EnchantStrikeBackAction extends BarrierAction {
    @Override
    protected void childInit() {
        super.childInit();
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (barrierType){
            case physicalGuard:
                return I18N.getString(R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_when_taking_physical_damage,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property), actionValue3.valueString());
            case magicalGuard:
                return I18N.getString(R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_when_taking_magical_damage,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property), actionValue3.valueString());
            case physicalDrain:
                return I18N.getString(R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_and_recover_the_same_HP_when_taking_physical_damage,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property), actionValue3.valueString());
            case magicalDrain:
                return I18N.getString(R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_and_recover_the_same_HP_when_taking_magical_damage,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property), actionValue3.valueString());
            case bothGuard:
                return I18N.getString(R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_when_taking_damage,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property), actionValue3.valueString());
            case bothDrain:
                return I18N.getString(R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_and_recover_the_same_HP_when_taking_damage,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.CEILING, property), actionValue3.valueString());
            default:
                return super.localizedDetail(level, property);
        }
    }
}
