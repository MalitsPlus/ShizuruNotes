package com.github.malitsplus.shizurunotes.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static Gson gson;

    private static void initOrCheck(){
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }
    /**
     * Json对象转Java对象
     * @param json
     * @param type Example.class
     * @return
     */
    public static <T> T getBeanFromJson(String json, Type type) {
        initOrCheck();
        return gson.fromJson(json, type);
    }

    /**
     * Json数组转Java数组（列表）
     * @param json
     * @param type 数组元素的类
     * @return
     */
    public static <T> List<T> getListFromJson(String json, Type type) {
        initOrCheck();
        if (TextUtils.isEmpty(json)) json = "[]";
        List<JsonObject> jsonObjects = gson.fromJson(json, new TypeToken<List<JsonObject>>(){}.getType());
        List<T> lists = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            lists.add(gson.fromJson(jsonObject, type));
        }
        return lists;
    }

    public static <T> List<T> getArrayFromJson(String json, TypeToken type) {
        initOrCheck();
        return gson.fromJson(json, type.getType());
    }

    /**
     * Java对象转Json
     * @param bean 对象实例
     * @return
     */
    public static <T> String getJsonFromBean(T bean) {
        initOrCheck();
        return gson.toJson(bean);
    }

    /**
     * Java List转Json数组
     * @param list List实例
     * @return
     */
    public static <T> String getJsonFromList(List<T> list) {
        initOrCheck();
        return gson.toJson(list);
    }

}
