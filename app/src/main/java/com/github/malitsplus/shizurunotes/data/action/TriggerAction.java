package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

public class TriggerAction extends ActionParameter {

    enum TriggerType{
        unknown(0),
        dodge(1),
        damage(2),
        hp(3),
        dead(4),
        critical(5),
        criticalWithSummon(6),
        limitTime(7),
        stealthFree(8),
        Break(9),
        dot(10),
        allBreak(11);

        private int value;
        TriggerType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static TriggerType parse(int value){
            for(TriggerType item : TriggerType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private TriggerType triggerType;

    @Override
    protected void childInit() {
        triggerType = TriggerType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (triggerType){
            case hp:
                return I18N.getString(R.string.Trigger_HP_is_below_d, Math.round(actionValue3.value));
            case limitTime:
                return I18N.getString(R.string.Trigger_Left_time_is_below_s_sec, Math.round(actionValue3.value));
            case damage:
                return I18N.getString(R.string.Trigger_d_on_damaged, Math.round(actionValue1.value));
            case dead:
                return I18N.getString(R.string.Trigger_d_on_dead, Math.round(actionValue1.value));
            case critical:
                return I18N.getString(R.string.Trigger_d_on_critical_damaged, Math.round(actionValue1.value));
            case stealthFree:
                return I18N.getString(R.string.Trigger_d_on_stealth, Math.round(actionValue1.value));
            case Break:
                return I18N.getString(R.string.Trigger_d1_on_break_and_last_for_s2_sec, Math.round(actionValue1.value), actionValue3.value);
            case dot:
                return I18N.getString(R.string.Trigger_d_on_dot_damaged, Math.round(actionValue1.value));
            case allBreak:
                return I18N.getString(R.string.Trigger_d_on_all_targets_break,
                        Math.round(actionValue1.value));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
