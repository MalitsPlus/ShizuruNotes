package com.github.nyanfantasia.shizurunotes.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

object JsonUtils {
    private var gson: Gson? = null
    private fun initOrCheck() {
        if (gson == null) {
            gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        }
    }

    /**
     * Json对象转Java对象
     * @param json
     * @param type Example.class
     * @return
     */
    fun <T> getBeanFromJson(json: String?, type: Type?): T {
        initOrCheck()
        return gson!!.fromJson(json, type)
    }

    /**
     * Json数组转Java数组（列表）
     * @param json
     * @param type 数组元素的类
     * @return
     */
    fun <T> getListFromJson(json: String?, type: Type?): List<T> {
        var json1 = json
        initOrCheck()
        if (TextUtils.isEmpty(json)) json1 = "[]"
        val jsonObjects = gson!!.fromJson<List<JsonObject>>(
            json1,
            object : TypeToken<List<JsonObject?>?>() {}.type
        )
        val lists: MutableList<T> = ArrayList()
        for (jsonObject in jsonObjects) {
            lists.add(gson!!.fromJson(jsonObject, type))
        }
        return lists
    }

    fun <T> getArrayFromJson(json: String?, type: TypeToken<*>): List<T> {
        initOrCheck()
        return gson!!.fromJson(json, type.type)
    }

    /**
     * Java对象转Json
     * @param bean 对象实例
     * @return
     */
    fun <T> getJsonFromBean(bean: T): String {
        initOrCheck()
        return gson!!.toJson(bean)
    }

    /**
     * Java List转Json数组
     * @param list List实例
     * @return
     */
    fun <T> getJsonFromList(list: List<T>?): String {
        initOrCheck()
        return gson!!.toJson(list)
    }
}