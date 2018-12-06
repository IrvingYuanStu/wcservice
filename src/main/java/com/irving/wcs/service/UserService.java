package com.irving.wcs.service;

import com.irving.wcs.dao.domain.User;

/**
 * @Description
 * @Author yuanyc
 * @Date 2018/12/6 7:00 PM
 **/
public interface UserService {

    /**
     * 创建用户
     * @Author yuanyc
     * @Date 8:04 PM 2018/12/6
     * @Param [userCode, userName, passWord]
     * @Return void
     **/
    void createUser(String userCode, String userName, String passWord);

    /**
     * 根据用户名查找用户
     * @Author yuanyc
     * @Date 8:04 PM 2018/12/6
     * @Param [userName]
     * @Return com.irving.wcs.dao.domain.User
     **/
    User getUserByUserName(String userName);
}
