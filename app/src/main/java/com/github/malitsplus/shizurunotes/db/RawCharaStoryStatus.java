package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.utils.Utils;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.CharaStoryStatus;
import com.github.malitsplus.shizurunotes.data.Property;

public class RawCharaStoryStatus {
    //public int story_id;
    //public String unlock_story_name;
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

    public Property getCharaStoryStatus(Chara chara){
        Property storyProperty = new Property();
        for(int i = 1; i <= 5; i++){
            int typeValue = (int)Utils.getValueFromObject(this, "status_type_" + i);
            if(typeValue != 0){
                int typeRate = (int)Utils.getValueFromObject(this, "status_rate_" + i);
                CharaStoryStatus charaStoryStatus = new CharaStoryStatus(chara.getCharaId(), typeValue, typeRate);
                storyProperty.plusEqual(charaStoryStatus.getProperty());
            }
        }
        return storyProperty;
    }
}
