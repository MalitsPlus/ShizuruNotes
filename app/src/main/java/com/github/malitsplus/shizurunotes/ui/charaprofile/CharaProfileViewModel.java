package com.github.malitsplus.shizurunotes.ui.charaprofile;

import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

public class CharaProfileViewModel extends ViewModel {
    private SharedViewModel sharedViewModel;

    public CharaProfileViewModel(SharedViewModel sharedViewModel){
        this.sharedViewModel = sharedViewModel;
    }
}
