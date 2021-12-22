package com.github.nyanfantasia.shizurunotes.ui.hatsune

import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.ui.base.HatsuneStageVT
import com.github.nyanfantasia.shizurunotes.ui.base.OnItemActionListener
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelHatsune

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