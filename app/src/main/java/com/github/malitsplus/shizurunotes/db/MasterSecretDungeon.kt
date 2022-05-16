package com.github.malitsplus.shizurunotes.db

import com.github.malitsplus.shizurunotes.data.SecretDungeonQuest
import com.github.malitsplus.shizurunotes.data.SecretDungeonSchedule

object MasterSecretDungeon {

    fun getSecretDungeonSchedules(): List<SecretDungeonSchedule> {
        val instances = DBHelper.get().getSecretDungeonSchedules().let { raws ->
            mutableListOf<SecretDungeonSchedule>().apply {
                raws?.forEach { raw ->
                    this.add(SecretDungeonSchedule(raw.dungeon_area_id, raw.start_time, raw.end_time))
                }
            }
        }
        instances.forEach { instance ->
            DBHelper.get().getSecretDungeonQuests(instance.dungeonAreaId)?.forEach { rawQuest ->
                DBHelper.get().getWaveGroupData(rawQuest.wave_group_id)?.let {
                    val battleName = "${rawQuest.difficulty} - ${rawQuest.floor_num}"
                    instance.waveGroupMap[battleName] = it.getWaveGroup(true)
                }
            }
        }
        return instances
    }
}