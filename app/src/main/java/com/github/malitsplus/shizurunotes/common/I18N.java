package com.github.malitsplus.shizurunotes.common;

import android.app.Application;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;

import com.github.malitsplus.shizurunotes.R;

import java.util.Arrays;

public class I18N {

    public static Application application;

    private I18N(){
    }

    public static String getString(@StringRes int resId){
        if(application != null)
            return application.getString(resId);
        else
            return "";
    }

    public static String[] getStringArray(@ArrayRes int resId){
        if(application != null)
            return application.getResources().getStringArray(resId);
        else
            return new String[] {""};
    }

    public static String getStringWithSpace(@StringRes int resId){
        if (application != null)
            return application.getString(R.string.space_modifier, application.getString(resId));
        else
            return "";
    }

    public static String getString(@StringRes int resId, Object... formatArgs){
        if(application != null)
            return application.getString(resId, formatArgs);
        else
            return "";
    }
}
