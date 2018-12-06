package com.irving.wcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @Description 自定义处理器
 * @Author yuanyc
 * @Date 2018/12/6 11:03 PM
 **/
public class WcsContextLoadListener extends ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(WcsContextLoadListener.class);

    /**
     * 服务器启动时监听执行
     * @Author yuanyc
     * @Date 11:08 PM 2018/12/6
     * @Param
     * @Return org.springframework.web.context.WebApplicationContext
     **/
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {

        //TODO 自动化版本控制
        logger.debug("执行数据库比对更新操作！！！");


        return super.initWebApplicationContext(servletContext);
    }
}
