package com.go.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by eriz on 2018/3/16.
 */
@Entity
@Table(name = "zz_user")
//@SequenceGenerator(name = "sequenceGenerator", sequenceName = "zz_user_sequence")
public class User {

    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;
    
    private String passward;

    public User() {
    }

    public User(String name, String passward) {
        this.name = name;
        this.passward = passward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", passward='" + passward + '\'' +
                '}';
    }
}
