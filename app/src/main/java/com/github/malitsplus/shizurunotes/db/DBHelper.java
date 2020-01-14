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


    public Cursor getCharaBase(String condition){
        if(!Utils.checkFile(DB_PATH + DB_NAME))
            return null;
        else
            return getReadableDatabase()
                .rawQuery("SELECT ud.unit_id, ud.unit_name, ud.prefab_id, ud.search_area_width, ud.atk_type, up.age, up.height, up.weight, IFNULL(au.unit_name, ud.unit_name) 'actual_name', ud.comment " +
                        "FROM unit_data AS ud " +
                        "LEFT JOIN unit_profile AS up ON ud.unit_id = up.unit_id " +
                        "LEFT JOIN actual_unit_background AS au ON substr(ud.unit_id,1,4) = substr(au.unit_id,1,4) " +
                        "WHERE ud.comment <> '' " + condition, null);
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
