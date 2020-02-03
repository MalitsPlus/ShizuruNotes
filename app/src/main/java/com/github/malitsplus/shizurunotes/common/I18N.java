package com.github.malitsplus.shizurunotes.common;

import android.app.Application;

import androidx.annotation.StringRes;

public class I18N {

    public static Application application;

    public static String getString(@StringRes int resId){
        if(application != null)
            return application.getString(resId);
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
