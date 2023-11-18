package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class LoopMotionRepeatAction extends ActionParameter {

    protected String successClause;
    protected String failureClause;

    @Override
    protected void childInit() {
        super.childInit();
        if(actionDetail2 != 0)
            successClause = I18N.getString(R.string.use_d_after_time_up, getActionNum(actionDetail2));
        if(actionDetail3 != 0)
            failureClause = I18N.getString(R.string.use_d_after_break, getActionNum(actionDetail3));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        String mainClause = I18N.getString(R.string.Repeat_effect_d1_every_s2_sec_up_to_s3_sec_break_if_taken_more_than_s4_damage,
                getActionNum(actionDetail1), actionValue2.valueString(), actionValue1.valueString(), actionValue3.valueString());
        if(successClause != null && failureClause != null)
            return mainClause + successClause + failureClause;
        else if(successClause != null)
            return mainClause + successClause;
        else if(failureClause != null)
            return mainClause + failureClause;
        else
            return mainClause;
    }
}
