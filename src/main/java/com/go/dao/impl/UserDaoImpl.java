package com.go.dao.impl;


import com.go.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eriz on 2018/3/16.
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    private Logger log = Logger.getLogger(this.getClass());


    public List getName() {
        String sql = "select * from user ";
        try {
            log.info("开始查询");
//            return runner.query(sql, new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            log.info("查询异常");
            e.printStackTrace();
        }
        return null;
    }
}
