package com.github.malitsplus.shizurunotes.ui.charalist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import java.util.*

class CharaListViewModel(
    private val sharedViewModelChara: SharedViewModelChara
) : ViewModel() {

    val liveCharaList = MutableLiveData<List<Chara>>()

    var selectedAttackType: String = "0"
    var selectedPosition: String = "0"
    var selectedSort: String = "0"
    var isAsc: Boolean = false

    val attackTypeMap = mapOf(
        0 to I18N.getString(R.string.ui_chip_any),
        1 to I18N.getString(R.string.ui_chip_atk_type_physical),
        2 to I18N.getString(R.string.ui_chip_atk_type_magical)
    )

    val positionMap = mapOf(
        0 to I18N.getString(R.string.ui_chip_any),
        1 to I18N.getString(R.string.ui_chip_position_forward),
        2 to I18N.getString(R.string.ui_chip_position_middle),
        3 to I18N.getString(R.string.ui_chip_position_rear)
    )

    val sortMap = mapOf(
        0 to I18N.getString(R.string.ui_chip_sort_new),
        1 to I18N.getString(R.string.ui_chip_sort_position),
        2 to I18N.getString(R.string.ui_chip_sort_physical_atk),
        3 to I18N.getString(R.string.ui_chip_sort_magical_atk),
        4 to I18N.getString(R.string.ui_chip_sort_physical_def),
        5 to I18N.getString(R.string.ui_chip_sort_magical_def),
        6 to I18N.getString(R.string.ui_chip_sort_age),
        7 to I18N.getString(R.string.ui_chip_sort_height),
        8 to I18N.getString(R.string.ui_chip_sort_weight),
        9 to I18N.getString(R.string.ui_chip_sort_burst_size)
    )


    fun filter(
        attackType: String?,
        position: String?,
        sortValue: String?,
        asc: Boolean?
    ) {
        selectedAttackType = attackType?: selectedAttackType
        selectedPosition = position?: selectedPosition
        sortValue?.apply {
            isAsc = if (this == selectedSort) !isAsc else false
            selectedSort = this
        }

        asc?.let { isAsc = it }

        val charaToShow: MutableList<Chara> = ArrayList()
        sharedViewModelChara.charaList.value?.forEach { chara ->
            if (checkAttackType(chara, selectedAttackType) && checkPosition(chara, selectedPosition)) {
                setSortValue(chara, selectedSort)
                charaToShow.add(chara)
            }
        }

        charaToShow.sortWith(kotlin.Comparator{ a: Chara, b: Chara ->
            val valueA : Int
            val valueB : Int
            when (selectedSort) {
                "0" -> {
                    return@Comparator if (b.startTime.isEqual(a.startTime)) 0 else if (b.startTime.isAfter(a.startTime) == isAsc) -1 else 1
                }
                "1" -> {
                    valueA = a.searchAreaWidth
                    valueB = b.searchAreaWidth
                }
                "2" -> {
                    valueA = a.charaProperty.getAtk()
                    valueB = b.charaProperty.getAtk()
                }
                "3" -> {
                    valueA = a.charaProperty.getMagicStr()
                    valueB = b.charaProperty.getMagicStr()
                }
                "4" -> {
                    valueA = a.charaProperty.getDef()
                    valueB = b.charaProperty.getDef()
                }
                "5" -> {
                    valueA = a.charaProperty.getMagicDef()
                    valueB = b.charaProperty.getMagicDef()
                }
                "6" -> {
                    valueA = if (a.age.contains("?")) 9999 else a.age.toInt()
                    valueB = if (b.age.contains("?")) 9999 else b.age.toInt()
                }
                "7" -> {
                    valueA = if (a.height.contains("?")) 9999 else a.height.toInt()
                    valueB = if (b.height.contains("?")) 9999 else b.height.toInt()
                }
                "8" -> {
                    valueA = if (a.weight.contains("?")) 9999 else a.weight.toInt()
                    valueB = if (b.weight.contains("?")) 9999 else b.weight.toInt()
                }
                else -> {
                    valueA = a.unitId
                    valueB = b.unitId
                }
            }
            (if (isAsc) -1 else 1) * valueB.compareTo(valueA)
        })
        liveCharaList.postValue(charaToShow)
    }

    fun filterDefault() {
        filter(selectedAttackType, selectedPosition, selectedSort, isAsc)
    }

    private fun checkPosition(chara: Chara, position: String): Boolean {
        return position == "0" || position == chara.position
    }

    private fun checkAttackType(chara: Chara, type: String): Boolean {
        return type == "0" || type.toInt() == chara.atkType
    }

    private fun setSortValue(chara: Chara, sortValue: String) {
        when (sortValue) {
            "1" -> chara.sortValue = chara.searchAreaWidth.toString()
            "2" -> chara.sortValue = chara.charaProperty.getAtk().toString()
            "3" -> chara.sortValue = chara.charaProperty.getMagicStr().toString()
            "4" -> chara.sortValue = chara.charaProperty.getDef().toString()
            "5" -> chara.sortValue = chara.charaProperty.getMagicDef().toString()
            "6" -> chara.sortValue = chara.age
            "7" -> chara.sortValue = chara.height
            "8" -> chara.sortValue = chara.weight
            else -> chara.sortValue = ""
        }
    }

//    override fun doCharaListLoadFinished() {
//        //filterDefault()
//    }
}