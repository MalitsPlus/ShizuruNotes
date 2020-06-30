package com.github.malitsplus.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.data.EquipmentPiece
import com.github.malitsplus.shizurunotes.data.Item
import com.github.malitsplus.shizurunotes.db.MasterEquipment
import kotlin.concurrent.thread

class SharedViewModelEquipment : ViewModel() {
    val equipmentFragmentMap = mutableMapOf<Int, EquipmentPiece>()
    val equipmentMap = MutableLiveData<MutableMap<Int, Equipment>>()
    val loadingFlag = MutableLiveData<Boolean>(false)
    val selectedDrops = MutableLiveData<MutableList<Item>>(mutableListOf())
    var selectedEquipment: Equipment? = null

    /***
     * 从数据库读取所有装备数据。
     */
    fun loadData() {
        if (equipmentMap.value.isNullOrEmpty()) {
            loadingFlag.value = true
            thread(start = true) {
                equipmentMap.postValue(MasterEquipment().getEquipmentMap())
                loadingFlag.postValue(false)
                callBack?.equipmentLoadFinished()
            }
        }
    }

    fun setDrop(item: Item) {
        selectedDrops.value?.clear()
        selectedDrops.value?.add(item)
    }

    var callBack: MasterEquipmentCallBack? = null
    interface MasterEquipmentCallBack {
        fun equipmentLoadFinished()
    }
}