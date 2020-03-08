package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.common.Statics

class RewardData(
    val rewardType: Int,
    val rewardId: Int,
    val rewardNum: Int,
    val odds: Int
) {
    val rewardIcon: String = Statics.EQUIPMENT_ICON_URL.format(rewardId)
}