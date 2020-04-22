package com.github.malitsplus.shizurunotes.ui.calendar.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.preference.*
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.user.UserSettings
import androidx.preference.Preference.OnPreferenceChangeListener
import com.github.malitsplus.shizurunotes.common.NotificationManager
import java.util.*

class EventNotificationSetting : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.event_notification_preferences, rootKey)

        findPreference<SwitchPreferenceCompat>(NORMAL_BEFORE)?.apply {
            onPreferenceChangeListener = OnPreferenceChangeListener(switchNotification())

        }
    }

    private fun switchNotification(): (p: Preference, b: Any) -> Boolean {
        return { p , b ->
            NotificationManager.get().refreshNotification()
                //.refreshSpecificNotification(p.key, b as Boolean)
            true
        }
    }
}