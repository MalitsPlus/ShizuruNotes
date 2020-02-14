package com.github.malitsplus.shizurunotes.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class SharedViewModelCharaFactory implements ViewModelProvider.Factory {
    private SharedViewModelChara sharedViewModelChara;

    public SharedViewModelCharaFactory(SharedViewModelChara sharedViewModelChara){
        this.sharedViewModelChara = sharedViewModelChara;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(SharedViewModelChara.class).newInstance(sharedViewModelChara);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
