package com.github.malitsplus.shizurunotes.data

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N

class Quest(
    val questId: Int,
    val areaId: Int,
    val questName: String,
    val waveGroupList: List<WaveGroup>
) {

    fun contains(itemId: Int): Boolean {
        dropList.forEach {
            if (it.rewardId % 10000 == itemId % 10000){
                return true
            }
        }
        return false
    }

    val dropList = mutableListOf<RewardData>().apply {
        waveGroupList.forEach { wave ->
            wave.dropRewardList?.forEach { drop ->
                drop.rewardDataList.forEach { reward ->
                    if (reward.rewardType == 4)
                        this.add(reward)
                }
            }
        }
    }

    val dropGold: Int by lazy {
        var gold = 0
        waveGroupList.forEach { wave ->
            wave.dropGoldList?.forEach {
                gold += it
            }
        }
        gold
    }

    val questType: QuestType by lazy {
        when(areaId) {
            in 11000..11999 -> QuestType.Normal
            in 12000..12999 -> QuestType.Hard
            in 13000..13999 -> QuestType.VeryHard
            else -> QuestType.Others
        }
    }

    enum class QuestType {
        Normal,
        Hard,
        VeryHard,
        Others;
        fun description(): String {
            return when(this) {
                Normal -> I18N.getStringWithSpace(R.string.text_quest_normal)
                Hard -> I18N.getStringWithSpace(R.string.text_quest_hard)
                VeryHard -> I18N.getStringWithSpace(R.string.text_quest_very_hard)
                else -> I18N.getStringWithSpace(R.string.unknown)
            }
        }
    }
}