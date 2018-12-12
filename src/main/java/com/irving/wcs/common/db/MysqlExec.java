package com.irving.wcs.common.db;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

import javax.sql.DataSource;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Description mysql 数据库的执行器
 * @Author yuanyc
 * @Date 2018/12/7 1:39 PM
 **/
public class MysqlExec extends SQLExec {

    /** 数据源 */
    private DataSource dataSource;

    public MysqlExec(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 初始化sql处理器
     * @Author yuanyc
     * @Date 10:42 PM 2018/12/7
     * @Param
     * @Return void
     **/
    public void initExec() {
        this.setEncoding("utf8");
        this.setPrint(false);
        // 设置出错时的处理方式
//        this.setOnerror((SQLExec.OnError) EnumeratedAttribute.
//                getInstance(SQLExec.OnError.class, "continue"));
        this.setProject(new Project());
    }

    /**
     * 获取链接，通过指定的数据源实现
     * 默认c3p0
     * @Author yuanyc
     * @Date 1:44 PM 2018/12/7
     * @Return java.sql.Connection
     **/
    @Override
    protected Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 执行查询，只返回一个值
     * @Author yuanyc
     * @Date 11:00 PM 2018/12/7
     * @Param
     * @Return java.lang.String
     **/
    public String queryString(String sql) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                return rs.getString(1);
            }
            return null;
        } finally {
            if (null != rs) {
                rs.close();
            }
            if (null != stmt) {
                stmt.close();
            }
            if (null != conn) {
                conn.close();
            }
        }
    }

    /**
     * 执行更新sql
     * @Author yuanyc
     * @Date 11:05 PM 2018/12/7
     * @Param
     * @Return int
     **/
    public void execSQL(String[] sqls) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            for (String sql : sqls) {
                stmt.addBatch(sql); // 需要批量执行，否则会语法解析错误
            }
            stmt.executeBatch();
        } finally {
            if (null != stmt) {
                stmt.close();
            }
            if (null != conn) {
                conn.close();
            }
        }
    }

    /**
     * 执行sql文件
     * @Author yuanyc
     * @Date 8:12 PM 2018/12/8
     * @Param
     * @Return void
     **/
    public void executeSqlFile(File sqlFile, String delimiter) {
        this.setSrc(sqlFile);
        this.setEncoding("UTF-8");
        if (StringUtils.isNoneBlank(delimiter)) {
            this.setDelimiter(delimiter);
        }
        this.execute();
    }
}
