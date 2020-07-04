package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.AttackPattern;

import java.util.ArrayList;
import java.util.List;

public class RawUnitAttackPattern {
    public int pattern_id;
    public int unit_id;
    public int loop_start;
    public int loop_end;
    public int atk_pattern_1;
    public int atk_pattern_2;
    public int atk_pattern_3;
    public int atk_pattern_4;
    public int atk_pattern_5;
    public int atk_pattern_6;
    public int atk_pattern_7;
    public int atk_pattern_8;
    public int atk_pattern_9;
    public int atk_pattern_10;
    public int atk_pattern_11;
    public int atk_pattern_12;
    public int atk_pattern_13;
    public int atk_pattern_14;
    public int atk_pattern_15;
    public int atk_pattern_16;
    public int atk_pattern_17;
    public int atk_pattern_18;
    public int atk_pattern_19;
    public int atk_pattern_20;

    public AttackPattern getAttackPattern(){
        List<Integer> attackPatternList = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            // mistake? deliberately? only cy knows
            if (i == 14) continue;
            int atkPattern =  (int)Utils.getValueFromObject(this, "atk_pattern_" + i);
            if(atkPattern != 0)
                attackPatternList.add(atkPattern);
            else
                break;
        }

        return new AttackPattern(
                pattern_id,
                unit_id,
                loop_start,
                loop_end,
                attackPatternList
        );
    }
}
