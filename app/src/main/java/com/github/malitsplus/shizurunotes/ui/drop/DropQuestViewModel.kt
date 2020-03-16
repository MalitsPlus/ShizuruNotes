package com.github.malitsplus.shizurunotes.ui.drop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelQuest
import java.util.*
import kotlin.concurrent.thread

class DropQuestViewModel(
    private val sharedQuest: SharedViewModelQuest,
    private val equipmentList: List<Equipment>?
) : ViewModel() {
    val searchedQuestList = MutableLiveData<MutableList<Any>>(mutableListOf())

    @Suppress("UNCHECKED_CAST")
    fun search() {
        if (equipmentList.isNullOrEmpty()) {
            searchedQuestList.value = sharedQuest.questList.value as MutableList<Any>?
        } else {
            thread(start = true) {
                val resultList = mutableListOf<Any>()
                val rawList = mutableListOf<Quest>()
                val andList = mutableListOf<Quest>()
                val orList = mutableListOf<Quest>()
                val middleList = mutableListOf<Quest>()

                sharedQuest.questList.value?.forEach { quest ->
                    equipmentList.forEach { equipment ->
                        if (quest.contains(equipment.equipmentId)) {
                            rawList.add(quest)
                        }
                    }
                }
                when(val num = equipmentList.size) {
                    1 -> searchedQuestList.postValue(rawList as MutableList<Any>)
                    else -> {
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
                            resultList.addAll(andList)
                        }
                        if (middleList.isNotEmpty()) {
                            resultList.add(I18N.getString(R.string.text_condition_andor))
                            resultList.addAll(middleList)
                        }
                        if (orList.isNotEmpty()) {
                            resultList.add(I18N.getString(R.string.text_condition_or))
                            resultList.addAll(orList)
                        }
                        searchedQuestList.postValue(resultList)
                    }
                }
            }
        }
    }
}