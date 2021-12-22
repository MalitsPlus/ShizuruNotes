package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.common.Statics

class RewardData(
    val rewardType: Int,
    val rewardId: Int,
    val rewardNum: Int,
    val odds: Int
) {
    val rewardIcon: String = Statics.EQUIPMENT_ICON_URL.format(rewardId)
    val oddsString: String = I18N.getString(R.string.percent_modifier).format(odds)

}