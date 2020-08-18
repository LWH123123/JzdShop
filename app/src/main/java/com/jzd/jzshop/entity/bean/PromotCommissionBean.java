package com.jzd.jzshop.entity.bean;


/**
 * @author LXB
 * @description:
 * @date :2020/4/15 9:36
 */
public class PromotCommissionBean {
    private int merchLogon;
    private String merchName;
    private String merchAmount;
    @Override
    public String toString() {
        return "PromotCommissionBean{" +
                "merchLogon=" + merchLogon +
                ", merchName='" + merchName + '\'' +
                ", merchAmount='" + merchAmount + '\'' +
                '}';
    }

    public PromotCommissionBean(int merchLogon, String merchName, String merchAmount) {
        this.merchLogon = merchLogon;
        this.merchName = merchName;
        this.merchAmount = merchAmount;
    }

    public int getMerchLogon() {
        return merchLogon;
    }

    public void setMerchLogon(int merchLogon) {
        this.merchLogon = merchLogon;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public String getMerchAmount() {
        return merchAmount;
    }

    public void setMerchAmount(String merchAmount) {
        this.merchAmount = merchAmount;
    }


}
