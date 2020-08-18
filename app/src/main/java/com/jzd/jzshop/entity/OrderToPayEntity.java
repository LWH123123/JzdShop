package com.jzd.jzshop.entity;

import com.google.gson.annotations.SerializedName;

public class OrderToPayEntity {
    /**
     * result : {"pay_type":"wechat","data":{"app_id":"wx19508d5fba6e7502","partner_id":"1431040802","prepay_id":"wx201234269923717fc49993781580985800","package":"Sign=WXPay","noncestr":"zHMrec4ehCE41211I1rmfziA4RvxW9wm","timestamp":"1574224466","sign":"C55F62A26F8E4362D33DCF0119A4671F"}}
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
         * data : {"app_id":"wx19508d5fba6e7502","partner_id":"1431040802","prepay_id":"wx201234269923717fc49993781580985800","package":"Sign=WXPay","noncestr":"zHMrec4ehCE41211I1rmfziA4RvxW9wm","timestamp":"1574224466","sign":"C55F62A26F8E4362D33DCF0119A4671F"}
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
             * prepay_id : wx201234269923717fc49993781580985800
             * package : Sign=WXPay
             * noncestr : zHMrec4ehCE41211I1rmfziA4RvxW9wm
             * timestamp : 1574224466
             * sign : C55F62A26F8E4362D33DCF0119A4671F
             */

            private String app_id;
            private String partner_id;
            private String prepay_id;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private String timestamp;
            private String sign;
            private String return_url; //h5 调原生支付成功后跳转url

            public String getReturn_url() {
                return return_url;
            }

            public void setReturn_url(String return_url) {
                this.return_url = return_url;
            }

            public String getString() {
                return string;
            }

            public void setString(String string) {
                this.string = string;
            }

            //支付保字段
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
        }
    }
}
