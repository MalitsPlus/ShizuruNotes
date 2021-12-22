package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;
import com.github.nyanfantasia.shizurunotes.data.PropertyKey;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AttackFieldAction extends ActionParameter {

    private ClassModifier damageClass;
    private FieldType fieldType;
    private List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        damageClass = actionDetail1 % 2 == 0 ? ClassModifier.magical : ClassModifier.physical;
        if (actionDetail1 <= 2)
            fieldType = FieldType.normal;
        else
            fieldType = FieldType.repeat;

        switch (damageClass){
            case magical:
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.magicStr));
                break;
            case physical:
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.atk));
        }
        durationValues.add(new ActionValue(actionValue5, actionValue6, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (fieldType){
            case repeat:
                if (targetParameter.targetType == TargetType.absolute) {
                    return I18N.getString(R.string.Summon_a_field_of_radius_d1_to_deal_s2_s3_damage_per_second_for_s4_sec_to_s5,
                            (int)actionValue7.value,
                            buildExpression(level, property),
                            damageClass.description(),
                            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property),
                            targetParameter.buildTargetClause());
                } else{
                    return I18N.getString(R.string.Summon_a_field_of_radius_d1_at_position_of_s2_to_deal_s3_s4_damage_per_second_for_s5_sec,
                            (int)actionValue7.value,
                            targetParameter.buildTargetClause(),
                            buildExpression(level, property),
                            damageClass.description(),
                            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
                }
            default:
                return super.localizedDetail(level, property);
        }
    }
}
