package com.github.malitsplus.shizurunotes.common;

public class Statics {
    //  API URL
    public static final String API_URL = "https://redive.estertion.win";

    //  database string for use
    public static String DB_FILE_NAME_COMPRESSED = "redive_jp.db.br";
    public static String DB_FILE_NAME = "redive_jp.db";
    public static String LATEST_VERSION_URL = API_URL + "/last_version_jp.json";
    public static String DB_FILE_URL = API_URL + "/db/" + DB_FILE_NAME_COMPRESSED;

    //  JP database
    public static final String DB_FILE_NAME_COMPRESSED_JP = "redive_jp.db.br";
    public static final String DB_FILE_NAME_JP = "redive_jp.db";
    public static final String LATEST_VERSION_URL_JP = API_URL + "/last_version_jp.json";
    public static final String DB_FILE_URL_JP = API_URL + "/db/" + DB_FILE_NAME_COMPRESSED_JP;

    //  CN database
    public static final String DB_FILE_NAME_COMPRESSED_CN = "redive_cn.db.br";
    public static final String DB_FILE_NAME_CN = "redive_cn.db";
    public static final String LATEST_VERSION_URL_CN = API_URL + "/last_version_cn.json";
    public static final String DB_FILE_URL_CN = API_URL + "/db/" + DB_FILE_NAME_COMPRESSED_CN;

    //  Resource URL
    public static final String IMAGE_URL = API_URL + "/card/full/%d.webp@h300";
    public static final String ICON_URL = API_URL + "/icon/unit/%d.webp";
    public static final String SKILL_ICON_URL = API_URL + "/icon/skill/%d.webp";
    public static final String EQUIPMENT_ICON_URL = API_URL + "/icon/equipment/%d.webp";
    public static final String ITEM_ICON_URL = API_URL + "/icon/item/%d.webp";
    public static final String UNKNOWN_ICON = API_URL + "/icon/equipment/999999.webp";

    //  App URL
    public static final String APP_RAW = "https://raw.githubusercontent.com/MalitsPlus/ShizuruNotes/master";
    public static final String APP_UPDATE_LOG = APP_RAW + "/update_log.json";
    public static final String APP_PACKAGE = "https://github.com/MalitsPlus/ShizuruNotes/releases/latest/download/shizurunotes-release.apk";
    public static final String APK_NAME = "shizurunotes-release.apk";
}
