package com.jzd.jzshop.entity;

import com.jzd.jzshop.utils.MoneyFormatUtils;

import java.util.List;

public class ShoppcarEntry {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * merch_id :
         * merch_name : 自营商品
         * coupon_data : []
         * data : [{"cart_id":"dzh9W3Sqs_YAP_anBMoasADTG2etQnsAMQ","gid":"yArYHNUkrd4XQcNXXS9WmoBomkSM0Mg","title":"2019秋冬新款优雅气质中长款系带收腰显瘦长袖针织连衣裙女a字裙","price":290,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/IhNMbytBQi9HtbB2iq2yZiq0jvbyim3i.jpg","status":"1","number":"1","spec_title":null,"isdispatch":1,"buy_msg":""},{"cart_id":"ndU9jXrrz1yicXS4iXD6pv4tnwMw","gid":"tzuohDLlz_RXt_BHeRlMEXJTj_bAL_QoOmz8YQMQ","title":"2件59元】打底衫女高领内搭紧身上衣服2019秋冬新款纯棉长袖T恤潮","price":159,"thumb":"http://img.alicdn.com/imgextra/i1/4145308922/O1CN01gA20HE2FmKBgy6p8N_!!0-item_pic.jpg","status":"1","number":"1","spec_title":null,"isdispatch":1,"buy_msg":""},{"cart_id":"XnVdeecxs9C3XNRKwWHzZ6AtWnfgMQ","gid":"zq3NLH0r_Q6X_FASRj4ICYDGuY7omAdMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","price":3798,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","status":"1","number":"1","spec_title":null,"isdispatch":1,"buy_msg":""},{"cart_id":"moGw8Xn_lv1X_XSF7caCv88TmtqoXmQMw","gid":"VzO4fbmjd_bAO_pnhNYNXCQTZqgosmx8HsMQ","title":"【新品赠流量卡】红米note8 4800万四摄拍照游戏学生智能小米手机redmi全面屏pro小米官方旗舰店正品7xiaomi","price":999,"thumb":"http://img.alicdn.com/imgextra/i4/1714128138/O1CN0141Z3u929zFkRgoepC_!!0-item_pic.jpg","status":"1","number":"2","spec_title":null,"isdispatch":1,"buy_msg":""}]
         */

        private String merch_id;
        private String merch_name;
        private List<CouponDataBean> coupon_data;
        private List<DataBean> data;

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

        public static class DataBean {

            /**
             * cart_id : dzh9W3Sqs_YAP_anBMoasADTG2etQnsAMQ
             * gid : yArYHNUkrd4XQcNXXS9WmoBomkSM0Mg
             * title : 2019秋冬新款优雅气质中长款系带收腰显瘦长袖针织连衣裙女a字裙
             * price : 290
             * thumb : http://test.gtt20.com/attachment/images/2/2019/10/IhNMbytBQi9HtbB2iq2yZiq0jvbyim3i.jpg
             * status : 1
             * number : 1
             * spec_title : null
             * isdispatch : 1
             * buy_msg :
             */

            private String cart_id;
            private String gid;
            private String title;
            private double price;
            private String thumb;
            private int optionid;
            private int  status;
            private int number;
            private String spec_title;
            private int isdispatch;
            private String buy_msg;
            private String merch_id;
            private String merch_name;
            private String maxbuy;
            private int selected;
            private String conpon;

            //是否为最后一个商品
            public int islast;
            //是否有优惠
            public int iscoupon;
            //判断是否为下一个店铺
            public int isnextstore;
            //商品的选中状态
            public boolean shopischeck;

            public String getConpon() {
                return conpon;
            }

            public void setConpon(String conpon) {
                this.conpon = conpon;
            }

            public int getIsnextstore() {
                return isnextstore;
            }

            public void setIsnextstore(int isnextstore) {
                this.isnextstore = isnextstore;
            }

            public int getIscoupon() {
                return iscoupon;
            }

            public void setIscoupon(int iscoupon) {
                this.iscoupon = iscoupon;
            }

            public int getOptionid() {
                return optionid;
            }

            public void setOptionid(int optionid) {
                this.optionid = optionid;
            }

            public String getMaxbuy() {
                return maxbuy;
            }

            public void setMaxbuy(String maxbuy) {
                this.maxbuy = maxbuy;
            }

            public int getSelected() {
                return selected;
            }

            public void setSelected(int selected) {
                this.selected = selected;
            }

            public int getIslast() {
                return islast;
            }

            public void setIslast(int islast) {
                this.islast = islast;
            }

            public boolean isIscheck() {
                return shopischeck;
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

            public boolean isShopischeck() {
                return shopischeck;
            }

            public void setShopischeck(boolean shopischeck) {
                this.shopischeck = shopischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.shopischeck = ischeck;
            }


            public String getCart_id() {
                return cart_id;
            }

            public void setCart_id(String cart_id) {
                this.cart_id = cart_id;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getPriceText() {
                return MoneyFormatUtils.keepTwoDecimal(price);
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int  getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getSpec_title() {
                return spec_title;
            }

            public void setSpec_title(String spec_title) {
                this.spec_title = spec_title;
            }

            public int getIsdispatch() {
                return isdispatch;
            }

            public void setIsdispatch(int isdispatch) {
                this.isdispatch = isdispatch;
            }

            public String getBuy_msg() {
                return buy_msg;
            }

            public void setBuy_msg(String buy_msg) {
                this.buy_msg = buy_msg;
            }
        }

        public static class CouponDataBean{
             private String merch_name;
             private String backtype;
             private double enough;
             private double backmoney;
             private String timestr;
             private int canget;
             private String coupon_id;
             private int  isuse;

            public String getMerch_name() {
                return merch_name;
            }

            public void setMerch_name(String merch_name) {
                this.merch_name = merch_name;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getBacktype() {
                return backtype;
            }

            public void setBacktype(String backtype) {
                this.backtype = backtype;
            }

            public String getEnoughText() {
                return enough==0?"不限制": String.format("满%s即可使用",enough);
            }

            public void setEnough(double enough) {
                this.enough = enough;
            }

            public double getBackmoney() {
                return backmoney;
            }
            public String getBackmoneyText() {
                return backmoney+(backtype.equals("0")?"元":"折");
            }

            public void setBackmoney(double backmoney) {
                this.backmoney = backmoney;
            }

            public String getTimestr() {
                return String.format("有效期：%s",timestr);
            }

            public void setTimestr(String timestr) {
                this.timestr = timestr;
            }

            public int getCanget() {
                return canget;
            }

            public void setCanget(int canget) {
                this.canget = canget;
            }
        }


    }
}
