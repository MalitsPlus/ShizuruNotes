package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.PropertyKey;
import com.github.malitsplus.shizurunotes.utils.Utils;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/***
 * TODO: 使所有改变系数值的 Actions 继承此类
 */
public class GiveValueAction extends ActionParameter{
    protected PropertyKey keyType;
    protected List<ActionValue> limitValues = new ArrayList<>();

    @Override
    protected void childInit() {
        actionValues.add(new ActionValue(actionValue2, actionValue3, null));
        limitValues.add(new ActionValue(actionValue4, actionValue5, null));
        switch ((int) actionValue1.value) {
            case 7:
                keyType = PropertyKey.atk; break;
            case 8:
                keyType = PropertyKey.magicStr; break;
            case 9:
                keyType = PropertyKey.def; break;
            case 10:
                keyType = PropertyKey.magicDef; break;
            default:
                keyType = PropertyKey.unknown; break;
        }
    }

    protected String buildFormulaString(int level, Property property) {
        String result = super.localizedDetail(level, property);
        switch ((int) actionValue1.value) {
            case 0:
                result = I18N.getString(R.string.Modifier_add_s1_HP_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, getActionNum(actionDetail1));
                break;
            case 1:
                result = I18N.getString(R.string.Modifier_add_s1_lost_HP_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, getActionNum(actionDetail1));
                break;
            case 2:
                /*
                 * TODO: 从表象出发，迎合游戏内数值手动乘个2，欢迎大佬提出有依据的解决方案。有关此bug详情请查看 issue#29
                 */
                String s1 = buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true);
                try {
                    s1 = Utils.roundIfNeed(2.0 * Double.parseDouble(s1));
                } catch (Exception e) {
                    s1 = "2 * " + s1;
                }
                result = I18N.getString(R.string.Modifier_add_s1_count_of_defeated_enemies_to_value_d2_of_effect_d3,
                        s1,
                        actionDetail2,
                        getActionNum(actionDetail1));
                break;
            case 4:
                result = I18N.getString(R.string.Modifier_add_s1_count_of_targets_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, getActionNum(actionDetail1));
                break;
            case 5:
                result = I18N.getString(R.string.Modifier_add_s1_count_of_damaged_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, getActionNum(actionDetail1));
                break;
            case 6:
                result = I18N.getString(R.string.Modifier_add_s1_total_damage_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, getActionNum(actionDetail1));
                break;
            case 12:
                result = I18N.getString(R.string.Modifier_add_s1_count_of_s2_behind_self_to_value_d3_of_effect_d4,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        targetParameter.buildTargetClause(), actionDetail2, getActionNum(actionDetail1));
                break;
            case 13:
                result = I18N.getString(R.string.Modifier_add_s1_lost_hp_total_hp_of_s2_behind_self_to_value_d3_of_effect_d4,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        targetParameter.buildTargetClause(), actionDetail2, getActionNum(actionDetail1));
                break;
            case 102:
                result = I18N.getString(R.string.Modifier_add_s1_count_of_omemes_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, getActionNum(actionDetail1));
                break;
            default:
                if (actionValue1.value >= 200 && actionValue1.value < 300){
                    result = I18N.getString(R.string.Modifier_add_s1_stacks_of_mark_ID_d2_to_value_d3_of_effect_d4,
                            buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                            ((int)actionValue1.value) % 200, actionDetail2, getActionNum(actionDetail1));
                } else if(actionValue1.value >= 7 && actionValue1.value <= 10){
                    result = I18N.getString(R.string.Modifier_add_s1_s2_of_s3_to_value_d4_of_effect_d5,
                            buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                            keyType.description(), targetParameter.buildTargetClause(), actionDetail2, getActionNum(actionDetail1));
                } else if(actionValue1.value >= 20 && actionValue1.value < 30) {
                    result = I18N.getString(R.string.Modifier_add_s1_number_on_counter_d2_to_value_d3_of_effect_d4,
                            buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                            (int)actionValue1.value % 10, actionDetail2, getActionNum(actionDetail1));
                }
                break;
        }
        if (actionValue4.value != 0 && actionValue5.value != 0) {
            result += I18N.getString(R.string.The_upper_limit_of_this_effect_is_s,
                    buildExpression(level, limitValues, null, property)
            );
        }
        return result;
    }

}
