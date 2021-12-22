package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.utils.Utils;
import com.github.nyanfantasia.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AttackSealAction extends ActionParameter {

    enum Condition{
        unknown(-1),
        damage(1),
        target(2),
        hit(3),
        criticalHit(4);

        private int value;
        Condition(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static Condition parse(int value){
            for(Condition item : Condition.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    enum Target{
        unknown(-1),
        target(0),
        owner(1);

        private int value;
        Target(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static Target parse(int value){
            for(Target item : Target.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    protected Condition condition;
    protected Target target;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        condition = Condition.parse(actionDetail1);
        target = Target.parse(actionDetail3);
        durationValues.add(new ActionValue(actionValue3, actionValue4, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        if(condition == Condition.hit)
            return I18N.getString(R.string.Make_s1_when_get_one_hit_by_the_caster_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec,
                    targetParameter.buildTargetClause(),
                    Utils.roundDownDouble(actionValue1.value),
                    Utils.roundDownDouble(actionValue2.value),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
        else if(condition == Condition.damage && target == Target.owner)
            return I18N.getString(R.string.Make_s1_when_deal_damage_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec,
                    targetParameter.buildTargetClause(),
                    Utils.roundDownDouble(actionValue1.value),
                    Utils.roundDownDouble(actionValue2.value),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
        else if(condition == Condition.criticalHit && target == Target.owner)
            return I18N.getString(R.string.Make_s1_when_deal_critical_damage_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec,
                    targetParameter.buildTargetClause(),
                    Utils.roundDownDouble(actionValue1.value),
                    Utils.roundDownDouble(actionValue2.value),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property));
        else
            return super.localizedDetail(level, property);
    }
}
