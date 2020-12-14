package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

class PassiveDamageUpAction extends ActionParameter {
    protected double debuffDamageUpValue;
    protected double debuffDamageUpLimitValue;
    protected double debuffDamageUpTimer;
    protected eCountType countType;
    protected eEffectType effectType;

    @Override
    protected void childInit() {
        debuffDamageUpLimitValue = actionValue2.value;
        debuffDamageUpValue = actionValue1.value;
        debuffDamageUpTimer = actionValue3.value;
        countType = eCountType.parse(actionDetail1);
        effectType = eEffectType.parse(actionDetail2);
        actionValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (countType) {
            case Debuff:
                return I18N.getString(R.string.Make_s1_damage_changes_into_s234_times_caps_to_s5_times_dur_s6_sec,
                        targetParameter.buildTargetClause(),
                        effectType.description(),
                        Utils.roundIfNeed(debuffDamageUpValue),
                        countType.description(),
                        Utils.roundIfNeed(debuffDamageUpLimitValue),
                        buildExpression(level, property)
                );
            default:
                return localizedDetail(level, property);
        }
    }

    protected enum eCountType {
        Unknown(-1),
        Debuff(1);

        private int value;
        eCountType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static eCountType parse(int value){
            for(eCountType item : eCountType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return Unknown;
        }

        public String description() {
            switch (this) {
                case Debuff:
                    return I18N.getString(R.string.debuff);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }

    protected enum eEffectType {
        Unknown(-1),
        Add(1),
        Subtract(2);

        private int value;
        eEffectType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static eEffectType parse(int value){
            for(eEffectType item : eEffectType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return Unknown;
        }

        public String description() {
            switch (this) {
                case Add:
                    return I18N.getString(R.string.add);
                case Subtract:
                    return I18N.getString(R.string.subtract);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }
}
