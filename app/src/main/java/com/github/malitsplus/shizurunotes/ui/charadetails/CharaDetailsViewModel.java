package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

public class CharaDetailsViewModel extends ViewModel {

    int unitId;
    private SharedViewModel sharedViewModel;
    public MutableLiveData<Chara> mutableChara = new MutableLiveData<>();



    public CharaDetailsViewModel(int unitId){
        this.unitId = unitId;
    }

    public void inflateData(){
        for(Chara chara : sharedViewModel.getCharaList()){
            if(unitId == chara.unitId){
                setChara(chara);
                break;
            }
        }
    }


    public void setSharedViewModel(SharedViewModel sharedViewModel){
        this.sharedViewModel = sharedViewModel;
    }
    public void setChara(Chara chara){
        this.mutableChara.setValue(chara);
    }

    public LiveData<Chara> getMutableChara(){
        return mutableChara;
    }
}
