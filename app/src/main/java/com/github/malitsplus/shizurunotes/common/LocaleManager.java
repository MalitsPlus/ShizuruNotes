package com.github.malitsplus.shizurunotes.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import androidx.annotation.RequiresApi;

import com.github.malitsplus.shizurunotes.user.UserSettings;
import com.github.malitsplus.shizurunotes.utils.Utils;

import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

@SuppressWarnings("deprecation")
public class LocaleManager {

    private static List<String> SUPPORTED_LANGUAGE = new ArrayList<>();
    static {
        SUPPORTED_LANGUAGE.add(Locale.JAPANESE.getLanguage());
        SUPPORTED_LANGUAGE.add(Locale.CHINESE.getLanguage());
        SUPPORTED_LANGUAGE.add(Locale.KOREAN.getLanguage());
        SUPPORTED_LANGUAGE.add(Locale.ENGLISH.getLanguage());
    }

    private final SharedPreferences prefs;

    LocaleManager(Context context) {
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context setLocale(Context context) {
        return updateResources(context, getLanguage());
    }

    public Context setNewLocale(Context context, String language) {
        return updateResources(context, language);
    }

    public String getLanguage() {

        String currentLanguage = prefs.getString(UserSettings.LANGUAGE_KEY, null);
        if(currentLanguage != null)
            return currentLanguage;

        String systemLanguage = Locale.getDefault().getLanguage();
        if(LocaleManager.SUPPORTED_LANGUAGE.contains(systemLanguage)) {
            persistLanguage(systemLanguage);
            return systemLanguage;
        }
        else {
            persistLanguage(Locale.JAPANESE.getLanguage());
            return Locale.JAPANESE.getLanguage();
        }
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        prefs.edit().putString(UserSettings.LANGUAGE_KEY, language).commit();
    }

    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        if (Utils.isAtLeastVersion(N)) {
            setLocaleForApi24(config, locale);
            context = context.createConfigurationContext(config);
        } else if (Utils.isAtLeastVersion(JELLY_BEAN_MR1)) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
//            config.locale = locale;
//            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    @RequiresApi(api = N)
    private void setLocaleForApi24(Configuration config, Locale target) {
        Set<Locale> set = new LinkedHashSet<>();
        // bring the target locale to the front of the list
        set.add(target);

        LocaleList all = LocaleList.getDefault();
        for (int i = 0; i < all.size(); i++) {
            // append other locales supported by the user
            set.add(all.get(i));
        }

        Locale[] locales = set.toArray(new Locale[0]);
        config.setLocales(new LocaleList(locales));
    }


    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Utils.isAtLeastVersion(N) ? config.getLocales().get(0) : config.locale;
    }
}