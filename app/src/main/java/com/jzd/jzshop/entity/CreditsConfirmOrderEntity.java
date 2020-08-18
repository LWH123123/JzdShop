package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/5/13 16:11
 */
public class CreditsConfirmOrderEntity {
    /**
     * result : {"shopname":"HUAWEI尚品","gid":"zzVIfHxkrq5C5oZMDHcG6M6onsqAMg","title":"华为 HUAWEI Mate 30 Pro 8GB+256GB 亮黑色 麒麟990芯片 4000万徕卡电影四摄 超曲面OLED环幕屏 超级快充 屏内指纹 4G全网通双卡双待手机","option":"蓝色+","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/PZb27s22DXZd6xLLH9fdD27SBDbdbs.jpg","credit":100000,"money":"399.00","dispatch_status":1,"dispatch":"0.00","lotterydraws":0,"type":0,"address":{"addr_id":"zT7dGyQ1_wZA_r13dn9LAF3GSAzona4c0Mg","realname":"李经理","mobile":"19910528359","province":"北京市","city":"北京辖区","area":"东城区","address":"昌平区沙河"}}
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
         * shopname : HUAWEI尚品
         * gid : zzVIfHxkrq5C5oZMDHcG6M6onsqAMg
         * title : 华为 HUAWEI Mate 30 Pro 8GB+256GB 亮黑色 麒麟990芯片 4000万徕卡电影四摄 超曲面OLED环幕屏 超级快充 屏内指纹 4G全网通双卡双待手机
         * option : 蓝色+
         * thumb : http://test.gtt20.com/attachment/images/2/2020/05/PZb27s22DXZd6xLLH9fdD27SBDbdbs.jpg
         * credit : 100000
         * money : 399.00
         * dispatch_status : 1
         * dispatch : 0.00
         * lotterydraws : 0
         * type : 0
         * address : {"addr_id":"zT7dGyQ1_wZA_r13dn9LAF3GSAzona4c0Mg","realname":"李经理","mobile":"19910528359","province":"北京市","city":"北京辖区","area":"东城区","address":"昌平区沙河"}
         */

        private String shopname;
        private String gid;
        private String title;
        private String option;
        private String thumb;
        private int credit;
        private String money;
        private int dispatch_status;
        private String dispatch;
        private int lotterydraws;
        private int type;
        private AddressBean address;

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
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

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getDispatch_status() {
            return dispatch_status;
        }

        public void setDispatch_status(int dispatch_status) {
            this.dispatch_status = dispatch_status;
        }

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public int getLotterydraws() {
            return lotterydraws;
        }

        public void setLotterydraws(int lotterydraws) {
            this.lotterydraws = lotterydraws;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * addr_id : zT7dGyQ1_wZA_r13dn9LAF3GSAzona4c0Mg
             * realname : 李经理
             * mobile : 19910528359
             * province : 北京市
             * city : 北京辖区
             * area : 东城区
             * address : 昌平区沙河
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
    }
}
