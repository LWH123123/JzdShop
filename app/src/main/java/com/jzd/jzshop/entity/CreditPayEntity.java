package com.jzd.jzshop.entity;

/**
 * @author lwh
 * @description :
 * @date 2020/5/14.
 */
public class CreditPayEntity {

    /**
     * result : {"logno":"EE20200514161410801394","money":"19689.95","price":"699.00"}
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
         * logno : EE20200514161410801394
         * money : 19689.95
         * price : 699.00
         */

        private String logno;
        private String money;
        private String price;

        public String getLogno() {
            return logno;
        }

        public void setLogno(String logno) {
            this.logno = logno;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
