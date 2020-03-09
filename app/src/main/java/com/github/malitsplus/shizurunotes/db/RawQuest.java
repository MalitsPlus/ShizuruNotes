package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.Quest;
import com.github.malitsplus.shizurunotes.data.WaveGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RawQuest {
    public int quest_id;
    public int area_id;
    public String quest_name;
    public int wave_group_id_1;
    public int wave_group_id_2;
    public int wave_group_id_3;

    public Quest getQuest(){
        List<WaveGroup> waveGroupList = new ArrayList<>();
        List<RawWaveGroup> rawWaveGroupList = DBHelper.get().getWaveGroupData(new ArrayList<>(Arrays.asList(wave_group_id_1, wave_group_id_2, wave_group_id_3)));
        if (rawWaveGroupList != null){
            for (RawWaveGroup rawWaveGroup : rawWaveGroupList){
                waveGroupList.add(rawWaveGroup.getWaveGroup(false));
            }
        }
        return new Quest(quest_id, area_id, quest_name, waveGroupList);
    }
}
