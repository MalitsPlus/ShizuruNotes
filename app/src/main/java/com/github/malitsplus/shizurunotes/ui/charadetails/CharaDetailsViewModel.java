package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

public class CharaDetailsViewModel extends ViewModel {

    private SharedViewModel sharedViewModel;
    public MutableLiveData<Chara> mutableChara = new MutableLiveData<>();

    public CharaDetailsViewModel(SharedViewModel sharedViewModel){
        this.sharedViewModel = sharedViewModel;
        setChara(sharedViewModel.getSelectedChara());
    }

    public void setChara(Chara chara){
        this.mutableChara.setValue(chara);
    }

    public LiveData<Chara> getMutableChara(){
        return mutableChara;
    }
}
