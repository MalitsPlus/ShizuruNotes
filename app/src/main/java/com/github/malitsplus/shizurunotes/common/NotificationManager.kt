package com.github.malitsplus.shizurunotes.common

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.github.malitsplus.shizurunotes.ui.calendar.notification.AlarmReceiver
import com.github.malitsplus.shizurunotes.ui.calendar.notification.NORMAL_BEFORE
import com.github.malitsplus.shizurunotes.ui.calendar.notification.NOTIFICATION_ACTION
import com.github.malitsplus.shizurunotes.ui.calendar.notification.NOTIFICATION_EXTRA_TYPE
import java.util.*

class NotificationManager private constructor(
    private val mContext: Context
) {
    companion object {
        private lateinit var instance: NotificationManager

        fun with (context: Context): NotificationManager {
            instance = NotificationManager(context)
            return instance
        }

        fun get(): NotificationManager {
            return instance
        }
    }

    private fun prepareAlarm() {
        val alarmMgr = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, 15)
//            set(Calendar.MINUTE, 49)
//            set(2020, 3, 20, 17, 56, 0)
            set(Calendar.SECOND, get(Calendar.SECOND) + 10)
        }
        val intent = Intent()
        intent.apply {
            setClass(mContext, AlarmReceiver::class.java)
            action = NOTIFICATION_ACTION
            putExtra(NOTIFICATION_EXTRA_TYPE, NORMAL_BEFORE)
        }
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0)
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}