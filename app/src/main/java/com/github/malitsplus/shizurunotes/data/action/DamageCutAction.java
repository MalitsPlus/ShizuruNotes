package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import java.util.ArrayList;
import java.util.List;

public class DamageCutAction extends ActionParameter {

    enum DamageType{
        Physical(1),
        Magical(2),
        All(3);

        private int value;
        DamageType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static DamageType parse(int value){
            for(DamageType item : DamageType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return All;
        }
    }

    protected DamageType damageType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        damageType = DamageType.parse(actionDetail1);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (damageType) {
            case Physical:
                return I18N.getString(R.string.Reduce_s1_physical_damage_taken_by_s2_for_s3_sec,
                        buildExpression(level, actionValues, null, property),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, durationValues, null, property));
            case Magical:
                return I18N.getString(R.string.Reduce_s1_magical_damage_taken_by_s2_for_s3_sec,
                        buildExpression(level, actionValues, null, property),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, durationValues, null, property));
            case All:
                return I18N.getString(R.string.Reduce_s1_all_damage_taken_by_s2_for_s3_sec,
                        buildExpression(level, actionValues, null, property),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, durationValues, null, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
