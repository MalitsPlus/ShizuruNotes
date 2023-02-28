package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RawClanBattlePeriod {
    public int clan_battle_id;
    public int release_month;
    public String start_time;
    public String end_time;

    public ClanBattlePeriod transToClanBattlePeriod(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
        return new ClanBattlePeriod(
                clan_battle_id,
                LocalDateTime.parse(start_time, formatter),
                LocalDateTime.parse(end_time, formatter)
        );
    }
}
