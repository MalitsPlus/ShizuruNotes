package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N

class OneStoryStatus(
    val storyId: Int,
    val storyName: String,
    val charaId: Int,
    val charaStoryStatusList: List<CharaStoryStatus>
) {
    val allProperty: Property
        get() {
            return Property().apply {
                charaStoryStatusList.forEach {
                    this.plusEqual(it.property)
                }
            }
        }

    val storyParsedName: String
        get() {
            return I18N.getString(R.string.episode_d, storyId % 100)
        }
}