package com.github.malitsplus.shizurunotes.db

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import com.github.malitsplus.shizurunotes.common.Statics
import com.github.malitsplus.shizurunotes.common.Utils
import java.util.*
import kotlin.collections.ArrayList

class DBHelper : SQLiteOpenHelper {

    companion object {
        const val DB_VERSION = 1

        @Volatile
        private lateinit var instance: DBHelper

        fun with(application: Application): DBHelper {
            synchronized (DBHelper::class.java) {
                instance = DBHelper(application)
            }
            return instance
        }

        @JvmStatic
        fun get(): DBHelper = instance
    }

    private constructor(application: Application?) : super(
        application,
        Statics.DB_FILE,
        null,
        DB_VERSION
    )
    private constructor(
        context: Context?,
        name: String?,
        factory: CursorFactory?,
        version: Int
    ) : super(context, name, factory, version)

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        dropAll(db)
        onCreate(db)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> cursor2List(
        cursor: Cursor,
        theClass: Class<*>
    ): List<T>? {
        val result: MutableList<T> = mutableListOf()
        val arrField = theClass.declaredFields
        try {
            while (cursor.moveToNext()) {
                if (cursor.isBeforeFirst) {
                    continue
                }
                val bean = theClass.newInstance()
                for (f in arrField) {
                    val columnName = f.name
                    val columnIdx = cursor.getColumnIndex(columnName)
                    if (columnIdx != -1) {
                        if (!f.isAccessible) {
                            f.isAccessible = true
                        }
                        val type = f.type
                        if (type == Byte::class.javaPrimitiveType) {
                            f[bean] = cursor.getShort(columnIdx).toByte()
                        } else if (type == Short::class.javaPrimitiveType) {
                            f[bean] = cursor.getShort(columnIdx)
                        } else if (type == Int::class.javaPrimitiveType) {
                            f[bean] = cursor.getInt(columnIdx)
                        } else if (type == Long::class.javaPrimitiveType) {
                            f[bean] = cursor.getLong(columnIdx)
                        } else if (type == String::class.java) {
                            f[bean] = cursor.getString(columnIdx)
                        } else if (type == ByteArray::class.java) {
                            f[bean] = cursor.getBlob(columnIdx)
                        } else if (type == Boolean::class.javaPrimitiveType) {
                            f[bean] = cursor.getInt(columnIdx) == 1
                        } else if (type == Float::class.javaPrimitiveType) {
                            f[bean] = cursor.getFloat(columnIdx)
                        } else if (type == Double::class.javaPrimitiveType) {
                            f[bean] = cursor.getDouble(columnIdx)
                        }
                    }
                }
                result.add(bean as T)
            }
        } catch (e: InstantiationException) {
            e.printStackTrace()
            return null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return null
        } finally {
            cursor.close()
        }
        return result
    }

    /***
     * 准备游标
     * @param tableName 表名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValue WHERE [key] IN ([keyValue])
     * @return 存有数据的游标
     */
    @SuppressLint("Recycle")
    private fun prepareCursor(
        tableName: String,
        key: String?,
        keyValue: List<String>?
    ): Cursor? {
        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
        val db = readableDatabase ?: return null
        return if (key == null || keyValue == null || keyValue.isEmpty()) {
            db.rawQuery("SELECT * FROM $tableName ", null)
        } else {
            val paraBuilder = StringBuilder()
            paraBuilder.append("(")
            for (s in keyValue) {
                if (!TextUtils.isEmpty(s)) {
                    paraBuilder.append("?")
                    paraBuilder.append(", ")
                }
            }
            paraBuilder.delete(paraBuilder.length - 2, paraBuilder.length)
            paraBuilder.append(")")
            db.rawQuery(
                "SELECT * FROM $tableName WHERE $key IN $paraBuilder ",
                keyValue.toTypedArray()
            )
        }
    }
    /******************* Method For Use  */
    /***
     * 由表名和类名无条件从数据库获取实体列表
     * @param tableName 表名
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
    </T> */
    private fun <T> getBeanList(
        tableName: String,
        theClass: Class<*>
    ): List<T>? {
        val cursor = prepareCursor(tableName, null, null) ?: return null
        return cursor2List(cursor, theClass)
    }

