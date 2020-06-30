package com.github.malitsplus.shizurunotes.ui.drop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Item
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.db.RawQuest
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelQuest
import java.util.*
import kotlin.Comparator
import kotlin.concurrent.thread

class DropQuestViewModel(
    private val sharedQuest: SharedViewModelQuest,
    private val itemList: List<Item>?
) : ViewModel() {
    val searchedQuestList = MutableLiveData<MutableList<Any>>(mutableListOf())

    @Suppress("UNCHECKED_CAST")
    fun search() {
        if (itemList.isNullOrEmpty()) {
            questTypeFilter(sharedQuest.questList.value)
            searchedQuestList.value = questTypeFilter(sharedQuest.questList.value) as MutableList<Any>?
        } else {
            thread(start = true) {
                val resultList = mutableListOf<Any>()
                var rawList = mutableListOf<Quest>()
                val andList = mutableListOf<Quest>()
                val orList = mutableListOf<Quest>()
                val middleList = mutableListOf<Quest>()

                sharedQuest.questList.value?.forEach { quest ->
                    itemList.forEach { item ->
                        if (quest.contains(item.itemId)) {
                            rawList.add(quest)
                        }
                    }
                }
                when(val num = itemList.size) {
                    1 -> {
                        rawList.sortByDescending { it.getOdds(itemList) }
                        questTypeFilter(rawList)
                        searchedQuestList.postValue(questTypeFilter(rawList) as MutableList<Any>)
                    }
                    else -> {
                        rawList = questTypeFilter(rawList).toMutableList()
                        rawList.forEach {
                            if (!andList.contains(it) && !middleList.contains(it)) {
                                when(Collections.frequency(rawList, it)) {
                                    num -> andList.add(it)
                                    1 -> orList.add(it)
                                    else -> middleList.add(it)
                                }
                            }
                        }
                        if (andList.isNotEmpty()) {
                            resultList.add(I18N.getString(R.string.text_condition_and))
                            resultList.addAll(andList.sortedByDescending { it.getOdds(itemList) })
                        }
                        if (middleList.isNotEmpty()) {
                            resultList.add(I18N.getString(R.string.text_condition_andor))
                            resultList.addAll(middleList.sortedByDescending { it.getOdds(itemList) })
                        }
                        if (orList.isNotEmpty()) {
                            resultList.add(I18N.getString(R.string.text_condition_or))
                            resultList.addAll(orList.sortedByDescending { it.getOdds(itemList) })
                        }
                        searchedQuestList.postValue(resultList)
                    }
                }
            }
        }
    }

    private fun questTypeFilter(list: List<Quest>?): List<Quest> {
        if (list == null) {
            return listOf()
        }
        return when {
            sharedQuest.includeNormal && !sharedQuest.includeHard -> {
                list.filter { it.questType == Quest.QuestType.Normal }
            }
            !sharedQuest.includeNormal && sharedQuest.includeHard -> {
                list.filter { it.questType == Quest.QuestType.Hard }
            }
            else -> {
                list
            }
        }
    }
}