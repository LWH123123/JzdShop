package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/10 14:30
 */
public class MineOrderEntity {

    /**
     * result : {"total":12,"data":[{"order_id":"815","ordersn":"SH20191227144736808460","price":"0.02","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=815","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Pyd4RaaxwrdSVUrYjXezZqWoGmdMsQMQ","title":"支付测试使用","price":"0.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=DwLN4w8TlpLBAKnaeWeLw2ItbJHqH24hQpahIZiLpwH8w44hTQ","number":"2","spec_title":""}]}]},{"order_id":"791","ordersn":"SH20191227132828780222","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=791","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"IyG4rafxKrTSVUXYlXQzRqzoGmYMuQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=o5k322Ck2Z1CDAkb3UbK2uSu2daodULolSlO2LslaL30ud29bu","number":"1","spec_title":""}]}]},{"order_id":"777","ordersn":"SH20191226202933419610","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=777","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"cyN4MaOxmrvSkUqYJXNzaqmotmtMiQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=fGks1gsqSQkeqkrsCDRQzGDBqBhebCQmQKkc1shSQMHq1qMHk1","number":"1","spec_title":""}]}]},{"order_id":"775","ordersn":"SH20191226202204621406","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=775","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Lys4uahxgrrSgUOYyXRzWqyoYmlMuQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=Awz29HNV2NW3LJP9zJsGxh2Tmu3sIoITg34mLWtxggu22Wg2pW","number":"1","spec_title":""}]}]},{"order_id":"761","ordersn":"SH20191226152116428546","price":"0.01","status":1,"paytype":22,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=761","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Fyt4cagxTrpSJUfYDXezZqooHmDMUQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=x5n64TArrT4NL94ARTPYanadlz99nt91d5y49N0ypY0AAzRD03","number":"1","spec_title":""}]}]},{"order_id":"727","ordersn":"SH20191218162222390028","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=727","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Ayz4MaFxFraSSUCYJXkzfqnocmAMIQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=ACGDkc50pNEs9SfCFoEF5D5ecGp8SPeG7kzP7kF88C9g9fkvnb","number":"1","spec_title":""}]}]},{"order_id":"726","ordersn":"SH20191218162134452444","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=726","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"yin4aK7xrzuSUN2YXc4zqlMomofMQMg","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=N2hcoKvukMZOlKNu55nutMuZvKl2Lo68ATN66VD2Vt2AMO2LKO","number":"1","spec_title":""}]}]},{"order_id":"724","ordersn":"SH20191218161849924182","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=724","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"UyI4zazxKrBSHUnYSXEzAqNoDmWMxQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=E740GA9ML9AXlcXSLlmC0Csl4WA7A3qmwLh4z4y9AS07LQvq3M","number":"1","spec_title":""}]}]},{"order_id":"723","ordersn":"SH20191218161654286444","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=723","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"ryn4NaVxlrcSEUjYEXCzKqsoHmSMYQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=EZB25oOJ99D9019j6SxZm65lFX0x12oSo4le2e9S91F41916LO","number":"1","spec_title":""}]}]},{"order_id":"722","ordersn":"SH20191218161356107258","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=722","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"yqO4aESxrHXSUIpYXRdzqkyomk4MQMg","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=WZ2z7Qqqv569GQ16Q6v2q9Fwi6WV6gWg6W9V02q9v19F986112","number":"1","spec_title":""}]}]},{"order_id":"718","ordersn":"SH20191218161106482842","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=718","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"y4I1TaxrHkUSUYf5GXzqos8omMnLRQMw","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=auLX0rPz0U0Uq7kC2cwr2XU0UL0lMb2u9urUMU9Lrrq2KFxpuU","number":"1","spec_title":""}]}]},{"order_id":"717","ordersn":"SH20191218160703242211","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=717","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"y4FaqaxrXRiSUYx1zXzqF6comMas9QMw","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=rdZHKb9AGGgXhkKR3XhG44z3fxhYhbBL94ayGX7ag4LfGLHh9K","number":"1","spec_title":""}]}]}]}
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
         * total : 12
         * data : [{"order_id":"815","ordersn":"SH20191227144736808460","price":"0.02","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=815","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Pyd4RaaxwrdSVUrYjXezZqWoGmdMsQMQ","title":"支付测试使用","price":"0.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=DwLN4w8TlpLBAKnaeWeLw2ItbJHqH24hQpahIZiLpwH8w44hTQ","number":"2","spec_title":""}]}]},{"order_id":"791","ordersn":"SH20191227132828780222","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=791","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"IyG4rafxKrTSVUXYlXQzRqzoGmYMuQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=o5k322Ck2Z1CDAkb3UbK2uSu2daodULolSlO2LslaL30ud29bu","number":"1","spec_title":""}]}]},{"order_id":"777","ordersn":"SH20191226202933419610","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=777","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"cyN4MaOxmrvSkUqYJXNzaqmotmtMiQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=fGks1gsqSQkeqkrsCDRQzGDBqBhebCQmQKkc1shSQMHq1qMHk1","number":"1","spec_title":""}]}]},{"order_id":"775","ordersn":"SH20191226202204621406","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=775","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Lys4uahxgrrSgUOYyXRzWqyoYmlMuQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=Awz29HNV2NW3LJP9zJsGxh2Tmu3sIoITg34mLWtxggu22Wg2pW","number":"1","spec_title":""}]}]},{"order_id":"761","ordersn":"SH20191226152116428546","price":"0.01","status":1,"paytype":22,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=761","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Fyt4cagxTrpSJUfYDXezZqooHmDMUQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=x5n64TArrT4NL94ARTPYanadlz99nt91d5y49N0ypY0AAzRD03","number":"1","spec_title":""}]}]},{"order_id":"727","ordersn":"SH20191218162222390028","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=727","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Ayz4MaFxFraSSUCYJXkzfqnocmAMIQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=ACGDkc50pNEs9SfCFoEF5D5ecGp8SPeG7kzP7kF88C9g9fkvnb","number":"1","spec_title":""}]}]},{"order_id":"726","ordersn":"SH20191218162134452444","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=726","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"yin4aK7xrzuSUN2YXc4zqlMomofMQMg","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=N2hcoKvukMZOlKNu55nutMuZvKl2Lo68ATN66VD2Vt2AMO2LKO","number":"1","spec_title":""}]}]},{"order_id":"724","ordersn":"SH20191218161849924182","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=724","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"UyI4zazxKrBSHUnYSXEzAqNoDmWMxQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=E740GA9ML9AXlcXSLlmC0Csl4WA7A3qmwLh4z4y9AS07LQvq3M","number":"1","spec_title":""}]}]},{"order_id":"723","ordersn":"SH20191218161654286444","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=723","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"ryn4NaVxlrcSEUjYEXCzKqsoHmSMYQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=EZB25oOJ99D9019j6SxZm65lFX0x12oSo4le2e9S91F41916LO","number":"1","spec_title":""}]}]},{"order_id":"722","ordersn":"SH20191218161356107258","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=722","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"yqO4aESxrHXSUIpYXRdzqkyomk4MQMg","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=WZ2z7Qqqv569GQ16Q6v2q9Fwi6WV6gWg6W9V02q9v19F986112","number":"1","spec_title":""}]}]},{"order_id":"718","ordersn":"SH20191218161106482842","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=718","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"y4I1TaxrHkUSUYf5GXzqos8omMnLRQMw","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=auLX0rPz0U0Uq7kC2cwr2XU0UL0lMb2u9urUMU9Lrrq2KFxpuU","number":"1","spec_title":""}]}]},{"order_id":"717","ordersn":"SH20191218160703242211","price":"0.01","status":1,"paytype":21,"closecomment":0,"iscomment":0,"is_peerpay":0,"order_express":"http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=717","data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"y4FaqaxrXRiSUYx1zXzqF6comMas9QMw","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=rdZHKb9AGGgXhkKR3XhG44z3fxhYhbBL94ayGX7ag4LfGLHh9K","number":"1","spec_title":""}]}]}]
         */

