package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 14:45
 */
public class CommisWithdraRecordEntity {
    /**
     * result : {"total":0,"data":[]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 0
         * data : []
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }
    }

    public static class DataBean {
        /**
         * title :
         * createtime : 2019-12-16 10:24
         * money : 0.01
         * status_str : 未充值
         */
        private String time;
        private double money;
        private int status;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }



    }
}
