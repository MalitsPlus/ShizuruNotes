package com.github.malitsplus.shizurunotes.common;

public class Statics {
    public static final String API_URL = "https://redive.estertion.win";

    //  JP data base
    public static final String DB_FILE_NAME_COMPRESSED = "redive_jp.db.br";
    public static final String DB_FILE_NAME = "redive_jp.db";
    public static final String LATEST_VERSION_URL = API_URL + "/last_version_jp.json";

    /*  CN data base
        【写给想推出国服版分支的开发者】
        国服数据库说明：
        理论上来说，直接调整这三行就能直接切换到国服分支。
        但就实际测试情况来看，想要切换到国服目前已知的问题有以下几个：
        1、国服没有开放专武，但B站已经将所有角色的专武数据塞进了数据库中，这和日服每开一个专武才加一条记录不同，
        而静流笔记是根据数据库中是否存在专武记录来判断一个角色是否拥有专武，所以会造成角色状态参数错误的添加了
        专武的值。
        2、国服数据库中目前不存在 [enemy_m_parts] 这张表，所以只要进入查看敌人相关数据的fragment就会抛出异常。
        这个问题在国服推出多目标Boss之后应该能够自动解决，但如果依照日服的进度，距离推出多目标Boss还存在不短的
        一段时间。
        3、国服公会战数据表所采用的 [clan_battle_map_data] 是日服目前已废弃的早期版本，而静流笔记只针对当前
        日服的会战数据表 [clan_battle_2_map_data] 进行了解读，这也是为什么静流笔记没有显示2019年4月以前的会
        战数据的原因。这个问题和多目标Boss一样，进度赶上来后会自动解决。
    */

//    public static final String DB_FILE_NAME_COMPRESSED = "redive_cn.db.br";
//    public static final String DB_FILE_NAME = "redive_cn.db";
//    public static final String LATEST_VERSION_URL = API_URL + "/last_version_cn.json";

    public static final String ICON_URL = API_URL + "/icon/unit/%d.webp";
    public static final String SKILL_ICON_URL = API_URL + "/icon/skill/%d.webp";
    public static final String IMAGE_URL = API_URL + "/card/full/%d.webp";
    public static final String EQUIPMENT_ICON_URL = API_URL + "/icon/equipment/%d.webp";
    public static final String DB_FILE_URL = API_URL + "/db/" + DB_FILE_NAME_COMPRESSED;
    public static final String UNKNOWN_ICON = API_URL + "/icon/equipment/999999.webp";

    public static final String APP_RAW = "https://raw.githubusercontent.com/MalitsPlus/ShizuruNotes/master";
    public static final String APP_UPDATE_LOG = APP_RAW + "/update_log.json";
    public static final String APP_PACKAGE = "https://github.com/MalitsPlus/ShizuruNotes/releases/latest/download/shizurunotes-release.apk";
    public static final String APK_NAME = "shizurunotes-release.apk";
}
