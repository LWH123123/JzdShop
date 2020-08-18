package com.jzd.jzshop.entity;

/**
 * @author lwh
 * @description :
 * @date 2020/5/19.
 */
public class LotteryEntity {


    /**
     * result : {"log_id":"mWlN3uCkrq4CRYRKAxkWWndvmW7MoMg","status":1}
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
         * log_id : mWlN3uCkrq4CRYRKAxkWWndvmW7MoMg
         * status : 1
         */

        private String log_id;
        private int status;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
