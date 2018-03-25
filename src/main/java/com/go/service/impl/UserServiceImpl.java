package com.go.service.impl;


import com.go.dao.UserDao;
import com.go.entity.User;
import com.go.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by eriz on 2018/3/16.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDaoImpl")
    private UserDao userDao;

    public List<User> getName() {
        return userDao.getName();
    }
}
