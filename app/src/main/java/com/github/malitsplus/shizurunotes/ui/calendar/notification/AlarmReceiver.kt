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
//    val normalBefore = "com.github.malitsplus.shizurunotes.normal_before"
//    val channelId = "push notification"
//    val notificationId = 12345651

    override fun onReceive(context: Context, intent: Intent) {
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
//        when (intent.action) {
//            normalBefore -> {
//                val newIntent = Intent(context, MainActivity::class.java)
//                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
//                val builder = NotificationCompat.Builder(context, channelId)
//                    .setSmallIcon(R.drawable.mic_notifications)
//                    .setContentTitle("HAHAHAHA")
//                    .setContentText("OHOHOH")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true)
//                with (NotificationManagerCompat.from(context)) {
//                    this.notify(notificationId, builder.build())
//                }
//            }
//            else -> {  }
//        }
    }
}