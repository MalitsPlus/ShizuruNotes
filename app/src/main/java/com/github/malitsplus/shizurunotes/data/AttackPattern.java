package com.github.malitsplus.shizurunotes.data;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.common.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttackPattern {
    private static String PHY_ICON = Statics.API_URL + "/icon/equipment/101011.webp";
    private static String MAG_ICON = Statics.API_URL + "/icon/equipment/101251.webp";

    public int patternId;
    public int unitId;
    public int loopStart;
    public int loopEnd;
    public List<AttackPatternItem> items = new ArrayList<>();
    public List<Integer> rawAttackPatterns;

    public AttackPattern(int patternId, int unitId, int loopStart, int loopEnd, List<Integer> rawAttackPatterns) {
        this.patternId = patternId;
        this.unitId = unitId;
        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        this.rawAttackPatterns = rawAttackPatterns;
    }

//    public AttackPattern setItems(){
//        items.clear();
//        for(int i = 0; i < rawAttackPatterns.size(); i++){
//            if (i >= loopEnd)
//                break;
//
//            int raw = rawAttackPatterns.get(i);
//            items.add(new AttackPatternItem(raw, getLoopText(i), ""));
//        }
//        return this;
//    }

    public AttackPattern setItems(List<Skill> skills, int atkType){
        items.clear();
        for(int i = 0; i < rawAttackPatterns.size(); i++){
            if (i >= loopEnd)
                break;

            int raw = rawAttackPatterns.get(i);

            String iconUrl;

            if(raw == 1){
                if(atkType == 2) {
                    iconUrl = MAG_ICON;
                }
                else {
                    iconUrl = PHY_ICON;
                }
            } else {
                Skill skill = null;
                for(Skill innerSkill : skills){
                    if(innerSkill.getSkillClass() == PatternType.parse(raw).skillClass()){
                        skill = innerSkill;
                        break;
                    }
                }
                if (skill != null) {
                    iconUrl = skill.iconUrl;
                } else {
                    iconUrl = Statics.UNKNOWN_ICON;
                }
            }
            items.add(new AttackPatternItem(raw, getLoopText(i), iconUrl));
        }
        return this;
    }

    private String getLoopText(int index){
        if(index + 1 == loopStart)
            return I18N.getString(R.string.loop_start);
        if(index + 1 == loopEnd)
            return I18N.getString(R.string.loop_end);
        return "";
    }

    public String getEnemyPatternText(String prefix){
        StringBuilder sb = new StringBuilder().append(prefix);

        boolean isSinglePattern = true;
        for (int it: rawAttackPatterns){
            if (it != 1 && it != 0) {
                isSinglePattern = false;
                break;
            }
        }
        if (isSinglePattern)
            return sb.append(I18N.getString(R.string.text_normal_attack_only)).toString();


        for (AttackPatternItem it: items){
            if (it.loopText.equals("")){
                sb.append(it.skillText);
            } else if (it.loopText.equals(I18N.getString(R.string.loop_start))){
                sb.append(I18N.getString(R.string.text_loop_start)).append(it.skillText);
            } else if (it.loopText.equals(I18N.getString(R.string.loop_end))){
                sb.append(it.skillText).append(I18N.getString(R.string.text_loop_end)).append("-");
                break;
            }
            sb.append("-");
        }
        return sb.deleteCharAt(sb.lastIndexOf("-")).toString();
    }

    public class AttackPatternItem{
        public String skillText;
        public String loopText;
        public String iconUrl;

        public AttackPatternItem(int rawAttackPatterns, String loopText, String iconUrl){
            this.skillText = PatternType.parse(rawAttackPatterns).description();
            this.loopText = loopText;
            this.iconUrl = iconUrl;
        }

        public String getSkillText() {
            return skillText;
        }

        public String getLoopText() {
            return loopText;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }
}

enum PatternType{
    none(0),
    hit(1),
    main1(1001),
    main2(1002),
    main3(1003),
    main4(1004),
    main5(1005),
    main6(1006),
    main7(1007),
    main8(1008),
    main9(1009),
    main10(1010),
    sp1(2001),
    sp2(2002),
    sp3(2003),
    sp4(2004),
    sp5(2005);

    private int value;
    PatternType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static PatternType parse(int value){
        for(PatternType item : PatternType.values()){
            if(item.getValue() == value)
                return item;
        }
        return none;
    }

    public Skill.SkillClass skillClass(){
        switch (this){
            case main1:
                return Skill.SkillClass.MAIN1;
            case main2:
                return Skill.SkillClass.MAIN2;
            case main3:
                return Skill.SkillClass.MAIN3;
            case main4:
                return Skill.SkillClass.MAIN4;
            case main5:
                return Skill.SkillClass.MAIN5;
            case main6:
                return Skill.SkillClass.MAIN6;
            case main7:
                return Skill.SkillClass.MAIN7;
            case main8:
                return Skill.SkillClass.MAIN8;
            case main9:
                return Skill.SkillClass.MAIN9;
            case main10:
                return Skill.SkillClass.MAIN10;
            case sp1:
                return Skill.SkillClass.SP1;
            case sp2:
                return Skill.SkillClass.SP2;
            case sp3:
                return Skill.SkillClass.SP3;
            case sp4:
                return Skill.SkillClass.SP4;
            case sp5:
                return Skill.SkillClass.SP5;
            default:
                return Skill.SkillClass.UNKNOWN;
        }
    }

    public String description(){
        switch (this){
            case hit:
                return I18N.getStringWithSpace(R.string.hit);
            case main1:
                return I18N.getStringWithSpace(R.string.main_skill_1);
            case main2:
                return I18N.getStringWithSpace(R.string.main_skill_2);
            case main3:
                return I18N.getStringWithSpace(R.string.main_skill_3);
            case main4:
                return I18N.getStringWithSpace(R.string.main_skill_4);
            case main5:
                return I18N.getStringWithSpace(R.string.main_skill_5);
            case main6:
                return I18N.getStringWithSpace(R.string.main_skill_6);
            case main7:
                return I18N.getStringWithSpace(R.string.main_skill_7);
            case sp1:
                return I18N.getStringWithSpace(R.string.sp_skill_1);
            case sp2:
                return I18N.getStringWithSpace(R.string.sp_skill_2);
            case sp3:
                return I18N.getStringWithSpace(R.string.sp_skill_3);
            case sp4:
                return I18N.getStringWithSpace(R.string.sp_skill_4);
            case sp5:
                return I18N.getStringWithSpace(R.string.sp_skill_5);
            default:
                return "";
        }
    }
}