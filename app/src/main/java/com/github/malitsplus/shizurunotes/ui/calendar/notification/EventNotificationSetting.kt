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
import java.util.*

class EventNotificationSetting : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.event_notification_preferences, rootKey)

        findPreference<SwitchPreferenceCompat>(NOTIFICATION_ENABLE)?.apply {
            onPreferenceChangeListener = OnPreferenceChangeListener { _ , newValue ->
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
        return { _ , b ->
            b as Boolean
            if (b) {
                prepareAlarm()
            }
            true
        }
    }

    private fun prepareAlarm() {
        val alarmMgr = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, 15)
//            set(Calendar.MINUTE, 49)
//            set(2020, 3, 20, 17, 56, 0)
            set(Calendar.SECOND, get(Calendar.SECOND) + 10)
        }
        val intent = Intent()
        intent.apply {
            setClass(requireContext(), AlarmReceiver::class.java)
            action = NOTIFICATION_ACTION
            putExtra(NOTIFICATION_EXTRA_TYPE, NORMAL_BEFORE)
        }
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun initializePreferences() {
        val flag = UserSettings.get().preference.getBoolean(NOTIFICATION_ENABLE, false)
        findPreference<PreferenceCategory>(CATEGORY_NORMAL)?.isEnabled = flag
        findPreference<PreferenceCategory>(CATEGORY_DUNGEON)?.isEnabled = flag
        findPreference<PreferenceCategory>(CATEGORY_HATSUNE)?.isEnabled = flag
    }
}