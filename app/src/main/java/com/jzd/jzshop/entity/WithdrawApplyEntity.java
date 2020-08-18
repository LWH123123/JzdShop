package com.jzd.jzshop.entity;

import com.jzd.jzshop.R;

/**
 * @author lwh
 * @description :
 * @date 2020/4/15.
 */
public class WithdrawApplyEntity {
    public ResultBean result;


    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private double money;

        public double getMoney() {
            return money;
        }

        public void setMoney(double monry) {
            this.money = monry;
        }
    }

}
