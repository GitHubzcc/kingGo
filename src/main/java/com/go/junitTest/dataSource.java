package com.go.junitTest;

import com.go.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

public class dataSource {


    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Test
    public void dataSource() {
        System.out.println(userService);
        userService.getName();
    }
}
