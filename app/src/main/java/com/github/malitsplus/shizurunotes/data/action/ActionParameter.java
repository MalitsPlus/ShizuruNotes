package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.data.Skill;

public class ActionParameter {
    public static ActionParameter type(int rawType){
        switch (rawType){
            case 1:
                return new DamageAction();
            case 2:
                return new MoveAction();

            default:
                return new ActionParameter();
        }
    }

    public int dependActionId;

    public int actionId;
    public int classId;
    public int actionType;
    public int actionDetail1;
    public int actionDetail2;
    public int actionDetail3;

    public double actionValue1;
    public double actionValue2;
    public double actionValue3;
    public double actionValue4;
    public double actionValue5;
    public double actionValue6;
    public double actionValue7;

    public int targetAssignment;
    public int targetArea;
    public int targetRange;
    public int targetType;
    public int targetNumber;
    public int targetCount;

    public TargetParameter targetParameter;

    public ActionParameter init(int actionId, int dependActionId, int classId, int actionType, int actionDetail1, int actionDetail2, int actionDetail3, double actionValue1, double actionValue2, double actionValue3, double actionValue4, double actionValue5, double actionValue6, double actionValue7, int targetAssignment, int targetArea, int targetRange, int targetType, int targetNumber, int targetCount, Skill.Action dependAction){
        this.actionId = actionId;
        this.dependActionId = dependActionId;
        this.classId = classId;
        this.actionType = actionType;
        this.actionDetail1 = actionDetail1;
        this.actionDetail2 = actionDetail2;
        this.actionDetail3 = actionDetail3;
        this.actionValue1 = actionValue1;
        this.actionValue2 = actionValue2;
        this.actionValue3 = actionValue3;
        this.actionValue4 = actionValue4;
        this.actionValue5 = actionValue5;
        this.actionValue6 = actionValue6;
        this.actionValue7 = actionValue7;
        this.targetAssignment = targetAssignment;
        this.targetArea = targetArea;
        this.targetRange = targetRange;
        this.targetType = targetType;
        this.targetNumber = targetNumber;
        this.targetCount = targetCount;
        targetParameter = new TargetParameter();
        return this;
    }
}

enum ActionType {
    unknown(0),
    damage(1),
    move(2),
    knock(3),
    heal(4),
    cure(5),
    guard(6),
    chooseArea(7),
    ailment(8),
    dot(9),
    aura(10),
    charm(11),
    blind(12),
    silence(13),
    changeMode(14),
    summon(15),
    changeEnergy(16),
    trigger(17),
    charge(18),
    damageCharge(19),
    taunt(20),
    invulnerable(21),
    changePattern(22),
    ifForChildren(23),
    revival(24),
    continuousAttack(25),
    additive(26),
    multiple(27),
    ifForAll(28),
    changeSearchArea(29),
    instantDeath(30),
    continuousAttackNearby(31),
    enhanceLifeSteal(32),
    enhanceStrikeBack(33),
    accumulativeDamage(34),
    seal(35),
    attackField(36),
    healField(37),
    changeParameterField(38),
    dotField(39),
    ailmentField(40),
    changeUBTime(41),
    loopTrigger(42),
    ifHasTarget(43),
    waveStartIdle(44),
    skillCount(45),
    gravity(46),
    upperLimitAttack(47),
    hot(48),
    dispel(49),
    channel(50),
    division(51),
    changeWidth(52),
    ifExistsFieldForAll(53),
    stealth(54),
    moveParts(55),
    countBlind(56),
    countDown(57),

    attackSeal(60),
    fear(61),
    awe(62),
    loop(63),
    toad(69),
    ex(90),
    exPlus(91);

    private int value;
    private ActionType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}