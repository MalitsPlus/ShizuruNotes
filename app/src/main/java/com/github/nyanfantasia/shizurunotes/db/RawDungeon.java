package com.github.nyanfantasia.shizurunotes.db;

import com.github.nyanfantasia.shizurunotes.data.Dungeon;
import com.github.nyanfantasia.shizurunotes.data.Enemy;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class RawDungeon {
    public int dungeon_area_id;
    public String dungeon_name;
    public String description;
    public int mode;
    public int wave_group_id;
    public int enemy_id_1;
    public int enemy_id_2;
    public int enemy_id_3;
    public int enemy_id_4;
    public int enemy_id_5;

    public Dungeon getDungeon(){
        List<RawEnemy> rawEnemyList = DBHelper.get().getEnemy(Lists.newArrayList(enemy_id_1, enemy_id_2, enemy_id_3, enemy_id_4, enemy_id_5));
        List<Enemy> enemyList = new ArrayList<>();
        for (RawEnemy raw: rawEnemyList) {
            enemyList.add(raw.getEnemy());
        }
        if (enemyList.size() > 0){
            return new Dungeon(
                    dungeon_area_id,
                    wave_group_id,
                    enemy_id_1,
                    mode,
                    dungeon_name,
                    description,
                    enemyList
            );
        } else {
            return null;
        }
    }
}
