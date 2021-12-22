package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;
import com.github.nyanfantasia.shizurunotes.data.PropertyKey;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class HealFieldAction extends ActionParameter {

    private ClassModifier healClass;
    private PercentModifier percentModifier;
    private FieldType fieldType;
    private List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        healClass = actionDetail1 % 2 == 0 ? ClassModifier.magical : ClassModifier.physical;
        percentModifier = PercentModifier.parse(actionDetail2);
        if (actionDetail1 <= 2)
            fieldType = FieldType.normal;
        else
            fieldType = FieldType.repeat;

        switch (healClass){
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
                    return I18N.getString(R.string.Summon_a_healing_field_of_radius_d1_to_heal_s2_s3_s4_HP_per_second_for_5s_sec,
                            (int)actionValue7.value,
                            targetParameter.buildTargetClause(),
                            buildExpression(level, property),
                            percentModifier.description(),
                            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
                } else{
                    return I18N.getString(R.string.Summon_a_healing_field_of_radius_d1_at_position_of_s2_to_heal_s3_s4_HP_per_second_for_s5_sec,
                            (int)actionValue7.value,
                            targetParameter.buildTargetClause(),
                            buildExpression(level, property),
                            percentModifier.description(),
                            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
                }
            default:
                return super.localizedDetail(level, property);
        }
    }
}

enum FieldType{
    normal,
    repeat;
}