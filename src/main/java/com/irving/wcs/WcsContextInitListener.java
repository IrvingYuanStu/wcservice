package com.irving.wcs;

import com.irving.wcs.common.db.SqlScriptExec;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * @Description 初始化监听处理器
 * @Author yuanyc
 * @Date 2018/12/6 11:03 PM
 **/
public class WcsContextInitListener extends ContextLoaderListener {

    private WebApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        SqlScriptExec exec = (SqlScriptExec) applicationContext.getBean("sqlExecutor");

        exec.execSqlFile();
    }
}
