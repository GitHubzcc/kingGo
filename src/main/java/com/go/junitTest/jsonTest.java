package com.go.junitTest;

import com.go.entity.User;
import com.go.util.sysUtil.JsonUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eriz on 2018/3/19.
 */
public class jsonTest {

    @Test
    public void json() {
        User user = new User("eriz", "123456");
//        String json = JsonUtils.toJson(user);
//        User json1 = JsonUtils.toObject("name:password",User.class);

//        System.out.println(json);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("alerf", "12");
        map.put("jock", "123");
        map.put("eriz", "1324");
        System.out.println(map.toString());
//        User json1 = JsonUtils.toObject(user.toString(), User.class);


    }
}
