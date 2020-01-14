package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsViewModelFactory implements ViewModelProvider.Factory {

    private String charaId;
    CharaDetailsViewModelFactory(String charaId){
        this.charaId = charaId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(String.class).newInstance(charaId);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
