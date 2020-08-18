package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/5/14 14:01
 */
public class ExchageGoodsNumEntity {
    /**
     * result : {"status":1,"num":"2","dispatch":"0.00","msg":""}
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
         * status : 1
         * num : 2
         * dispatch : 0.00
         * msg :
         */

        private int status;
        private int num;
        private String dispatch;
        private String msg;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
