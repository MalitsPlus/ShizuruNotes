package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.WaveGroup;

public class RawWaveGroup {
    public int id;
    public int wave_group_id;
    public int enemy_id_1;
    public int enemy_id_2;
    public int enemy_id_3;
    public int enemy_id_4;
    public int enemy_id_5;

    public WaveGroup getWaveGroup(){
        return new WaveGroup(
                id,
                wave_group_id,
                enemy_id_1 == 0 ? null : enemy_id_1,
                enemy_id_2 == 0 ? null : enemy_id_2,
                enemy_id_3 == 0 ? null : enemy_id_3,
                enemy_id_4 == 0 ? null : enemy_id_4,
                enemy_id_5 == 0 ? null : enemy_id_5
        );
    }
}
