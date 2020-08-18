package com.jzd.jzshop.entity;

public class OrderPayIndexEntity {
    /**
     * result : {"order_id":"226","order_sn":"SH20191120164835808880","order_price":"0.01","pay_type":"wechat","receive_name":"张三","receive_mobile":"13621119999","receive_address":"河北省石家庄桥西区abc"}
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
         * order_id : 226
         * order_sn : SH20191120164835808880
         * order_price : 0.01
         * pay_type : wechat
         * receive_name : 张三
         * receive_mobile : 13621119999
         * receive_address : 河北省石家庄桥西区abc
         */

        private String order_id;
        private String order_sn;
        private String order_price;
        private String pay_type;
        private String receive_name;
        private String receive_mobile;
        private String receive_address;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_price() {
            return order_price;
        }
        public String getOrder_priceText() {
            return order_price+"元";
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getPay_type() {
            return pay_type;
        }
        public String getPay_typeText() {
            if(pay_type.equals("credit")){
                return "余额支付";
            }
            else if(pay_type.equals("cash")){
                return "货到付款";
            }
            else if(pay_type.equals("wechat")){
                return "微信支付";
            }
            else if(pay_type.equals("alipay")){
                return "支付宝";
            }
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getReceive_name() {
            return receive_name;
        }
        public String getReceive_nameText() {
            return receive_name+" "+receive_mobile;
        }

        public void setReceive_name(String receive_name) {
            this.receive_name = receive_name;
        }

        public String getReceive_mobile() {
            return receive_mobile;
        }

        public void setReceive_mobile(String receive_mobile) {
            this.receive_mobile = receive_mobile;
        }

        public String getReceive_address() {
            return receive_address;
        }

        public void setReceive_address(String receive_address) {
            this.receive_address = receive_address;
        }
    }
}
