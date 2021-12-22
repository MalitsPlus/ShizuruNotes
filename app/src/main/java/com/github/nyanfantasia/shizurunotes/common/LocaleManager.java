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
    //對比完整語言資料的文字格式

    private final SharedPreferences prefs;

    LocaleManager(Context context) {
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context setLocale(Context context) {
        return updateResources(context, getLanguage().toLanguageTag());
    }

    public Locale regionExplicit(String inputL){
        //轉換語言資訊的文字資料為 Android 在地化格式，用於修正 Arrays 的手動切換功能
        Locale outputL;
        if (inputL.equals("zh-CN")|| equals("zh-Hans-CN")|| equals("zh-Hans-HK")|| equals("zh-Hans-MO")){
            outputL = new Locale("zh", "CN");
        }
        else if (inputL.equals("zh-TW") || equals("zh-HK") || equals("zh-MO")|| equals("zh-Hant-TW")|| equals("zh-Hant-HK")|| equals("zh-Hant-MO")){
            outputL = new Locale("zh", "TW");
        }
        else {
            outputL = new Locale(inputL);
        }
        return outputL;
        // 英文韓文會爆炸，不應該處理
    }

    public Context setNewLocale(Context context, String language) {
        return updateResources(context, language);
    }

    public Locale getLanguage() {
        String currentLanguage = prefs.getString(UserSettings.LANGUAGE_KEY, null);
        if(currentLanguage != null)
            return regionExplicit(currentLanguage);

        Locale systemLanguage = Locale.getDefault();
        if(LocaleManager.SUPPORTED_LANGUAGE.contains(systemLanguage.getLanguage())) {
            if (systemLanguage.getLanguage().equals("zh")) {
                if (systemLanguage.toLanguageTag().equals("zh-Hant-TW") || equals("zh-Hant-HK") || equals("zh-Hant-MO")|| equals("zh-TW")|| equals("zh-HK")|| equals("zh-MO")) {
                    persistLanguage("zh-TW");
                }
                else {
                    persistLanguage("zh-CN");
                }
                return regionExplicit(getLanguage().toLanguageTag());
                // 要命，都是 if else
            }
            else {
                persistLanguage(systemLanguage.getLanguage());
                return regionExplicit(systemLanguage.getLanguage());
            }
            // 比對資料庫，擁有紀錄再設定為系統語言
        }
        else {
            persistLanguage(Locale.JAPANESE.getLanguage());
            return Locale.JAPANESE;
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
        Locale locale = regionExplicit(language);
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