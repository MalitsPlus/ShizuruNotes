package com.github.nyanfantasia.shizurunotes.data.action;

import com.github.nyanfantasia.shizurunotes.R;
import com.github.nyanfantasia.shizurunotes.common.I18N;
import com.github.nyanfantasia.shizurunotes.utils.Utils;
import com.github.nyanfantasia.shizurunotes.data.Property;

public class ChangePatternAction extends ActionParameter {
    @Override
    protected void childInit() {
        super.childInit();
    }

    @Override
    public String localizedDetail(int level, Property property) {
        switch (actionDetail1){
            case 1:
                if (actionValue1.value > 0) {
                    return I18N.getString(R.string.Change_attack_pattern_to_d1_for_s2_sec,
                            actionDetail2 % 10, Utils.roundDouble(actionValue1.value));
                } else {
                    return I18N.getString(R.string.Change_attack_pattern_to_d,
                            actionDetail2 % 10);
                }
            case 2:
                return I18N.getString(R.string.Change_skill_visual_effect_for_s_sec,
                        Utils.roundDouble(actionValue1.value));
            default:
                return super.localizedDetail(level, property);
        }
    }
}
