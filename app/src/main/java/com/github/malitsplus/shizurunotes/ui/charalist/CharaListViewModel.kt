package com.github.malitsplus.shizurunotes.ui.charalist

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.mancj.materialsearchbar.MaterialSearchBar
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
        4 to I18N.getString(R.string.ui_chip_sort_physical_critical),
        5 to I18N.getString(R.string.ui_chip_sort_magical_critical),
        6 to I18N.getString(R.string.ui_chip_sort_physical_def),
        7 to I18N.getString(R.string.ui_chip_sort_magical_def),
        8 to I18N.getString(R.string.ui_chip_sort_tp_up),
        9 to I18N.getString(R.string.ui_chip_sort_tp_reduce),
        10 to I18N.getString(R.string.ui_chip_sort_age),
        11 to I18N.getString(R.string.ui_chip_sort_height),
        12 to I18N.getString(R.string.ui_chip_sort_weight)
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
                    valueA = a.charaProperty.getPhysicalCritical()
                    valueB = b.charaProperty.getPhysicalCritical()
                }
                "5" -> {
                    valueA = a.charaProperty.getMagicCritical()
                    valueB = b.charaProperty.getMagicCritical()
                }
                "6" -> {
                    valueA = a.charaProperty.getDef()
                    valueB = b.charaProperty.getDef()
                }
                "7" -> {
                    valueA = a.charaProperty.getMagicDef()
                    valueB = b.charaProperty.getMagicDef()
                }
                "8" -> {
                    valueA = a.charaProperty.getEnergyRecoveryRate()
                    valueB = b.charaProperty.getEnergyRecoveryRate()
                }
                "9" -> {
                    valueA = a.charaProperty.getEnergyReduceRate()
                    valueB = b.charaProperty.getEnergyReduceRate()
                }
                "10" -> {
                    valueA = if (a.age.contains("?")) 9999 else a.age.toInt()
                    valueB = if (b.age.contains("?")) 9999 else b.age.toInt()
                }
                "11" -> {
                    valueA = if (a.height.contains("?")) 9999 else a.height.toInt()
                    valueB = if (b.height.contains("?")) 9999 else b.height.toInt()
                }
                "12" -> {
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
            "4" -> chara.sortValue = chara.charaProperty.getPhysicalCritical().toString()
            "5" -> chara.sortValue = chara.charaProperty.getMagicCritical().toString()
            "6" -> chara.sortValue = chara.charaProperty.getDef().toString()
            "7" -> chara.sortValue = chara.charaProperty.getMagicDef().toString()
            "8" -> chara.sortValue = chara.charaProperty.getEnergyRecoveryRate().toString()
            "9" -> chara.sortValue = chara.charaProperty.getEnergyReduceRate().toString()
            "10" -> {
                if (chara.actualName == "出雲 宮子") {
                    chara.sortValue = I18N.getString(R.string.aged_s, chara.age)
                } else {
                    chara.sortValue = chara.age
                }
            }
            "11" -> chara.sortValue = chara.height
            "12" -> chara.sortValue = chara.weight
            else -> chara.sortValue = ""
        }
    }

    val textWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            searchChara(s)
        }
    }

    fun searchChara(inputText: CharSequence) {
        if (inputText.isNotEmpty()) {
            val charaToShow: MutableList<Chara> = ArrayList()
            sharedViewModelChara.charaList.value?.forEach { chara ->
                if (chara.unitName.startsWith(inputText)
                    || chara.kana.startsWith(inputText)
                    || chara.actualName.startsWith(inputText)
                ) {
                    charaToShow.add(chara)
                }
            }
            liveCharaList.postValue(charaToShow)
        } else {
            filterDefault()
        }
    }
}