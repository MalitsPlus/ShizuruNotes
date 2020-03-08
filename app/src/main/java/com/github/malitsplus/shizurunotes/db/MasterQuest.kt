package com.github.malitsplus.shizurunotes.db

import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.db.DBHelper.Companion.get
import java.util.*

class MasterQuest {
    val quest: List<Quest>
        get() {
            val questList: MutableList<Quest> = ArrayList()
            get().getQuests()?.forEach {
                questList.add(it.quest)
            }
            return questList
        }
}