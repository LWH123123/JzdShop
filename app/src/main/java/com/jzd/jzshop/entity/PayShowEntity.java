package com.jzd.jzshop.entity;

/**
 * @author LWH
 * @description:
 * @date :2019/12/17 9:20
 */
public class PayShowEntity {
    private int wx=0;
    private int zfb=0;
    private int balance=0;
    private int forother=0;

    public PayShowEntity(int wx, int zfb, int balance, int forother) {
        this.wx = wx;
        this.zfb = zfb;
        this.balance = balance;
        this.forother = forother;
    }


    public PayShowEntity(int wx, int zfb, int balance) {
        this.wx = wx;
        this.zfb = zfb;
        this.balance = balance;
    }

    public PayShowEntity() {
    }

    public int getWx() {
        return wx;
    }

    public void setWx(int wx) {
        this.wx = wx;
    }

    public int getZfb() {
        return zfb;
    }

    public void setZfb(int zfb) {
        this.zfb = zfb;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getForother() {
        return forother;
    }

    public void setForother(int forother) {
        this.forother = forother;
    }

    public void setStatus(){
        setWx(0);
        setZfb(0);
        setBalance(0);
    }

}
