package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.Property;

public class ModeChangeAction extends ActionParameter {

    enum ModeChangeType{
        unknown(0),
        time(1),
        energy(2),
        release(3);

        private int value;
        ModeChangeType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static ModeChangeType parse(int value){
            for(ModeChangeType item : ModeChangeType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private ModeChangeType modeChangeType;

    @Override
    protected void childInit() {
        modeChangeType = ModeChangeType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (modeChangeType){
            case time:
                return I18N.getString(R.string.Change_attack_pattern_to_d1_for_s2_sec,
                        actionDetail2 % 10, actionValue1);
            case energy:
                return I18N.getString(R.string.Cost_s1_TP_sec_change_attack_pattern_to_d2_until_TP_is_zero,
                        Utils.roundDownDouble(actionValue1.value), actionDetail2 % 10);
            case release:
                return I18N.getString(R.string.Change_attack_pattern_back_to_d_after_effect_over,
                        actionDetail2 % 10);
            default:
                return super.localizedDetail(level, property);
        }
    }
}
