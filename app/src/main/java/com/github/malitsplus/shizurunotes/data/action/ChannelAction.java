package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;

public class ChannelAction extends AuraAction {

    enum ReleaseType{
        damage(1),
        unknown(2);

        private int value;
        ReleaseType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static ReleaseType parse(int value){
            for(ReleaseType item : ReleaseType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    protected ReleaseType releaseType;

    @Override
    protected void childInit() {
        super.childInit();
        releaseType = ReleaseType.parse(actionDetail2);

    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (releaseType){
            case damage:
                return I18N.getString(R.string.Channeling_for_s1_sec_disrupted_by_taking_damage_d2_times_s3_s4_s5_s6_s7,
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property),
                        actionDetail3,
                        auraActionType.description(),
                        targetParameter.buildTargetClause(),
                        buildExpression(level, RoundingMode.UP, property),
                        percentModifier.description(),
                        auraType.description());
            default:
                return super.localizedDetail(level, property);
        }
    }
}
