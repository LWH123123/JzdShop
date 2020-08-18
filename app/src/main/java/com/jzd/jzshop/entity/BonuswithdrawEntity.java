package com.jzd.jzshop.entity;

public class BonuswithdrawEntity {

    /**
     * result : {"cansettle":0,"commission_total":"0.00","commission_ok":"0.00","commission_check":"0.00","commission_apply":"0.00","commission_fail":"0.00","commission_pay":"0.00","commission_wait":"0.00","desc":"买家确认收货后，立即获得分销佣金\\r\\n注意：可用佣金满%s 后才能申请提现","desc_params":"1元"}
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
         * cansettle : 0
         * commission_total : 0.00
         * commission_ok : 0.00
         * commission_check : 0.00
         * commission_apply : 0.00
         * commission_fail : 0.00
         * commission_pay : 0.00
         * commission_wait : 0.00
         * desc : 买家确认收货后，立即获得分销佣金\r\n注意：可用佣金满%s 后才能申请提现
         * desc_params : 1元
         */

        private int cansettle;
        private String commission_total;
        private String commission_ok;
        private String commission_check;
        private String commission_apply;
        private String commission_fail;
        private String commission_pay;
        private String commission_wait;
        private String desc;
        private String desc_params;

        public int getCansettle() {
            return cansettle;
        }

        public void setCansettle(int cansettle) {
            this.cansettle = cansettle;
        }

        public String getCommission_total() {
            return commission_total;
        }

        public void setCommission_total(String commission_total) {
            this.commission_total = commission_total;
        }

        public String getCommission_ok() {
            return commission_ok;
        }

        public void setCommission_ok(String commission_ok) {
            this.commission_ok = commission_ok;
        }

        public String getCommission_check() {
            return commission_check;
        }

        public void setCommission_check(String commission_check) {
            this.commission_check = commission_check;
        }

        public String getCommission_apply() {
            return commission_apply;
        }

        public void setCommission_apply(String commission_apply) {
            this.commission_apply = commission_apply;
        }

        public String getCommission_fail() {
            return commission_fail;
        }

        public void setCommission_fail(String commission_fail) {
            this.commission_fail = commission_fail;
        }

        public String getCommission_pay() {
            return commission_pay;
        }

        public void setCommission_pay(String commission_pay) {
            this.commission_pay = commission_pay;
        }

        public String getCommission_wait() {
            return commission_wait;
        }

        public void setCommission_wait(String commission_wait) {
            this.commission_wait = commission_wait;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc_params() {
            return desc_params;
        }

        public void setDesc_params(String desc_params) {
            this.desc_params = desc_params;
        }
    }
}
