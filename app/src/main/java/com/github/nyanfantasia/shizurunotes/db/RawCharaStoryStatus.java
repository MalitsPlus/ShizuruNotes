package com.github.nyanfantasia.shizurunotes.db;

import com.github.nyanfantasia.shizurunotes.data.OneStoryStatus;
import com.github.nyanfantasia.shizurunotes.utils.Utils;
import com.github.nyanfantasia.shizurunotes.data.Chara;
import com.github.nyanfantasia.shizurunotes.data.CharaStoryStatus;

import java.util.ArrayList;
import java.util.List;

public class RawCharaStoryStatus {
    public int story_id;
    public String unlock_story_name;
    public int status_type_1;
    public int status_rate_1;
    public int status_type_2;
    public int status_rate_2;
    public int status_type_3;
    public int status_rate_3;
    public int status_type_4;
    public int status_rate_4;
    public int status_type_5;
    public int status_rate_5;
    public int chara_id_1;

    public OneStoryStatus getCharaStoryStatus(Chara chara){
        List<CharaStoryStatus> list = new ArrayList<>();
        for(int i = 1; i <= 5; i++){
            int typeValue = (int)Utils.getValueFromObject(this, "status_type_" + i);
            if(typeValue != 0){
                int typeRate = (int)Utils.getValueFromObject(this, "status_rate_" + i);
                CharaStoryStatus charaStoryStatus = new CharaStoryStatus(chara.getCharaId(), typeValue, typeRate);
                list.add(charaStoryStatus);
            }
        }
        return new OneStoryStatus(story_id, unlock_story_name, chara_id_1, list);
    }
}
