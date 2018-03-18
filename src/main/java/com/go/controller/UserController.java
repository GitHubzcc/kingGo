package com.go.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by eriz on 2018/3/17.
 */
@Controller("userController")
public class UserController {

    //    @Resource(name = "userServiceImpl")
//    private UserService userService;
//    private Logger log = Logger.getLogger(UserController.class);
    protected Logger log = Logger.getLogger(getClass().getName());


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
//        request.setAttribute("key", "key");

        HttpSession session = request.getSession();
        session.setAttribute("key1","key1");
        log.warn("测试logger日志打印结果.........");
        log.debug("===========debug信息===============");
        log.info("===========info信息===============");
        log.error("===========error信息===============");
//        userService.getName();
        return "index";
    }
}
