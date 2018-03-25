package com.go.controller;

import com.go.service.UserService;
import com.go.util.sysUtil.JsonMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eriz on 2018/3/17.
 */
@Controller("userController")
public class UserController {

    @Resource(name = "userServiceImpl")
    private UserService userService;
    private Logger log = Logger.getLogger(getClass().getName());


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        log.info("===========测试filter , 开始缓存session=================");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("username", "eriz");
        return "welcome";
    }


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
//        request.setAttribute("key", "key");

        HttpSession session = request.getSession();
        session.setAttribute("key1", "key1");
        log.warn("测试logger日志打印结果.........");
        log.debug("===========debug信息===============");
        log.info("===========info信息===============");
        log.error("===========error信息===============");
//        userService.getName();
        return "login";
    }


    //返回json数据测试
    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object json() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("eriz", "123");
        map.put("jock", "45");
        map.put("marry", "789");
        return JsonMessage.success(map);
    }
}
