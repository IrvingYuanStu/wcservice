package com.irving.wcs.dao.impl;

import com.irving.wcs.dao.UserDao;
import com.irving.wcs.dao.domain.User;
import com.irving.wcs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author yuanyc
 * @Date 2018/12/6 7:52 PM
 **/
@Component("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }
}
