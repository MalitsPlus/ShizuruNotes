package com.github.malitsplus.shizurunotes.common;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class UserSettings {

    private static volatile UserSettings instance;
    private Application application;

    private UserSettings(Application application){
        this.application = application;
    }

    public static UserSettings with(Application application){
        if(instance == null) {
            synchronized (UserSettings.class) {
                instance = new UserSettings(application);
            }
        }
        return instance;
    }

    public static UserSettings get(){
        if(instance == null){
            throw new NullPointerException("No instance of UserSettings.");
        }
        return instance;
    }

    public SharedPreferences getPreference(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
