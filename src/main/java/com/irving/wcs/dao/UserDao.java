package com.irving.wcs.dao;

import com.irving.wcs.dao.domain.User;

/**
 * @Description
 * @Author yuanyc
 * @Date 2018/12/6 7:01 PM
 **/
public interface UserDao {

    /**
     * 创建一个用户
     * @Author yuanyc
     * @Date 3:52 PM 2018/12/11
     * @Param
     * @Return void
     **/
    void createUser(User user);
}
