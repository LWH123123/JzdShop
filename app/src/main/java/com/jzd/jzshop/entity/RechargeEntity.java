package com.jzd.jzshop.entity;

import com.google.gson.annotations.SerializedName;

public class RechargeEntity {

    /**
     * result : {"pay_type":"wechat","data":{"app_id":"wx19508d5fba6e7502","partner_id":"1431040802","prepay_id":"wx111246174948713bee11258d1705506300","package":"Sign=WXPay","noncestr":"Bd4PCQNpC0DdElh0FeCq5cS7eFpsLSq4","timestamp":"1581396377","sign":"4E950505360D766D75E635F6D62412D4","string":"wdawd"}}
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
         * pay_type : wechat
         * data : {"app_id":"wx19508d5fba6e7502","partner_id":"1431040802","prepay_id":"wx111246174948713bee11258d1705506300","package":"Sign=WXPay","noncestr":"Bd4PCQNpC0DdElh0FeCq5cS7eFpsLSq4","timestamp":"1581396377","sign":"4E950505360D766D75E635F6D62412D4","string":"wdawd"}
         */

        private String pay_type;
        private DataBean data;

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * app_id : wx19508d5fba6e7502
             * partner_id : 1431040802
             * prepay_id : wx111246174948713bee11258d1705506300
             * package : Sign=WXPay
             * noncestr : Bd4PCQNpC0DdElh0FeCq5cS7eFpsLSq4
             * timestamp : 1581396377
             * sign : 4E950505360D766D75E635F6D62412D4
             * string : wdawd
             */

            private String app_id;
            private String partner_id;
            private String prepay_id;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private String timestamp;
            private String sign;
            private String string;

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public String getPartner_id() {
                return partner_id;
            }

            public void setPartner_id(String partner_id) {
                this.partner_id = partner_id;
            }

            public String getPrepay_id() {
                return prepay_id;
            }

            public void setPrepay_id(String prepay_id) {
                this.prepay_id = prepay_id;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getString() {
                return string;
            }

            public void setString(String string) {
                this.string = string;
            }
        }
    }
}
