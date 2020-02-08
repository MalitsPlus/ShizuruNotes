package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.db.RawEquipmentEnhanceData;

import java.math.RoundingMode;

public class NoDamageAction extends ActionParameter {

    enum NoDamageType{
        unknown(0),
        noDamage(1),
        dodgePhysics(2),
        dodgeAll(3),
        abnormal(4),
        debuff(5);

        private int value;
        NoDamageType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static NoDamageType parse(int value){
            for(NoDamageType item : NoDamageType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private NoDamageType noDamageType;

    @Override
    protected void childInit() {
        noDamageType = NoDamageType.parse(actionDetail1);
        actionValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (noDamageType){
            case noDamage:
                return I18N.getString(R.string.Make_s1_to_be_invulnerable_for_s2_sec,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.UNNECESSARY, property));
            case dodgePhysics:
                return I18N.getString(R.string.Make_s1_to_be_invulnerable_to_physical_damage_for_s2_sec,
                        targetParameter.buildTargetClause(), buildExpression(level, RoundingMode.UNNECESSARY, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
