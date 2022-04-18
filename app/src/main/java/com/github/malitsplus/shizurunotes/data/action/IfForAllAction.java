package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.utils.Utils;

public class IfForAllAction extends ActionParameter {

    private String trueClause;
    private String falseClause;
    private IfType ifType;

    @Override
    protected void childInit() {
        ifType = IfType.parse(actionDetail1);

        if (actionDetail2 != 0){
            if(actionDetail1 == 710 || actionDetail1 == 100 || actionDetail1 == 1700 || actionDetail1 == 1601) {
                IfType ifType = IfType.parse(actionDetail1);
                if (ifType != null)
                    trueClause = I18N.getString(R.string.use_d1_to_s2_if_s3,
                        actionDetail2 % 100, targetParameter.buildTargetClause(true), ifType.description());
            } else if(actionDetail1 >= 0 && actionDetail1 < 100){
                trueClause = I18N.getString(R.string.d1_chance_use_d2,
                        actionDetail1, actionDetail2 % 10);
            } else if(actionDetail1 >=500 && actionDetail1 <= 512) {
                trueClause = I18N.getString(R.string.use_d1_if_s2_has_s3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(), ifType.description());
            } else if(actionDetail1 == 599){
                trueClause = I18N.getString(R.string.use_d1_if_s2_has_any_dot_debuff,
                        actionDetail2 % 10, targetParameter.buildTargetClause());
            } else if(actionDetail1 >= 600 && actionDetail1 < 700){
                trueClause = I18N.getString(R.string.use_d1_if_s2_is_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 600, (int)actionValue3.value);
            } else if(actionDetail1 == 700){
                trueClause = I18N.getString(R.string.use_d1_if_s2_is_alone,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true));
            } else if(actionDetail1 >= 701 && actionDetail1 < 710){
                trueClause = I18N.getString(R.string.use_d1_if_the_count_of_s2_except_stealth_units_is_d3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(), actionDetail1 - 700);
            } else if(actionDetail1 == 720) {
                trueClause = I18N.getString(R.string.use_d1_if_among_s2_exists_unit_ID_d3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(), (int) actionValue3.value);
            } else if(actionDetail1 == 721) {
                trueClause = I18N.getString(R.string.use_d1_to_s2_in_state_of_ID_d3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true), (int) actionValue3.value);
            } else if (actionDetail1 >=901 && actionDetail1 < 1000) {
                trueClause = I18N.getString(R.string.use_d1_if_s2_HP_is_below_d3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 900);
            } else if(actionDetail1 == 1000){
                trueClause = I18N.getString(R.string.if_target_is_defeated_by_the_last_effect_then_use_d,
                        actionDetail2 % 10);
            } else if(actionDetail1 == 1001){
                trueClause = I18N.getString(R.string.Use_s_if_this_skill_get_critical,
                        actionDetail2 % 10);
            } else if(actionDetail1 >= 1200 && actionDetail1 < 1300){
                trueClause = I18N.getString(R.string.counter_d3_is_greater_than_or_equal_to_d1_then_use_d2,
                        actionDetail1 % 10, actionDetail2 % 10, actionDetail1 % 100 / 10);
            } else if(actionDetail1 >= 6000 && actionDetail1 < 7000 && actionValue3.value == 0) {
                trueClause = I18N.getString(R.string.use_d1_to_s2_in_state_of_ID_d3,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 6000);
            } else if(actionDetail1 >= 6000 && actionDetail1 < 7000) {
                trueClause = I18N.getString(R.string.use_d1_if_s2_is_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                        actionDetail2 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 6000, (int)actionValue3.value);
            }
        } else if (actionDetail3 == 0){
            trueClause = I18N.getString(R.string.no_effect);
        }

        if (actionDetail3 != 0){
            if(actionDetail1 == 710 || actionDetail1 == 100 || actionDetail1 == 1700 || actionDetail1 == 1601){
                IfType ifType = IfType.parse(actionDetail1);
                if (ifType != null)
                    falseClause = I18N.getString(R.string.use_d1_to_s2_if_not_s3,
                            actionDetail3 % 100, targetParameter.buildTargetClause(true), ifType.description());
            } else if(actionDetail1 >= 0 && actionDetail1 < 100){
                falseClause = I18N.getString(R.string.d1_chance_use_d2,
                        100 - actionDetail1, actionDetail3 % 10);
            } else if(actionDetail1 >=500 && actionDetail1 <= 512) {
                falseClause = I18N.getString(R.string.use_d1_if_s2_does_not_have_s3,
                        actionDetail3 % 10, targetParameter.buildTargetClause(), ifType.description());
            }else if(actionDetail1 == 599){
                falseClause = I18N.getString(R.string.use_d1_if_s2_has_no_dot_debuff,
                        actionDetail3 % 10, targetParameter.buildTargetClause());
            } else if(actionDetail1 >= 600 && actionDetail1 < 700){
                falseClause = I18N.getString(R.string.use_d1_if_s2_is_not_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                        actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 600, (int)actionValue3.value);
            } else if(actionDetail1 == 700){
                falseClause = I18N.getString(R.string.use_d1_if_s2_is_not_alone,
                        actionDetail3 % 10, targetParameter.buildTargetClause(true));
            } else if(actionDetail1 >= 701 && actionDetail1 < 710){
                falseClause = I18N.getString(R.string.use_d1_if_the_count_of_s2_except_stealth_units_is_not_d3,
                        actionDetail3 % 10, targetParameter.buildTargetClause(), actionDetail1 - 700);
            } else if(actionDetail1 == 720) {
                falseClause = I18N.getString(R.string.use_d1_if_among_s2_does_not_exists_unit_ID_d3,
                        actionDetail3 % 10, targetParameter.buildTargetClause(), (int) actionValue3.value);
            } else if(actionDetail1 == 721) {
                falseClause = I18N.getString(R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                        actionDetail3 % 10, targetParameter.buildTargetClause(true), (int) actionValue3.value);
            } else if (actionDetail1 >=901 && actionDetail1 < 1000) {
                falseClause = I18N.getString(R.string.use_d1_if_s2_HP_is_not_below_d3,
                        actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 900);
            } else if(actionDetail1 == 1000){
                falseClause = I18N.getString(R.string.if_target_is_not_defeated_by_the_last_effect_then_use_d,
                        actionDetail3 % 10);
            } else if(actionDetail1 == 1001){
                falseClause = I18N.getString(R.string.Use_s_if_this_skill_not_get_critical,
                        actionDetail3 % 10);
            } else if(actionDetail1 >= 1200 && actionDetail1 < 1300){
                falseClause = I18N.getString(R.string.counter_d3_is_less_than_d1_then_use_d2,
                        actionDetail1 % 10, actionDetail3 % 10, actionDetail1 % 100 / 10);
            } else if(actionDetail1 >= 6000 && actionDetail1 < 7000 && actionValue3.value == 0) {
                falseClause = I18N.getString(R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                        actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 6000);
            } else if(actionDetail1 >= 6000 && actionDetail1 < 7000) {
                falseClause = I18N.getString(R.string.use_d1_if_s2_is_not_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                        actionDetail3 % 10, targetParameter.buildTargetClause(true), actionDetail1 - 6000, (int)actionValue3.value);
            }
        } else if (actionDetail2 == 0){
            falseClause = I18N.getString(R.string.no_effect);
        }

    }

    @Override
    public String localizedDetail(int level, Property property) {
        if(trueClause != null && falseClause != null)
            return I18N.getString(R.string.Exclusive_condition_s, trueClause + falseClause);
        else if(trueClause != null)
            return I18N.getString(R.string.Exclusive_condition_s, trueClause);
        else if(falseClause != null)
            return I18N.getString(R.string.Exclusive_condition_s, falseClause);
        else
            return super.localizedDetail(level, property);
    }
}
