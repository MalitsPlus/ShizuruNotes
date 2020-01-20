package com.github.malitsplus.shizurunotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.github.malitsplus.shizurunotes.common.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 10014050;
    private static final String DB_PATH = "data/data/com.github.malitsplus.shizurunotes/databases/";
    private static final String DB_NAME = "redive_jp.db";

    private int serverVersion;
    private Context mContext;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper get(Context context){
        return new DBHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAll(db);
        onCreate(db);
    }

    /***
     * 删除所有表
     * @param db
     */
    private void dropAll(SQLiteDatabase db){
        List<String> SQLs = new ArrayList<>();
        String op = "DROP TABLE IF EXISTS ";
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().startsWith("TABLE_NAME")) {
                try {
                    SQLs.add(op + field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String sql : SQLs) {
            db.execSQL(sql);
        }
    }

    /***
     * 一般查询
     * @param tableName
     * @param columnName
     * @param key (Nullable)
     * @param keyValue (Nullable)
     * @return Map of the result
     */
    public Map<String, String> getData(String tableName, String columnName, @Nullable String key, @Nullable List<String> keyValue) {
        Map<String, String> result = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (db == null){
            return null;
        }
        if (keyValue == null){
            cursor = db.rawQuery("select * from " + tableName, null);
        }else if (keyValue.size() > 0){
            StringBuilder paraBuilder = new StringBuilder();
            paraBuilder.append("(");
            for (String s : keyValue) {
                if (!TextUtils.isEmpty(s)){
                    paraBuilder.append("?");
                    paraBuilder.append(", ");
                }
            }
            paraBuilder.delete(paraBuilder.length() - 2, paraBuilder.length());
            paraBuilder.append(")");
            cursor = db.rawQuery("select * from " + tableName + " where " + key + " in " + paraBuilder.toString(), keyValue.toArray(new String[keyValue.size()]));
        } else {
            return result;
        }
        while (cursor.moveToNext()) {
            //result.put(cursor.getString(cursor.getColumnIndex(key)), cursor.getString(cursor.getColumnIndex(columnName)));
            result.put("result3", cursor.getString(3));
        }
        cursor.close();
        return result;
    }

    public Map<String, String> getDataByRawSql(String sql) {
        Map<String, String> result = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (db == null){
            return null;
        }
        if (sql == null){
            return null;
        }

        cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            //result.put(cursor.getString(cursor.getColumnIndex(key)), cursor.getString(cursor.getColumnIndex(columnName)));
            result.put("result3", cursor.getString(3));
        }
        cursor.close();
        return result;
    }

    /***
     * 查询带attachDB的数据
     * @param tableName
     * @param attachDB
     * @param columnName
     * @param key
     * @param keyValue
     * @return
     */
    public Map<String, String> getDataWithAttachTable(String tableName, String attachDB, String columnName, @Nullable String key, @Nullable List<String> keyValue) {
        Map<String, String> result = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (db == null){
            return null;
        }
        if (keyValue == null){
            cursor = db.rawQuery("select * from " + attachDB + "." + tableName, null);
        }else if (keyValue.size() > 0){
            StringBuilder paraBuilder = new StringBuilder();
            paraBuilder.append("(");
            for (String s : keyValue) {
                if (!TextUtils.isEmpty(s)){
                    paraBuilder.append("?");
                    paraBuilder.append(", ");
                }
            }
            paraBuilder.delete(paraBuilder.length() - 2, paraBuilder.length());
            paraBuilder.append(")");
            cursor = db.rawQuery("select  * from " + tableName + " where " + key + " in " + paraBuilder.toString(), keyValue.toArray(new String[keyValue.size()]));
        } else {
            return result;
        }
        while (cursor.moveToNext()) {
            //result.put(cursor.getString(cursor.getColumnIndex(key)), cursor.getString(cursor.getColumnIndex(columnName)));
            result.put("result3", cursor.getString(3));
        }
        cursor.close();
        return result;
    }

    /***
     * 删除表
     * @param tableName
     * @return
     */
    public boolean dropTable(String tableName){
        try {
            getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + tableName);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /***
     * 获取查询语句的第一行第一列值
     * @param sql
     * @return
     */
    public String getOne(String sql){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToNext();
        String result = cursor.getString(0);
        cursor.close();
        return result;
    }

    /***
     * 获取角色基础数据
     * @param condition 排序规则
     * @return 角色数据游标
     */
    public Cursor getCharaBase(String condition){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT ud.unit_id, ud.unit_name, ud.prefab_id, ud.search_area_width, ud.atk_type, up.age, up.guild, up.race, up.height, up.weight, up.birth_month, up.birth_day, up.blood_type, up.favorite, up.voice, up.catch_copy, IFNULL(au.unit_name, ud.unit_name) 'actual_name' " +
                        "FROM unit_data AS ud " +
                        "LEFT JOIN unit_profile AS up ON ud.unit_id = up.unit_id " +
                        "LEFT JOIN actual_unit_background AS au ON substr(ud.unit_id,1,4) = substr(au.unit_id,1,4) " +
                        "WHERE ud.comment <> '' " + condition, null);
    }

    /***
     * 获取角色星级数据
     * @param unitId 角色id
     * @return
     */
    public Cursor getCharaRarity(int unitId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM unit_rarity WHERE unit_id=? ORDER BY rarity DESC ", new String[] {String.valueOf(unitId)});
    }

    /***
     * 获取角色剧情数据
     * @param charaId 角色id的前4位
     * @return
     */
    public Cursor getCharaStoryStatus(int charaId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        String mCharaId = String.valueOf(charaId);
        return getReadableDatabase()
                .rawQuery("SELECT * FROM chara_story_status WHERE chara_id_1 = ? OR chara_id_2 = ? OR chara_id_3 = ? OR chara_id_4 = ? OR chara_id_5 = ? OR chara_id_6 = ? ", new String[] {mCharaId});
    }

    /***
     * 获取角色Rank汇总数据
     * @param unitId 角色id
     * @return
     */
    public Cursor getCharaPromotionStatus(int unitId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM unit_promotion_status WHERE unit_id=? ORDER BY promotion_level DESC ", new String[] {String.valueOf(unitId)});
    }

    /***
     * 获取角色装备数据
     * @param unitId 角色id
     * @return
     */
    public Cursor getCharaEquipmentSlots(int unitId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM unit_promotion WHERE unit_id=? ORDER BY promotion_level DESC ", new String[] {String.valueOf(unitId)});
    }

    /***
     * 获取装备数据
     * @param slots 装备ids
     * @return
     */
    public Cursor getEquipments(ArrayList<Integer> slots){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM equipment_data WHERE equipment_id IN (" + Utils.splitIntegerWithComma(slots) + ") ", null);
    }

    /***
     * 获取装备强化数据
     * @param slots 装备ids
     * @return
     */
    public Cursor getEquipmentEnhance(ArrayList<Integer> slots){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM equipment_enhance_rate WHERE equipment_id IN (" + Utils.splitIntegerWithComma(slots) + ") ", null);
    }

    /***
     * 获取专属装备数据
     * @param unitId 角色id
     * @return
     */
    public Cursor getUniqueEquipment(int unitId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT e.* FROM unique_equipment_data AS e JOIN unit_unique_equip AS u ON e.equipment_id=u.equip_id WHERE u.unit_id=? ", new String[]{String.valueOf(unitId)});
    }

    /***
     * 获取专属装备强化数据
     * @param unitId 角色id
     * @return
     */
    public Cursor getUniqueEquipmentEnhance(int unitId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT e.* FROM unique_equipment_enhance_rate AS e JOIN unit_unique_equip AS u ON e.equipment_id=u.equip_id WHERE u.unit_id=? ", new String[]{String.valueOf(unitId)});
    }

    public int getMaxCharaLevel(){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return 0;
        return Integer.parseInt(getOne("SELECT max(team_level) FROM experience_team "));
    }

    public int getMaxCharaRank(){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return 0;
        return Integer.parseInt(getOne("SELECT max(promotion_level) FROM unit_promotion "));
    }

    public int getMaxUniqueEquipmentLevel(){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return 0;
        return Integer.parseInt(getOne("SELECT max(enhance_level) FROM unique_equipment_enhance_data "));
    }

    /***
     * 获取所有角色Id
     * @return
     */
    public ArrayList<Integer> getAllCharaId(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> result = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT unit_id FROM unit_data ORDER BY search_area_width ", null);
        while (cursor.moveToNext()){
            result.add(cursor.getInt(0));
        }
        return result;
    }

    /***
     * 随机生成ID
     * @return
     */
    public String getRandomId(){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 16; i++){
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
