package com.github.malitsplus.shizurunotes.ui.secretdungeon

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.ui.base.HatsuneStageVT
import com.github.malitsplus.shizurunotes.ui.base.OnItemActionListener
import com.github.malitsplus.shizurunotes.ui.base.SecretDungeonScheduleVT
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelHatsune
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelSecretDungeon

class SecretDungeonViewModel(
    private val sharedSecretDungeon: SharedViewModelSecretDungeon
) : ViewModel() {
        val viewList = mutableListOf<ViewType<*>>()
        get() {
            field.clear()
            sharedSecretDungeon.secretDungeonScheduleList.value?.forEach {
                field.add(SecretDungeonScheduleVT(it))
            }
            return field
        }
}

interface OnSecretDungeonScheduleClickListener<T>: OnItemActionListener {
    fun onSecretDungeonScheduleClicked(item: T)
}