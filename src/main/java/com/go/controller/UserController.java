package com.go.controller;

import com.go.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by eriz on 2018/3/17.
 */
@Controller
public class UserController {

//    @Resource(name = "userServiceImpl")
//    private UserService userService;

    @RequestMapping(value = "index.do",method = RequestMethod.GET)
    public String test(){

//        userService.getName();
        return "index";
    }
}
