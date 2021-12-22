package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.data.Property;
import com.github.nyanfantasia.shizurunotes.data.PropertyKey;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RegenerationAction extends ActionParameter {

    enum RegenerationType{
        unknown(-1),
        hp(1),
        tp(2);

        private int value;
        RegenerationType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static RegenerationType parse(int value){
            for(RegenerationType item : RegenerationType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description(){
            switch (this){
                case hp:
                    return I18N.getString(R.string.HP);
                case tp:
                    return I18N.getString(R.string.TP);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }

    protected ClassModifier healClass;
    protected RegenerationType regenerationType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        healClass = ClassModifier.parse(actionDetail1);
        regenerationType = RegenerationType.parse(actionDetail2);
        durationValues.add(new ActionValue(actionValue5, actionValue6, null));
        switch (healClass){
            case magical:
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.magicStr));
                break;
            case physical:
                actionValues.add(new ActionValue(actionValue1, actionValue2, null));
                actionValues.add(new ActionValue(actionValue3, actionValue4, PropertyKey.atk));
                break;
        }
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Restore_s1_s2_s3_per_second_for_s4_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, property),
                regenerationType.description(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
    }
}
