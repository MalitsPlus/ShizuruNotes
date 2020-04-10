package com.github.malitsplus.shizurunotes.ui.calendar.notification

import android.os.Bundle
import androidx.preference.*
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.user.UserSettings
import androidx.preference.Preference.OnPreferenceChangeListener

class EventNotificationSetting : PreferenceFragmentCompat(){

    companion object{
        const val NOTIFICATION_ENABLE = "notification_enable"
        const val CATEGORY_NORMAL = "category_normal"
        const val CATEGORY_DUNGEON = "category_dungeon"
        const val CATEGORY_HATSUNE = "category_hatsune"
        const val NORMAL_BEFORE = "normal_before"
        const val DUNGEON_BEFORE_2 = "dungeon_before_2"
        const val DUNGEON_BEFORE = "dungeon_before"
        const val HATSUNE_BEFORE = "hatsune_before"
        const val HATSUNE_LAST = "hatsune_last"
        const val HATSUNE_LAST_HOUR = "hatsune_last_hour"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.event_notification_preferences, rootKey)

        findPreference<SwitchPreferenceCompat>(NOTIFICATION_ENABLE)?.apply {
            onPreferenceChangeListener = OnPreferenceChangeListener { preference, newValue ->
                newValue as Boolean
                findPreference<PreferenceCategory>(CATEGORY_NORMAL)?.isEnabled = newValue
                findPreference<PreferenceCategory>(CATEGORY_DUNGEON)?.isEnabled = newValue
                findPreference<PreferenceCategory>(CATEGORY_HATSUNE)?.isEnabled = newValue
                true
            }
        }

        findPreference<SwitchPreferenceCompat>(NORMAL_BEFORE)?.apply {
            onPreferenceChangeListener = OnPreferenceChangeListener(switchNotification())
        }

        initializePreferences()
    }

    private fun switchNotification(): (p: Preference, b: Any) -> Boolean {
        return { preference: Preference, any: Any ->

            true
        }
    }

    private fun initializePreferences() {
        val flag = UserSettings.get().preference.getBoolean(NOTIFICATION_ENABLE, false)
        findPreference<PreferenceCategory>(CATEGORY_NORMAL)?.isEnabled = flag
        findPreference<PreferenceCategory>(CATEGORY_DUNGEON)?.isEnabled = flag
        findPreference<PreferenceCategory>(CATEGORY_HATSUNE)?.isEnabled = flag
    }

}