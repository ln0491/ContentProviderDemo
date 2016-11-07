package com.liu.contentproviderdemo.bean;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class Book {
    public Integer _id;
    public String name;

    @Override
    public String toString() {
        return "Book{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }
}
