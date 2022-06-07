package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.util.ArrayList;
import java.util.List;

public class SpyAction extends ActionParameter{

    enum CancelType{
        None(0),
        Damaged(1);

        private int value;
        CancelType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static CancelType parse(int value){
            for(CancelType item : CancelType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return None;
        }
    }

    protected CancelType cancelType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        cancelType = CancelType.parse(actionDetail2);
        durationValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (cancelType) {
            case Damaged:
                return I18N.getString(R.string.Make_s1_invisible_for_s2_cancels_on_taking_damage,
                        targetParameter.buildTargetClause(),
                        buildExpression(level, actionValues, null, property));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
