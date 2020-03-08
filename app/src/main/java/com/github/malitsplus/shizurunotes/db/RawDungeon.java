package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Dungeon;

public class RawDungeon {
    public int dungeon_area_id;
    public String dungeon_name;
    public String description;
    public int wave_group_id;
    public int enemy_id_1;

    public Dungeon getDungeon(){
        RawEnemy raw = DBHelper.get().getEnemy(enemy_id_1);
        if (raw != null){
            return new Dungeon(
                    dungeon_area_id,
                    wave_group_id,
                    enemy_id_1,
                    dungeon_name,
                    description,
                    raw.getEnemy()
            );
        } else {
            return null;
        }
    }
}
