package com.github.malitsplus.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Quest
import com.github.malitsplus.shizurunotes.db.MasterQuest
import kotlin.concurrent.thread

class SharedViewModelQuest : ViewModel() {
    val questList = MutableLiveData<MutableList<Quest>>()
    val loadingFlag = MutableLiveData<Boolean>(false)

    /***
     * 从数据库读取所有任务数据。
     */
    fun loadData() {
        if (questList.value.isNullOrEmpty()) {
            loadingFlag.value = true
            thread(start = true) {
                questList.postValue(MasterQuest().quest)
                loadingFlag.postValue(false)
            }
        }
    }
}