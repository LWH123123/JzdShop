package com.jzd.jzshop.entity;

/**
 * @author LWH
 * @description:
 * @date :2019/12/19 17:44
 */
public class MessageEntity {
    private String age;
    private int select;



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "age='" + age + '\'' +
                ", select=" + select +
                '}';
    }
}
