package com.github.malitsplus.shizurunotes.ui.secretdungeon

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.data.WaveGroup
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelHatsune
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelSecretDungeon

class SecretDungeonWaveViewModel(
    private val sharedSecretDungeon: SharedViewModelSecretDungeon
) : ViewModel() {
        val viewList = mutableListOf<ViewType<*>>()
            get() {
                field.clear()
                sharedSecretDungeon.selectedSchedule?.let { schedule ->
                    schedule.waveGroupMap.forEach {
                        field.add(SecretDungeonQuestVT(it))
                    }
                }
                return field
            }
}

interface OnSecretDungeonQuestClickListener: OnItemActionListener {
    fun onSecretDungeonQuestClicked(waveGroup: WaveGroup)
}
