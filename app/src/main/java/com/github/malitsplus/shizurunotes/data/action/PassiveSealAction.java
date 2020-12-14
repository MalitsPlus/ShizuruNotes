package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

class PassiveSealAction extends ActionParameter {
    protected int sealNumLimit;
    protected List<ActionValue> sealDuration = new ArrayList<>();
    protected List<ActionValue> lifeTime = new ArrayList<>();
    protected ePassiveTiming passiveTiming;
    protected eSealTarget sealTarget;

    @Override
    protected void childInit() {
        sealNumLimit = (int)actionValue1.value;
        sealDuration.add(new ActionValue(actionValue3, actionValue4, null));
        lifeTime.add(new ActionValue(actionValue5, actionValue6, null));
        passiveTiming = ePassiveTiming.parse(actionDetail1);
        sealTarget = eSealTarget.parse(actionDetail3);
    }

    @Override
    public String localizedDetail(int level, Property property) {
            return I18N.getString(R.string.Passive_Whenever_s1_get_s2_seals_s3_with_d4_marks_id_d5_for_s6_sec_caps_at_d7_This_passive_skill_will_listen_for_s8_sec,
                    targetParameter.buildTargetClause(),
                    passiveTiming.description(),
                    sealTarget.description(),
                    actionDetail2,
                    (int)actionValue2.value,
                    buildExpression(level, sealDuration, RoundingMode.UNNECESSARY, property),
                    (int)actionValue1.value,
                    buildExpression(level, lifeTime, RoundingMode.UNNECESSARY, property)
            );
        }

    protected enum ePassiveTiming {
        Unknown(-1),
        Buff(1);

        private int value;
        ePassiveTiming(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static ePassiveTiming parse(int value){
            for(ePassiveTiming item : ePassiveTiming.values()){
                if(item.getValue() == value)
                    return item;
            }
            return Unknown;
        }

        public String description() {
            switch (this) {
                case Buff:
                    return I18N.getString(R.string.buffs);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }

    protected enum eSealTarget {
        Unknown(-1),
        Self(0);

        private int value;
        eSealTarget(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static eSealTarget parse(int value){
            for(eSealTarget item : eSealTarget.values()){
                if(item.getValue() == value)
                    return item;
            }
            return Unknown;
        }

        public String description() {
            switch (this) {
                case Self:
                    return I18N.getString(R.string.self);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }
}
