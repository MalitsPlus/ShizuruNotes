package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EveryAttackCriticalAction extends ActionParameter {
    enum CriticalType{
        physical(1),
        magical(2),
        both(3);

        private int value;
        CriticalType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static CriticalType parse(int value){
            for(CriticalType item : CriticalType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return physical;
        }

        public String description() {
            switch (this) {
                case physical: return I18N.getString(R.string.physical);
                case magical: return I18N.getString(R.string.magical);
                case both: return I18N.getString(R.string.both);
            }
            return I18N.getString(R.string.unknown);
        }
    }

    protected CriticalType criticalType;
    protected List<ActionValue> durationValues = new ArrayList<>();

    @Override
    protected void childInit() {
        super.childInit();
        criticalType = CriticalType.parse(actionDetail1);
        durationValues.add(new ActionValue(actionValue1, actionValue2, null));
    }

    @Override
    public String localizedDetail(int level, Property property) {
        return I18N.getString(R.string.Make_s1_dmg_dealt_by_self_promised_to_get_critical_for_s2_sec,
            criticalType.description(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        );
    }
}
