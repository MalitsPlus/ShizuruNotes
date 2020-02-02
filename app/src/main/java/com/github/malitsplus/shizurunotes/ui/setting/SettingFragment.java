package com.github.malitsplus.shizurunotes.ui.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.App;
import com.github.malitsplus.shizurunotes.common.LocaleManager;
import com.github.malitsplus.shizurunotes.ui.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class SettingFragment extends PreferenceFragmentCompat {

    public static final String LANGUAGE_KEY = "language";
    private SettingViewModel settingViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference languagePreference = findPreference(LANGUAGE_KEY);
        if(languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                App.localeManager.setNewLocale(getActivity().getApplication(), (String) newValue);
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                return true;
            });
        }
    }



    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        settingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

     */
}