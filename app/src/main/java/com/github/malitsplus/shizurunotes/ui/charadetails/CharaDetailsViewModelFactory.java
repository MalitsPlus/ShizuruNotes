package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsViewModelFactory implements ViewModelProvider.Factory {

    private String charaJson;
    CharaDetailsViewModelFactory(String charaJson){
        this.charaJson = charaJson;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(String.class).newInstance(charaJson);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }

}
