package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description: 签到
 * @date :2020/4/7 14:47
 */
public class ToSignEntity {

    /**
     * result : {"msg":"签到成功!日常签到+2积分\r\n端午奖励签到+35积分"}
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
         * msg : 签到成功!日常签到+2积分
         端午奖励签到+35积分
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
