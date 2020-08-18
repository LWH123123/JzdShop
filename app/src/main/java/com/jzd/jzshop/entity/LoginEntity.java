package com.jzd.jzshop.entity;

public class LoginEntity {
    /**
     * result : {"user_token":"WmnXGxkPFMJp"}
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
         * user_token : WmnXGxkPFMJp
         */

        private String user_token;

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }
    }
}
