package com.github.malitsplus.shizurunotes.common;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class Statics {
    public static final String DB_PATH = "data/data/com.github.malitsplus.shizurunotes/databases/";
    public static final String DB_FILE_COMPRESSED = "redive_jp.db.br";
    public static final String DB_FILE = "redive_jp.db";

    public static final String API_URL = "https://redive.estertion.win";
    public static final String LAST_VERSION_URL = API_URL + "/last_version_jp.json";
    public static final String ICON_URL = API_URL + "/icon/unit/%d.webp";
    public static final String SKILL_ICON_URL = API_URL + "/icon/skill/%d.webp";
    public static final String IMAGE_URL = API_URL + "/card/full/%d.webp";
    public static final String DB_FILE_URL = API_URL + "/db/redive_jp.db.br";

    public static final String APP_URL = "https://raw.githubusercontent.com/MalitsPlus/ShizuruNotes/master";
    public static final String APP_UPDATE_LOG =  APP_URL + "/update_log.json?token=AL7TV25KCB2IJEAR2RYKXR26JKEEI";

    public static final String UNKNOWN_ICON = API_URL + "/icon/equipment/999999.webp";

}
