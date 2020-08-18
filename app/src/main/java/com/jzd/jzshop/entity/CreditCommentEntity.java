package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2020/5/15 9:54
 */
public class CreditCommentEntity {

    /**
     * result : {"status":1,"log_id":"mtcPHPqrTO0CcfADuDGWdZkpncF9poMw","shopname":"HUAWEI尚品","gid":"wdzJuXm9jmAyMZDOmW2WamconwMw","title":"小米手环4石墨黑 AI彩屏心率运动手环游泳姿势识别50米防水6轴传感器24小时高精准心率监测","option":null,"thumb":"http://test.gtt20.com/attachment/images/2/2020/05/iia9dG5C51snUN17dFfuaviNv1WDzz.jpg","credit":20000,"money":"0.00","number":1}
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
         * status : 1
         * log_id : mtcPHPqrTO0CcfADuDGWdZkpncF9poMw
         * shopname : HUAWEI尚品
         * gid : wdzJuXm9jmAyMZDOmW2WamconwMw
         * title : 小米手环4石墨黑 AI彩屏心率运动手环游泳姿势识别50米防水6轴传感器24小时高精准心率监测
         * option : null
         * thumb : http://test.gtt20.com/attachment/images/2/2020/05/iia9dG5C51snUN17dFfuaviNv1WDzz.jpg
         * credit : 20000
         * money : 0.00
         * number : 1
         */

        private int status;
        private String log_id;
        private String shopname;
        private String gid;
        private String title;
        private String option;
        private String thumb;
        private int credit;
        private String money;
        private int number;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
