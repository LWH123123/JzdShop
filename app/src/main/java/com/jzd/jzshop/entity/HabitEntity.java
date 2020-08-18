package com.jzd.jzshop.entity;

/**
 * @author LWH
 * @description:
 * @date :2019/12/24 11:25
 */
public class HabitEntity {
    private String name;
    private boolean status;

    public HabitEntity(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
