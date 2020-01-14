package com.github.malitsplus.shizurunotes.ui.clanbattle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClanBattleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClanBattleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}