package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class IfForChildrenAction extends ActionParameter {

    private String trueClause;
    private String falseClause;
    private IfType ifType;

    @Override
    protected void childInit() {

        if(actionDetail2 != 0){
            ifType = IfType.parse(actionDetail1);
            if(ifType != null) {
                trueClause = I18N.getString(R.string.use_d1_to_s2_if_s3,
                        actionDetail2 % 100, targetParameter.buildTargetClause(true), ifType.description());
            } else {
                if((actionDetail1 >= 600 && actionDetail1 < 700) || actionDetail1 == 710){
                    trueClause = I18N.getString(R.string.use_d1_to_s2_in_state_of_ID_d3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 600);
                } else if(actionDetail1 == 700){
                    trueClause = I18N.getString(R.string.use_d1_to_s2_if_it_is_alone,
                            actionDetail2 % 10, targetParameter.buildTargetClause(true));
                } else if(actionDetail1 >= 901 && actionDetail1 < 1000){
                    trueClause = I18N.getString(R.string.use_d1_if_s2_HP_is_below_d3,
                            actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 900);
                } else if (actionDetail1 == 1300) {
                    trueClause = I18N.getString(R.string.use_d1_to_s2_if_target_is_magical_type,
                            actionDetail2 % 10, targetParameter.buildTargetClause(true));
                } else if (actionDetail1 >= 6000 && actionDetail1 < 7000) {
                    trueClause = I18N.getString(R.string.use_d1_to_s2_in_state_of_ID_d3,
                            actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 6000);
                }
            }
        }

        if(actionDetail3 != 0){
            ifType = IfType.parse(actionDetail1);
            if(ifType != null) {
                falseClause = I18N.getString(R.string.use_d1_to_s2_if_not_s3,
                        actionDetail3 % 100, targetParameter.buildTargetClause(true), ifType.description());
            } else {
                if((actionDetail1 >= 600 && actionDetail1 < 700) || actionDetail1 == 710){
                    falseClause = I18N.getString(R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                            actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 600);
                } else if(actionDetail1 == 700){
                    falseClause = I18N.getString(R.string.use_d1_to_s2_if_it_is_not_alone,
                            actionDetail3 % 10, targetParameter.buildTargetClause(true));
                } else if(actionDetail1 >= 901 && actionDetail1 < 1000){
                    falseClause = I18N.getString(R.string.use_d1_if_s2_HP_is_not_below_d3,
                            actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 900);
                } else if (actionDetail1 == 1300) {
                    falseClause = I18N.getString(R.string.use_d1_to_s2_if_target_is_not_magical_type,
                            actionDetail3 % 10, targetParameter.buildTargetClause(true));
                } else if (actionDetail1 >= 6000 && actionDetail1 < 7000) {
                    falseClause = I18N.getString(R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                            actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 6000);
                }
            }
        }

    }

    @Override
    public String localizedDetail(int level, Property property) {
        if(actionDetail1 == 100 || actionDetail1 == 101 || actionDetail1 == 200 || actionDetail1 == 300 || actionDetail1 == 500 || actionDetail1 == 501
                || actionDetail1 == 502 || actionDetail1 == 503 || actionDetail1 == 512
                || (actionDetail1 >=600 && actionDetail1 < 900) || (actionDetail1 >= 901 && actionDetail1 < 1000)
                || actionDetail1 == 1300 || actionDetail1 == 1400 || (actionDetail1 >= 6000 && actionDetail1 < 7000)) {
            if(trueClause != null && falseClause != null)
                return I18N.getString(R.string.Condition_s, trueClause + falseClause);
            else if(trueClause != null)
                return I18N.getString(R.string.Condition_s, trueClause);
            else if(falseClause != null)
                return I18N.getString(R.string.Condition_s, falseClause);
        } else if(actionDetail1 >= 0 && actionDetail1 < 100){
            if(actionDetail2 != 0 && actionDetail3 != 0){
                return I18N.getString(R.string.Random_event_d1_chance_use_d2_otherwise_d3, actionDetail1, actionDetail2 % 10, actionDetail3 % 10);
            } else if(actionDetail2 != 0){
                return I18N.getString(R.string.Random_event_d1_chance_use_d2, actionDetail1, actionDetail2 % 10);
            } else if(actionDetail3 != 0){
                return I18N.getString(R.string.Random_event_d1_chance_use_d2, 100 - actionDetail1, actionDetail3 % 10);
            }
        }
        return super.localizedDetail(level, property);
    }
}

enum IfType{
    controllered(100),
    hastened(101),
    blind(200),
    convert(300),
    decoy(400),
    burn(500),
    curse(501),
    poison(502),
    venom(503),
    poisonOrVenom(512),
    Break(710),
    polymorph(1400);

    private int value;
    IfType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static IfType parse(int value){
        for(IfType item : IfType.values()){
            if(item.getValue() == value)
                return item;
        }
        return null;
    }

    public String description(){
        switch (this){
            case controllered: return I18N.getString(R.string.controlled);
            case hastened: return I18N.getString(R.string.hastened);
            case blind: return I18N.getString(R.string.blinded);
            case convert: return I18N.getString(R.string.charmed_or_confused);
            case decoy: return I18N.getString(R.string.decoying);
            case burn: return I18N.getString(R.string.burned);
            case curse: return I18N.getString(R.string.cursed);
            case poison: return I18N.getString(R.string.poisoned);
            case venom: return I18N.getString(R.string.venomed);
            case poisonOrVenom: return I18N.getString(R.string.poisoned_or_venomed);
            case Break: return I18N.getString(R.string.breaking);
            case polymorph: return I18N.getString(R.string.polymorphed);
            default: return "";
        }
    }
}