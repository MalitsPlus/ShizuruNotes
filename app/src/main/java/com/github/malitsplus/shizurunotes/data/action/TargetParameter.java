package com.github.malitsplus.shizurunotes.data.action;

import android.widget.Switch;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.data.Skill;

public class TargetParameter {

    private TargetAssignment targetAssignment;
    private TargetNumber targetNumber;
    private int rawTargetType;
    private TargetType targetType;
    private TargetRange targetRange;
    private DirectionType direction;
    private TargetCount targetCount;

    private Skill.Action dependAction;

    public TargetParameter(int targetAssignment, int targetNumber, int targetType, int targetRange, int targetArea, int targetCount, Skill.Action dependAction){
        this.targetAssignment = TargetAssignment.parse(targetAssignment);
        this.targetNumber = TargetNumber.parse(targetNumber);
        this.rawTargetType = targetType;
        this.targetType = TargetType.parse(targetType);
        this.targetRange = TargetRange.parse(targetRange);
        this.direction = DirectionType.parse(targetArea);
        this.targetCount = TargetCount.parse(targetCount);
        this.dependAction = dependAction;
        String aaa = this.targetType.description(this.targetCount, null);
    }



}

enum TargetAssignment {
    none(0),
    enemy(1),
    friendly(2),
    all(3);

    private int value;
    private TargetAssignment(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static TargetAssignment parse(int value){
        for(TargetAssignment item : TargetAssignment.values()){
            if(item.getValue() == value)
                return item;
        }
        return none;
    }

    public String description(){
        switch (this){
            case enemy:
                return I18N.getString(R.string.enemy);
            case friendly:
                return I18N.getString(R.string.friendly);
            case all:
                return I18N.getString(R.string.both_sides);
            default:
                return "";
        }
    }
}

enum TargetNumber{
    first(0),
    second(1),
    third(2),
    fourth(3),
    fifth(4),
    other(5);

    private int value;
    private TargetNumber(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static TargetNumber parse(int value){
        for(TargetNumber item : TargetNumber.values()){
            if(item.getValue() == value)
                return item;
        }
        return other;
    }

    public String description(){
        switch (this){
            case first:
                return I18N.getString(R.string.first);
            case second:
                return I18N.getString(R.string.second);
            case third:
                return I18N.getString(R.string.third);
            case fourth:
                return I18N.getString(R.string.fourth);
            case fifth:
                return I18N.getString(R.string.fifth);
            default:
                return "";
        }
    }
}

enum TargetType{
    unknown(-1),
    zero(0),
    none(1),
    random(2),
    near(3),
    far(4),
    hpAscending(5),
    hpDescending(6),
    self(7),
    randomOnce(8),
    forward(9),
    backward(10),
    absolute(11),
    tpDescending(12),
    tpAscending(13),
    atkDescending(14),
    atkAscending(15),
    magicSTRDescending(16),
    magicSTRAscending(17),
    summon(18),
    tpReducing(19),
    physics(20),
    magic(21),
    allSummonRandom(22),
    selfSummonRandom(23),
    boss(24),
    hpAscendingOrNear(25),
    hpDescendingOrNear(26),
    tpDescendingOrNear(27),
    tpAscendingOrNear(28),
    atkDescendingOrNear(29),
    atkAscendingOrNear(30),
    magicSTRDescendingOrNear(31),
    magicSTRAscendingOrNear(32);

    private int value;
    private TargetType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static TargetType parse(int value){
        for(TargetType item : TargetType.values()){
            if(item.getValue() == value)
                return item;
        }
        return unknown;
    }

    public boolean isExclusiveWithAll(){
        switch (this){
            case unknown:
            case magic:
            case physics:
            case summon:
            case boss:
                return false;
            default:
                return true;
        }
    }
    public boolean ignoresOne(){
        switch (this){
            case unknown:
            case random:
            case randomOnce:
            case absolute:
            case summon:
            case selfSummonRandom:
            case allSummonRandom:
            case magic:
            case physics:
                return false;
            default:
                return true;
        }
    }

    public String description(){
        switch (this){
            case unknown:
                return I18N.getString(R.string.unknown);
            case random:
            case randomOnce:
                return I18N.getString(R.string.random);
            case zero:
            case near:
            case none:
                return I18N.getString(R.string.the_nearest);
            case far:
                return I18N.getString(R.string.the_farthest);
            case hpAscending:
            case hpAscendingOrNear:
                return I18N.getString(R.string.the_lowest_HP_ratio);
            case hpDescending:
            case hpDescendingOrNear:
                return I18N.getString(R.string.the_highest_HP_ratio);
            case self:
                return I18N.getString(R.string.self);
            case forward:
                return I18N.getString(R.string.the_most_backward);
            case backward:
                return I18N.getString(R.string.the_most_forward);
            case absolute:
                return I18N.getString(R.string.targets_within_the_scope);
            case tpDescending:
            case tpDescendingOrNear:
                return I18N.getString(R.string.the_highest_TP);
            case tpAscending:
            case tpReducing:
            case tpAscendingOrNear:
                return I18N.getString(R.string.the_lowest_TP);
            case atkDescending:
            case atkDescendingOrNear:
                return I18N.getString(R.string.the_highest_ATK);
            case atkAscending:
            case atkAscendingOrNear:
                return I18N.getString(R.string.the_lowest_ATK);
            case magicSTRDescending:
            case magicSTRDescendingOrNear:
                return I18N.getString(R.string.the_highest_Magic_STR);
            case magicSTRAscending:
            case magicSTRAscendingOrNear:
                return I18N.getString(R.string.the_lowest_Magic_STR);
            case summon:
                return I18N.getString(R.string.minion);
            case physics:
                return I18N.getString(R.string.physics);
            case magic:
                return I18N.getString(R.string.magic);
            case allSummonRandom:
                return I18N.getString(R.string.random_minion);
            case selfSummonRandom:
                return I18N.getString(R.string.random_self_minion);
            case boss:
                return I18N.getString(R.string.boss);
            default:
                return "";
        }
    }

