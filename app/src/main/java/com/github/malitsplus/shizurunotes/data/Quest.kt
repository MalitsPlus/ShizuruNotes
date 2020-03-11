package com.github.malitsplus.shizurunotes.data

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
                    this.add(reward)
                }
            }
        }
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
        Others
    }
}