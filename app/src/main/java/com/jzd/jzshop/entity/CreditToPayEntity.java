package com.jzd.jzshop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author lwh
 * @description :
 * @date 2020/5/15.
 */
public class CreditToPayEntity {

    /**
     * result : {"pay_type":"wechat","data":{"app_id":"wx19508d5fba6e7502","partner_id":"1431040802","prepay_id":"wx1511192642769777db0d79191172787900","package":"Sign=WXPay","noncestr":"f305c7VzQ8eQ7dO3CvX9eKXez8A08cbk","timestamp":"1589512766","sign":"EFBCB607591C64ABA1946D58458932A0"}}
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
         * data : {"app_id":"wx19508d5fba6e7502","partner_id":"1431040802","prepay_id":"wx1511192642769777db0d79191172787900","package":"Sign=WXPay","noncestr":"f305c7VzQ8eQ7dO3CvX9eKXez8A08cbk","timestamp":"1589512766","sign":"EFBCB607591C64ABA1946D58458932A0"}
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
             * prepay_id : wx1511192642769777db0d79191172787900
             * package : Sign=WXPay
             * noncestr : f305c7VzQ8eQ7dO3CvX9eKXez8A08cbk
             * timestamp : 1589512766
             * sign : EFBCB607591C64ABA1946D58458932A0
             */

            private String app_id;
            private String partner_id;
            private String prepay_id;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private String timestamp;
            private String sign;

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
        }
    }
}