    /***
     * 由表名、类名、条件键值从数据库获取实体列表
     * @param tableName 表名
     * @param theClass 类名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValues WHERE [key] IN ([keyValue])
     * @param <T> theClass的类
     * @return 生成的实体列表
    </T> */
    private fun <T> getBeanList(
        tableName: String,
        theClass: Class<*>,
        key: String?,
        keyValues: List<String>?
    ): List<T>? {
        val cursor = prepareCursor(tableName, key, keyValues) ?: return null
        return cursor2List(cursor, theClass)
    }

    /***
     * 由表名、类名、条件键值从数据库获取单个实体
     * @param tableName 表名
     * @param theClass 类名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValue WHERE [key] IN ([keyValue])
     * @param <T> theClass的类
     * @return 生成的实体
    </T> */
    private fun <T> getBean(
        tableName: String,
        theClass: Class<*>,
        key: String?,
        keyValue: String
    ): T? {
        val cursor =
            prepareCursor(tableName, key, listOf(keyValue)) ?: return null
        val data: List<T>? = cursor2List(cursor, theClass)
        return if (data?.isNotEmpty() == true) data[0] else null
    }

//    /***
//     * 由SQL语句、SQL中的键值从数据库获取单个实体
//     * @param sql SQL语句
//     * @param keyValue IN (?) => ?=keyValue
//     * @param theClass 类名
//     * @param <T> theClass的类
//     * @return 生成的实体
//    </T> */
//    @SuppressLint("Recycle")
//    private fun <T> getBeanByRaw(
//        sql: String?,
//        keyValue: String,
//        theClass: Class<*>
//    ): T? {
//        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
//        val cursor =
//            readableDatabase.rawQuery(sql, arrayOf(keyValue)) ?: return null
//        val data: List<T>? = cursor2List(cursor, theClass)
//        return if (data?.isNotEmpty() == true) data[0] else null
//    }

    /***
     * 由SQL语句、SQL中的键值从数据库获取单个实体
     * @param sql SQL语句
     * @param keyValue IN (?) => ?=keyValue
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体
    </T> */
    @SuppressLint("Recycle")
    private fun <T> getBeanByRaw(
        sql: String?,
        theClass: Class<*>
    ): T? {
        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
        val cursor =
            readableDatabase.rawQuery(sql, null) ?: return null
        val data: List<T>? = cursor2List(cursor, theClass)
        return if (data?.isNotEmpty() == true) data[0] else null
    }

//    /***
//     * 由SQL语句无条件从数据库获取实体列表
//     * @param sql SQL语句
//     * @param theClass 类名
//     * @param <T> theClass的类
//     * @return 生成的实体列表
//    </T> */
//    @SuppressLint("Recycle")
//    private fun <T> getBeanListByRaw(
//        sql: String?,
//        theClass: Class<*>
//    ): List<T>? {
//        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
//        val cursor = readableDatabase.rawQuery(sql, null) ?: return null
//        return cursor2List(cursor, theClass)
//    }

    /***
     * 由SQL语句无条件从数据库获取实体列表
     * @param sql SQL语句
     * @param keyValue 替换？的值
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
    </T> */
    @SuppressLint("Recycle")
    private fun <T> getBeanListByRaw(
        sql: String?,
        theClass: Class<*>
    ): List<T>? {
        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
        val cursor =
            readableDatabase.rawQuery(sql, null) ?: return null
        return cursor2List(cursor, theClass)
    }

