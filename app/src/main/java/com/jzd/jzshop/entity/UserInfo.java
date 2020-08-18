package com.jzd.jzshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.jzd.jzshop.utils.MoneyFormatUtils;

import java.io.Serializable;

/**
 * @author LWH
 * @description:
 * @date :2019/12/12 9:56
 */
public class UserInfo implements Serializable {
    private String usericon;
    private String nickname;
    private String phone;
    private double points;
    private double money;
    private int isLogin;
    //二期改版新增字段
    private int invit_id;
    private double money2;
    private String recommand_name; //推荐人
    private String level; //会员等级
    private String token; //手动添加token ,解决只能在model 中获取token 问题
    private int dfk_num;
    private int dfh_num;
    private int dshh_num;
    private int thh_num;

    public int getDfk_num() {
        return dfk_num;
    }

    public void setDfk_num(int dfk_num) {
        this.dfk_num = dfk_num;
    }

    public int getDfh_num() {
        return dfh_num;
    }

    public void setDfh_num(int dfh_num) {
        this.dfh_num = dfh_num;
    }

    public int getDshh_num() {
        return dshh_num;
    }

    public void setDshh_num(int dshh_num) {
        this.dshh_num = dshh_num;
    }

    public int getThh_num() {
        return thh_num;
    }

    public void setThh_num(int thh_num) {
        this.thh_num = thh_num;
    }




    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getInvit_id() {
        return invit_id;
    }

    public void setInvit_id(int invit_id) {
        this.invit_id = invit_id;
    }

    public double getMoney2() {
        return money2;
    }

    public void setMoney2(double money2) {
        this.money2 = money2;
    }

    public String getRecommand_name() {
        return recommand_name;
    }

    public void setRecommand_name(String recommand_name) {
        this.recommand_name = recommand_name;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }


    public String getShowPoints(){
        return MoneyFormatUtils.keepTwoDecimalNoDisplay0(points);
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
