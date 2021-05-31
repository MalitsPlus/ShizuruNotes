package com.github.malitsplus.shizurunotes.data;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;

public class Ailment {

    public class AilmentDetail{
        public Object detail;
        public void setDetail(Object obj){
            this.detail = obj;
        }

        public String description(){
            if(detail instanceof DotDetail){
                return ((DotDetail)detail).description();
            } else if(detail instanceof ActionDetail){
                return ((ActionDetail)detail).description();
            } else if(detail instanceof CharmDetail){
                return ((CharmDetail)detail).description();
            } else {
                return I18N.getString(R.string.Unknown);
            }
        }
    }

    public enum DotDetail {
        detain(0),
        poison(1),
        burn(2),
        curse(3),
        violentPoison(4),
        hex(5),
        compensation(6),
        unknown(-1);

        private int value;
        DotDetail(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static DotDetail parse(int value){
            for(DotDetail item : DotDetail.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description(){
            switch (this){
                case detain:
                    return I18N.getString(R.string.Detain_Damage);
                case poison:
                    return I18N.getString(R.string.Poison);
                case burn:
                    return I18N.getString(R.string.Burn);
                case curse:
                    return I18N.getString(R.string.Curse);
                case violentPoison:
                    return I18N.getString(R.string.Violent_Poison);
                case hex:
                    return I18N.getString(R.string.Hex);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }

    public enum CharmDetail{
        charm(0),
        confuse(1);

        private int value;
        CharmDetail(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static CharmDetail parse(int value){
            for(CharmDetail item : CharmDetail.values()){
                if(item.getValue() == value)
                    return item;
            }
            return null;
        }

        public String description(){
            switch (this){
                case charm:
                    return I18N.getString(R.string.Charm);
                case confuse:
                    return I18N.getString(R.string.Confuse);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }

    public enum ActionDetail{
        slow(1),
        haste(2),
        paralyse(3),
        freeze(4),
        bind(5),
        sleep(6),
        stun(7),
        petrify(8),
        detain(9),
        faint(10),
        timeStop(11),
        unknown(12);

        private int value;
        ActionDetail(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static ActionDetail parse(int value){
            for(ActionDetail item : ActionDetail.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description(){
            switch (this){
                case slow:
                    return I18N.getString(R.string.Slow);
                case haste:
                    return I18N.getString(R.string.Haste);
                case paralyse:
                    return I18N.getString(R.string.Paralyse);
                case freeze:
                    return I18N.getString(R.string.Freeze);
                case bind:
                    return I18N.getString(R.string.Bind);
                case sleep:
                    return I18N.getString(R.string.Sleep);
                case stun:
                    return I18N.getString(R.string.Stun);
                case petrify:
                    return I18N.getString(R.string.Petrify);
                case detain:
                    return I18N.getString(R.string.Detain);
                case faint:
                    return I18N.getString(R.string.Faint);
                case timeStop:
                    return I18N.getString(R.string.time_stop);
                default:
                    return I18N.getString(R.string.Unknown);
            }
        }
    }

    public enum AilmentType{
        knockBack(3),
        action(8),
        dot(9),
        charm(11),
        darken(12),
        silence(13),
        confuse(19),
        instantDeath(30),
        countBlind(56),
        inhibitHeal(59),
        attackSeal(60),
        fear(61),
        awe(62),
        toad(69),
        maxHP(70),
        hPRegenerationDown(76),
        damageTakenIncreased(78),
        damageByBehaviour(79),
        unknown(80);

        private int value;
        AilmentType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static AilmentType parse(int value){
            for(AilmentType item : AilmentType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }

        public String description(){
            switch (this){
                case knockBack:
                    return I18N.getString(R.string.Knock_Back);
                case action:
                    return I18N.getString(R.string.Action);
                case dot:
                    return I18N.getString(R.string.Dot);
                case charm:
                    return I18N.getString(R.string.Charm);
                case darken:
                    return I18N.getString(R.string.Blind);
                case silence:
                    return I18N.getString(R.string.Silence);
                case instantDeath:
                    return I18N.getString(R.string.Instant_Death);
                case confuse:
                    return I18N.getString(R.string.Confuse);
                case countBlind:
                    return I18N.getString(R.string.Count_Blind);
                case inhibitHeal:
                    return I18N.getString(R.string.Inhibit_Heal);
                case fear:
                    return I18N.getString(R.string.Fear);
                case attackSeal:
                    return I18N.getString(R.string.Seal);
                case awe:
                    return I18N.getString(R.string.Awe);
                case toad:
                    return I18N.getString(R.string.Polymorph);
                case maxHP:
                    return I18N.getString(R.string.Changing_Max_HP);
                case hPRegenerationDown:
                    return I18N.getString(R.string.HP_Regeneration_Down);
                case damageTakenIncreased:
                    return I18N.getString(R.string.Damage_Taken_Increased);
                case damageByBehaviour:
                    return I18N.getString(R.string.Damage_By_Behaviour);
                default:
                    return I18N.getString(R.string.Unknown_Effect);
            }
        }
    }

    public AilmentType ailmentType;
    public AilmentDetail ailmentDetail;

    public Ailment(int type, int detail){

        ailmentType = AilmentType.parse(type);
        ailmentDetail = new AilmentDetail();
        switch (ailmentType){
            case action:
                ailmentDetail.setDetail(ActionDetail.parse(detail));
                break;
            case dot:
            case damageByBehaviour:
                ailmentDetail.setDetail(DotDetail.parse(detail));
                break;
            case charm:
                ailmentDetail.setDetail(CharmDetail.parse(detail));
                break;
            default:
                ailmentDetail = null;
                break;
        }
    }

    public String description(){
        if(ailmentDetail != null)
            return ailmentDetail.description();
        else
            return ailmentType.description();
    }
}


