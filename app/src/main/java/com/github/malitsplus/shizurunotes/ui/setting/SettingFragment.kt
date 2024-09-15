package com.github.malitsplus.shizurunotes.ui.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.github.malitsplus.shizurunotes.BuildConfig
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.NotificationManager
import com.github.malitsplus.shizurunotes.common.UpdateManager
import com.github.malitsplus.shizurunotes.db.DBHelper
import com.github.malitsplus.shizurunotes.db.MasterEquipment
import com.github.malitsplus.shizurunotes.user.UserSettings
import com.github.malitsplus.shizurunotes.user.UserSettings.Companion.DB_VERSION
import com.github.malitsplus.shizurunotes.utils.FileUtils
import com.jakewharton.processphoenix.ProcessPhoenix
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

class SettingFragment : PreferenceFragmentCompat() {

    companion object {
        const val IMPORT_DB_REQ_CODE = 574423
    }

    override fun onResume() {
        super.onResume()
        findPreference<Preference>(DB_VERSION)?.summary =
            UserSettings.get().getDbVersion().toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMPORT_DB_REQ_CODE
            && resultCode == Activity.RESULT_OK
        ) {
            data?.data?.also { uri ->
                val contentResolver = requireContext().contentResolver
                val destFile = File(FileUtils.getDbFilePath())
                if (destFile.exists()) {
                    DBHelper.get().close()
                    synchronized(DBHelper::class.java) {
                        destFile.delete()
                    }
                }
                contentResolver.openInputStream(uri).use { inputStream ->
                    if (inputStream == null) {
                        throw Error("Cannot open a stream for '$uri'")
                    }
                    FileOutputStream(destFile).use { outputStream ->
                        val buffer = ByteArray(1024)
                        var bytesRead: Int
                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }
                    }
                }
            }
            if (MasterEquipment().getEquipmentMap().size > 1) {
                UpdateManager.get().updateHandler.sendEmptyMessage(UpdateManager.UPDATE_COMPLETED)
            } else {
                UpdateManager.get().updateHandler.sendEmptyMessage(UpdateManager.UPDATE_DOWNLOAD_ERROR)
            }
        }
    }

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        //app版本提示
        findPreference<Preference>(UserSettings.APP_VERSION)?.apply {
            summary = BuildConfig.VERSION_NAME
            isSelectable = false
        }

        //数据库版本
        findPreference<Preference>(DB_VERSION)?.apply {
            isSelectable = false
//            onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                it.isEnabled = false
//                UpdateManager.get().checkDatabaseVersion()
//                thread(start = true) {
//                    Thread.sleep(5000)
//                    activity?.runOnUiThread {
//                        it.isEnabled = true
//                    }
//                }
//                true
//            }
        }

        findPreference<Preference>("importDb")?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "*/*"
                    putExtra(
                        DocumentsContract.EXTRA_INITIAL_URI,
                        requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
                    )
                }
                startActivityForResult(intent, IMPORT_DB_REQ_CODE)
                true
            }
        }

        //重下数据库，暂时停用
//        findPreference<Preference>("reDownloadDb")?.apply {
//            onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                UpdateManager.get().forceDownloadDb()
//                true
//            }
//        }

        //日志
        findPreference<Preference>(UserSettings.LOG)?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val action = SettingContainerFragmentDirections.actionNavSettingContainerToNavLog()
                findNavController().navigate(action)
                true
            }
        }

        //关于
        findPreference<Preference>(UserSettings.ABOUT)?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val action =
                    SettingContainerFragmentDirections.actionNavSettingContainerToNavSettingAbout()
                findNavController().navigate(action)
                true
            }
        }

        //语言选择框
        val languagePreference = findPreference<ListPreference>(UserSettings.LANGUAGE_KEY)
        if (languagePreference != null) {
            languagePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _: Preference?, newValue: Any? ->
                    App.localeManager.setNewLocale(
                        requireActivity().application,
                        newValue as String?
                    )
                    thread(start = true) {
                        Thread.sleep(100)
                        ProcessPhoenix.triggerRebirth(activity)
                    }
                    true
                }
        }

        //服务器选择
        val serverPreference = findPreference<ListPreference>(UserSettings.SERVER_KEY)
        if (serverPreference != null) {
            serverPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    if (!UserSettings.get().getHideServerSwitchHint()) {
                        thread(start = true) {
                            Thread.sleep(100)
                            activity?.runOnUiThread {
                                MaterialDialog(requireContext(), MaterialDialog.DEFAULT_BEHAVIOR)
                                    .title(R.string.dialog_server_switch_title)
                                    .message(R.string.dialog_server_switch_text)
                                    .show {
                                        checkBoxPrompt(R.string.do_not_show_anymore) { checked ->
                                            UserSettings.get().setHideServerSwitchHint(checked)
                                        }
                                        positiveButton(res = R.string.text_ok)
                                    }
                            }
                        }
                    }
                    true
                }
            serverPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, _ ->
                    NotificationManager.get().cancelAllAlarm()
                    thread(start = true) {
                        Thread.sleep(100)
                        ProcessPhoenix.triggerRebirth(activity)
                    }
                    true
                }
        }
    }
}