    /***
     * 删除所有表
     * @param db
     */
    private fun dropAll(db: SQLiteDatabase) {
        val sqls: MutableList<String> =
            ArrayList()
        val op = "DROP TABLE IF EXISTS "
        for (field in this.javaClass.declaredFields) {
            if (field.name.startsWith("TABLE_NAME")) {
                try {
                    sqls.add(op + field[this])
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        }
        for (sql in sqls) {
            db.execSQL(sql)
        }
    }

    /***
     * 删除表
     * @param tableName
     * @return
     */
    fun dropTable(tableName: String): Boolean {
        return try {
            writableDatabase.execSQL("DROP TABLE IF EXISTS $tableName")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /***
     * 获取查询语句的第一行第一列值
     * @param sql
     * @return
     */
    private fun getOne(sql: String?): String? {
        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
        val cursor = readableDatabase.rawQuery(sql, null)
        cursor.moveToNext()
        val result = cursor.getString(0)
        cursor.close()
        return result
    }

    /***
     * 获取 int-string map
     * @param sql
     * @return
     */
    private fun getIntStringMap(
        sql: String,
        key: String?,
        value: String?
    ): Map<Int, String>? {
        if (!Utils.checkFile(Statics.DB_PATH + Statics.DB_FILE)) return null
        val cursor = readableDatabase.rawQuery(sql, null)
        val result: MutableMap<Int, String> =
            HashMap()
        while (cursor.moveToNext()) {
            result[cursor.getInt(cursor.getColumnIndex(key))] =
                cursor.getString(cursor.getColumnIndex(value))
        }
        cursor.close()
        return result
    }


    /************************* public field **************************/

    /***
     * 获取角色基础数据
     */
    fun getCharaBase(): List<RawUnitBasic>? {
        return getBeanListByRaw(
            """
                SELECT ud.unit_id
                ,ud.unit_name
                ,ud.kana
                ,ud.prefab_id
                ,ud.move_speed
                ,ud.search_area_width
                ,ud.atk_type
                ,ud.normal_atk_cast_time
                ,ud.guild_id
                ,ud.comment
                ,ud.start_time
                ,up.age
                ,up.guild
                ,up.race
                ,up.height
                ,up.weight
                ,up.birth_month
                ,up.birth_day
                ,up.blood_type
                ,up.favorite
                ,up.voice
                ,up.catch_copy
                ,up.self_text
                ,IFNULL(au.unit_name, ud.unit_name) 'actual_name' 
                FROM unit_data AS ud 
                LEFT JOIN unit_profile AS up ON ud.unit_id = up.unit_id 
                LEFT JOIN actual_unit_background AS au ON substr(ud.unit_id,1,4) = substr(au.unit_id,1,4) 
                WHERE ud.comment <> '' 
                """,
            RawUnitBasic::class.java
        )
    }

    /***
     * 获取角色星级数据
     */
    fun getUnitRarity(unitId: Int): RawUnitRarity? {
        return getBeanByRaw<RawUnitRarity>(
            """
                SELECT * 
                FROM unit_rarity 
                WHERE unit_id=$unitId 
                ORDER BY rarity DESC 
                """,
            RawUnitRarity::class.java
        )
    }

    /***
     * 获取角色星级数据
     */
    fun getUnitRarityList(unitId: Int): List<RawUnitRarity>? {
        return getBeanListByRaw(
            """
                SELECT * 
                FROM unit_rarity 
                WHERE unit_id=$unitId 
                ORDER BY rarity DESC 
                """,
            RawUnitRarity::class.java
        )
    }

    /***
     * 获取角色剧情数据
     */
    fun getCharaStoryStatus(charaId: Int): List<RawCharaStoryStatus>? {

        return getBeanListByRaw(
            """
                SELECT * 
                FROM chara_story_status 
                WHERE chara_id_1 = $charaId 
                OR chara_id_2 = $charaId 
                OR chara_id_3 = $charaId 
                OR chara_id_4 = $charaId 
                OR chara_id_5 = $charaId 
                OR chara_id_6 = $charaId 
                OR chara_id_7 = $charaId 
                OR chara_id_8 = $charaId 
                OR chara_id_9 = $charaId 
                OR chara_id_10 = $charaId 
                """,
            RawCharaStoryStatus::class.java
        )
    }

    /***
     * 获取角色Rank汇总数据
     * @param unitId 角色id
     * @return
     */
    fun getCharaPromotionStatus(unitId: Int): List<RawPromotionStatus>? {
        return  getBeanListByRaw(
            """
                SELECT * 
                FROM unit_promotion_status 
                WHERE unit_id=$unitId 
                ORDER BY promotion_level DESC 
                """,
            RawPromotionStatus::class.java
        )
    }

    /***
     * 获取角色装备数据
     * @param unitId 角色id
     * @return
     */
    fun getCharaPromotion(unitId: Int): List<RawUnitPromotion>? {
        return getBeanListByRaw(
            """
                SELECT * 
                FROM unit_promotion 
                WHERE unit_id=$unitId
                ORDER BY promotion_level DESC 
                """,
            RawUnitPromotion::class.java
        )
    }

    /***
     * 获取装备数据
     * @param slots 装备ids
     * @return
     */
    fun getEquipments(slots: ArrayList<Int>?): List<RawEquipmentData>? {
        return getBeanListByRaw(
            """
                SELECT 
                a.*
                ,b.max_equipment_enhance_level 
                FROM equipment_data a, 
                ( SELECT promotion_level, max( equipment_enhance_level ) max_equipment_enhance_level FROM equipment_enhance_data GROUP BY promotion_level ) b 
                WHERE a.promotion_level = b.promotion_level 
                AND a.equipment_id IN ( ${Utils.splitIntegerWithComma(slots)} ) 
                """,
            RawEquipmentData::class.java
        )
    }

    /***
     * 获取装备强化数据
     * @param slots 装备ids
     * @return
     */
    fun getEquipmentEnhance(slots: ArrayList<Int>?): List<RawEquipmentEnhanceData>? {
        return getBeanListByRaw(
            """
                SELECT * 
                FROM equipment_enhance_rate 
                WHERE equipment_id IN ( ${Utils.splitIntegerWithComma(slots)} ) 
                """,
            RawEquipmentEnhanceData::class.java
        )
    }

    /***
     * 获取专属装备数据
     * @param unitId 角色id
     * @return
     */
    fun getUniqueEquipment(unitId: Int): RawUniqueEquipmentData? {
        return getBeanByRaw<RawUniqueEquipmentData>(
            """
                SELECT e.* 
                FROM unique_equipment_data AS e 
                JOIN unit_unique_equip AS u ON e.equipment_id=u.equip_id 
                WHERE u.unit_id=$unitId 
                """,
            RawUniqueEquipmentData::class.java
        )
    }

    /***
     * 获取专属装备强化数据
     * @param unitId 角色id
     * @return
     */
    fun getUniqueEquipmentEnhance(unitId: Int): RawUniqueEquipmentEnhanceData? {
        return getBeanByRaw<RawUniqueEquipmentEnhanceData>(
            """
                SELECT e.* 
                FROM unique_equipment_enhance_rate AS e 
                JOIN unit_unique_equip AS u ON e.equipment_id=u.equip_id 
                WHERE u.unit_id=$unitId 
                """,
            RawUniqueEquipmentEnhanceData::class.java
        )
    }

    /***
     * 获取角色技能数据
     * @param unitId
     * @return
     */
    fun getUnitSkillData(unitId: Int): RawUnitSkillData? {
        return getBeanByRaw<RawUnitSkillData>(
            """
                SELECT * 
                FROM unit_skill_data 
                WHERE unit_id=$unitId 
                """,
            RawUnitSkillData::class.java
        )
    }

    /***
     * 获取技能数据
     * @param skillId
     * @return
     */
    fun getSkillData(skillId: Int): RawSkillData? {
        return getBeanByRaw<RawSkillData>(
            """
                SELECT * 
                FROM skill_data 
                WHERE skill_id=$skillId 
                """,
            RawSkillData::class.java
        )
    }

    /***
     * 获取技能动作数据
     * @param actionId
     * @return
     */
    fun getSkillAction(actionId: Int): RawSkillAction? {
        return getBeanByRaw<RawSkillAction>(
            """
                SELECT * 
                FROM skill_action 
                WHERE action_id=$actionId 
                """,
            RawSkillAction::class.java
        )
    }

    /***
     * 获取行动顺序
     * @param unitId
     * @return
     */
    fun getUnitAttackPattern(unitId: Int): List<RawUnitAttackPattern>? {
        return getBeanListByRaw(
        """
            SELECT * 
            FROM unit_attack_pattern 
            WHERE unit_id=$unitId 
            ORDER BY pattern_id 
            """,
            RawUnitAttackPattern::class.java
        )
    }

    /***
     * 获取会战期次
     * @param
     * @return
     */
    fun getClanBattlePeriod(): List<RawClanBattlePeriod>? {
        return getBeanListByRaw(
            """
                SELECT * 
                FROM clan_battle_period 
                WHERE clan_battle_id > 1014 
                ORDER BY clan_battle_id DESC 
                """,
                RawClanBattlePeriod::class.java
        )
    }

    /***
     * 获取会战phase
     * @param
     * @return
     */
    fun getClanBattlePhase(clanBattleId: Int): List<RawClanBattlePhase>? {
        return getBeanListByRaw(
            """
                SELECT DISTINCT 
                phase 
                ,wave_group_id_1 
                ,wave_group_id_2 
                ,wave_group_id_3 
                ,wave_group_id_4 
                ,wave_group_id_5 
                FROM clan_battle_2_map_data WHERE clan_battle_id=$clanBattleId 
                ORDER BY phase DESC 
                """,
            RawClanBattlePhase::class.java
        )
    }

    /***
     * 获取会战phase-wave
     * @param
     * @return
     */
    fun getClanBattleWaveEnemy(waveGroupList: List<Int>): List<RawWaveGroup>? {
        return getBeanListByRaw(
                """
                    SELECT * 
                    FROM wave_group_data 
                    WHERE wave_group_id IN ( %s ) 
                    """.format(waveGroupList.toString()
                        .replace("[", "")
                        .replace("]", "")),
            RawWaveGroup::class.java
        )
    }

    /***
     * 获取会战bossList
     * @param
     * @return
     */
    fun getClanBattleBoss(enemyIdList: List<Int>): List<RawClanBattleBoss>? {
        return getBeanListByRaw(
                """
                    SELECT 
                    a.* 
                    ,b.union_burst 
                    ,b.union_burst_evolution 
                    ,b.main_skill_1 
                    ,b.main_skill_evolution_1 
                    ,b.main_skill_2 
                    ,b.main_skill_evolution_2 
                    ,b.ex_skill_1 
                    ,b.ex_skill_evolution_1 
                    ,b.main_skill_3 
                    ,b.main_skill_4 
                    ,b.main_skill_5 
                    ,b.main_skill_6 
                    ,b.main_skill_7 
                    ,b.main_skill_8 
                    ,b.main_skill_9 
                    ,b.main_skill_10 
                    ,b.ex_skill_2 
                    ,b.ex_skill_evolution_2 
                    ,b.ex_skill_3 
                    ,b.ex_skill_evolution_3 
                    ,b.ex_skill_4 
                    ,b.ex_skill_evolution_4 
                    ,b.ex_skill_5 
                    ,b.sp_skill_1 
                    ,b.ex_skill_evolution_5 
                    ,b.sp_skill_2 
                    ,b.sp_skill_3 
                    ,b.sp_skill_4 
                    ,b.sp_skill_5 
                    ,c.child_enemy_parameter_1 
                    ,c.child_enemy_parameter_2 
                    ,c.child_enemy_parameter_3 
                    ,c.child_enemy_parameter_4 
                    ,c.child_enemy_parameter_5 
                    ,u.prefab_id 
                    ,u.atk_type 
                    ,u.normal_atk_cast_time
					,u.search_area_width
                    FROM 
                    unit_skill_data b 
                    ,enemy_parameter a 
                    LEFT JOIN enemy_m_parts c ON a.enemy_id = c.enemy_id 
                    LEFT JOIN unit_enemy_data u ON a.unit_id = u.unit_id 
                    WHERE 
                    a.unit_id = b.unit_id 
                    AND a.enemy_id in ( %s )  
                    """.format(enemyIdList.toString()
                        .replace("[", "")
                        .replace("]", "")),
                RawClanBattleBoss::class.java
        )
    }

    /***
     * 获取会战boss
     * @param
     * @return
     */
    fun getClanBattleBoss(enemyId: Int): RawClanBattleBoss? {
        return getClanBattleBoss(listOf(enemyId))?.get(0)
    }

    /***
     * 获取敌人抗性值
     * @param
     * @return
     */
    fun getResistData(resistStatusId: Int): RawResistData? {
        return getBeanByRaw<RawResistData>(
            """
                SELECT * 
                FROM resist_data 
                WHERE resist_status_id=$resistStatusId  
                """,
            RawResistData::class.java
        )
    }

    /***
     * 获取友方召唤物
     */
    fun getUnitMinion(minionId: Int): RawUnitMinion? {
        return getBeanByRaw<RawUnitMinion>(
            """
                SELECT
                a.*,
                b.union_burst,
                b.union_burst_evolution,
                b.main_skill_1,
                b.main_skill_evolution_1,
                b.main_skill_2,
                b.main_skill_evolution_2,
                b.ex_skill_1,
                b.ex_skill_evolution_1,
                b.main_skill_3,
                b.main_skill_4,
                b.main_skill_5,
                b.main_skill_6,
                b.main_skill_7,
                b.main_skill_8,
                b.main_skill_9,
                b.main_skill_10,
                b.ex_skill_2,
                b.ex_skill_evolution_2,
                b.ex_skill_3,
                b.ex_skill_evolution_3,
                b.ex_skill_4,
                b.ex_skill_evolution_4,
                b.ex_skill_5,
                b.sp_skill_1,
                b.ex_skill_evolution_5,
                b.sp_skill_2,
                b.sp_skill_3,
                b.sp_skill_4,
                b.sp_skill_5
            FROM
                unit_skill_data b,
                unit_data a
            WHERE
                a.unit_id = b.unit_id
                AND a.unit_id = $minionId
                """,
            RawUnitMinion::class.java
        )
    }

    /***
     * 获取敌方召唤物
     */
    fun getEnemyMinion(enemyId: Int): RawClanBattleBoss? {
        return getBeanByRaw<RawClanBattleBoss>(
            """
                SELECT
                d.unit_name,
                d.prefab_id,
                d.search_area_width,
                d.atk_type,
                d.move_speed,
                a.*,
                b.*,
                d.normal_atk_cast_time,
                c.child_enemy_parameter_1,
                c.child_enemy_parameter_2,
                c.child_enemy_parameter_3,
                c.child_enemy_parameter_4,
                c.child_enemy_parameter_5
                FROM
                enemy_parameter a
                JOIN unit_skill_data AS b ON a.unit_id = b.unit_id
                JOIN unit_enemy_data AS d ON a.unit_id = d.unit_id
                LEFT JOIN enemy_m_parts c ON a.enemy_id = c.enemy_id
                WHERE a.enemy_id = $enemyId
                """,
            RawClanBattleBoss::class.java
        )
    }

    /***
     * 获取会战bossList
     * @param
     * @return
     */
    fun getDungeons(): List<RawDungeon>? {
        return getBeanListByRaw(
            """
                SELECT
                a.dungeon_area_id,
                a.dungeon_name,
                a.description,
                b.*
                FROM
                dungeon_area_data AS a 
                JOIN wave_group_data AS b ON a.wave_group_id=b.wave_group_id 
                ORDER BY a.dungeon_area_id DESC 
                """,
            RawDungeon::class.java
        )
    }

    /***
     * 获取异常状态map
     * @param
     * @return
     */
    val ailmentMap: Map<Int, String>?
        get() = getIntStringMap(
            "SELECT * FROM ailment_data ",
            "ailment_id",
            "ailment_name"
        )

    val maxCharaLevel: Int
        get() {
            val result = getOne("SELECT max(team_level) FROM experience_team ")
            return result?.toInt() ?: 0
        }

    val maxCharaRank: Int
        get() {
            val result = getOne("SELECT max(promotion_level) FROM unit_promotion ")
            return result?.toInt() ?: 0
        }

    val maxUniqueEquipmentLevel: Int
        get() {
            val result =
                getOne("SELECT max(enhance_level) FROM unique_equipment_enhance_data ")
            return result?.toInt() ?: 0
        }

    /***
     * 随机生成16位随机英数字符串
     * @return
     */
    val randomId: String
        get() {
            val str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            val random = Random()
            val sb = StringBuffer()
            for (i in 0..15) {
                val number = random.nextInt(36)
                sb.append(str[number])
            }
            return sb.toString()
        }


}