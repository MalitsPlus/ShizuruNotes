package com.github.malitsplus.shizurunotes.ui.charalist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.Statics
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.ui.SharedViewModel
import java.util.*

class CharaListViewModel(private val sharedViewModel: SharedViewModel) : ViewModel() {

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

        asc?.apply { isAsc = this }

        val charaToShow: MutableList<Chara> = ArrayList()
        for (chara in sharedViewModel.charaList) {
            if (checkAttackType(chara, selectedAttackType) && checkPosition(chara, selectedPosition)) {
                setSortValue(chara, selectedSort)
                charaToShow.add(chara)
            }
        }

        charaToShow.sortWith(kotlin.Comparator{ a: Chara, b: Chara ->
            val valueA: Long
            val valueB: Long
            when (selectedSort) {
                "0" -> {
                    valueA = a.startTime
                    valueB = b.startTime
                }
                "1" -> {
                    valueA = a.searchAreaWidth.toLong()
                    valueB = b.searchAreaWidth.toLong()
                }
                "2" -> {
                    valueA = a.charaProperty.getAtk().toLong()
                    valueB = b.charaProperty.getAtk().toLong()
                }
                "3" -> {
                    valueA = a.charaProperty.getMagicStr().toLong()
                    valueB = b.charaProperty.getMagicStr().toLong()
                }
                "4" -> {
                    valueA = a.charaProperty.getDef().toLong()
                    valueB = b.charaProperty.getDef().toLong()
                }
                "5" -> {
                    valueA = a.charaProperty.getMagicDef().toLong()
                    valueB = b.charaProperty.getMagicDef().toLong()
                }
                "6" -> {
                    valueA = if (a.age.contains("?")) 9999 else a.age.toLong()
                    valueB = if (b.age.contains("?")) 9999 else b.age.toLong()
                }
                "7" -> {
                    valueA = if (a.height.contains("?")) 9999 else a.height.toLong()
                    valueB = if (b.height.contains("?")) 9999 else b.height.toLong()
                }
                "8" -> {
                    valueA = if (a.weight.contains("?")) 9999 else a.weight.toLong()
                    valueB = if (b.weight.contains("?")) 9999 else b.weight.toLong()
                }
                else -> {
                    valueA = a.unitId.toLong()
                    valueB = b.unitId.toLong()
                }
            }
            (if (isAsc) -1 else 1) * valueB.compareTo(valueA)
        })
        liveCharaList.value = charaToShow
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
}