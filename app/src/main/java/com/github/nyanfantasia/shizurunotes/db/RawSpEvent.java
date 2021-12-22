package com.github.nyanfantasia.shizurunotes.db;

import com.github.nyanfantasia.shizurunotes.data.Enemy;
import com.github.nyanfantasia.shizurunotes.data.SpEvent;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class RawSpEvent {
    public int boss_id;
    public String name;
    public int wave_group_id;
    public int enemy_id_1;
    public int enemy_id_2;
    public int enemy_id_3;
    public int enemy_id_4;
    public int enemy_id_5;

    public SpEvent getSpEvent(){
        List<RawEnemy> rawEnemyList = DBHelper.get().getEnemy(Lists.newArrayList(enemy_id_1, enemy_id_2, enemy_id_3, enemy_id_4, enemy_id_5));
        List<Enemy> enemyList = new ArrayList<>();
        for (RawEnemy raw: rawEnemyList) {
            enemyList.add(raw.getEnemy());
        }
        if (enemyList.size() > 0){
            return new SpEvent(
                    boss_id,
                    name,
                    wave_group_id,
                    enemyList);
        } else {
            return null;
        }
    }
}
