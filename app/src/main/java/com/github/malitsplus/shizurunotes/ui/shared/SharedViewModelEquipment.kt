package com.github.malitsplus.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.db.MasterEquipment
import kotlin.concurrent.thread

class SharedViewModelEquipment : ViewModel() {
    val equipmentMap = MutableLiveData<MutableMap<Int, Equipment>>()
    val loadingFlag = MutableLiveData<Boolean>(false)

    /***
     * 从数据库读取所有装备数据。
     * 此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    fun loadData() {
        if (equipmentMap.value.isNullOrEmpty()) {
            thread(start = true) {
                loadingFlag.postValue(true)
                equipmentMap.postValue(MasterEquipment().getEquipmentMap())
                loadingFlag.postValue(false)
            }
        }
    }

}