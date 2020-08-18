package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 17:59
 */
public class SignReceiveEntity {
    /**
     * result : {"msg":"领取成功!连续签到5天获得奖励50积分"}
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
         * msg : 领取成功!连续签到5天获得奖励50积分
         */

        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
