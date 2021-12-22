package com.github.nyanfantasia.shizurunotes.ui.hatsune

import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.data.WaveGroup
import com.github.nyanfantasia.shizurunotes.ui.base.HatsuneWaveVT
import com.github.nyanfantasia.shizurunotes.ui.base.OnItemActionListener
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelHatsune

class HatsuneWaveViewModel(
    private val sharedHatsune: SharedViewModelHatsune
) : ViewModel() {

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            field.clear()
            sharedHatsune.selectedHatsune?.let { stage ->
                stage.battleWaveGroupMap.forEach {
                    field.add(HatsuneWaveVT(it))
                }
            }
            return field
        }
}

interface OnWaveClickListener : OnItemActionListener {
    fun onWaveClick(waveGroup: WaveGroup)
}