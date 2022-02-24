@file:Suppress("EnumEntryName")

package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getStringWithSpace
import com.github.nyanfantasia.shizurunotes.common.Statics
import com.github.nyanfantasia.shizurunotes.data.Skill.SkillClass

class AttackPattern(
    var patternId: Int,
    var unitId: Int,
    var loopStart: Int,
    var loopEnd: Int,
    var rawAttackPatterns: List<Int>
) {
    var items: MutableList<AttackPatternItem> = ArrayList()

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

    fun setItems(skills: List<Skill>, atkType: Int): AttackPattern {
        items.clear()
        for (i in rawAttackPatterns.indices) {
            if (i >= loopEnd) break
            val raw = rawAttackPatterns[i]
            var iconUrl: String
            if (raw == 1) {
                iconUrl = if (atkType == 2) {
                    MAG_ICON
                } else {
                    PHY_ICON
                }
            } else {
                var skill: Skill? = null
                for (innerSkill in skills) {
                    if (innerSkill.skillClass === PatternType.parse(raw).skillClass()) {
                        skill = innerSkill
                        break
                    }
                }
                iconUrl = skill?.iconUrl ?: Statics.UNKNOWN_ICON
            }
            items.add(AttackPatternItem(raw, getLoopText(i), iconUrl))
        }
        return this
    }

    private fun getLoopText(index: Int): String {
        if (index + 1 == loopStart) return getString(R.string.loop_start)
        return if (index + 1 == loopEnd) getString(R.string.loop_end) else ""
    }

    //    public String getEnemyPatternText(String prefix){
    //        StringBuilder sb = new StringBuilder().append(prefix);
    //
    //        boolean isSinglePattern = true;
    //        for (int it: rawAttackPatterns){
    //            if (it != 1 && it != 0) {
    //                isSinglePattern = false;
    //                break;
    //            }
    //        }
    //        if (isSinglePattern)
    //            return sb.append(I18N.getString(R.string.text_normal_attack_only)).toString();
    //
    //
    //        for (AttackPatternItem it: items){
    //            if (it.loopText.equals("")){
    //                sb.append(it.skillText);
    //            } else if (it.loopText.equals(I18N.getString(R.string.loop_start))){
    //                sb.append(I18N.getString(R.string.text_loop_start)).append(it.skillText);
    //            } else if (it.loopText.equals(I18N.getString(R.string.loop_end))){
    //                sb.append(it.skillText).append(I18N.getString(R.string.text_loop_end)).append("-");
    //                break;
    //            }
    //            sb.append("-");
    //        }
    //        return sb.deleteCharAt(sb.lastIndexOf("-")).toString();
    //    }

    inner class AttackPatternItem(rawAttackPatterns: Int, var loopText: String, var iconUrl: String?) {
        var skillText: String = PatternType.parse(rawAttackPatterns).description()

    }

    companion object {
        private const val PHY_ICON = Statics.API_URL + "/icon/equipment/101011.webp"
        private const val MAG_ICON = Statics.API_URL + "/icon/equipment/101251.webp"
    }
}

internal enum class PatternType(val value: Int) {
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

    fun skillClass(): SkillClass {
        return when (this) {
            main1 -> SkillClass.MAIN1
            main2 -> SkillClass.MAIN2
            main3 -> SkillClass.MAIN3
            main4 -> SkillClass.MAIN4
            main5 -> SkillClass.MAIN5
            main6 -> SkillClass.MAIN6
            main7 -> SkillClass.MAIN7
            main8 -> SkillClass.MAIN8
            main9 -> SkillClass.MAIN9
            main10 -> SkillClass.MAIN10
            sp1 -> SkillClass.SP1
            sp2 -> SkillClass.SP2
            sp3 -> SkillClass.SP3
            sp4 -> SkillClass.SP4
            sp5 -> SkillClass.SP5
            else -> SkillClass.UNKNOWN
        }
    }

    fun description(): String {
        return when (this) {
            hit -> getStringWithSpace(R.string.hit)
            main1 -> getStringWithSpace(R.string.main_skill_1)
            main2 -> getStringWithSpace(R.string.main_skill_2)
            main3 -> getStringWithSpace(R.string.main_skill_3)
            main4 -> getStringWithSpace(R.string.main_skill_4)
            main5 -> getStringWithSpace(R.string.main_skill_5)
            main6 -> getStringWithSpace(R.string.main_skill_6)
            main7 -> getStringWithSpace(R.string.main_skill_7)
            main8 -> getStringWithSpace(R.string.main_skill_8)
            main9 -> getStringWithSpace(R.string.main_skill_9)
            main10 -> getStringWithSpace(R.string.main_skill_10)
            sp1 -> getStringWithSpace(R.string.sp_skill_1)
            sp2 -> getStringWithSpace(R.string.sp_skill_2)
            sp3 -> getStringWithSpace(R.string.sp_skill_3)
            sp4 -> getStringWithSpace(R.string.sp_skill_4)
            sp5 -> getStringWithSpace(R.string.sp_skill_5)
            else -> ""
        }
    }

    companion object {
        fun parse(value: Int): PatternType {
            for (item in values()) {
                if (item.value == value) return item
            }
            return none
        }
    }
}