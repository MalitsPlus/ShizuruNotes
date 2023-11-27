package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class CopyAtkParamAction extends ActionParameter {

    enum AtkType{
        atk(1),
        magicStr(2);

        private int value;
        AtkType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static AtkType parse(int value){
            for(AtkType item : AtkType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return atk;
        }

        public String description() {
            switch (this) {
                case atk: return I18N.getString(R.string.ATK);
                case magicStr: return I18N.getString(R.string.Magic_STR);
            }
            return I18N.getString(R.string.unknown);
        }
    }

    protected AtkType atkType;

    @Override
    protected void childInit() {
        super.childInit();
        atkType = AtkType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Use_param_s1_of_s2_for_action_d3,
            atkType.description(),
            targetParameter.buildTargetClause(),
            getActionNum(actionDetail2)
        );
    }
}
