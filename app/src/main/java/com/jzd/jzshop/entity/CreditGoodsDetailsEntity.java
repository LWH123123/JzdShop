package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/5/13.
 */
public class CreditGoodsDetailsEntity {

    /**
     * result : {"gid":"zoUk9fr_WtnA_yHu44WAVmBGypPLwmQMw","title":"红包001","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg","credit":"30","money":"0.00","price":"0.00","total":10000,"joins":6,"dispatch":"0.00","type":3,"lotterydraws":0,"content":"","member":{"money":"6573.34","points":81265},"specs_list":[],"specs_data":[]}
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
         * gid : zoUk9fr_WtnA_yHu44WAVmBGypPLwmQMw
         * title : 红包001
         * thumb : http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg
         * credit : 30
         * money : 0.00
         * price : 0.00
         * total : 10000
         * joins : 6
         * dispatch : 0.00
         * type : 3
         * lotterydraws : 0
         * content :
         * member : {"money":"6573.34","points":81265}
         * specs_list : []
         * specs_data : []
         */

        private String gid;
        private String title;
        private String thumb;
        private String credit;
        private String money;
        private String price;
        private int total;
        private int joins;
        private int istime;
        private String dispatch;
        private int type;
        private int lotterydraws;
        private String timestart;
        private String timeend;
        private String content;
        private MemberBean member;
        private List<BaseShopSpecEntity> specs_list;
        private List<SpecsDataBean> specs_data;


        public String getTimestart() {
            return timestart;
        }

        public void setTimestart(String timestart) {
            this.timestart = timestart;
        }

        public String getTimeend() {
            return timeend;
        }

        public void setTimeend(String timeend) {
            this.timeend = timeend;
        }

        public int getIstime() {
            return istime;
        }

        public void setIstime(int istime) {
            this.istime = istime;
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

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

        public int getJoins() {
            return joins;
        }

        public void setJoins(int joins) {
            this.joins = joins;
        }

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLotterydraws() {
            return lotterydraws;
        }

        public void setLotterydraws(int lotterydraws) {
            this.lotterydraws = lotterydraws;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public List<BaseShopSpecEntity> getSpecs_list() {
            return specs_list;
        }

        public void setSpecs_list(List<BaseShopSpecEntity> specs_list) {
            this.specs_list = specs_list;
        }

        public List<SpecsDataBean> getSpecs_data() {
            return specs_data;
        }

        public void setSpecs_data(List<SpecsDataBean> specs_data) {
            this.specs_data = specs_data;
        }

        public static class MemberBean {
            /**
             * money : 6573.34
             * points : 81265
             */

            private String money;
            private int points;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }
        }

        public static class SpecsDataBean{
         /*          "optionid": 19,
                     "title": "红色+联通",
                     "specs_id": "12_10",
                     "total": 100,
                     "credit": 100000,
                     "money": "199.00"*/
            private int optionid;
            private String title;
            private String specs_id;
            private String total;
            private int credit;
            private String money;


            public int getOptionid() {
                return optionid;
            }

            public void setOptionid(int optionid) {
                this.optionid = optionid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSpecs_id() {
                return specs_id;
            }

            public void setSpecs_id(String specs_id) {
                this.specs_id = specs_id;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
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
        }





    }



}
