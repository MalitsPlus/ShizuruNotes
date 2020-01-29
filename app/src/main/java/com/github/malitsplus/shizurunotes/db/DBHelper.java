package com.github.malitsplus.shizurunotes.db;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.TabHost;

import androidx.annotation.Nullable;

import com.github.malitsplus.shizurunotes.common.Utils;
import com.github.malitsplus.shizurunotes.data.UnitSkillData;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 10014050;
    private static final String DB_PATH = "data/data/com.github.malitsplus.shizurunotes/databases/";
    private static final String DB_NAME = "redive_jp.db";

    private static volatile DBHelper instance;

    private int serverVersion;

    public DBHelper(Application application){
        super(application, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper with(Application application){
        if(instance == null) {
            synchronized (DBHelper.class) {
                instance = new DBHelper(application);
            }
        }
        return instance;
        //return new DBHelper(context);
    }

    public static DBHelper get(){
        if(instance == null){
            throw new NullPointerException("No instance of DBHelper.");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAll(db);
        onCreate(db);
    }

    private static <T> List<T> cursor2List(Cursor cursor, Class theClass) throws IllegalAccessException, InstantiationException {
        List<T> result = new ArrayList<>();

        Field[] arrField = theClass.getDeclaredFields();
        while (cursor.moveToNext()){
            if (cursor.isBeforeFirst()){
                continue;
            }
            Object bean = theClass.newInstance();
            for (Field f : arrField) {
                String columnName = f.getName();
                int columnIdx = cursor.getColumnIndex(columnName);
                if (columnIdx != -1) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    Class<?> type = f.getType();
                    if (type == byte.class) {
                        f.set(bean, (byte) cursor.getShort(columnIdx));
                    } else if (type == short.class) {
                        f.set(bean, cursor.getShort(columnIdx));
                    } else if (type == int.class) {
                        f.set(bean, cursor.getInt(columnIdx));
                    } else if (type == long.class) {
                        f.set(bean, cursor.getLong(columnIdx));
                    } else if (type == String.class) {
                        f.set(bean, cursor.getString(columnIdx));
                    } else if (type == byte[].class) {
                        f.set(bean, cursor.getBlob(columnIdx));
                    } else if (type == boolean.class) {
                        f.set(bean, cursor.getInt(columnIdx) == 1);
                    } else if (type == float.class) {
                        f.set(bean, cursor.getFloat(columnIdx));
                    } else if (type == double.class) {
                        f.set(bean, cursor.getDouble(columnIdx));
                    }
                }
            }
            result.add((T)bean);
        }
        cursor.close();
        return result;
    }

    /***
     * 准备游标
     * @param tableName 表名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValue WHERE [key] IN ([keyValue])
     * @return 存有数据的游标
     */
    private Cursor prepareCursor(String tableName, @Nullable String key, @Nullable List<String> keyValue){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        SQLiteDatabase db = getReadableDatabase();
        if (db == null){
            return null;
        }
        if (key == null || keyValue == null || keyValue.size() == 0){
            return db.rawQuery("SELECT * FROM " + tableName, null);
        } else {
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
            return db.rawQuery("SELECT * FROM " + tableName + " WHERE " + key + " IN " + paraBuilder.toString(), keyValue.toArray(new String[0]));
        }
    }


    /******************* Method For Use ********************/

    /***
     * 由表名和类名[无条件]从数据库获取实体列表
     * @param tableName 表名
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> getBeanList(String tableName, Class theClass) throws InstantiationException, IllegalAccessException {
        Cursor cursor = prepareCursor(tableName, null, null);
        if (cursor == null)
            return null;
        return cursor2List(cursor, theClass);
    }

    /***
     * 由表名、类名、条件键值从数据库获取实体列表
     * @param tableName 表名
     * @param theClass 类名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValues WHERE [key] IN ([keyValue])
     * @param <T> theClass的类
     * @return 生成的实体列表
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> getBeanList(String tableName, Class theClass, String key, List<String> keyValues) throws InstantiationException, IllegalAccessException {
        Cursor cursor = prepareCursor(tableName, key, keyValues);
        if (cursor == null)
            return null;
        return cursor2List(cursor, theClass);
    }

    /***
     * 由表名、类名、条件键值从数据库获取单个实体
     * @param tableName 表名
     * @param theClass 类名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValue WHERE [key] IN ([keyValue])
     * @param <T> theClass的类
     * @return 生成的实体
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> T getBean(String tableName, Class theClass, String key, String keyValue) throws InstantiationException, IllegalAccessException {
        Cursor cursor = prepareCursor(tableName, key, Collections.singletonList(keyValue));
        if (cursor == null)
            return null;
        List<T> data = cursor2List(cursor, theClass);
        if (data != null && data.size() > 0)
            return data.get(0);
        return null;
    }

    /***
     * 由SQL语句、SQL中的键值从数据库获取单个实体
     * @param sql SQL语句
     * @param keyValue IN (?) => ?=keyValue
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> T getBeanByRaw(String sql, String keyValue, Class theClass) throws InstantiationException, IllegalAccessException {
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{keyValue});
        if (cursor == null)
            return null;
        List<T> data = cursor2List(cursor, theClass);
        if (data != null && data.size() > 0)
            return data.get(0);
        return null;
    }

    /***
     * 由SQL语句[无条件]从数据库获取实体列表
     * @param sql SQL语句
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> getBeanListByRaw(String sql, Class theClass) throws InstantiationException, IllegalAccessException {
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor == null)
            return null;
        return cursor2List(cursor, theClass);
    }

    /***
     * 由SQL语句[无条件]从数据库获取实体列表
     * @param sql SQL语句
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> getBeanListByRaw(String sql, String keyValue, Class theClass) throws InstantiationException, IllegalAccessException {
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{keyValue});
        if (cursor == null)
            return null;
        return cursor2List(cursor, theClass);
    }

    /*********************End Method For Use**************************/

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
     * @return 角色数据游标
     */
    public List<RawUnitBasic> getCharaBase(){
        List<RawUnitBasic> rawList;
        try{
            rawList = getBeanListByRaw("SELECT ud.unit_id, ud.unit_name, ud.prefab_id, ud.move_speed, ud.search_area_width, ud.atk_type, ud.normal_atk_cast_time, ud.guild_id, ud.comment, ud.start_time, up.age, up.guild, up.race, up.height, up.weight, up.birth_month, up.birth_day, up.blood_type, up.favorite, up.voice, up.catch_copy, up.self_text, IFNULL(au.unit_name, ud.unit_name) 'actual_name' " +
                    "FROM unit_data AS ud " +
                    "LEFT JOIN unit_profile AS up ON ud.unit_id = up.unit_id " +
                    "LEFT JOIN actual_unit_background AS au ON substr(ud.unit_id,1,4) = substr(au.unit_id,1,4) " +
                    "WHERE ud.comment <> '' ",
                    RawUnitBasic.class);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return rawList;
    }

    /***
     * 获取角色星级数据
     * @param unitId 角色id
     * @return
     */
    public RawUnitRarity getCharaRarity(int unitId){
        RawUnitRarity raw;
        try{
            raw = getBeanByRaw("SELECT * FROM unit_rarity WHERE unit_id=? ORDER BY rarity DESC ",
                    String.valueOf(unitId),
                    RawUnitRarity.class
                    );
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return raw;
    }

    /***
     * 获取角色剧情数据
     * @param charaId 角色id的前4位
     * @return
     */
    public List<RawCharaStoryStatus> getCharaStoryStatus(int charaId){
        List<RawCharaStoryStatus> rawList;
        try{
            rawList = getBeanListByRaw("SELECT * FROM chara_story_status WHERE chara_id_1 = ? OR chara_id_2 = ? OR chara_id_3 = ? OR chara_id_4 = ? OR chara_id_5 = ? OR chara_id_6 = ? OR chara_id_7 = ? OR chara_id_8 = ? OR chara_id_9 = ? OR chara_id_10 = ? ",
                    String.valueOf(charaId),
                    RawCharaStoryStatus.class);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return rawList;
    }

    /***
     * 获取角色Rank汇总数据
     * @param unitId 角色id
     * @return
     */
    public RawPromotionStatus getCharaPromotionStatus(int unitId){
        RawPromotionStatus raw;
        try{
            raw = getBeanByRaw("SELECT * FROM unit_promotion_status WHERE unit_id=? ORDER BY promotion_level DESC ",
                    String.valueOf(unitId),
                    RawPromotionStatus.class
            );
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return raw;
    }

    /***
     * 获取角色装备数据
     * @param unitId 角色id
     * @return
     */
    public RawUnitPromotion getCharaPromotion(int unitId){
        RawUnitPromotion raw;
        try{
            raw = getBeanByRaw("SELECT * FROM unit_promotion WHERE unit_id=? ORDER BY promotion_level DESC ",
                    String.valueOf(unitId),
                    RawUnitPromotion.class
            );
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return raw;
    }

    /***
     * 获取装备数据
     * @param slots 装备ids
     * @return
     */
    public List<RawEquipmentData> getEquipments(ArrayList<Integer> slots){
        List<RawEquipmentData> rawList;
        try{
            rawList = getBeanListByRaw("SELECT a.*,b.max_equipment_enhance_level FROM equipment_data a, ( SELECT promotion_level, max( equipment_enhance_level ) max_equipment_enhance_level FROM equipment_enhance_data GROUP BY promotion_level ) b " +
                            "WHERE a.promotion_level = b.promotion_level AND a.equipment_id IN (" + Utils.splitIntegerWithComma(slots) + ") ",
                    RawEquipmentData.class);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return rawList;
    }

    /***
     * 获取装备强化数据
     * @param slots 装备ids
     * @return
     */
    public List<RawEquipmentEnhanceData> getEquipmentEnhance(ArrayList<Integer> slots){
        List<RawEquipmentEnhanceData> rawList;
        try{
            rawList = getBeanListByRaw("SELECT * FROM equipment_enhance_rate WHERE equipment_id IN (" + Utils.splitIntegerWithComma(slots) + ") ",
                    RawEquipmentEnhanceData.class);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return rawList;
    }

    /***
     * 获取专属装备数据
     * @param unitId 角色id
     * @return
     */
    public RawUniqueEquipmentData getUniqueEquipment(int unitId){
        RawUniqueEquipmentData raw;
        try{
            raw = getBeanByRaw("SELECT e.* FROM unique_equipment_data AS e JOIN unit_unique_equip AS u ON e.equipment_id=u.equip_id WHERE u.unit_id=? ",
                    String.valueOf(unitId),
                    RawUniqueEquipmentData.class
            );
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return raw;
    }

    /***
     * 获取专属装备强化数据
     * @param unitId 角色id
     * @return
     */
    public RawUniqueEquipmentEnhanceData getUniqueEquipmentEnhance(int unitId){
        RawUniqueEquipmentEnhanceData raw;
        try{
            raw = getBeanByRaw("SELECT e.* FROM unique_equipment_enhance_rate AS e JOIN unit_unique_equip AS u ON e.equipment_id=u.equip_id WHERE u.unit_id=? ",
                    String.valueOf(unitId),
                    RawUniqueEquipmentEnhanceData.class
            );
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
        return raw;
    }

    /***
     * 获取角色技能数据
     * @param unitId
     * @return
     */
    public UnitSkillData getCharaUnitSkillData(int unitId){
        UnitSkillData result;
        try{
            result = DBHelper.get().getBean("unit_skill_data", UnitSkillData.class, "unit_id", String.valueOf(unitId));
        } catch (InstantiationException | IllegalAccessException e){
            result = null;
        }
        return result;
    }

    /***
     * 获取角色技能数据
     * @param unitId
     * @return
     */
    public Cursor getCharaSkills(int unitId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM unit_skill_data WHERE unit_id=? ", new String[]{String.valueOf(unitId)});
    }

    /***
     * 获取技能数据
     * @param skillId
     * @return
     */
    public Cursor getSkill(int skillId){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        return getReadableDatabase()
                .rawQuery("SELECT * FROM skill_data WHERE skill_id=? ", new String[]{String.valueOf(skillId)});
    }

    public int getMaxCharaLevel(){
        String result = getOne("SELECT max(team_level) FROM experience_team ");
        return result == null ? 0 : Integer.parseInt(result);
    }

    public int getMaxCharaRank(){
        String result = getOne("SELECT max(promotion_level) FROM unit_promotion ");
        return result == null ? 0 : Integer.parseInt(result);
    }

    public int getMaxUniqueEquipmentLevel(){
        String result = getOne("SELECT max(enhance_level) FROM unique_equipment_enhance_data ");
        return result == null ? 0 : Integer.parseInt(result);
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
