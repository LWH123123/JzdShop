package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/1 16:03
 */
public class CouponCenterEntity {

    /**
     * result : {"total":5,"data":[{"coupon_id":"y9oE4Ox_gxHX_yjAVNPDfr5D_ABXr_rmG18gMw","name":"满200减50","backtype":0,"enough":"200.00","backmoney":"50.00","timestr":"即购买日内30天有效","desc":"","type":1},{"coupon_id":"woqdTK9m_tsA_Q4yURAaXP4WqLejMg","name":"满50","backtype":0,"enough":"0.00","backmoney":"5.00","timestr":"即购买日内30天有效","desc":"","type":1},{"coupon_id":"mu1NPKyk_rjX_BeyYKcfAWwT2NwiMg","name":"满200全场85折","backtype":1,"enough":"150.00","backmoney":"8.50","timestr":"即领取日内30天有效","desc":"","type":0},{"coupon_id":"nNbz7Dl_VKlX_Cx3PcYCbliWWtMw","name":"满100减30","backtype":0,"enough":"100.00","backmoney":"30.00","timestr":"即领取日内30天有效","desc":"","type":0},{"coupon_id":"n4s03bi9NBgiVMimTXD6Q9nsMw","name":"优惠券001","backtype":0,"enough":"99.00","backmoney":"15.00","timestr":"即购买日内7天有效","desc":"","type":1}]}
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
         * total : 5
         * data : [{"coupon_id":"y9oE4Ox_gxHX_yjAVNPDfr5D_ABXr_rmG18gMw","name":"满200减50","backtype":0,"enough":"200.00","backmoney":"50.00","timestr":"即购买日内30天有效","desc":"","type":1},{"coupon_id":"woqdTK9m_tsA_Q4yURAaXP4WqLejMg","name":"满50","backtype":0,"enough":"0.00","backmoney":"5.00","timestr":"即购买日内30天有效","desc":"","type":1},{"coupon_id":"mu1NPKyk_rjX_BeyYKcfAWwT2NwiMg","name":"满200全场85折","backtype":1,"enough":"150.00","backmoney":"8.50","timestr":"即领取日内30天有效","desc":"","type":0},{"coupon_id":"nNbz7Dl_VKlX_Cx3PcYCbliWWtMw","name":"满100减30","backtype":0,"enough":"100.00","backmoney":"30.00","timestr":"即领取日内30天有效","desc":"","type":0},{"coupon_id":"n4s03bi9NBgiVMimTXD6Q9nsMw","name":"优惠券001","backtype":0,"enough":"99.00","backmoney":"15.00","timestr":"即购买日内7天有效","desc":"","type":1}]
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * coupon_id : y9oE4Ox_gxHX_yjAVNPDfr5D_ABXr_rmG18gMw
             * name : 满200减50
             * backtype : 0
             * enough : 200.00
             * backmoney : 50.00
             * timestr : 即购买日内30天有效
             * desc :
             * type : 1
             */

            private String coupon_id;
            private String name;
            private int backtype;
            private String enough;
            private String backmoney;
            private String timestr;
            private String desc;
            private int type;
            //
            private String money;//100元/1.2折
            private String coupontype;//折扣券
            private String describe;//满...元可用
            private int showdescribe=1;//0时说明可见  1时不可见

            public int getShowdescribe() {
                return showdescribe;
            }

            public void setShowdescribe(int showdescribe) {
                this.showdescribe = showdescribe;
            }

            public String getMoney() {
                if(backtype==0){
                    return backmoney.substring(0,backmoney.length()-3);
                }else {
                    return backmoney+"折";
                }
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getDescribe() {
                return "满"+enough+"元可用";
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getCoupontype() {
                if(backtype==0){
                    return  "满减券";
                }else{
                    return "折扣券";
                }
            }

            public void setCoupontype(String coupontype) {
                this.coupontype = coupontype;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getBacktype() {
                return backtype;
            }

            public void setBacktype(int backtype) {
                this.backtype = backtype;
            }

            public String getEnough() {
                return enough;
            }

            public void setEnough(String enough) {
                this.enough = enough;

            }

            public String getBackmoney() {
                if(backtype==0){
                  return String.valueOf(Integer.parseInt(backmoney));
                }else {
                    return String.valueOf(Integer.parseInt(backmoney));
                }
            }

            public void setBackmoney(String backmoney) {
                this.backmoney = backmoney;
            }

            public String getTimestr() {
                return timestr;
            }

            public void setTimestr(String timestr) {
                this.timestr = timestr;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
