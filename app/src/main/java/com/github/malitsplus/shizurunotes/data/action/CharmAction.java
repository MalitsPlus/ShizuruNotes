package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CharmAction extends ActionParameter {

    enum CharmType{
        unknown(-1),
        charm(0),
        confusion(1);

        private int value;
        CharmType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static CharmType parse(int value){
            for(CharmType item : CharmType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private List<ActionValue> chanceValues = new ArrayList<>();
    private List<ActionValue> durationValues = new ArrayList<>();
    private CharmType charmType;

    @Override
    protected void childInit() {
        charmType = CharmType.parse(actionDetail1);
        durationValues.add(new ActionValue(actionValue1, actionValue2, null));
        switch (charmType){
            case charm:
                chanceValues.add(new ActionValue(actionValue3, actionValue4 * 100, null));
                break;
            default:
                chanceValues.add(new ActionValue(actionValue3 * 100, actionValue4 * 100, null));
                break;
        }
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (charmType){
            case charm:
                return I18N.getString(R.string.Charm_s1_with_s2_chance_for_s3_sec, targetParameter.buildTargetClause(), buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property), buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            case confusion:
                return I18N.getString(R.string.Confuse_s1_with_s2_chance_for_s3_sec, targetParameter.buildTargetClause(), buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property), buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
