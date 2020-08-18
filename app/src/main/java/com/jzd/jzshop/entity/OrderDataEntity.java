package com.jzd.jzshop.entity;

import java.text.DecimalFormat;

public class OrderDataEntity {
    private int couponnumber;
    private String tatol;
    private String freight;
    private double coupon;
    private int goodnumber;
    //商城优惠
    private String shopcoupon;
    private double shopmoney;
    //店铺优惠
    private String storecoupon;
    private double storemoney;

    //店铺优惠券
    private String couponmessage;

    //最终价格
    private String money;

    //秒杀优惠
    private String seckill;

    //促销优惠
    private String promation;

    public String getPromation() {
        return promation;
    }

    public void setPromation(String promation) {
        this.promation = promation;
    }

    public String getCouponmessage() {
        return couponmessage;
    }

    public void setCouponmessage(String couponmessage) {
        this.couponmessage = couponmessage;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }




    public String getStorecoupon() {
        return storecoupon;
    }

    public void setStorecoupon(String storecoupon) {
        this.storecoupon = storecoupon;
    }

    public double getStoremoney() {
        return storemoney;
    }

    public void setStoremoney(double storemoney) {
        this.storemoney = storemoney;
    }

    public String getSeckill() {
        return seckill;
    }

    public void setSeckill(String seckill) {
        this.seckill = seckill;
    }

    public String getShopcoupon() {
        return shopcoupon;
    }

    public void setShopcoupon(String shopcoupon) {
        this.shopcoupon = shopcoupon;
    }
    public double getShopmoney() {
        return shopmoney;
    }
    public void setShopmoney(double shopmoney) {
        this.shopmoney = shopmoney;
    }
    public int getCouponnumber() {
        return couponnumber;
    }

    public void setCouponnumber(int couponnumber) {
        this.couponnumber = couponnumber;
    }

    public String getTatol() {
        double v = Double.parseDouble(tatol);
        DecimalFormat fnums = new DecimalFormat("##0.00");
        String format = fnums.format(v);
        return format;
    }

    public void setTatol(String tatol) {
        this.tatol = tatol;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public int getGoodnumber() {
        return goodnumber;
    }

    public void setGoodnumber(int goodnumber) {
        this.goodnumber = goodnumber;
    }
}
