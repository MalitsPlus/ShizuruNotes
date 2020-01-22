package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsViewModelFactory implements ViewModelProvider.Factory {

    private int unitId;

    CharaDetailsViewModelFactory(int unitId){
        this.unitId = unitId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(Application.class ,String.class).newInstance(unitId);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }

}
