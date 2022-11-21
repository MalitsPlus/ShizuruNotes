package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DispelAction extends ActionParameter {

    enum DispelType{
        unknown(0),
        buff(1),
        debuff(2),
        statusUpBuff(3),
        barriers(10);

        private int value;
        DispelType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static DispelType parse(int value){
            for(DispelType item : DispelType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description(){
            switch (this){
                case buff:
                    return I18N.getString(R.string.buffs);
                case debuff:
                    return I18N.getString(R.string.debuffs);
                case statusUpBuff:
                    return I18N.getString(R.string.status_up_buffs);
                case barriers:
                    return I18N.getString(R.string.barriers);
                default:
                    return I18N.getString(R.string.unknown);
            }
        }
    }

    protected DispelType dispelType;
    protected List<ActionValue> chanceValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        dispelType = DispelType.parse(actionDetail1);
        chanceValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Clear_all_s1_on_s2_with_chance_s3,
                dispelType.description(),
                targetParameter.buildTargetClause(),
                buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property));
    }
}