        private int total;
        private List<DataBeanX> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public static class DataBeanX {
            /**
             * order_id : 815
             * ordersn : SH20191227144736808460
             * price : 0.02
             * status : 1
             * paytype : 21
             * closecomment : 0
             * iscomment : 0
             * is_peerpay : 0
             * order_express : http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=order.express&id=815
             * data : [{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"Pyd4RaaxwrdSVUrYjXezZqWoGmdMsQMQ","title":"支付测试使用","price":"0.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=DwLN4w8TlpLBAKnaeWeLw2ItbJHqH24hQpahIZiLpwH8w44hTQ","number":"2","spec_title":""}]}]
             */

            private String order_id;
            private String ordersn;
            private String price;
            private int status;
            private int paytype;
            private int closecomment;
            private String statusstr;
            private int iscomment;
            private int is_peerpay;
            private String order_express;
            private int sendtype;
            private List<DataBean> data;


            public int getSendtype() {
                return sendtype;
            }

            public void setSendtype(int sendtype) {
                this.sendtype = sendtype;
            }

            public String getStatusstr() {
                return statusstr;
            }

            public void setStatusstr(String statusstr) {
                this.statusstr = statusstr;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPaytype() {
                return paytype;
            }

            public void setPaytype(int paytype) {
                this.paytype = paytype;
            }

            public int getClosecomment() {
                return closecomment;
            }

            public void setClosecomment(int closecomment) {
                this.closecomment = closecomment;
            }

            public int getIscomment() {
                return iscomment;
            }

            public void setIscomment(int iscomment) {
                this.iscomment = iscomment;
            }

            public int getIs_peerpay() {
                return is_peerpay;
            }

            public void setIs_peerpay(int is_peerpay) {
                this.is_peerpay = is_peerpay;
            }

            public String getOrder_express() {
                return order_express;
            }

            public void setOrder_express(String order_express) {
                this.order_express = order_express;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * merch_id :
                 * merch_name : HUAWEI尚品
                 * goods : [{"gid":"Pyd4RaaxwrdSVUrYjXezZqWoGmdMsQMQ","title":"支付测试使用","price":"0.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=DwLN4w8TlpLBAKnaeWeLw2ItbJHqH24hQpahIZiLpwH8w44hTQ","number":"2","spec_title":""}]
                 */

                private String merch_id;
                private String merch_name;
                private List<GoodsBean> goods;

                public String getMerch_id() {
                    return merch_id;
                }

                public void setMerch_id(String merch_id) {
                    this.merch_id = merch_id;
                }

                public String getMerch_name() {
                    return merch_name;
                }

                public void setMerch_name(String merch_name) {
                    this.merch_name = merch_name;
                }

                public List<GoodsBean> getGoods() {
                    return goods;
                }

                public void setGoods(List<GoodsBean> goods) {
                    this.goods = goods;
                }

                public static class GoodsBean {
                    /**
                     * gid : Pyd4RaaxwrdSVUrYjXezZqWoGmdMsQMQ
                     * title : 支付测试使用
                     * price : 0.02
                     * thumb : http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png?t=DwLN4w8TlpLBAKnaeWeLw2ItbJHqH24hQpahIZiLpwH8w44hTQ
                     * number : 2
                     * spec_title :
                     */
                    private String orderid;
                    private String allprice;
                    private String gid;
                    private String title;
                    private String price;
                    private String thumb;
                    private String number;
                    private String spec_title;

                    public String getAllprice() {
                        return allprice;
                    }

                    public void setAllprice(String allprice) {
                        this.allprice = allprice;
                    }

                    public String getOrderid() {
                        return orderid;
                    }

                    public void setOrderid(String orderid) {
                        this.orderid = orderid;
                    }

                    public String getGid() {
                        return gid;
                    }

                    public void setGid(String gid) {
                        this.gid = gid;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getThumb() {
                        return thumb;
                    }

                    public void setThumb(String thumb) {
                        this.thumb = thumb;
                    }

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public String getSpec_title() {
                        return spec_title;
                    }

                    public void setSpec_title(String spec_title) {
                        this.spec_title = spec_title;
                    }
                }
            }
        }
    }
}
