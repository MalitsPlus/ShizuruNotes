package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.Statics
import com.github.malitsplus.shizurunotes.db.DBHelper

class Enemy (
    val enemyId: Int
) {
    var unitId = 0
    var level = 0
    var prefabId = 0
    var atkType = 0
    var searchAreaWidth = 0
    var normalAtkCastTime = 0.0
    var resistStatusId: Int? = null
    var isMultiTarget: Boolean = false
    lateinit var name: String
    lateinit var comment: String
    lateinit var property: Property
    lateinit var  iconUrl: String
    var resistMap: Map<String, Int>? = null
    val attackPatternList = mutableListOf<AttackPattern>()
    val skills = mutableListOf<Skill>()
    val children = mutableListOf<Enemy>()

    fun setBasic(unitId: Int, name: String, comment: String, level: Int, prefabId: Int, atkType: Int, searchAreaWidth: Int, normalAtkCastTime: Double, resistStatusId: Int, property: Property){
        this.unitId = unitId
        this.name = name
        this.comment = comment
        this.level = level
        this.prefabId = prefabId
        this.atkType = atkType
        this.searchAreaWidth = searchAreaWidth
        this.normalAtkCastTime = normalAtkCastTime
        this.resistStatusId = resistStatusId
        this.property = property

        DBHelper.get().getUnitAttackPattern(unitId)?.forEach {
            attackPatternList.add(it.attackPattern.setItems(skills, atkType))
        }

        iconUrl = Statics.ICON_URL.format(prefabId)

        if (resistStatusId != 0){
            resistMap = DBHelper.get().getResistData(resistStatusId)?.resistData
        }
    }

    fun getLevelString(): String{
        return I18N.getString(R.string.text_level) + level
    }
}