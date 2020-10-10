package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.PropertyKey;
import com.github.malitsplus.shizurunotes.utils.Utils;

import java.math.RoundingMode;

public class AdditiveAction extends ActionParameter {

    private PropertyKey keyType;

    @Override
    protected void childInit() {
        actionValues.add(new ActionValue(actionValue2, actionValue3, null));

        switch ((int) actionValue1) {
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

    @Override
    public String localizedDetail(int level, Property property) {
        switch ((int) actionValue1) {
            case 0:
                return I18N.getString(R.string.Modifier_add_s1_HP_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, actionDetail1 % 10);
            case 1:
                return I18N.getString(R.string.Modifier_add_s1_lost_HP_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, actionDetail1 % 10);
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
                return I18N.getString(R.string.Modifier_add_s1_count_of_defeated_enemies_to_value_d2_of_effect_d3,
                        s1,
                        actionDetail2,
                        actionDetail1 % 10);
            case 4:
                return I18N.getString(R.string.Modifier_add_s1_count_of_targets_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, actionDetail1 % 10);
            case 5:
                return I18N.getString(R.string.Modifier_add_s1_count_of_damaged_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, actionDetail1 % 10);
            case 6:
                return I18N.getString(R.string.Modifier_add_s1_total_damage_to_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, actionDetail1 % 10);
            case 12:
                return I18N.getString(R.string.Modifier_add_s1_count_of_s2_behind_self_to_value_d3_of_effect_d4,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        targetParameter.buildTargetClause(), actionDetail2, actionDetail1 % 10);
            case 102:
                return I18N.getString(R.string.Modifier_add_s1_count_of_omemes_value_d2_of_effect_d3,
                        buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                        actionDetail2, actionDetail1 % 10);
        }
        if(actionValue1 >= 200 && actionValue1 < 300){
            return I18N.getString(R.string.Modifier_add_s1_stacks_of_mark_ID_d2_to_value_d3_of_effect_d4,
                    buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                    ((int)actionValue1) % 200, actionDetail2, actionDetail1 % 10);
        } else if(actionValue1 >= 7 && actionValue1 <= 10){
            return I18N.getString(R.string.Modifier_add_s1_s2_of_s3_to_value_d4_of_effect_d5,
                    buildExpression(level, null, RoundingMode.UNNECESSARY, property, false, false, true),
                    keyType.description(), targetParameter.buildTargetClause(), actionDetail2, actionDetail1 % 10);
        }
        return super.localizedDetail(level, property);
    }
}
