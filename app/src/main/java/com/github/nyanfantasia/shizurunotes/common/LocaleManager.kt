package com.github.nyanfantasia.shizurunotes.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import java.util.*

class LocaleManager internal constructor(context: Context?) {
    companion object {
        //已支援語言的資料庫，對比完整語言資料的文字格式(String)
        private val SUPPORTED_LANGUAGE: MutableList<String> = ArrayList()

        init {
            SUPPORTED_LANGUAGE.add(Locale.JAPANESE.language)
            SUPPORTED_LANGUAGE.add(Locale.CHINESE.language)
            SUPPORTED_LANGUAGE.add(Locale.KOREAN.language)
            SUPPORTED_LANGUAGE.add(Locale.ENGLISH.language)
        }
    }

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context!!)
    fun setLocale(context: Context): Context {
        return updateResources(context, language)
    }

    val language: String = "system"
        get() {
            //每次啟動(非首次)應用程式的語言判斷
            val currentLanguage = prefs.getString(UserSettings.LANGUAGE_KEY, null)
            if (currentLanguage != null) return currentLanguage

            //首次啟動時的判斷
            val systemLanguage = Locale.getDefault()
            return if (SUPPORTED_LANGUAGE.contains(systemLanguage.language)) {
                persistLanguage("system")
                field
            } else {
                // 無匹配資料庫，設定為日文
                persistLanguage(Locale.JAPANESE.language)
                Locale.JAPANESE.language
            }
        }

    fun setNewLocale(context: Context, language: String): Context {
        return updateResources(context, language)
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        prefs.edit().putString(UserSettings.LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(context: Context, language: String): Context {
        var context1 = context
        var locale = Locale(language)

        // Workaround for String and Locale conversion
        when (language) {
            "zh-Hant" -> locale = Locale.TAIWAN
            "zh-Hans" -> locale = Locale.CHINA
            "system" -> locale = Locale.getDefault()
            else
            -> Locale.setDefault(locale)
        }

        val res = context1.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context1 = context1.createConfigurationContext(config)
        return context1
    }

}