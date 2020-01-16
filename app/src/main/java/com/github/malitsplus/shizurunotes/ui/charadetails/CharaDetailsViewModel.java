package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.common.JsonUtils;
import com.github.malitsplus.shizurunotes.data.Chara;

import java.util.List;

public class CharaDetailsViewModel extends ViewModel {

    public MutableLiveData<Chara> chara = new MutableLiveData<>();



    public CharaDetailsViewModel(String charaJson){
        this.chara.setValue(JsonUtils.getBeanFromJson(charaJson, Chara.class));

    }

    public CharaDetailsViewModel(Chara chara){
        this.chara.setValue(chara);
    }

    public void initProperties(){
        this.chara.getValue().setRarityProperty();
        this.chara.getValue().setPropertyGrowth();
    }

    public void setChara(Chara chara){
        this.chara.setValue(chara);
    }

    public LiveData<Chara> getChara(){
        return chara;
    }
}
