package com.jzd.jzshop.entity;

import com.google.gson.Gson;

public class CouponEntity {
    /**
     * coupon_id : imYNXPekC_MXp_fyGYZfTAATt2UiMQ
     * backtype : 1
     * enough : 150.00
     * backmoney : 8.5
     * timestr : 自领取日后30天有效
     * canget : 0
     */
    private String coupon_log_ids;
    private String coupon_id;
    private String backtype;
    private double enough;
    private double backmoney;
    private String timestr;
    private int canget;
    private String merch_name;
    private String merch_id;

    public String getCoupon_log_ids() {
        return coupon_log_ids;
    }

    public void setCoupon_log_ids(String coupon_log_ids) {
        this.coupon_log_ids = coupon_log_ids;
    }

    public String getMerch_id() {
        return merch_id;
    }

    public void setMerch_id(String merch_id) {
        this.merch_id = merch_id;
    }

    public String getMerch_name() {
        return merch_name;
    }

    public void setMerch_name(String merch_name) {
        this.merch_name = merch_name;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getBacktype() {
        return backtype;
    }

    public void setBacktype(String backtype) {
        this.backtype = backtype;
    }

    public double getEnough() {
        return enough;
    }

    public double getEnoughNumber() {
        return Double.valueOf(enough);
    }
    public String getEnoughText() {
         int dEnough = (int) enough;
        return String.valueOf(this.enough).equals("0") ? "不限制" : String.format("满%s即可使用", dEnough);
    }

    public void setEnough(double enough) {
        this.enough = enough;
    }

    public double getBackmoney() {
        return backmoney;
    }

    public String getBackmoneyText() {
        return backmoney+"";
    }
    public String getCouponType(){
        return backtype.equals("0") ? "元优惠券" : "折优惠券";
    }

    public void setBackmoney(double backmoney) {
        this.backmoney = backmoney;
    }

    public String getTimestr() {
        return String.format("有效期：%s", timestr);
    }

    public void setTimestr(String timestr) {
        this.timestr = timestr;
    }

    public int getCanget() {
        return canget;
    }

    public void setCanget(int canget) {
        this.canget = canget;
    }

    /**
     * 转换其他接口中的coupon
     * 因为所有cuopon结构都是一样的 所以可以这样操作
     * @param otherTypeCoupon
     * @return
     */
    public static CouponEntity conversion(Object otherTypeCoupon) {
        String jsonstr = new Gson().toJson(otherTypeCoupon);
        CouponEntity couponEntity = new Gson().fromJson(jsonstr, CouponEntity.class);
        return couponEntity;
    }
}
