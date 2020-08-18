package com.jzd.jzshop.entity;

public class CouponDetailEntity {

    //商品总价
    private String total;
    //店铺优惠的金额
    private String coupon;
    //优惠总额
    private String allcoupon;
    //最终价格
    private String money;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getAllcoupon() {
        return allcoupon;
    }

    public void setAllcoupon(String allcoupon) {
        this.allcoupon = allcoupon;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
