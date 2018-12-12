package com.irving.wcs.common.db;

/**
 * @Description 脚本解析使用的一些常量
 * @Author yuanyc
 * @Date 2018/12/11 3:01 PM
 **/
public class ScriptConstants {

    /** 创建表结构脚本 */
    public static final String CREATE_SQL_NAME = "DBCreate.sql";
    /** 初始化数据脚本 */
    public static final String INIT_SQL_NAME = "DBInit.sql";
    /** 更新脚本 */
    public static final String UPDATE_SQL_NAME = "DBUpdate.sql";

    /** 版本检测格式 */
    public static final String VERSION_FORMAT= "--db-version:";
}
