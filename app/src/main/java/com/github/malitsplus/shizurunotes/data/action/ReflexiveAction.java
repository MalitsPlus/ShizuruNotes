package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class ReflexiveAction extends ActionParameter {

    enum ReflexiveType{
        unknown(0),
        normal(1),
        search(2),
        position(3);

        private int value;
        ReflexiveType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static ReflexiveType parse(int value){
            for(ReflexiveType item : ReflexiveType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private ReflexiveType reflexiveType;

    @Override
    protected void childInit() {
        reflexiveType = ReflexiveType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        if(targetParameter.targetType == TargetType.absolute)
            return I18N.getString(R.string.Change_the_perspective_to_s1_d2, targetParameter.buildTargetClause(), (int)actionValue1);
        else if(reflexiveType == ReflexiveType.search)
            return I18N.getString(R.string.Scout_and_change_the_perspective_on_s, targetParameter.buildTargetClause());
        else
            return I18N.getString(R.string.Change_the_perspective_on_s, targetParameter.buildTargetClause());
    }
}