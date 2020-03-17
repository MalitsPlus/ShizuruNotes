package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.EnemyRewardData;
import com.github.malitsplus.shizurunotes.data.RewardData;

import java.util.ArrayList;
import java.util.List;

public class RawEnemyRewardData {
    public int drop_reward_id;
    public int drop_count;
    public int reward_type_1;
    public int reward_id_1;
    public int reward_num_1;
    public int odds_1;
    public int reward_type_2;
    public int reward_id_2;
    public int reward_num_2;
    public int odds_2;
    public int reward_type_3;
    public int reward_id_3;
    public int reward_num_3;
    public int odds_3;
    public int reward_type_4;
    public int reward_id_4;
    public int reward_num_4;
    public int odds_4;
    public int reward_type_5;
    public int reward_id_5;
    public int reward_num_5;
    public int odds_5;

    public EnemyRewardData getEnemyRewardData() {
        List<RewardData> rewardDataList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int rewardId = (int)Utils.getValueFromObject(this, "reward_id_" + i);
            if (rewardId != 0) {
                rewardDataList.add(new RewardData(
                        (int)Utils.getValueFromObject(this, "reward_type_" + i),
                        rewardId,
                        (int)Utils.getValueFromObject(this, "reward_num_" + i),
                        (int)Utils.getValueFromObject(this, "odds_" + i)
                ));
            }
        }
        return new EnemyRewardData(rewardDataList);
    }

}
