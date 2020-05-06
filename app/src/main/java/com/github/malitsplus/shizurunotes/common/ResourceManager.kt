package com.github.malitsplus.shizurunotes.common

import android.app.Application

class ResourceManager private constructor(
    private val application: Application
) {
    companion object {
        private lateinit var instance: ResourceManager

        fun with (application: Application): ResourceManager {
            instance = ResourceManager(application)
            return instance
        }

        fun get(): ResourceManager {
            return instance
        }
    }

    fun getColor(colorRes: Int): Int {
        return application.getColor(colorRes)
    }
}