package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/1/7 11:50
 */
public class ItemStyleEntity {
    private String background;
    private String navstyle;

    public ItemStyleEntity(String background, String navstyle) {
        this.background = background;
        this.navstyle = navstyle;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getNavstyle() {
        return navstyle;
    }

    public void setNavstyle(String navstyle) {
        this.navstyle = navstyle;
    }

    @Override
    public String toString() {
        return "ItemStyleEntity{" +
                "background='" + background + '\'' +
                ", navstyle='" + navstyle + '\'' +
                '}';
    }
}
