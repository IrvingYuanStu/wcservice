package com.irving.wcs.mapper;

import com.irving.wcs.dao.domain.User;

/**
 * @Description
 * @Author yuanyc
 * @Date 2018/12/6 7:54 PM
 **/
public interface UserMapper {

    /**
     * @Author yuanyc
     * @Description 创建User到数据库
     * @Date 8:00 PM 2018/12/6
     * @Param [user] 用户对象
     * @Return void
     **/
    void createUser(User user);
}
