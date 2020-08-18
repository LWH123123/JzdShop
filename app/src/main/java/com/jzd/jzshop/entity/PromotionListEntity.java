package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description: 推广明细
 * @date :2020/4/16 13:27
 */
public class PromotionListEntity {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * ordersn : ME20200415172431620544
         * createtime : 2020-04-15 17:24:31
         * money : 179.17
         * level : 1
         */

        private String ordersn;
        private String createtime;
        private double money;
        private int level;

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
