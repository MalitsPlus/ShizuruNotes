package com.github.malitsplus.shizurunotes.db;


import com.github.malitsplus.shizurunotes.data.ClanBattlePhase;

public class RawClanBattlePhase {
    public int phase;
    public int wave_group_id_1;
    public int wave_group_id_2;
    public int wave_group_id_3;
    public int wave_group_id_4;
    public int wave_group_id_5;

    public ClanBattlePhase getClanBattlePhase(){
        return new ClanBattlePhase(
                phase,
                wave_group_id_1 == 0 ? null : wave_group_id_1,
                wave_group_id_2 == 0 ? null : wave_group_id_2,
                wave_group_id_3 == 0 ? null : wave_group_id_3,
                wave_group_id_4 == 0 ? null : wave_group_id_4,
                wave_group_id_5 == 0 ? null : wave_group_id_5
                );
    }
}
