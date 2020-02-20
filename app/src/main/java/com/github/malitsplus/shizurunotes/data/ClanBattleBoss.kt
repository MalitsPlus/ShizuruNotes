package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.Statics
import com.github.malitsplus.shizurunotes.db.DBHelper
import kotlin.properties.Delegates

class ClanBattleBoss(
    val enemyId: Int
) {
    var unitId by Delegates.notNull<Int>()
    var level by Delegates.notNull<Int>()
    var prefabId by Delegates.notNull<Int>()
    var resistStatusId: Int? = null
    lateinit var name: String
    lateinit var property: Property
    lateinit var  iconUrl: String
    val resistName = mutableListOf<String>()
    val resistRate = mutableListOf<Int>()


    val attackPatternList = mutableListOf<AttackPattern>()

    val skills = mutableListOf<Skill>()
    val children = mutableListOf<ClanBattleBoss>()

    fun setBasic(unitId: Int, name: String, level: Int, prefabId: Int, resistStatusId: Int, property: Property){
        this.unitId = unitId
        this.name = name
        this.level = level
        this.prefabId = prefabId
        this.resistStatusId = resistStatusId
        this.property = property

        DBHelper.get().getUnitAttackPattern(unitId)?.forEach {
            attackPatternList.add(it.attackPattern.setItems())
        }

        iconUrl = Statics.ICON_URL.format(prefabId);

        if (resistStatusId != 0){
            val resistMap = DBHelper.get().getResistData(resistStatusId)?.resistData
            resistMap?.forEach {
                resistName.add(it.key)
                resistRate.add(it.value)
            }
        }
    }

    fun getLevelString(): String{
        return I18N.getString(R.string.text_level) + level
    }
}