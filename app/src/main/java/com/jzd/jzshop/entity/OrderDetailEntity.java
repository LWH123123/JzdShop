package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/13 10:57
 */
public class OrderDetailEntity {

    /**
     * result : {"order_id":"826","order_sn":"SH20191231153723402730","order_status":1,"statusstr":"买家已付款","is_refund":1,"closecomment":0,"iscomment":0,"paytype":21,"is_peerpay":0,"order_price":"0.01","express":{"express_com":"","express_sn":""},"address":{"receive_name":"刘经理","receive_mobile":"15335822257","receive_address":"湖北省黄石市大冶市8都看"},"times":{"create_time":"2019-12-31 15:37:23","pay_time":"2019-12-31 15:37:36","send_time":"","finish_time":""},"calc_data":[{"id":"dispatch","type":"1","price":"0.00"}],"data":[{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"mC99eCUx_SpA_AtXQvWdChcW_nDA_EUinPqcQMg","title":"支付测试使用","spec_title":"","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","price":"0.01","number":"1","is_gift":0}]}]}
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
         * order_id : 826
         * order_sn : SH20191231153723402730
         * order_status : 1
         * statusstr : 买家已付款
         * is_refund : 1
         * closecomment : 0
         * iscomment : 0
         * paytype : 21
         * is_peerpay : 0
         * order_price : 0.01
         * express : {"express_com":"","express_sn":""}
         * address : {"receive_name":"刘经理","receive_mobile":"15335822257","receive_address":"湖北省黄石市大冶市8都看"}
         * times : {"create_time":"2019-12-31 15:37:23","pay_time":"2019-12-31 15:37:36","send_time":"","finish_time":""}
         * calc_data : [{"id":"dispatch","type":"1","price":"0.00"}]
         * data : [{"merch_id":"","merch_name":"HUAWEI尚品","goods":[{"gid":"mC99eCUx_SpA_AtXQvWdChcW_nDA_EUinPqcQMg","title":"支付测试使用","spec_title":"","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","price":"0.01","number":"1","is_gift":0}]}]
         */

        private String order_id;
        private String order_sn;
        private int order_status;
        private String statusstr;
        private int is_refund;
        public int getRefund_id() {
            return refund_id;
        }
        public void setRefund_id(int refund_id) {
            this.refund_id = refund_id;
        }
        private int refund_id;
        private int closecomment;
        private int iscomment;
        private int paytype;
        private int is_peerpay;
        private String order_price;
        private List<ExpressBean> express;
        private AddressBean address;
        private TimesBean times;
        private List<CalcDataBean> calc_data;
        private List<DataBean> data;
        private String refund_reply;// 驳回原因
        private String chat_id;// (客服对话)消息所有者ID

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        public String getRefund_reply() {
            return refund_reply;
        }

        public void setRefund_reply(String refund_reply) {
            this.refund_reply = refund_reply;
        }



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

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getStatusstr() {
            return statusstr;
        }

        public void setStatusstr(String statusstr) {
            this.statusstr = statusstr;
        }

        public int getIs_refund() {
            return is_refund;
        }

        public void setIs_refund(int is_refund) {
            this.is_refund = is_refund;
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

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public int getIs_peerpay() {
            return is_peerpay;
        }

        public void setIs_peerpay(int is_peerpay) {
            this.is_peerpay = is_peerpay;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public List<ExpressBean> getExpress() {
            return express;
        }

        public void setExpress(List<ExpressBean> express) {
            this.express = express;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public TimesBean getTimes() {
            return times;
        }

        public void setTimes(TimesBean times) {
            this.times = times;
        }

        public List<CalcDataBean> getCalc_data() {
            return calc_data;
        }

        public void setCalc_data(List<CalcDataBean> calc_data) {
            this.calc_data = calc_data;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class ExpressBean {
            /**
             * express_com :
             * express_sn :
             */

            private String expresscom;
            private String expresssn;
            private String sendtype;

            public String getExpresscom() {
                return expresscom;
            }

            public void setExpresscom(String expresscom) {
                this.expresscom = expresscom;
            }

            public String getExpresssn() {
                return expresssn;
            }

            public void setExpresssn(String expresssn) {
                this.expresssn = expresssn;
            }

            public String getSendtype() {
                return sendtype;
            }

            public void setSendtype(String sendtype) {
                this.sendtype = sendtype;
            }

            public String getBundle() {
                return bundle;
            }

            public void setBundle(String bundle) {
                this.bundle = bundle;
            }

            private String bundle;


        }

        public static class AddressBean {
            /**
             * receive_name : 刘经理
             * receive_mobile : 15335822257
             * receive_address : 湖北省黄石市大冶市8都看
             */

            private String receive_name;
            private String receive_mobile;
            private String receive_address;

            public String getReceive_name() {
                return receive_name;
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

        public static class TimesBean {
            /**
             * create_time : 2019-12-31 15:37:23
             * pay_time : 2019-12-31 15:37:36
             * send_time :
             * finish_time :
             */

            private String create_time;
            private String pay_time;
            private String send_time;
            private String finish_time;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getSend_time() {
                return send_time;
            }

            public void setSend_time(String send_time) {
                this.send_time = send_time;
            }

            public String getFinish_time() {
                return finish_time;
            }

            public void setFinish_time(String finish_time) {
                this.finish_time = finish_time;
            }
        }

        public static class CalcDataBean {
            /**
             * id : dispatch
             * type : 1
             * price : 0.00
             */

            private String id;
            private String type;
            private double price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }

        public static class DataBean {
            /**
             * merch_id :
             * merch_name : HUAWEI尚品
             * goods : [{"gid":"mC99eCUx_SpA_AtXQvWdChcW_nDA_EUinPqcQMg","title":"支付测试使用","spec_title":"","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","price":"0.01","number":"1","is_gift":0}]
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
                 * gid : mC99eCUx_SpA_AtXQvWdChcW_nDA_EUinPqcQMg
                 * title : 支付测试使用
                 * spec_title :
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png
                 * price : 0.01
                 * number : 1
                 * is_gift : 0
                 */

                private String gid;
                private String title;
                private String spec_title;
                private String thumb;
                private String price;
                private int number;
                private int is_gift;

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

                public String getSpec_title() {
                    return spec_title;
                }

                public void setSpec_title(String spec_title) {
                    this.spec_title = spec_title;
                }

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public int getIs_gift() {
                    return is_gift;
                }

                public void setIs_gift(int is_gift) {
                    this.is_gift = is_gift;
                }
            }
        }
    }
}
