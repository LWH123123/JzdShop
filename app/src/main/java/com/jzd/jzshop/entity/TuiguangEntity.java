package com.jzd.jzshop.entity;

/**
 * @author LWH
 * @description:
 * @date :2020/1/3 16:41
 */
public class TuiguangEntity {

    /**
     * result : {"invit_id":117,"avatar":"http://test.gtt20.com/attachment/images/2/2020/01/iGwpTYPqt6KtsPNwPs5gyctGmDq5mD.jpg","nickname":"城煦源","recommand_name":"","level":"默认等级","commission_pay":"0.00","commission_ok":"0.00","commission_total":"0.00","commission_check":"0.00","commission_apply":"0.00","commission_fail":"0.00","commission_wait":"0.00","commission_lock":"0.00","order_count":0,"apply_count":0,"down_count":0}
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
         * invit_id : 117
         * avatar : http://test.gtt20.com/attachment/images/2/2020/01/iGwpTYPqt6KtsPNwPs5gyctGmDq5mD.jpg
         * nickname : 城煦源
         * recommand_name :
         * level : 默认等级
         * commission_pay : 0.00
         * commission_ok : 0.00
         * commission_total : 0.00
         * commission_check : 0.00
         * commission_apply : 0.00
         * commission_fail : 0.00
         * commission_wait : 0.00
         * commission_lock : 0.00
         * order_count : 0
         * apply_count : 0
         * down_count : 0
         */

        private int invit_id;
        private String avatar;
        private String nickname;
        private String recommand_name;
        private String level;
        private String commission_pay;
        private String commission_ok;
        private String commission_total;
        private String commission_check;
        private String commission_apply;
        private String commission_fail;
        private String commission_wait;
        private String commission_lock;
        private int order_count;
        private int apply_count;
        private int down_count;

        public int getInvit_id() {
            return invit_id;
        }

        public void setInvit_id(int invit_id) {
            this.invit_id = invit_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRecommand_name() {
            return recommand_name;
        }

        public void setRecommand_name(String recommand_name) {
            this.recommand_name = recommand_name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCommission_pay() {
            return commission_pay;
        }

        public void setCommission_pay(String commission_pay) {
            this.commission_pay = commission_pay;
        }

        public String getCommission_ok() {
            return commission_ok;
        }

        public void setCommission_ok(String commission_ok) {
            this.commission_ok = commission_ok;
        }

        public String getCommission_total() {
            return commission_total;
        }

        public void setCommission_total(String commission_total) {
            this.commission_total = commission_total;
        }

        public String getCommission_check() {
            return commission_check;
        }

        public void setCommission_check(String commission_check) {
            this.commission_check = commission_check;
        }

        public String getCommission_apply() {
            return commission_apply;
        }

        public void setCommission_apply(String commission_apply) {
            this.commission_apply = commission_apply;
        }

        public String getCommission_fail() {
            return commission_fail;
        }

        public void setCommission_fail(String commission_fail) {
            this.commission_fail = commission_fail;
        }

        public String getCommission_wait() {
            return commission_wait;
        }

        public void setCommission_wait(String commission_wait) {
            this.commission_wait = commission_wait;
        }

        public String getCommission_lock() {
            return commission_lock;
        }

        public void setCommission_lock(String commission_lock) {
            this.commission_lock = commission_lock;
        }

        public int getOrder_count() {
            return order_count;
        }

        public void setOrder_count(int order_count) {
            this.order_count = order_count;
        }

        public int getApply_count() {
            return apply_count;
        }

        public void setApply_count(int apply_count) {
            this.apply_count = apply_count;
        }

        public int getDown_count() {
            return down_count;
        }

        public void setDown_count(int down_count) {
            this.down_count = down_count;
        }
    }
}
