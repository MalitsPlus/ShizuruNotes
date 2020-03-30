package com.github.malitsplus.shizurunotes.ui.setting

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.malitsplus.shizurunotes.BuildConfig
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.common.UpdateManager
import com.github.malitsplus.shizurunotes.user.UserSettings
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlin.concurrent.thread

class SettingFragment : PreferenceFragmentCompat() {

    companion object{
        const val LANGUAGE_KEY = "language"
    }

    override fun onResume() {
        super.onResume()
        findPreference<Preference>("dbVersion")
            ?.summary = UserSettings.get().preference.getInt("dbVersion", 0).toString()
    }

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        //app版本提示
        findPreference<Preference>("appVersion")?.apply {
            summary = BuildConfig.VERSION_NAME
            isSelectable = false
//            onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                it.isEnabled = false
//                UpdateManager.get().checkAppVersion(false)
//                thread(start = true) {
//                    Thread.sleep(5000)
//                    activity?.runOnUiThread {
//                        it.isEnabled = true
//                    }
//                }
//                true
//            }
        }

        //数据库版本
        findPreference<Preference>("dbVersion")?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                it.isEnabled = false
                UpdateManager.get().checkDatabaseVersion()
                thread(start = true){
                    Thread.sleep(5000)
                    activity?.runOnUiThread {
                        it.isEnabled = true
                    }
                }
                true
            }
        }

        //重下数据库
        findPreference<Preference>("reDownloadDb")?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                it.isEnabled = false
                UpdateManager.get().forceDownloadDb()
//                UpdateManager.get().checkDatabaseVersion(true)
//                thread(start = true){
//                    Thread.sleep(5000)
//                    activity?.runOnUiThread {
//                        it.isEnabled = true
//                    }
//                }
                true
            }
        }

        //日志
        findPreference<Preference>("log")?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val action = SettingContainerFragmentDirections.actionNavSettingContainerToNavLog()
                findNavController().navigate(action)
                true
            }
        }

        //关于
        findPreference<Preference>("about")?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val action = SettingContainerFragmentDirections.actionNavSettingContainerToNavSettingAbout()
                findNavController().navigate(action)
                true
            }
        }

        //语言选择框
        val languagePreference = findPreference<ListPreference>(LANGUAGE_KEY)
        if (languagePreference != null) {
            languagePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _: Preference?, newValue: Any? ->
                    App.localeManager.setNewLocale(
                        requireActivity().application,
                        newValue as String?
                    )
                    thread(start = true){
                        Thread.sleep(100)
                        ProcessPhoenix.triggerRebirth(activity)
                    }
//                    val i = Intent(activity, MainActivity::class.java)
//                    startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                    true
                }
        }


    }
}