package com.github.malitsplus.shizurunotes.ui.charaprofile

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara

class CharaProfileViewModel(
    val sharedChara: SharedViewModelChara
) : ViewModel() {

    val viewList = mutableListOf<ViewType<*>>()
        get() {
            field.clear()
            sharedChara.selectedChara?.let { chara ->
                field.add(CharaProfileVT(chara))
                field.add(TextTagVT(I18N.getString(R.string.chara_story_status)))
                chara.storyStatusList.forEach { story ->
                    if (chara.charaId == story.charaId) {
                        field.add(TextTagAlphaVT(story.storyParsedName))
                        story.allProperty.nonZeroPropertiesMap.forEach {
                            field.add(PropertyVT(it))
                        }
                    }
                }
                field.add(SpaceVT())
                field.add(CharaUniqueEquipmentVT(chara.uniqueEquipment ?: Equipment.getNull))
                chara.rankEquipments.entries.forEach {
                    field.add(CharaRankEquipmentVT(it))
                }
                chara
            }
            return field
        }
}

interface OnEquipmentClickListener<T>: OnItemActionListener {
    fun onEquipmentClicked(item: T)
}