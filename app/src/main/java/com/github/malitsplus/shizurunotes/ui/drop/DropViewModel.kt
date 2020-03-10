package com.github.malitsplus.shizurunotes.ui.drop

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import java.util.*

class DropViewModel : ViewModel() {

    var itemList = mutableListOf<Any>()

    fun refreshList(equipList: List<Equipment>) {
        itemList.clear()
        var currentRarity = 0
        equipList.forEach {
            if (currentRarity != it.rarity) {
                currentRarity = it.rarity
                itemList.add(currentRarity)
            }
            itemList.add(it)
        }
    }
}