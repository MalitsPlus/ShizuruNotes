package com.github.malitsplus.shizurunotes.data.action;

public class ActionRaw {

    public int dependAction;

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

    //public String description;

    public ActionRaw(int actionId, int dependAction){
        this.actionId = actionId;
        this.dependAction = dependAction;
    }

    public void setActionData(int classId, int actionType, int actionDetail1, int actionDetail2, int actionDetail3, double actionValue1, double actionValue2, double actionValue3, double actionValue4, double actionValue5, double actionValue6, double actionValue7, int targetAssignment, int targetArea, int targetRange, int targetType, int targetNumber, int targetCount){
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
    }

}
