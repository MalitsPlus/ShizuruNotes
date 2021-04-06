package com.github.malitsplus.shizurunotes.common

import android.content.Context
import com.github.malitsplus.shizurunotes.user.UserSettings
import com.github.malitsplus.shizurunotes.utils.LogUtils

class CrashManager(
    private val context: Context,
    private val defaultCrashHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        LogUtils.file(LogUtils.E, e::class.simpleName + ":" + e.message, e.stackTrace)
        UserSettings.get().setAbnormalExit(true)
        Thread.sleep(100)
        defaultCrashHandler?.uncaughtException(t, e)
    }
}