package com.github.malitsplus.shizurunotes.common;

import com.github.malitsplus.shizurunotes.BuildConfig;

public class Statics {
    public static final String DB_PATH = "data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    public static final String DB_FILE_COMPRESSED = "redive_jp.db.br";
    public static final String DB_FILE = "redive_jp.db";

    public static final String API_URL = "https://redive.estertion.win";
    public static final String LAST_VERSION_URL = API_URL + "/last_version_jp.json";
    public static final String ICON_URL = API_URL + "/icon/unit/%d.webp";
    public static final String SKILL_ICON_URL = API_URL + "/icon/skill/%d.webp";
    public static final String IMAGE_URL = API_URL + "/card/full/%d.webp";
    public static final String EQUIPMENT_ICON_URL = API_URL + "/icon/equipment/%d.webp";
    public static final String DB_FILE_URL = API_URL + "/db/redive_jp.db.br";
    public static final String UNKNOWN_ICON = API_URL + "/icon/equipment/999999.webp";

    public static final String APP_RAW = "https://raw.githubusercontent.com/MalitsPlus/ShizuruNotes/master";
    public static final String APP_UPDATE_LOG = APP_RAW + "/update_log.json";
    public static final String APP_PACKAGE = "https://github.com/MalitsPlus/ShizuruNotes/releases/latest/download/shizurunotes-release.apk";
    public static final String APK_NAME = "shizurunotes-release.apk";
}
