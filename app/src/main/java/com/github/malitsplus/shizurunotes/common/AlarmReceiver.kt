package com.github.malitsplus.shizurunotes.common

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.ui.MainActivity
import kotlin.random.Random

const val NOTIFICATION_CHANNEL_DEFAULT = "default"
const val NOTIFICATION_CHANNEL_LOW = "low"

const val NOTIFICATION_ACTION = "com.github.malitsplus.shizurunotes.NOTIFICATION"
const val NOTIFICATION_EXTRA_TYPE = "com.github.malitsplus.shizurunotes.NOTIFICATION_EXTRA"

const val NORMAL_BEFORE = "normal_before"
const val DUNGEON_BEFORE_2 = "dungeon_before_2"
const val DUNGEON_BEFORE = "dungeon_before"
const val HATSUNE_LAST = "hatsune_last"
const val HATSUNE_LAST_HOUR = "hatsune_last_hour"
const val TOWER_LAST_HOUR = "tower_last_hour"

val TYPE_STRING_LIST = listOf(
    NORMAL_BEFORE,
    DUNGEON_BEFORE_2,
    DUNGEON_BEFORE,
    HATSUNE_LAST,
    HATSUNE_LAST_HOUR,
    TOWER_LAST_HOUR
)

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            NOTIFICATION_ACTION -> {
                var title = ""
                var text = ""
                var channelId =
                    NOTIFICATION_CHANNEL_LOW
                when (intent.extras?.getString(NOTIFICATION_EXTRA_TYPE)) {
                    NORMAL_BEFORE -> {
                        title = I18N.getString(R.string.notification_normal_before_title)
                        text = I18N.getString(R.string.notification_normal_before_text)
                    }
                    DUNGEON_BEFORE_2 -> {
                        title = I18N.getString(R.string.notification_dungeon_before_2_title)
                        text = I18N.getString(R.string.notification_dungeon_before_2_text)
                    }
                    DUNGEON_BEFORE -> {
                        title = I18N.getString(R.string.notification_dungeon_before_title)
                        text = I18N.getString(R.string.notification_dungeon_before_text)
                    }
                    HATSUNE_LAST -> {
                        title = I18N.getString(R.string.notification_hatsune_last_title)
                        text = I18N.getString(R.string.notification_hatsune_last_text)
                    }
                    HATSUNE_LAST_HOUR -> {
                        title = I18N.getString(R.string.notification_hatsune_last_hour_title)
                        text = I18N.getString(R.string.notification_hatsune_last_hour_text)
                        channelId =
                            NOTIFICATION_CHANNEL_DEFAULT
                    }
                    TOWER_LAST_HOUR -> {
                        title = I18N.getString(R.string.notification_tower_last_hour_title)
                        text = I18N.getString(R.string.notification_tower_last_hour_text)
                        channelId =
                            NOTIFICATION_CHANNEL_DEFAULT
                    }
                }
                val newIntent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0)
                val builder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.mic_crepe_filled)
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                with (NotificationManagerCompat.from(context)) {
                    //暂时不需要保存ID，随机搞一个吧
                    notify(Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE), builder.build())
                }
            }
        }
    }
}