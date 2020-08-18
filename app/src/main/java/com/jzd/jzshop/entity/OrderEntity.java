package com.jzd.jzshop.entity;

import java.util.List;

public class OrderEntity {

    /**
     * result : {"discount_shop":[],"address":{"addr_id":"nhJNLWBqqN43Ah7XCbXjqibjmzNgMg","realname":"张三","mobile":"13621119999","province":"河北省","city":"石家庄","area":"桥西区","address":"abc"},"data":[{"merch_name":"平台店铺","dispatch":0,"discount":{"seckillinfo":0,"enough_info":[]},"coupon_data":[{"coupon_log_id":"mzYokeU2E_aAo_ASmVrPEAeWimQrznIAMQ","backtype":"0","enough":"99.00","backmoney":15,"timestr":"2019-11-22 16:37"}],"goods":[{"gid":"PzrNlLkrR_cXT_DSsRcIICDGGuloBmxMpkMQ","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","spec_title":null,"price":"3798.00","number":"4"},{"gid":"zAx4bwmj_djA_wCnNRbNCx2Tqz5omay8sMg","title":"【新品赠流量卡】红米note8 4800万四摄拍照游戏学生智能小米手机redmi全面屏pro小米官方旗舰店正品7xiaomi","thumb":"http://img.alicdn.com/imgextra/i4/1714128138/O1CN0141Z3u929zFkRgoepC_!!0-item_pic.jpg","spec_title":null,"price":"999.00","number":"2"}]}]}
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
         * discount_shop : []
         * address : {"addr_id":"nhJNLWBqqN43Ah7XCbXjqibjmzNgMg","realname":"张三","mobile":"13621119999","province":"河北省","city":"石家庄","area":"桥西区","address":"abc"}
         * data : [{"merch_name":"平台店铺","dispatch":0,"discount":{"seckillinfo":0,"enough_info":[]},"coupon_data":[{"coupon_log_id":"mzYokeU2E_aAo_ASmVrPEAeWimQrznIAMQ","backtype":"0","enough":"99.00","backmoney":15,"timestr":"2019-11-22 16:37"}],"goods":[{"gid":"PzrNlLkrR_cXT_DSsRcIICDGGuloBmxMpkMQ","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","spec_title":null,"price":"3798.00","number":"4"},{"gid":"zAx4bwmj_djA_wCnNRbNCx2Tqz5omay8sMg","title":"【新品赠流量卡】红米note8 4800万四摄拍照游戏学生智能小米手机redmi全面屏pro小米官方旗舰店正品7xiaomi","thumb":"http://img.alicdn.com/imgextra/i4/1714128138/O1CN0141Z3u929zFkRgoepC_!!0-item_pic.jpg","spec_title":null,"price":"999.00","number":"2"}]}]
         */

        private AddressBean address;
        private List<?> discount_shop;
        private List<DataBean> data;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public List<?> getDiscount_shop() {
            return discount_shop;
        }

        public void setDiscount_shop(List<?> discount_shop) {
            this.discount_shop = discount_shop;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class AddressBean {
            /**
             * addr_id : nhJNLWBqqN43Ah7XCbXjqibjmzNgMg
             * realname : 张三
             * mobile : 13621119999
             * province : 河北省
             * city : 石家庄
             * area : 桥西区
             * address : abc
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
        }

        public static class DataBean {
            /**
             * merch_name : 平台店铺
             * dispatch : 0
             * discount : {"seckillinfo":0,"enough_info":[]}
             * coupon_data : [{"coupon_log_id":"mzYokeU2E_aAo_ASmVrPEAeWimQrznIAMQ","backtype":"0","enough":"99.00","backmoney":15,"timestr":"2019-11-22 16:37"}]
             * goods : [{"gid":"PzrNlLkrR_cXT_DSsRcIICDGGuloBmxMpkMQ","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","spec_title":null,"price":"3798.00","number":"4"},{"gid":"zAx4bwmj_djA_wCnNRbNCx2Tqz5omay8sMg","title":"【新品赠流量卡】红米note8 4800万四摄拍照游戏学生智能小米手机redmi全面屏pro小米官方旗舰店正品7xiaomi","thumb":"http://img.alicdn.com/imgextra/i4/1714128138/O1CN0141Z3u929zFkRgoepC_!!0-item_pic.jpg","spec_title":null,"price":"999.00","number":"2"}]
             */

            private String merch_name;
            private int dispatch;
            private DiscountBean discount;
            private List<CouponDataBean> coupon_data;
            private List<GoodsBean> goods;

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

            public DiscountBean getDiscount() {
                return discount;
            }

            public void setDiscount(DiscountBean discount) {
                this.discount = discount;
            }

            public List<CouponDataBean> getCoupon_data() {
                return coupon_data;
            }

            public void setCoupon_data(List<CouponDataBean> coupon_data) {
                this.coupon_data = coupon_data;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class DiscountBean {
                /**
                 * seckillinfo : 0
                 * enough_info : []
                 */

                private int seckillinfo;
                private List<?> enough_info;

                public int getSeckillinfo() {
                    return seckillinfo;
                }

                public void setSeckillinfo(int seckillinfo) {
                    this.seckillinfo = seckillinfo;
                }

                public List<?> getEnough_info() {
                    return enough_info;
                }

                public void setEnough_info(List<?> enough_info) {
                    this.enough_info = enough_info;
                }
            }

            public static class CouponDataBean {
                /**
                 * coupon_log_id : mzYokeU2E_aAo_ASmVrPEAeWimQrznIAMQ
                 * backtype : 0
                 * enough : 99.00
                 * backmoney : 15
                 * timestr : 2019-11-22 16:37
                 */

                private String coupon_log_id;
                private String backtype;
                private String enough;
                private int backmoney;
                private String timestr;

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

                public int getBackmoney() {
                    return backmoney;
                }

                public void setBackmoney(int backmoney) {
                    this.backmoney = backmoney;
                }

                public String getTimestr() {
                    return timestr;
                }

                public void setTimestr(String timestr) {
                    this.timestr = timestr;
                }
            }

            public static class GoodsBean {
                /**
                 * gid : PzrNlLkrR_cXT_DSsRcIICDGGuloBmxMpkMQ
                 * title : 可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg
                 * spec_title : null
                 * price : 3798.00
                 * number : 4
                 */

                private String gid;
                private String title;
                private String thumb;
                private Object spec_title;
                private String price;
                private String number;

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
        }
    }
}
