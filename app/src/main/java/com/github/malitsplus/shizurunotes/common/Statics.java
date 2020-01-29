package com.github.malitsplus.shizurunotes.common;

import java.util.ArrayList;
import java.util.List;

public class Statics {
    public final static String API_URL = "https://redive.estertion.win";
    public final static String LAST_VERSION_URL = API_URL + "/last_version_jp.json";
    public final static String ICON_URL = API_URL + "/icon/unit/%d.webp";
    public final static String IMAGE_URL = API_URL + "/card/full/%d.webp";
    public final static String DB_FILE_URL = API_URL + "/db/redive_jp.db.br";

    public final static String FILTER_NULL = "N";
    public final static String FILTER_FORWARD = "F";
    public final static String FILTER_MIDDLE = "M";
    public final static String FILTER_REAR = "R";

    public final static List<String> SKILL_TAGS;
    static {
        SKILL_TAGS = new ArrayList<>();
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("main_skill_1");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
        SKILL_TAGS.add("union_burst");
    }
}
