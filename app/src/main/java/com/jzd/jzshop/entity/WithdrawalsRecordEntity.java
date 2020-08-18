package com.jzd.jzshop.entity;

import java.util.List;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class WithdrawalsRecordEntity {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title :
         * createtime : 2019-12-16 10:24
         * money : 0.01
         * status_str : 未充值
         */

        private String title;
        private String createtime;
        private double money;
        private String status_str;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getStatus_str() {
            return status_str;
        }

        public void setStatus_str(String status_str) {
            this.status_str = status_str;
        }
    }
}
