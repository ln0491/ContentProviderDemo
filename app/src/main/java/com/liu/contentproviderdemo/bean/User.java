package com.liu.contentproviderdemo.bean;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class User {
    public Integer _id;
    public String name;
    public Integer sex;

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
