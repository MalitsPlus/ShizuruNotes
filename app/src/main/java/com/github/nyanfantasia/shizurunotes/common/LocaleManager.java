package com.github.nyanfantasia.shizurunotes.common;

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

import com.github.nyanfantasia.shizurunotes.user.UserSettings;
import com.github.nyanfantasia.shizurunotes.utils.Utils;

import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

@SuppressWarnings("deprecation")
public class LocaleManager {

    private static final List<String> SUPPORTED_LANGUAGE = new ArrayList<>();
    static {
        SUPPORTED_LANGUAGE.add(Locale.JAPANESE.getLanguage());
        SUPPORTED_LANGUAGE.add(Locale.CHINESE.getLanguage());
        SUPPORTED_LANGUAGE.add(Locale.KOREAN.getLanguage());
        SUPPORTED_LANGUAGE.add(Locale.ENGLISH.getLanguage());
    }
    //已支援語言的資料庫，對比完整語言資料的文字格式(String)

    private final SharedPreferences prefs;

    LocaleManager(Context context) {
        //從 Android Shared preference 取得資料
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context setLocale(Context context) {
        return updateResources(context, getLanguage());
    }
    //設定語言時使用

    public Context setNewLocale(Context context, String language) {
        //用於處理更換新語言
        return updateResources(context, language);
    }

    public String getLanguage() {
        String currentLanguage = prefs.getString(UserSettings.LANGUAGE_KEY, null);
        if(currentLanguage != null)
            return currentLanguage;
        //每次啟動(非首次)應用程式的語言判斷

        Locale systemLanguage = Locale.getDefault();
        if(LocaleManager.SUPPORTED_LANGUAGE.contains(systemLanguage.getLanguage())) {
            if (systemLanguage.getLanguage().equals("zh")) {
                if (!systemLanguage.getScript().equals("")) {
                    persistLanguage(systemLanguage.getLanguage() + "-" + systemLanguage.getScript());
                    //API 21+, but some Android version/edition doesn't report Script for Chinese
                }
                else if (systemLanguage.getCountry().equals("CN")){
                    //yeah, for MIUI and else shit
                    persistLanguage("zh-Hans");
                }
                else {
                    persistLanguage("zh-Hant");
                    //I guess there's no other things like zh-CN for zh-Hans
                }
            }
            else {
                persistLanguage(systemLanguage.getLanguage());
            }
                return getLanguage();
            // 比對資料庫，擁有紀錄再設定為系統語言，此工作為初始化處理程序
        }
        else {
            persistLanguage(Locale.JAPANESE.getLanguage());
            return Locale.JAPANESE.getLanguage();
            // 無匹配資料庫，設定為日文
        }
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        prefs.edit().putString(UserSettings.LANGUAGE_KEY, language).commit();
    }

    private Context updateResources(Context context, String language) {
        Locale locale =new Locale(language);
        if(language.equals("zh-Hant"))
            locale = Locale.TAIWAN;
        else if (language.equals("zh-Hans"))
        locale = Locale.CHINA;
        else
            // Workaround for String and Locale conversion
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