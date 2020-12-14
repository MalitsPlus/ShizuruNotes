package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class RevivalAction extends ActionParameter {

    enum RevivalType{
        unknown(0),
        normal(1),
        phoenix(2);

        private int value;
        RevivalType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static RevivalType parse(int value){
            for(RevivalType item : RevivalType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private RevivalType revivalType;

    @Override
    protected void childInit() {
        revivalType = RevivalType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (revivalType){
            case normal:
                return I18N.getString(R.string.Revive_s1_with_d2_HP,
                        targetParameter.buildTargetClause(), Math.round(actionValue2.value * 100));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
