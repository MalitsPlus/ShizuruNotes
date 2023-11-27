package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Property;

public class ChangeCriticalReferenceAction extends ActionParameter {

    enum CriticalReferenceType{
        unknown(-1),
        normal(1),
        physicalCrit(2),
        magicCrit(3),
        sumCrit(4);

        private int value;
        CriticalReferenceType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static CriticalReferenceType parse(int value){
            for(CriticalReferenceType item : CriticalReferenceType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description() {
            switch (this) {
                case normal: return I18N.getString(R.string.normal);
                case physicalCrit: return I18N.getString(R.string.Physical_Critical);
                case magicCrit: return I18N.getString(R.string.Magic_Critical);
                case sumCrit: return I18N.getString(R.string.Sum_of_phy_and_mag);
            }
            return I18N.getString(R.string.unknown);
        }
    }

    protected CriticalReferenceType referenceType;

    @Override
    protected void childInit() {
        super.childInit();
        referenceType = CriticalReferenceType.parse(actionDetail2);
    }

    @Override
    public String localizedDetail(int level, Property property) {
        if (referenceType == CriticalReferenceType.normal) {
            return I18N.getString(R.string.no_effect);
        }
        return I18N.getString(R.string.Use_critical_reference_s1_for_skill_d2,
            referenceType.description(),
            getActionNum(actionDetail1)
        );
    }
}
