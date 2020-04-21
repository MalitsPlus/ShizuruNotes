package com.github.malitsplus.shizurunotes.ui.calendar.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.ui.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ACTION = "com.github.malitsplus.shizurunotes.NOTIFICATION"
    }
    val channelId = "pushN"
    val notificationId = 12345651

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
        when (intent.action) {
            NOTIFICATION_ACTION -> {
                val newIntent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0)
                val builder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.mic_notifications)
                    .setContentTitle("HAHAHAHA")
                    .setContentText("OHOHOH")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                with (NotificationManagerCompat.from(context)) {
                    this.notify(notificationId, builder.build())
                }
            }
            else -> {
                val newIntent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0)
                val builder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.mic_notifications)
                    .setContentTitle("HAHAHAHA")
                    .setContentText("OHOHOH")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                with (NotificationManagerCompat.from(context)) {
                    this.notify(notificationId, builder.build())
                }
            }
        }
    }
}