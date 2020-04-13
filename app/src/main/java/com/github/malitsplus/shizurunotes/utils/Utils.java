package com.github.malitsplus.shizurunotes.utils;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;

import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.data.Chara;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Utils {

    private static Application app;
    public static void setApp(Application application) {
        Utils.app = application;
    }
    public static Application getApp() {
        return app;
    }

    /***
     * 以逗号分隔字符串
     * @param list
     * @return
     */
    public static String splitIntegerWithComma(ArrayList<Integer> list){
        if(list.isEmpty())
            return null;

        StringBuilder sb = new StringBuilder();
        for(Integer item : list){
            sb.append(item).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }


    public static Object getValueFromObject(@NonNull Object object, @NonNull String fieldName) {
        Field field;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }


    private static DecimalFormat format = new DecimalFormat("#");
    public static String roundDownDouble(double value){
        return format.format(Math.floor(value));
    }
    public static String roundUpDouble(double value){
        return format.format(Math.ceil(value));
    }
    public static String roundDouble(double value){
        return format.format(Math.round(value));
    }
    public static String roundIfNeed(double value) {
        if (value % 1 == 0) {
            return roundDouble(value);
        } else {
            return String.valueOf(value);
        }
    }

    public static String getCurrentProcessName() {
        return Thread.currentThread().getName();
    }
}
