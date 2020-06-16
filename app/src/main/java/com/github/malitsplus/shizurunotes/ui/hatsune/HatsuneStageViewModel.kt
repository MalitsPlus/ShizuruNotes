package com.github.malitsplus.shizurunotes.ui.hatsune

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.ui.base.HatsuneStageVT
import com.github.malitsplus.shizurunotes.ui.base.OnItemActionListener
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelHatsune

class HatsuneStageViewModel(
    private val sharedHatsune: SharedViewModelHatsune
) : ViewModel() {

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            field.clear()
            sharedHatsune.hatsuneStageList.value?.forEach {
                field.add(HatsuneStageVT(it))
            }
            return field
        }
}

interface OnHatsuneClickListener<T>: OnItemActionListener {
    fun onStageClicked(item: T)
}