package com.github.malitsplus.shizurunotes.ui.charaprofile

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.ui.base.CharaProfileVT
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara

class CharaProfileViewModel(
    val sharedChara: SharedViewModelChara
) : ViewModel() {

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            field.clear()
            sharedChara.selectedChara?.let {
                val charaProfileVT = CharaProfileVT(it)
                field.add(charaProfileVT)
                it
            }
            return field
        }

}