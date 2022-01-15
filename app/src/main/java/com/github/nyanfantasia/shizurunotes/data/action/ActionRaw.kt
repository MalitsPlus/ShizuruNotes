package com.github.nyanfantasia.shizurunotes.data.action

class ActionRaw     //public String description;
    (var actionId: Int, var dependAction: Int) {
    var classId = 0
    var actionType = 0
    var actionDetail1 = 0
    var actionDetail2 = 0
    var actionDetail3 = 0
    var actionValue1 = 0.0
    var actionValue2 = 0.0
    var actionValue3 = 0.0
    var actionValue4 = 0.0
    var actionValue5 = 0.0
    var actionValue6 = 0.0
    var actionValue7 = 0.0
    var targetAssignment = 0
    var targetArea = 0
    var targetRange = 0
    var targetType = 0
    var targetNumber = 0
    var targetCount = 0
    fun setActionData(
        classId: Int,
        actionType: Int,
        actionDetail1: Int,
        actionDetail2: Int,
        actionDetail3: Int,
        actionValue1: Double,
        actionValue2: Double,
        actionValue3: Double,
        actionValue4: Double,
        actionValue5: Double,
        actionValue6: Double,
        actionValue7: Double,
        targetAssignment: Int,
        targetArea: Int,
        targetRange: Int,
        targetType: Int,
        targetNumber: Int,
        targetCount: Int
    ) {
        this.classId = classId
        this.actionType = actionType
        this.actionDetail1 = actionDetail1
        this.actionDetail2 = actionDetail2
        this.actionDetail3 = actionDetail3
        this.actionValue1 = actionValue1
        this.actionValue2 = actionValue2
        this.actionValue3 = actionValue3
        this.actionValue4 = actionValue4
        this.actionValue5 = actionValue5
        this.actionValue6 = actionValue6
        this.actionValue7 = actionValue7
        this.targetAssignment = targetAssignment
        this.targetArea = targetArea
        this.targetRange = targetRange
        this.targetType = targetType
        this.targetNumber = targetNumber
        this.targetCount = targetCount
    }
}