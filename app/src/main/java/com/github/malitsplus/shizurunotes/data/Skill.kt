package com.github.malitsplus.shizurunotes.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.ui.base.BackgroundSpan
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.Statics
import com.github.malitsplus.shizurunotes.data.action.ActionParameter
import com.github.malitsplus.shizurunotes.data.action.ActionRaw
import com.github.malitsplus.shizurunotes.data.action.SummonAction
import com.github.malitsplus.shizurunotes.db.DBHelper
import java.util.*

/***
 *
 */
class Skill(
    val skillId: Int,
    val skillClass: SkillClass,
    val enemySkillLevel: Int = 0
) {
    //java类用构造器
    constructor(skillId: Int, skillClass: SkillClass) : this(skillId, skillClass, 0)

    enum class SkillClass(val value: String) {
        UB("UB"),
        UB_EVO("UB+"),
        MAIN1("M1"),
        MAIN1_EVO("M1+"),
        MAIN2("M2"),
        MAIN2_EVO("M2+"),
        MAIN3("M3"),
        MAIN4("M4"),
        MAIN5("M5"),
        MAIN6("M6"),
        MAIN7("M7"),
        MAIN8("M8"),
        MAIN9("M9"),
        MAIN10("M10"),
        SP1("S1"),
        SP2("S2"),
        SP3("S3"),
        SP4("S4"),
        SP5("S5"),
        EX1("E1"),
        EX1_EVO("E1+"),
        EX2("E2"),
        EX2_EVO("E2+"),
        EX3("E3"),
        EX3_EVO("E3+"),
        EX4("E4"),
        EX4_EVO("E4+"),
        EX5("E5"),
        EX5_EVO("E5+"),
        UNKNOWN("");

        fun description(): String {
            return when (this) {
                UB ->
                    I18N.getStringWithSpace(R.string.union_burst)
                UB_EVO ->
                    I18N.getStringWithSpace(R.string.union_burst_evo)
                MAIN1 ->
                    I18N.getStringWithSpace(R.string.main_skill_1)
                MAIN2 ->
                    I18N.getStringWithSpace(R.string.main_skill_2)
                MAIN3 ->
                    I18N.getStringWithSpace(R.string.main_skill_3)
                MAIN4 ->
                    I18N.getStringWithSpace(R.string.main_skill_4)
                MAIN5 ->
                    I18N.getStringWithSpace(R.string.main_skill_5)
                MAIN6 ->
                    I18N.getStringWithSpace(R.string.main_skill_6)
                MAIN7 ->
                    I18N.getStringWithSpace(R.string.main_skill_7)
                MAIN8 ->
                    I18N.getStringWithSpace(R.string.main_skill_8)
                MAIN9 ->
                    I18N.getStringWithSpace(R.string.main_skill_9)
                MAIN10 ->
                    I18N.getStringWithSpace(R.string.main_skill_10)
                MAIN1_EVO ->
                    I18N.getStringWithSpace(R.string.main_skill_1_evo)
                MAIN2_EVO ->
                    I18N.getStringWithSpace(R.string.main_skill_2_evo)
                SP1 ->
                    I18N.getStringWithSpace(R.string.sp_skill_1)
                SP2 ->
                    I18N.getStringWithSpace(R.string.sp_skill_2)
                SP3 ->
                    I18N.getStringWithSpace(R.string.sp_skill_3)
                SP4 ->
                    I18N.getStringWithSpace(R.string.sp_skill_4)
                SP5 ->
                    I18N.getStringWithSpace(R.string.sp_skill_5)
                EX1 ->
                    I18N.getStringWithSpace(R.string.ex_skill_1)
                EX2 ->
                    I18N.getStringWithSpace(R.string.ex_skill_2)
                EX3 ->
                    I18N.getStringWithSpace(R.string.ex_skill_3)
                EX4 ->
                    I18N.getStringWithSpace(R.string.ex_skill_4)
                EX5 ->
                    I18N.getStringWithSpace(R.string.ex_skill_5)
                EX1_EVO ->
                    I18N.getStringWithSpace(R.string.ex_skill_1_evo)
                EX2_EVO ->
                    I18N.getStringWithSpace(R.string.ex_skill_2_evo)
                EX3_EVO ->
                    I18N.getStringWithSpace(R.string.ex_skill_3_evo)
                EX4_EVO ->
                    I18N.getStringWithSpace(R.string.ex_skill_4_evo)
                EX5_EVO ->
                    I18N.getStringWithSpace(R.string.ex_skill_5_evo)
                else ->
                    I18N.getStringWithSpace(R.string.unknown)
            }
        }

        companion object {
            @JvmStatic
            fun parse(value: String): SkillClass {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return UNKNOWN
            }
        }
    }

    val actions: List<Action> = mutableListOf()
    var actionRawList: List<ActionRaw> = ArrayList()
    var skillType = 0
    var skillAreaWidth = 0
    var skillCastTime = 0.0
    var iconType = 0
    val castTimeText: String
        get() = I18N.getString(R.string.text_cast_time) + skillCastTime + "s"

    lateinit var skillName: String
    lateinit var description: String
    lateinit var iconUrl: String
    lateinit var actionDescriptions: SpannableStringBuilder

    val friendlyMinionList = mutableListOf<Minion>()
    val enemyMinionList = mutableListOf<Enemy>()

    /***
    * ！！！此类必须在协程中进行实例化！！！
    */
    init {
        DBHelper.get().getSkillData(this.skillId)?.setSkillData(this)
        actions.forEach { action ->
            //向actionList中填入具体值
            DBHelper.get().getSkillAction(action.actionId)?.setActionData(action)
        }
        actions.forEach { action ->
            //先检查dependAction
            if (action.dependActionId != 0) {
                for (searched in actions) {
                    if (searched.actionId == action.dependActionId) {
                        //需要先为其建立params
                        searched.buildParameter()
                        action.dependAction = searched
                        break
                    }
                }
            }
            action.buildParameter()

            //如果是召唤技能还需要再读库
            if (action.parameter is SummonAction){
                //我方召唤物
                if (enemySkillLevel == 0) {
                    action.parameter.actionDetail2.let { unitId ->
                        val minion = DBHelper.get().getUnitMinion(unitId)?.unitMinion
                        DBHelper.get().getUnitRarityList(unitId)?.forEach {
                            minion?.apply {
                                propertyMap[it.rarity] = it.property
                                propertyGrowthMap[it.rarity] = it.propertyGrowth
                            }
                        }
                        DBHelper.get().getUnitAttackPattern(unitId)?.forEach {
                            minion?.attackPattern?.add(it.attackPattern)
                        }
                        if (minion != null)
                            friendlyMinionList.add(minion)
                        unitId
                    }
                }
                //敌方召唤物
                else {
                    action.parameter.actionDetail2.let { enemyId ->
                        var isDuplicate = false
                        for (it in enemyMinionList){
                            if (it.enemyId == enemyId){
                                isDuplicate = true
                                break
                            }
                        }
                        if (!isDuplicate) {
                            val enemyMinion = DBHelper.get().getEnemyMinion(enemyId)
                                ?.enemy?.let { minion ->
                                DBHelper.get().getUnitAttackPattern(minion.unitId)?.forEach {
                                    minion.attackPatternList.add(it.attackPattern)
                                }
                                minion
                            }
                            if (enemyMinion != null)
                                enemyMinionList.add(enemyMinion)
                        }
                        enemyId
                    }
                }
            }
        }
    }

    fun setSkillData(
        skillName: String,
        skillType: Int,
        skillAreaWidth: Int,
        skillCastTime: Double,
        description: String,
        iconType: Int
    ) {
        this.skillName = skillName
        this.skillType = skillType
        this.skillAreaWidth = skillAreaWidth
        this.skillCastTime = skillCastTime
        this.description = description
        this.iconType = iconType
        iconUrl = Statics.SKILL_ICON_URL.format(iconType)
    }

    fun setActionDescriptions(
        level: Int,
        property: Property
    ) {
        val builder = SpannableStringBuilder()
        for (i in actions.indices) {
            builder.append("  ")
                .append((i + 1).toString())
                .append("  ")
            builder.setSpan(
                BackgroundSpan(
                    BackgroundSpan.BORDER_RECT
                ),
                builder.length - 4,
                builder.length - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.append(actions[i].parameter.localizedDetail(level, property))
                .append("\n")
        }
        if (builder.isNotEmpty())
            builder.delete(builder.length - 1, builder.length)
        actionDescriptions = builder
    }

    inner class Action(
        val actionId: Int,
        val dependActionId: Int
    ) {
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

        var dependAction: Action? = null
        lateinit var parameter: ActionParameter
        fun buildParameter() {
            val isEnemySkill = enemySkillLevel != 0

            parameter =
                ActionParameter.type(actionType).init(
                    isEnemySkill,
                    actionId,
                    dependActionId,
                    classId,
                    actionType,
                    actionDetail1,
                    actionDetail2,
                    actionDetail3,
                    actionValue1,
                    actionValue2,
                    actionValue3,
                    actionValue4,
                    actionValue5,
                    actionValue6,
                    actionValue7,
                    targetAssignment,
                    targetArea,
                    targetRange,
                    targetType,
                    targetNumber,
                    targetCount,
                    dependAction
                )
        }
    }
}