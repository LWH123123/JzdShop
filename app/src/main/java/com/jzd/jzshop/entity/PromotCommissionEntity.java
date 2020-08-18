package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/4/11 17:44
 */
public class PromotCommissionEntity {

    /**
     * result : {"commission":"0.00","myshop":"0.00","dividend":"0.00","merch":"0.00"}
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
         * commission : 0.00
         * myshop : 0.00
         * dividend : 0.00
         * merch : 0.00
         */

        private String commission;
        private String myshop;
        private String dividend;
        private String merch;

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getMyshop() {
            return myshop;
        }

        public void setMyshop(String myshop) {
            this.myshop = myshop;
        }

        public String getDividend() {
            return dividend;
        }

        public void setDividend(String dividend) {
            this.dividend = dividend;
        }

        public String getMerch() {
            return merch;
        }

        public void setMerch(String merch) {
            this.merch = merch;
        }
    }
}
