package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.common.Statics
import kotlin.properties.Delegates

class ClanBattleBoss(
    val enemyId: Int
) {
    var unitId by Delegates.notNull<Int>()
    var level by Delegates.notNull<Int>()
    var prefabId by Delegates.notNull<Int>()
    var resistStatusId by Delegates.notNull<Int>()
    lateinit var name: String
    lateinit var property: Property
    var iconUrl: String? = null

    val skills = ArrayList<Skill>()
    val children = ArrayList<ClanBattleBoss>()

    fun setBasic(unitId: Int, name: String, level: Int, prefabId: Int, resistStatusId: Int, property: Property){
        this.unitId = unitId
        this.name = name
        this.level = level
        this.prefabId = prefabId
        this.resistStatusId = resistStatusId
        this.property = property

        iconUrl = Statics.ICON_URL.format(prefabId);
    }
}