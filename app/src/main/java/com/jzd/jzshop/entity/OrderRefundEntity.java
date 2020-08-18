package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/17 17:23
 */
public class OrderRefundEntity {
    /**
     * result : {"status":1,"refund_content":"","data_refund_type":[{"id":0,"name":"退款(仅退款不退货)"}],"data_reason":["不想要了","卖家缺货","拍错了/订单信息错误","其它"],"refund_info":{"refund_price":"895.60","reason":"","refund_type":0,"content":null,"images":[]}}
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
         * refund_content :
         * data_refund_type : [{"id":0,"name":"退款(仅退款不退货)"}]
         * data_reason : ["不想要了","卖家缺货","拍错了/订单信息错误","其它"]
         * refund_info : {"refund_price":"895.60","reason":"","refund_type":0,"content":null,"images":[]}
         */

        private int status;
        private String refund_content;
        private RefundInfoBean refund_info;
        private List<DataRefundTypeBean> data_refund_type;
        private List<String> data_reason;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefund_content() {
            return refund_content;
        }

        public void setRefund_content(String refund_content) {
            this.refund_content = refund_content;
        }

        public RefundInfoBean getRefund_info() {
            return refund_info;
        }

        public void setRefund_info(RefundInfoBean refund_info) {
            this.refund_info = refund_info;
        }

        public List<DataRefundTypeBean> getData_refund_type() {
            return data_refund_type;
        }

        public void setData_refund_type(List<DataRefundTypeBean> data_refund_type) {
            this.data_refund_type = data_refund_type;
        }

        public List<String> getData_reason() {
            return data_reason;
        }

        public void setData_reason(List<String> data_reason) {
            this.data_reason = data_reason;
        }

        public static class RefundInfoBean {
            /**
             * refund_price : 895.60
             * reason :
             * refund_type : 0
             * content : null
             * images : []
             */

            private String refund_price;
            private String reason;
            private int refund_type;
            private String content;
            private List<String> images;

            public String getRefund_price() {
                return refund_price;
            }

            public void setRefund_price(String refund_price) {
                this.refund_price = refund_price;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getRefund_type() {
                return refund_type;
            }

            public void setRefund_type(int refund_type) {
                this.refund_type = refund_type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }

        public static class DataRefundTypeBean {
            /**
             * id : 0
             * name : 退款(仅退款不退货)
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