    public String description(TargetCount targetCount, String localizedCount){
        String localizedModifier = localizedCount == null ? targetCount.description() : localizedCount;
        switch (this){
            case unknown:
                return I18N.getString(R.string.s_unknown_type, localizedModifier);
            case zero:
            case near:
            case none:
                return I18N.getString(R.string.s_nearest, localizedModifier);
            case far:
                return I18N.getString(R.string.s_farthest, localizedModifier);
            case hpAscending:
                return I18N.getString(R.string.s_lowest_HP_ratio, localizedModifier);
            case hpDescending:
                return I18N.getString(R.string.s_highest_HP_ratio, localizedModifier);
            case forward:
                return I18N.getString(R.string.s_most_backward, localizedModifier);
            case backward:
                return I18N.getString(R.string.s_most_forward, localizedModifier);
            case tpDescending:
            case tpDescendingOrNear:
                return I18N.getString(R.string.s_highest_TP, localizedModifier);
            case tpAscending:
            case tpReducing:
            case tpAscendingOrNear:
                return I18N.getString(R.string.s_lowest_TP, localizedModifier);
            case atkDescending:
            case atkDescendingOrNear:
                return I18N.getString(R.string.s_highest_ATK, localizedModifier);
            case atkAscending:
            case atkAscendingOrNear:
                return I18N.getString(R.string.s_lowest_ATK, localizedModifier);
            case magicSTRDescending:
            case magicSTRDescendingOrNear:
                return I18N.getString(R.string.s_highest_Magic_STR, localizedModifier);
            case magicSTRAscending:
            case magicSTRAscendingOrNear:
                return I18N.getString(R.string.s_lowest_Magic_STR, localizedModifier);
            case random:
            case randomOnce:
                return I18N.getString(R.string.s_random, localizedModifier);
            case summon:
                return I18N.getString(R.string.s_minion, localizedModifier);
            case physics:
                return I18N.getString(R.string.s_physics, localizedModifier);
            case magic:
                return I18N.getString(R.string.s_magic, localizedModifier);
            case boss:
                return I18N.getString(R.string.s_boss, localizedModifier);
            default:
                return description();
        }
    }

    public String description(TargetNumber targetNumber, String localizedNumer){

    }

}

enum TargetCount{
    zero(0),
    one(1),
    two(2),
    three(3),
    four(4),
    all(99);

    private int value;
    private TargetCount(int value){
        this.value = value;
        if(value == 1)
            pluralModifier = PluralModifier.one;
        else
            pluralModifier = PluralModifier.many;
    }
    public int getValue(){
        return value;
    }
    public static TargetCount parse(int value){
        for(TargetCount item : TargetCount.values()){
            if(item.getValue() == value)
                return item;
        }
        return all;
    }

    public String description(){
        switch (this){
            case zero:
                return "";
            case one:
                return I18N.getString(R.string.one);
            case two:
                return I18N.getString(R.string.two);
            case three:
                return I18N.getString(R.string.three);
            case four:
                return I18N.getString(R.string.four);
            default:
                return I18N.getString(R.string.all);
        }
    }

    enum PluralModifier {
        one,
        many;

        public String description(){
            switch (this){
                case one:
                    return I18N.getString(R.string.target);
                default:
                    return I18N.getString(R.string.targets);
            }
        }
    }
    PluralModifier pluralModifier;

}

enum TargetRange{
    zero,
    all,
    finite,
    infinite,
    unknown;

    public int rawRange;
    public static TargetRange parse(int range){
        TargetRange t;
        if(range == -1)
            t = infinite;
        else if(range == 0)
            t = zero;
        else if(range > 0 && range < 2160)
            t = finite;
        else if(range >= 2160) {
            t = all;
            t.rawRange = 2160;
            return t;
        } else
            t = unknown;

        t.rawRange = range;
        return t;
    }
}

enum DirectionType{
    front(1),
    frontAndBack(2),
    all(3);

    private int value;
    private DirectionType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static DirectionType parse(int value){
        for(DirectionType item : DirectionType.values()){
            if(item.getValue() == value)
                return item;
        }
        return all;
    }

    public String description(){
        switch (this){
            case front:
                return I18N.getString(R.string.front_including_self);
            case frontAndBack:
                return I18N.getString(R.string.front_and_back);
            default:
                return "";
        }
    }

    public String rawDescription(){
        switch (this){
            case front:
                return I18N.getString(R.string.front);
            case frontAndBack:
                return I18N.getString(R.string.front_and_back);
            default:
                return "";
        }
    }
}