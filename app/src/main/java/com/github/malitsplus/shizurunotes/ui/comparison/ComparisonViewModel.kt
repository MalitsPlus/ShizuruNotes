package com.github.malitsplus.shizurunotes.ui.comparison

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara

class ComparisonViewModel(
    private val sharedViewModelChara: SharedViewModelChara
) : ViewModel() {
    val rankList = mutableListOf<Int>()

    init {
        for (i in this.sharedViewModelChara.maxCharaRank downTo 2) {
            rankList.add(i)
        }
    }

}