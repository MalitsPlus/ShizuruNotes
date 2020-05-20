package com.github.malitsplus.shizurunotes.ui.setting

import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import com.github.malitsplus.shizurunotes.BuildConfig
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N

class AboutViewModel : ViewModel() {
    val versionText: String = I18N.getString(R.string.about_version).format(BuildConfig.VERSION_NAME)
    val developer: Spanned = HtmlCompat.fromHtml(I18N.getString(R.string.about_developer), HtmlCompat.FROM_HTML_MODE_LEGACY)
    val translator: Spanned = HtmlCompat.fromHtml(I18N.getString(R.string.about_translator), HtmlCompat.FROM_HTML_MODE_LEGACY)
    val license: Spanned = HtmlCompat.fromHtml(I18N.getString(R.string.about_license), HtmlCompat.FROM_HTML_MODE_LEGACY)
}