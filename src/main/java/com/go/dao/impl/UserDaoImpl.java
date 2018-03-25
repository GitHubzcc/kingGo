package com.go.dao.impl;


import com.go.dao.UserDao;
import com.go.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by eriz on 2018/3/16.
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    private Logger log = Logger.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    public List getName() {
        String sql = "select user from User user ";
        try {
            log.info("开始查询");
            entityManager.createQuery(sql, User.class).setFlushMode(FlushModeType.COMMIT).getResultList();
//            return runner.query(sql, new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            log.info("查询异常");
            e.printStackTrace();
        }
        return null;
    }
}
