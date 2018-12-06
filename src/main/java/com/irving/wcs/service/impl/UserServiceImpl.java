package com.irving.wcs.service.impl;

import com.irving.wcs.common.constants.UserState;
import com.irving.wcs.common.util.UUIDGenerator;
import com.irving.wcs.dao.domain.User;
import com.irving.wcs.mapper.UserMapper;
import com.irving.wcs.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description
 * @Author yuanyc
 * @Date 2018/12/6 7:52 PM
 **/
@Component("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void createUser(String userCode, String userName, String passWord) {
        User user = new User();
        user.setUserId(UUIDGenerator.generateUUID());
        user.setState(UserState.NORMAL.ordinal());
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(passWord);

        userMapper.createUser(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }
}
