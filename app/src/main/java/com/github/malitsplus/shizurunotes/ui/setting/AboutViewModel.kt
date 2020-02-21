package com.github.malitsplus.shizurunotes.ui.setting

import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.BuildConfig
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N

class AboutViewModel : ViewModel() {

    val versionText: String = I18N.getString(R.string.about_version).format(BuildConfig.VERSION_NAME)

}