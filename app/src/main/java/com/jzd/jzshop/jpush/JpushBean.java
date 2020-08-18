package com.jzd.jzshop.jpush;

/**
 * @author LXB
 * @description: Jpush
 *   JPushInterface.EXTRA_EXTRA   服务器推送下来的附加字段
 * @date :2020/3/24 14:26
 */
public class JpushBean {
    /**
     * param1 : app:order.detail
     * param2 : {"order_id":"1305"}
     */

    private String param1;
    private String param2;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public class Param{

        /**
         * order_id : 1316
         */

        private String order_id;
        private String refund_id;//售后申请ID
        // 客户聊天
        private String ltype;//消息操作者的角色
        private String receive_id;//接收方ID
        private String receive_name;//商户名称

        public String getReceive_name() {
            return receive_name;
        }

        public void setReceive_name(String receive_name) {
            this.receive_name = receive_name;
        }

        public String getLtype() {
            return ltype;
        }

        public void setLtype(String ltype) {
            this.ltype = ltype;
        }

        public String getReceive_id() {
            return receive_id;
        }

        public void setReceive_id(String receive_id) {
            this.receive_id = receive_id;
        }

        public String getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }



        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
