package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/5/13 10:43
 */
public class CreditOrderDetailEntity {
    /**
     * result : {"log_id":"ztEnNXj_dZSA_SEQpUdCiKEjqpa2OkssMw","shopname":"HUAWEI尚品","logno":"EE20200513091800644441","gid":"zOPofhxr_s2A_xEy4tIWAuaGyCWpmBlQMg","title":"红包001","option":"","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg","credit":30,"money":"0.00","dispatch":"0.00","number":1,"state":2,"state_str":"已兑换，等待领取","type":3,"express_com":"","express_sn":"","address":{"realname":"刘经理","mobile":"15335822257","province":"湖北省","city":"黄石市","area":"大冶市","address":"8都看"},"time":{"createtime":"2020-05-13 09:18:00","paytime":"2020-05-13 09:18:00","sendtime":"","finishtime":""}}
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
         * log_id : ztEnNXj_dZSA_SEQpUdCiKEjqpa2OkssMw
         * shopname : HUAWEI尚品
         * logno : EE20200513091800644441
         * gid : zOPofhxr_s2A_xEy4tIWAuaGyCWpmBlQMg
         * title : 红包001
         * option :
         * thumb : http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg
         * credit : 30
         * money : 0.00
         * dispatch : 0.00
         * number : 1
         * state : 2
         * state_str : 已兑换，等待领取
         * type : 3
         * express_com :
         * express_sn :
         * address : {"realname":"刘经理","mobile":"15335822257","province":"湖北省","city":"黄石市","area":"大冶市","address":"8都看"}
         * time : {"createtime":"2020-05-13 09:18:00","paytime":"2020-05-13 09:18:00","sendtime":"","finishtime":""}
         */

        private String log_id;
        private String shopname;
        private String logno;
        private String gid;
        private String title;
        private String option;
        private String thumb;
        private int credit;
        private String money;
        private String dispatch;
        private int number;
        private int state;
        private String state_str;
        private int type;
        private String express_com;
        private String express_sn;
        private AddressBean address;
        private TimeBean time;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getLogno() {
            return logno;
        }

        public void setLogno(String logno) {
            this.logno = logno;
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

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getState_str() {
            return state_str;
        }

        public void setState_str(String state_str) {
            this.state_str = state_str;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getExpress_com() {
            return express_com;
        }

        public void setExpress_com(String express_com) {
            this.express_com = express_com;
        }

        public String getExpress_sn() {
            return express_sn;
        }

        public void setExpress_sn(String express_sn) {
            this.express_sn = express_sn;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public TimeBean getTime() {
            return time;
        }

        public void setTime(TimeBean time) {
            this.time = time;
        }

        public static class AddressBean {
            /**
             * realname : 刘经理
             * mobile : 15335822257
             * province : 湖北省
             * city : 黄石市
             * area : 大冶市
             * address : 8都看
             */

            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;

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

        public static class TimeBean {
            /**
             * createtime : 2020-05-13 09:18:00
             * paytime : 2020-05-13 09:18:00
             * sendtime :
             * finishtime :
             */

            private String createtime;
            private String paytime;
            private String sendtime;
            private String finishtime;

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getPaytime() {
                return paytime;
            }

            public void setPaytime(String paytime) {
                this.paytime = paytime;
            }

            public String getSendtime() {
                return sendtime;
            }

            public void setSendtime(String sendtime) {
                this.sendtime = sendtime;
            }

            public String getFinishtime() {
                return finishtime;
            }

            public void setFinishtime(String finishtime) {
                this.finishtime = finishtime;
            }
        }
    }
}
