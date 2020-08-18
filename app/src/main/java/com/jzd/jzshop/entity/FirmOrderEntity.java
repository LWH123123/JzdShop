package com.jzd.jzshop.entity;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class FirmOrderEntity {
    /**
     * result : {"dispatch":"0.00","coupon_data":[{"coupon_log_id":"zAS93rgrrCMCZeSLCjy26n8omrWgMg","backtype":1,"enough":"150.00","backmoney":"8.50","timestr":"2019-12-25 17:24"}],"discount_shop":{"seckillinfo":"0.00","enough_info":[{"enough":"500.00","money":"60.00","is_shop":1}]},"address":{"addr_id":"mOhIHXxh9Lii4OdfWvcGyvOrnxQsgMg","realname":"王","mobile":"18534856679","province":"内蒙古自治区","city":"赤峰市","area":"松山区","address":"扬州"},"data":[{"merch_id":"","merch_name":"平台店铺","dispatch":0,"goods":[{"gid":"zgWNLt8r_m4X_bmSRL9IClRGuHMomsSMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","spec_title":null,"price":"3798.00","number":"1"},{"gid":"ybiYHSQkrSsXQw4XXuiWmh6oml9M0Mg","title":"2019秋冬新款优雅气质中长款系带收腰显瘦长袖针织连衣裙女a字裙","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/IhNMbytBQi9HtbB2iq2yZiq0jvbyim3i.jpg","spec_title":null,"price":"290.00","number":"1"}]},{"merch_id":"zYeGxaw_xMhX_HlUtVKAhf6Wmjki7nQMw","merch_name":"博世金乾盛专卖店","dispatch":0,"goods":[{"gid":"ytrPXaw_NDsA_SnipUbAYvGGmoaDWmMUMw","title":"博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/yCJvgyYz0F9NHF9049G1hA1CcTC1Gkk1.jpg","spec_title":null,"price":"359.00","number":"1"}]}]}
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
         * dispatch : 0.00
         * coupon_data : [{"coupon_log_id":"zAS93rgrrCMCZeSLCjy26n8omrWgMg","backtype":1,"enough":"150.00","backmoney":"8.50","timestr":"2019-12-25 17:24"}]
         * discount_shop : {"seckillinfo":"0.00","enough_info":[{"enough":"500.00","money":"60.00","is_shop":1}]}
         * address : {"addr_id":"mOhIHXxh9Lii4OdfWvcGyvOrnxQsgMg","realname":"王","mobile":"18534856679","province":"内蒙古自治区","city":"赤峰市","area":"松山区","address":"扬州"}
         * data : [{"merch_id":"","merch_name":"平台店铺","dispatch":0,"goods":[{"gid":"zgWNLt8r_m4X_bmSRL9IClRGuHMomsSMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","spec_title":null,"price":"3798.00","number":"1"},{"gid":"ybiYHSQkrSsXQw4XXuiWmh6oml9M0Mg","title":"2019秋冬新款优雅气质中长款系带收腰显瘦长袖针织连衣裙女a字裙","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/IhNMbytBQi9HtbB2iq2yZiq0jvbyim3i.jpg","spec_title":null,"price":"290.00","number":"1"}]},{"merch_id":"zYeGxaw_xMhX_HlUtVKAhf6Wmjki7nQMw","merch_name":"博世金乾盛专卖店","dispatch":0,"goods":[{"gid":"ytrPXaw_NDsA_SnipUbAYvGGmoaDWmMUMw","title":"博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/yCJvgyYz0F9NHF9049G1hA1CcTC1Gkk1.jpg","spec_title":null,"price":"359.00","number":"1"}]}]
         */

        private String dispatch;
        private DiscountShopBean discount_shop;
        private List<CouponDataBean> coupon_data;
        private AddressBean address;
        private List<DataBean> data;

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public DiscountShopBean getDiscount_shop() {
            return discount_shop;
        }

        public void setDiscount_shop(DiscountShopBean discount_shop) {
            this.discount_shop = discount_shop;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public List<CouponDataBean> getCoupon_data() {
            return coupon_data;
        }

        public void setCoupon_data(List<CouponDataBean> coupon_data) {
            this.coupon_data = coupon_data;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DiscountShopBean {
            /**
             * seckillinfo : 0.00
             * enough_info : [{"enough":"500.00","money":"60.00","is_shop":1}]
             */

            private String seckillinfo;
            private String discount_info;
            private List<EnoughInfoBean> enough_info;


            public String getSeckillinfo() {
                return seckillinfo;
            }

            public void setSeckillinfo(String seckillinfo) {
                this.seckillinfo = seckillinfo;
            }

            public String getDiscount_info() {
                return discount_info;
            }

            public void setDiscount_info(String discount_info) {
                this.discount_info = discount_info;
            }

            public List<EnoughInfoBean> getEnough_info() {
                return enough_info;
            }

            public void setEnough_info(List<EnoughInfoBean> enough_info) {
                this.enough_info = enough_info;
            }

            public static class EnoughInfoBean {
                /**
                 * enough : 500.00
                 * money : 60.00
                 * is_shop : 1
                 */

                private String enough;
                private String money;
                private int is_shop;

                public String getEnough() {
                    return enough;
                }

                public void setEnough(String enough) {
                    this.enough = enough;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public int getIs_shop() {
                    return is_shop;
                }

                public void setIs_shop(int is_shop) {
                    this.is_shop = is_shop;
                }

                public List<String> coupon(){
                    ArrayList<String> arr = new ArrayList<>();
                    StringBuffer sbone = new StringBuffer();
                    StringBuffer sbtwo = new StringBuffer();
                    if(is_shop==1){
                        sbone.append("商户优惠");
                    }else if(is_shop==0){
                        sbone.append("商城优惠");
                    }
                        sbtwo.append("每笔满"+enough+"元立减"+money+"元)");
                    arr.add(sbone.toString());
                    arr.add(sbtwo.toString());
                    return arr;
                }
            }
        }

        public static class AddressBean {
            /**
             * addr_id : mOhIHXxh9Lii4OdfWvcGyvOrnxQsgMg
             * realname : 王
             * mobile : 18534856679
             * province : 内蒙古自治区
             * city : 赤峰市
             * area : 松山区
             * address : 扬州
             */

            private String addr_id;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;

            public String getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddressText(){
                return province+city+area+address;
            }
        }

        public static class CouponDataBean {
            /**
             * coupon_log_id : zAS93rgrrCMCZeSLCjy26n8omrWgMg
             * backtype : 1
             * enough : 150.00
             * backmoney : 8.50
             * timestr : 2019-12-25 17:24
             */

            private String coupon_log_id;
            private String backtype;
            private String enough;
            private double backmoney;
            private String timestr;
            private String merch_id;
            private int iscollect=0;

            public int getIscollect() {
                return iscollect;
            }

            public void setIscollect(int iscollect) {
                this.iscollect = iscollect;
            }

            public String getMerch_id() {
                return merch_id;
            }

            public void setMerch_id(String merch_id) {
                this.merch_id = merch_id;
            }

            public String getCoupon_log_id() {
                return coupon_log_id;
            }

            public void setCoupon_log_id(String coupon_log_id) {
                this.coupon_log_id = coupon_log_id;
            }

            public String getBacktype() {
                return backtype;
            }

            public void setBacktype(String backtype) {
                this.backtype = backtype;
            }

            public String getEnough() {
                return enough;
            }

            public void setEnough(String enough) {
                this.enough = enough;
            }

            public double getBackmoney() {
                return backmoney;
            }

            public void setBackmoney(double backmoney) {
                this.backmoney = backmoney;
            }

            public String getTimestr() {
                return timestr;
            }

            public void setTimestr(String timestr) {
                this.timestr = timestr;
            }

            public String getBackmoneyText() {
//                return backmoney+(backtype.equals("0")?"元":"折");
                return String.valueOf(backmoney);
            }

            public String getEnoughText() {
                int a=0;
                if(!TextUtils.isEmpty(enough)){
                    double v = Double.parseDouble(enough);
                    a= (int) v;
                }
                return enough.equals("0")?"不限制": String.format("满%s即可使用",a);
            }
            public String getTimestrText() {
                return String.format("有效期：%s",timestr);
            }

        }

        public static class DataBean {
            /**
             * merch_id :
             * merch_name : 平台店铺
             * dispatch : 0
             * goods : [{"gid":"zgWNLt8r_m4X_bmSRL9IClRGuHMomsSMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","spec_title":null,"price":"3798.00","number":"1"},{"gid":"ybiYHSQkrSsXQw4XXuiWmh6oml9M0Mg","title":"2019秋冬新款优雅气质中长款系带收腰显瘦长袖针织连衣裙女a字裙","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/IhNMbytBQi9HtbB2iq2yZiq0jvbyim3i.jpg","spec_title":null,"price":"290.00","number":"1"}]
             */

            private String merch_id;
            private String merch_name;
            private int dispatch;
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

            public int getDispatch() {
                return dispatch;
            }

            public void setDispatch(int dispatch) {
                this.dispatch = dispatch;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * gid : zgWNLt8r_m4X_bmSRL9IClRGuHMomsSMkMg
                 * title : 可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg
                 * spec_title : null
                 * price : 3798.00
                 * number : 1
                 */
                private String merch_id;
                private String merch_name;
                private int dispatch;
                private String gid;
                private String title;
                private String thumb;
                private Object spec_title;
                private String price;
                private String number;
                private String seckillprice;//新增秒杀优惠
                private String discountprice;//新增促销优惠
                private int use_coupon;
                private int islast;


                public String getSeckillprice() {
                    return seckillprice;
                }

                public void setSeckillprice(String seckillprice) {
                    this.seckillprice = seckillprice;
                }

                public String getDiscountprice() {
                    return discountprice;
                }

                public void setDiscountprice(String discountprice) {
                    this.discountprice = discountprice;
                }

                public int getUse_coupon() {
                    return use_coupon;
                }

                public void setUse_coupon(int use_coupon) {
                    this.use_coupon = use_coupon;
                }

                public int getIslast() {
                    return islast;
                }

                public void setIslast(int islast) {
                    this.islast = islast;
                }

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

                public int getDispatch() {
                    return dispatch;
                }

                public void setDispatch(int dispatch) {
                    this.dispatch = dispatch;
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

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public Object getSpec_title() {
                    return spec_title;
                }

                public void setSpec_title(Object spec_title) {
                    this.spec_title = spec_title;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "merch_id='" + merch_id + '\'' +
                        ", merch_name='" + merch_name + '\'' +
                        ", dispatch=" + dispatch +
                        ", goods=" + goods.toString() +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "dispatch='" + dispatch + '\'' +
                    ", discount_shop=" + discount_shop +
                    ", address=" + address +
                    ", coupon_data=" + coupon_data +
                    ", data=" + data +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "FirmOrderEntity{" +
                "result=" + result.toString() +
                '}';
    }
}
