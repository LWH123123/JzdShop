package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/3/26 11:38
 */
public class MessageCenterEntity {

    /**
     * result : {"total":2,"total_noread":2,"data":[{"id":"2","title":"订单支付成功","content":"您的订单已经成功支付，我们将尽快为您安排发货。订单编号：ME20200326165338440450","type":"app:order.payed","read":0,"time":"2020-03-26 16:53","oData":{"order_id":"1329"}},{"id":"1","title":"订单支付成功","content":"您的订单已经成功支付，我们将尽快为您安排发货。订单编号：SH20200326163925830622","type":"app:order.payed","read":0,"time":"2020-03-26 16:39","oData":{"order_id":"1328"}}]}
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
         * total : 2
         * total_noread : 2
         * data : [{"id":"2","title":"订单支付成功","content":"您的订单已经成功支付，我们将尽快为您安排发货。订单编号：ME20200326165338440450","type":"app:order.payed","read":0,"time":"2020-03-26 16:53","oData":{"order_id":"1329"}},{"id":"1","title":"订单支付成功","content":"您的订单已经成功支付，我们将尽快为您安排发货。订单编号：SH20200326163925830622","type":"app:order.payed","read":0,"time":"2020-03-26 16:39","oData":{"order_id":"1328"}}]
         */

        private int total;
        private int total_noread;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal_noread() {
            return total_noread;
        }

        public void setTotal_noread(int total_noread) {
            this.total_noread = total_noread;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 2
             * title : 订单支付成功
             * content : 您的订单已经成功支付，我们将尽快为您安排发货。订单编号：ME20200326165338440450
             * type : app:order.payed
             * read : 0
             * time : 2020-03-26 16:53
             * oData : {"order_id":"1329"}
             */

            private String id;
            private String title;
            private String content;
            private String type;
            private int read;
            private String time;
            private ODataBean oData;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getRead() {
                return read;
            }

            public void setRead(int read) {
                this.read = read;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public ODataBean getOData() {
                return oData;
            }

            public void setOData(ODataBean oData) {
                this.oData = oData;
            }

            public static class ODataBean {
                /**
                 * order_id : 1329
                 */

                private String order_id;
                private String refund_id;

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
    }
}
