package com.github.malitsplus.shizurunotes.data.action;

public enum ActionType {
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
    knightGuard(71),
    logBarrier(73),
    ex(90),
    exPlus(91);

    private int value;
    private ActionType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static ActionType parse(int value){
        for(ActionType item : ActionType.values()){
            if(item.getValue() == value)
                return item;
        }
        return unknown;
    }
}