package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Quest
import java.util.*

class MasterQuest {
    val quest: MutableList<Quest>
        get() {
            val questList: MutableList<Quest> = ArrayList()
            DBHelper.get().getQuests()?.forEach {
                questList.add(it.quest)
            }
            return questList
        }
}