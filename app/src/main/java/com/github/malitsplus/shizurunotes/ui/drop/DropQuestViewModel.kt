package com.github.malitsplus.shizurunotes.ui.drop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelQuest
import kotlin.concurrent.thread

class DropQuestViewModel(
    private val sharedQuest: SharedViewModelQuest,
    private val equipmentList: List<Equipment>?
) : ViewModel() {
    val searchedQuestList = MutableLiveData<MutableList<Quest>>(mutableListOf())

    fun search() {
        if (equipmentList.isNullOrEmpty()) {
            searchedQuestList.value = sharedQuest.questList.value
        } else {
            thread(start = true) {
                val andList = mutableListOf<Quest>()
                val orList = mutableListOf<Quest>()
                equipmentList.forEach { equipment ->
                    sharedQuest.questList.value?.forEach { quest ->
                        if (quest.contains(equipment.equipmentId)) {

                        }
                    }
                }
            }
        }
    }

    fun addItem2List(item: Quest) {

    }
}