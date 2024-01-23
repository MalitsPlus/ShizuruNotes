package com.github.malitsplus.shizurunotes.utils

import android.content.Context
import androidx.annotation.RawRes
import java.io.InputStream

class AssetUtils {
    companion object {
        fun readStringFromRaw(context: Context, @RawRes id : Int): String? {
            var str: String? = null
            try {
                val inputStream: InputStream = context.resources.openRawResource(id)
                str = inputStream.bufferedReader().use { it.readText() }
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
            return str
        }
    }
}