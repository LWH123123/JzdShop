package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/20 17:21
 */
public class MyPagerEntity {

    public MyPagerEntity(String title, List<MyPagerEntity.DataBean> list) {
        this.title = title;
        this.list = list;
    }

    private String title;
    private List<MyPagerEntity.DataBean> list;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MyPagerEntity.DataBean> getList() {
        return list;
    }

    public void setList(List<MyPagerEntity.DataBean> list) {
        this.list = list;
    }

    public static class DataBean {
        private String name;
        private int icon;

        public DataBean(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }

    public static class DataBeanX {

        public DataBeanX(String nickName, String logo, String tuijin, String invitecode, String level, String asset, String balanc, String intrgral) {
            this.nickName = nickName;
            this.logo = logo;
            this.tuijin = tuijin;
            this.invitecode = invitecode;
            this.level = level;
            this.asset = asset;
            this.balanc = balanc;
            this.intrgral = intrgral;
        }

        private String nickName;
        private String logo;
        private String tuijin;
        private String invitecode;
        private String level;
        private String asset;
        private String balanc;
        private String intrgral;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTuijin() {
            return tuijin;
        }

        public void setTuijin(String tuijin) {
            this.tuijin = tuijin;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAsset() {
            return asset;
        }

        public void setAsset(String asset) {
            this.asset = asset;
        }

        public String getBalanc() {
            return balanc;
        }

        public void setBalanc(String balanc) {
            this.balanc = balanc;
        }

        public String getIntrgral() {
            return intrgral;
        }

        public void setIntrgral(String intrgral) {
            this.intrgral = intrgral;
        }
    }

}
