package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String charaJson;

    CharaDetailsViewModelFactory(Application application, String charaJson){
        this.application = application;
        this.charaJson = charaJson;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(Application.class ,String.class).newInstance(application, charaJson);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }

}
