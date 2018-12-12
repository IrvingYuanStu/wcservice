package com.irving.wcs.common.db;

import com.irving.wcs.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description sql脚本执行器
 * @Author yuanyc
 * @Date 2018/12/7 9:14 PM
 **/
public class SqlScriptExec {

    private static final Logger logger = LoggerFactory.getLogger(SqlScriptExec.class);

    /** 数据源 */
    private DataSource dataSource;

    /** 具体的sql执行器 */
    private MysqlExec mysqlExec;

    public SqlScriptExec(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execSqlFile() {
        Set<File> versionDirs = this.getVersionDirs();
        if (this.isInitial()) {
            // 初始化数据库
            this.initDB(versionDirs);
        } else {
            // 执行更新逻辑
            this.updateDB(versionDirs);
        }
    }

    /**
     * 是否是初始化安装
     * @Author yuanyc
     * @Date 11:21 PM 2018/12/7
     * @Param
     * @Return boolean
     **/
    private boolean isInitial() {
        try {
            mysqlExec = new MysqlExec(dataSource);
            String tables = mysqlExec.queryString("show tables;");
            if (StringUtils.isBlank(tables)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取所有的版本子目录类, eg. 0.0.1 0.0.2
     * @Author yuanyc
     * @Date 5:24 PM 2018/12/8
     * @Param
     * @Return java.util.Set<java.io.File>
     **/
    private Set<File> getVersionDirs() {
        Set<File> versionDirs = new HashSet<>();
        String sqlFilePath = SqlScriptExec.class.getClassLoader().getResource("scripts/sql").getPath();
        File sqlFileDir = new File(sqlFilePath);
        if (sqlFileDir.isDirectory()) {
            for (File versionDir : sqlFileDir.listFiles()) {
                versionDirs.add(versionDir);
            }
        }
        return versionDirs;
    }

    /**
     * 初始化数据库
     * @Author yuanyc
     * @Date 8:18 PM 2018/12/8
     * @Param
     * @Return void
     **/
    private void initDB(Set<File> versionDirs) {
        for (File versionDir : versionDirs) {
            execFiles(versionDir, ScriptConstants.CREATE_SQL_NAME);
            execFiles(versionDir, ScriptConstants.INIT_SQL_NAME);
        }
    }

    /**
     * 执行更新
     * @Author yuanyc
     * @Date 1:49 PM 2018/12/9
     * @Param
     * @Return void
     **/
    private void updateDB(Set<File> versionDirs) {
        try {
            String currentVersion = mysqlExec.queryString("select db_version from t_version where id = 1;");

            int lastIndex = currentVersion.lastIndexOf(".");
            String mainVersion = currentVersion.substring(0, lastIndex);

            // 大于当前版本的全部执行
            Set<File> temp = new HashSet<>();
            for (File file : versionDirs) {
                String version = file.getName();
                boolean isNewer = compareVersion(currentVersion, version);
                if(isNewer) {
                    logger.debug("version="+version+" is newer than currentVersion="+currentVersion);
                    temp.add(file);
                }
                // 当前版本的update.sql
                if (mainVersion.equals(version)) {
                    String[] sqls = this.loadSql(file, currentVersion);
                    mysqlExec.execSQL(sqls);
                }
            }
            initDB(temp);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行指定文件
     * @Author yuanyc
     * @Date 8:24 PM 2018/12/8
     * @Param
     * @Return void
     **/
    private void execFiles(File versionDir, String fileName) {
        String name = versionDir.getAbsolutePath() + File.separator +fileName;
        File sqlFile = new File(name);
        mysqlExec = new MysqlExec(dataSource);  //无法服用，写一次文件后，会造成链接断开失败
        mysqlExec.initExec();
        mysqlExec.executeSqlFile(sqlFile, ";");
    }

    /**
     * 加载要更新的sql
     * @Author yuanyc
     * @Date 2:35 PM 2018/12/11
     * @Param
     * @Return java.io.File
     **/
    private String[] loadSql(File updateFileDir, String curVersion) {
        String filePath = updateFileDir.getAbsolutePath() + File.separator + ScriptConstants.UPDATE_SQL_NAME;
        StringBuilder sb = new StringBuilder();

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            String line;
            while (null != (line = br.readLine())) {
                if (line.indexOf(ScriptConstants.VERSION_FORMAT) != -1) {
                    String version = line.substring(ScriptConstants.VERSION_FORMAT.length());
                    if (compareVersion(curVersion, version)) {
                        while(null != (line = br.readLine())) {
                            sb.append(line);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String sqlBatch = sb.toString();
        String[] sqls = sqlBatch.split(";");
        return sqls;
    }

    /**
     * v1 是否小于 v2
     * @Author yuanyc
     * @Date 2:57 PM 2018/12/9
     * @Param v1 curVersion v2 dirVersion
     * @Return boolean
     **/
    private boolean compareVersion(String v1, String v2) {
        if (StringUtils.isBlank(v1) || StringUtils.isBlank(v2)) {
            return false;
        }

        int[] v1Arr = StringUtil.toIntArr(v1, "\\.");
        int[] v2Arr = StringUtil.toIntArr(v2, "\\.");
        for (int i=0; i<3; i++) {
            if (v1Arr[i] > v2Arr[i]) {
                return false;
            }
            if (v1Arr[i] < v2Arr[i]) {
                return true;
            }
        }

        // 前3位相等，比较第4位
        if (v1Arr.length < v2Arr.length) {
            return true;
        }
        if (v1Arr.length == 4 && v2Arr.length == 4) {
            if (v1Arr[3] < v2Arr[3]) {
                return true;
            }
        }
        return false;
    }
}
