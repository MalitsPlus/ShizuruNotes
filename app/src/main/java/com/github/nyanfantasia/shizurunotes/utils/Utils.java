package com.github.nyanfantasia.shizurunotes.utils;

import android.app.Application;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
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

    private static DecimalFormat format2 = new DecimalFormat("0.0");
    public static String getOneDecimalPlaces(Double value) {
        return format2.format(value);
    }

    public static String getCurrentProcessName() {
        return Thread.currentThread().getName();
    }

    public static double getScreenRatio() {
        DisplayMetrics metrics = app.getResources().getDisplayMetrics();
        return Double.parseDouble(String.valueOf(metrics.heightPixels)) / metrics.widthPixels;
    }

    private static final char[] HEX_DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Bytes to hex string.
     * <p>e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }, true) returns "00A8"</p>
     *
     * @param bytes       The bytes.
     * @param isUpperCase True to use upper case, false otherwise.
     * @return hex string
     */
    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) return "";
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }
}
