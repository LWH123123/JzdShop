package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description: 积分商城 - 全部商品
 * @date :2020/5/9 15:42
 */
public class CreditsAllGoodsEntity {
    /**
     * result : {"total":4,"category":[{"cate_id":"ydDz1brqnDznIWxH2CGqQ9NrmAMw","cate_name":"3C"},{"cate_id":"IwkdyXvgN9qyuEfdODRTw_NAb_arDmTQMQ","cate_name":"优惠券"},{"cate_id":"nDaNKpDx9ZonAf8dWw3z_T1A_bUrnZ3gMg","cate_name":"余额"},{"cate_id":"kmw4ceLwqqOyA4OYVXDzNiXrVnLwMQ","cate_name":"红包"},{"cate_id":"ozsoreq2b_hAh_RSgVhPYACWGmwrXnYAMQ","cate_name":"衣服"}],"data":[{"gid":"yh9YedTy_jnX_RaC4MjZWo1GWfeonvMQMg","title":"小米（MI）小爱智能音箱Pro 无线蓝牙音响 语音控制智能家居 APP远程操控 专业DTS 音效 黑色","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/Q1AGEArDieZwDIA1MMWZdZUgM1NDv7.jpg","credit":300000,"money":"0.00","type":0},{"gid":"BzxIafwktrICp5MMBDsGY6HoqnRAMQ","title":"华为 HUAWEI Mate 30 Pro 8GB+256GB 亮黑色 麒麟990芯片 4000万徕卡电影四摄 超曲面OLED环幕屏 超级快充 屏内指纹 4G全网通双卡双待手机","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/PZb27s22DXZd6xLLH9fdD27SBDbdbs.jpg","credit":1000000,"money":"0.00","type":0},{"gid":"wOWdXsam9I2yMT8ZWev2WY9onAVwMg","title":"小米手环4石墨黑 AI彩屏心率运动手环游泳姿势识别50米防水6轴传感器24小时高精准心率监测","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/iia9dG5C51snUN17dFfuaviNv1WDzz.jpg","credit":20000,"money":"0.00","type":0},{"gid":"yNyapfk9c88y4dwt2C26vSGongMw","title":"现货速发【现货国行4788元起-可分期】Apple 苹果 iPhone 11 手机 黑色 全网通 64GB","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/UtR25Z7c3g96k593k7mat956g99rm9.jpg","credit":500000,"money":"499.00","type":0}]}
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
         * total : 4
         * category : [{"cate_id":"ydDz1brqnDznIWxH2CGqQ9NrmAMw","cate_name":"3C"},{"cate_id":"IwkdyXvgN9qyuEfdODRTw_NAb_arDmTQMQ","cate_name":"优惠券"},{"cate_id":"nDaNKpDx9ZonAf8dWw3z_T1A_bUrnZ3gMg","cate_name":"余额"},{"cate_id":"kmw4ceLwqqOyA4OYVXDzNiXrVnLwMQ","cate_name":"红包"},{"cate_id":"ozsoreq2b_hAh_RSgVhPYACWGmwrXnYAMQ","cate_name":"衣服"}]
         * data : [{"gid":"yh9YedTy_jnX_RaC4MjZWo1GWfeonvMQMg","title":"小米（MI）小爱智能音箱Pro 无线蓝牙音响 语音控制智能家居 APP远程操控 专业DTS 音效 黑色","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/Q1AGEArDieZwDIA1MMWZdZUgM1NDv7.jpg","credit":300000,"money":"0.00","type":0},{"gid":"BzxIafwktrICp5MMBDsGY6HoqnRAMQ","title":"华为 HUAWEI Mate 30 Pro 8GB+256GB 亮黑色 麒麟990芯片 4000万徕卡电影四摄 超曲面OLED环幕屏 超级快充 屏内指纹 4G全网通双卡双待手机","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/PZb27s22DXZd6xLLH9fdD27SBDbdbs.jpg","credit":1000000,"money":"0.00","type":0},{"gid":"wOWdXsam9I2yMT8ZWev2WY9onAVwMg","title":"小米手环4石墨黑 AI彩屏心率运动手环游泳姿势识别50米防水6轴传感器24小时高精准心率监测","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/iia9dG5C51snUN17dFfuaviNv1WDzz.jpg","credit":20000,"money":"0.00","type":0},{"gid":"yNyapfk9c88y4dwt2C26vSGongMw","title":"现货速发【现货国行4788元起-可分期】Apple 苹果 iPhone 11 手机 黑色 全网通 64GB","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/UtR25Z7c3g96k593k7mat956g99rm9.jpg","credit":500000,"money":"499.00","type":0}]
         */

        private int total;
        private List<CategoryBean> category;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class CategoryBean {
            /**
             * cate_id : ydDz1brqnDznIWxH2CGqQ9NrmAMw
             * cate_name : 3C
             */

            private String cate_id;
            private String cate_name;

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }
        }

        public static class DataBean {
            /**
             * gid : yh9YedTy_jnX_RaC4MjZWo1GWfeonvMQMg
             * title : 小米（MI）小爱智能音箱Pro 无线蓝牙音响 语音控制智能家居 APP远程操控 专业DTS 音效 黑色
             * thumb : http://test.gtt20.com/attachment/images/2/2020/05/Q1AGEArDieZwDIA1MMWZdZUgM1NDv7.jpg
             * credit : 300000
             * money : 0.00
             * type : 0
             */

            private String gid;
            private String title;
            private String thumb;
            private int credit;
            private String money;
            private int type;
            private int lotterydraws; //是否抽奖 0：否 1：是

            public int getLotterydraws() {
                return lotterydraws;
            }

            public void setLotterydraws(int lotterydraws) {
                this.lotterydraws = lotterydraws;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
