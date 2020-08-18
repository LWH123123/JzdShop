package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 17:50
 */
public class LogisticInfoEntity {

    /**
     * result : {"statusstr":3,"expresscom":"圆通速递","expresssn":"YT4321811626161","address":{"receive_name":"刘小","receive_mobile":"18335885678","receive_address":"山东省青岛市胶州市哈哈"},"goods":[{"gid":"yr24aq8xrGxSUtoYXl7zqK2omxdMQMg","title":"支付测试使用","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","spec_title":"","total":1}],"data":[{"time":"2020-01-03 17:10:14","step":"客户签收人: 丰巢签收有问题打180013300318 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18001330318，投诉电话：010-67383552"},{"time":"2020-01-03 16:13:38","step":"快件已由双合家园D区出口丰巢智能柜1号柜丰巢柜代收，取件码已发送，请及时取件。"},{"time":"2020-01-03 14:38:29","step":"【北京市朝阳区欢乐谷公司】 派件中  派件人: 余广兴 电话 18001330318  如有疑问，请联系：010-67383552"},{"time":"2020-01-03 14:29:41","step":"【北京市朝阳区欢乐谷公司】 已收入"},{"time":"2020-01-03 12:22:46","step":"【北京转运中心】 已发出 下一站 【北京市朝阳区欢乐谷】"},{"time":"2020-01-03 12:15:22","step":"【北京转运中心公司】 已收入"},{"time":"2020-01-02 18:54:41","step":"【临沂转运中心】 已发出 下一站 【北京转运中心】"},{"time":"2020-01-02 18:53:15","step":"【临沂转运中心公司】 已收入"},{"time":"2020-01-02 16:40:49","step":"【山东省临沂市】 已发出 下一站 【临沂转运中心】"},{"time":"2020-01-02 16:28:17","step":"【山东省临沂市公司】 已打包"},{"time":"2020-01-02 16:22:11","step":"【山东省临沂市公司】 已收件 取件人: 盛维芳 (15890586231)"}]}
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
         * statusstr : 3
         * expresscom : 圆通速递
         * expresssn : YT4321811626161
         * address : {"receive_name":"刘小","receive_mobile":"18335885678","receive_address":"山东省青岛市胶州市哈哈"}
         * goods : [{"gid":"yr24aq8xrGxSUtoYXl7zqK2omxdMQMg","title":"支付测试使用","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","spec_title":"","total":1}]
         * data : [{"time":"2020-01-03 17:10:14","step":"客户签收人: 丰巢签收有问题打180013300318 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18001330318，投诉电话：010-67383552"},{"time":"2020-01-03 16:13:38","step":"快件已由双合家园D区出口丰巢智能柜1号柜丰巢柜代收，取件码已发送，请及时取件。"},{"time":"2020-01-03 14:38:29","step":"【北京市朝阳区欢乐谷公司】 派件中  派件人: 余广兴 电话 18001330318  如有疑问，请联系：010-67383552"},{"time":"2020-01-03 14:29:41","step":"【北京市朝阳区欢乐谷公司】 已收入"},{"time":"2020-01-03 12:22:46","step":"【北京转运中心】 已发出 下一站 【北京市朝阳区欢乐谷】"},{"time":"2020-01-03 12:15:22","step":"【北京转运中心公司】 已收入"},{"time":"2020-01-02 18:54:41","step":"【临沂转运中心】 已发出 下一站 【北京转运中心】"},{"time":"2020-01-02 18:53:15","step":"【临沂转运中心公司】 已收入"},{"time":"2020-01-02 16:40:49","step":"【山东省临沂市】 已发出 下一站 【临沂转运中心】"},{"time":"2020-01-02 16:28:17","step":"【山东省临沂市公司】 已打包"},{"time":"2020-01-02 16:22:11","step":"【山东省临沂市公司】 已收件 取件人: 盛维芳 (15890586231)"}]
         */

        private int status;
        private String expresscom;
        private String expresssn;
        private AddressBean address;
        private String imaurl;
        private String number;
        private List<GoodsBean> goods;
        private List<DataBean> data;


        public String getImaurl() {
            return imaurl;
        }

        public void setImaurl(String imaurl) {
            this.imaurl = imaurl;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getStatusstr() {
            return status;
        }

        public void setStatusstr(int statusstr) {
            this.status = statusstr;
        }

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

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class AddressBean {
            /**
             * receive_name : 刘小
             * receive_mobile : 18335885678
             * receive_address : 山东省青岛市胶州市哈哈
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

        public static class GoodsBean {
            /**
             * gid : yr24aq8xrGxSUtoYXl7zqK2omxdMQMg
             * title : 支付测试使用
             * thumb : http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png
             * spec_title :
             * total : 1
             */

            private String gid;
            private String title;
            private String thumb;
            private String spec_title;
            private int total;

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

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getSpec_title() {
                return spec_title;
            }

            public void setSpec_title(String spec_title) {
                this.spec_title = spec_title;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class DataBean {
            /**
             * time : 2020-01-03 17:10:14
             * step : 客户签收人: 丰巢签收有问题打180013300318 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18001330318，投诉电话：010-67383552
             */

            private String time;
            private String step;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStep() {
                return step;
            }

            public void setStep(String step) {
                this.step = step;
            }
        }
    }
}
