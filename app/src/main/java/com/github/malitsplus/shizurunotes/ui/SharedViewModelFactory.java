package com.github.malitsplus.shizurunotes.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class SharedViewModelFactory implements ViewModelProvider.Factory {
    private SharedViewModel sharedViewModel;

    public SharedViewModelFactory(SharedViewModel sharedViewModel){
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(SharedViewModel.class).newInstance(sharedViewModel);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
