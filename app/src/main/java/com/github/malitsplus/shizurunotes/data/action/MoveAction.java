package com.github.malitsplus.shizurunotes.data.action;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.I18N;
import com.github.malitsplus.shizurunotes.common.Utils;
import com.github.malitsplus.shizurunotes.data.Property;

public class MoveAction extends ActionParameter {

    enum MoveType{
        unknown(0),
        targetReturn(1),
        absoluteReturn(2),
        target(3),
        absolute(4),
        targetByVelocity(5),
        absoluteByVelocity(6),
        absoluteWithoutDirection(7);

        private int value;
        MoveType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public static MoveType parse(int value){
            for(MoveType item : MoveType.values()){
                if(item.getValue() == value)
                    return item;
            }
            return unknown;
        }
    }

    private MoveType moveType;

    @Override
    protected void childInit(){
        moveType = MoveType.parse(actionDetail1);
    }

    @Override
    public String localizedDetail(int level, Property property){
        switch (moveType){
            case targetReturn:
                return I18N.getString(R.string.Change_self_position_to_s_then_return, targetParameter.buildTargetClause());
            case absoluteReturn:
                if(actionValue1 > 0)
                    return I18N.getString(R.string.Change_self_position_s_forward_then_return, Utils.roundDownDouble(actionValue1));
                else
                    return I18N.getString(R.string.Change_self_position_s_backward_then_return, Utils.roundDownDouble(-actionValue1));
            case target:
                return I18N.getString(R.string.Change_self_position_to_s, targetParameter.buildTargetClause());
            case absolute:
            case absoluteWithoutDirection:
                if(actionValue1 > 0)
                    return I18N.getString(R.string.Change_self_position_s_forward, Utils.roundDownDouble(actionValue1));
                else
                    return I18N.getString(R.string.Change_self_position_s_backward, Utils.roundDownDouble(-actionValue1));
            case targetByVelocity:
                if(actionValue1 > 0)
                    return I18N.getString(R.string.Move_to_s_in_front_of_s_with_velocity_s_sec, Utils.roundDownDouble(actionValue1), targetParameter.buildTargetClause(), actionValue2);
                else
                    return I18N.getString(R.string.Move_to_s_behind_of_s_with_velocity_s_sec, Utils.roundDownDouble(-actionValue1), targetParameter.buildTargetClause(), actionValue2);
            case absoluteByVelocity:
                if(actionValue1 > 0)
                    return I18N.getString(R.string.Move_forward_s_with_velocity_s_sec, Utils.roundDownDouble(actionValue1), actionValue2);
                else
                    return I18N.getString(R.string.Move_backward_s_with_velocity_s_sec, Utils.roundDownDouble(-actionValue1), actionValue2);
            default:
                return super.localizedDetail(level, property);
        }
    }

}
