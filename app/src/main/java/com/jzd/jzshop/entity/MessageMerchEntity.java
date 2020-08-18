package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/2 10:49
 */
public class MessageMerchEntity {

    /**
     * result : {"goods":[{"title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","spec_title":"","price":"799.00","total":1,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg"}],"order_price":"734.00","ordersn":"ME20200402094329706393","time":"2020-04-02 09:43:35","address":{"receive_name":"高老师","receive_mobile":"13621118758","receive_address":"北京市北京辖区西城区桥头"}}
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
         * goods : [{"title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","spec_title":"","price":"799.00","total":1,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg"}]
         * order_price : 734.00
         * ordersn : ME20200402094329706393
         * time : 2020-04-02 09:43:35
         * address : {"receive_name":"高老师","receive_mobile":"13621118758","receive_address":"北京市北京辖区西城区桥头"}
         */

        private String order_price;
        private String ordersn;
        private String time;
        private AddressBean address;
        private List<GoodsBean> goods;

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public static class AddressBean {
            /**
             * receive_name : 高老师
             * receive_mobile : 13621118758
             * receive_address : 北京市北京辖区西城区桥头
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
             * title : 苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人
             * spec_title :
             * price : 799.00
             * total : 1
             * thumb : http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg
             */

            private String title;
            private String spec_title;
            private String price;
            private int total;
            private String thumb;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }
}
