package com.github.nyanfantasia.shizurunotes.user

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.github.nyanfantasia.shizurunotes.utils.FileUtils
import com.github.nyanfantasia.shizurunotes.utils.JsonUtils
import com.github.nyanfantasia.shizurunotes.utils.LogUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread

class UserSettings private constructor(
    private val application: Application
) {
    companion object {
        const val LANGUAGE_KEY = "language"
        const val SERVER_KEY = "server"
        const val EXPRESSION_STYLE = "expressionStyle2"
        const val LIMIT_CLAN_BATTLE_KEY = "limitClanBattleNum"
        const val BADGE_KEY = "isBadgeVisible"
        const val HIDE_SERVER_SWITCH_HINT_KEY = "hideServerSwitchHint"
        const val LOG = "log"
        const val DB_VERSION = "dbVersion_new"
        const val DB_VERSION_JP = "dbVersion_jp"
        const val DB_VERSION_CN = "dbVersion_cn"
        const val APP_VERSION = "appVersion"
        const val ABOUT = "about"
        const val EXPRESSION_VALUE = 0
        const val EXPRESSION_EXPRESSION = 1
        const val EXPRESSION_ORIGINAL = 2
        const val SERVER_JP = "jp"
        const val SERVER_CN = "cn"
        const val LAST_DB_HASH = "last_db_hash"
        const val ABNORMAL_EXIT = "abnormal_exit"

        private const val userDataFileName = "userData.json"

        @Volatile
        private lateinit var instance: UserSettings

        fun with(application: Application) {
            synchronized(UserSettings::class.java) {
                instance = UserSettings(application)
            }
        }

        @JvmStatic
        fun get(): UserSettings {
            return instance
        }
    }

    private val json: String
        get() {
            val stringBuilder = StringBuilder()
            if (FileUtils.checkFile(FileUtils.getFileFilePath(userDataFileName))) {
                try {
                    application.openFileInput(userDataFileName).use { fis ->
                        val inputStreamReader = InputStreamReader(fis, StandardCharsets.UTF_8)
                        val reader = BufferedReader(inputStreamReader)
                        var line = reader.readLine()
                        while (line != null) {
                            stringBuilder.append(line).append('\n')
                            line = reader.readLine()
                        }
                    }
                } catch (e: IOException) {
                    LogUtils.file(LogUtils.E, "GetUserJson", e.message, e.stackTrace)
                }
            }
            return stringBuilder.toString()
        }

    private val userData: UserData = if (json.isNotEmpty()) {
        JsonUtils.getBeanFromJson<UserData>(json, UserData::class.java)
    } else {
        UserData()
    }

    val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(application)

    var lastEquipmentIds: List<Int>
        get() {
            return if (userData.lastEquipmentIds != null) {
                userData.lastEquipmentIds
            } else {
                emptyList()
            }
        }
        set(ids) {
            userData.lastEquipmentIds = ids
            saveJson()
        }

    private fun saveJson() {
        thread(start = true) {
            val json = JsonUtils.getJsonFromBean(userData)
            try {
                application.openFileOutput(userDataFileName, Context.MODE_PRIVATE).use { fos ->
                    fos.write(json.toByteArray())
                }
            } catch (e: IOException) {
                LogUtils.file(LogUtils.E, "SaveUserJson", e.message, e.stackTrace)
            }
        }
    }

    fun getUserServer(): String {
        return preference.getString(SERVER_KEY, SERVER_JP) ?: SERVER_JP
    }

    fun getDbVersion(): Long {
        return if (preference.getString(SERVER_KEY, null) == SERVER_CN) {
            preference.getLong(DB_VERSION_CN, 0)
        } else {
            preference.getLong(DB_VERSION_JP, 0)
        }
    }

    fun getLanguage(): String {
        return if (preference.getString(LANGUAGE_KEY, null).equals("zh-Hant") || equals("zh-Hans")){
            "zh"
        } else {
            preference.getString(LANGUAGE_KEY, null) ?: "ja"
        }
    }

    @SuppressLint("ApplySharedPref")
    fun setDbVersion(newVersion: Long, async: Boolean = true) {
        when (preference.getString(SERVER_KEY, SERVER_JP)) {
            SERVER_JP -> {
                if (async) {
                    preference.edit().putLong(DB_VERSION_JP, newVersion).apply()
                } else {
                    preference.edit().putLong(DB_VERSION_JP, newVersion).commit()
                }
            }
            SERVER_CN -> {
                if (async) {
                    preference.edit().putLong(DB_VERSION_CN, newVersion).apply()
                } else {
                    preference.edit().putLong(DB_VERSION_CN, newVersion).commit()
                }
            }
        }
    }

    fun getExpression(): Int {
        return preference.getString(EXPRESSION_STYLE, "0")?.toInt() ?: 0
    }
    fun setExpression(newValue: Int) {
        preference.edit().putString(EXPRESSION_STYLE, newValue.toString()).apply()
    }
    fun getClanBattleLimit(): Boolean {
        return preference.getBoolean(LIMIT_CLAN_BATTLE_KEY, false)
    }
    fun getBadgeVisibility(): Boolean {
        return preference.getBoolean(BADGE_KEY, true)
    }
    fun setBadgeVisibility(visible: Boolean) {
        preference.edit().putBoolean(BADGE_KEY, visible).apply()
    }
    fun getHideServerSwitchHint(): Boolean {
        return preference.getBoolean(HIDE_SERVER_SWITCH_HINT_KEY, false)
    }
    fun setHideServerSwitchHint(isHide: Boolean) {
        preference.edit().putBoolean(HIDE_SERVER_SWITCH_HINT_KEY, isHide).apply()
    }
    fun getDBHash(): String {
        return preference.getString(LAST_DB_HASH, "0") ?: "0"
    }
    fun setDBHash(newValue: String) {
        preference.edit().putString(LAST_DB_HASH, newValue).apply()
    }
    fun getAbnormalExit(): Boolean {
        return preference.getBoolean(ABNORMAL_EXIT, false)
    }
    fun setAbnormalExit(value: Boolean) {
        preference.edit().putBoolean(ABNORMAL_EXIT, value).apply()
    }
}