package com.github.nyanfantasia.shizurunotes.common

import android.app.Application
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.github.nyanfantasia.shizurunotes.R

class I18N {
    companion object {
        var application: Application? = null
        fun getString(@StringRes resId: Int): String {
            return if (application != null) application!!.getString(
                resId
            ) else ""
        }

        fun getStringArray(@ArrayRes resId: Int): Array<String> {
            return if (application != null) application!!.resources.getStringArray(
                resId
            ) else arrayOf("")
        }

        @JvmStatic
        fun getStringWithSpace(@StringRes resId: Int): String {
            return if (application != null) application!!.getString(
                R.string.space_modifier,
                application!!.getString(resId)
            ) else ""
        }

        @JvmStatic
        fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
            return if (application != null) application!!.getString(
                resId,
                *formatArgs
            ) else ""
        }
    }
}