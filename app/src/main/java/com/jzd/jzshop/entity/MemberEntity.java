package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MemberEntity implements Serializable {
    /**
     * result : {"avatar":"WmnXGxkPFMJp"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * avatar : WmnXGxkPFMJp
         */
        private String avatar;
        private double money;
        private String nickname;
        private double points;
        private String commission_url;
        private String commissionwith_url;
        private String memberwith_url;
        private String sign_url;
        private String couponmy_url;
        private String favorite_url;
        private String coupon_url;
        private String merch_url;
        private int dot0;
        private int dot1;
        private int dot2;
        private int dot4;
        //二期改版 新增字段
        private int invit_id;
        private double money2;
        private String recommand_name; //推荐人
        private String level; //等级
        private String group_url; //我的团队
        private String myshop_url; //我的小店

        public String getGroup_url() {
            return group_url;
        }

        public void setGroup_url(String group_url) {
            this.group_url = group_url;
        }

        public String getMyshop_url() {
            return myshop_url;
        }

        public void setMyshop_url(String myshop_url) {
            this.myshop_url = myshop_url;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getInvit_id() {
            return invit_id;
        }

        public void setInvit_id(int invit_id) {
            this.invit_id = invit_id;
        }

        public double getMoney2() {
            return money2;
        }

        public void setMoney2(double money2) {
            this.money2 = money2;
        }

        public String getRecommand_name() {
            return recommand_name;
        }

        public void setRecommand_name(String recommand_name) {
            this.recommand_name = recommand_name;
        }

        public int getDot0() {
            return dot0;
        }

        public void setDot0(int dot0) {
            this.dot0 = dot0;
        }

        public int getDot1() {
            return dot1;
        }

        public void setDot1(int dot1) {
            this.dot1 = dot1;
        }

        public int getDot2() {
            return dot2;
        }

        public void setDot2(int dot2) {
            this.dot2 = dot2;
        }

        public int getDot4() {
            return dot4;
        }

        public void setDot4(int dot4) {
            this.dot4 = dot4;
        }

        public String getCommission_url() {
            return commission_url;
        }

        public void setCommission_url(String commission_url) {
            this.commission_url = commission_url;
        }

        public String getCommissionwith_url() {
            return commissionwith_url;
        }

        public void setCommissionwith_url(String commissionwith_url) {
            this.commissionwith_url = commissionwith_url;
        }

        public String getMemberwith_url() {
            return memberwith_url;
        }

        public void setMemberwith_url(String memberwith_url) {
            this.memberwith_url = memberwith_url;
        }

        public String getSign_url() {
            return sign_url;
        }

        public void setSign_url(String sign_url) {
            this.sign_url = sign_url;
        }

        public String getCouponmy_url() {
            return couponmy_url;
        }

        public void setCouponmy_url(String couponmy_url) {
            this.couponmy_url = couponmy_url;
        }

        public String getFavorite_url() {
            return favorite_url;
        }

        public void setFavorite_url(String favorite_url) {
            this.favorite_url = favorite_url;
        }

        public String getCoupon_url() {
            return coupon_url;
        }

        public void setCoupon_url(String coupon_url) {
            this.coupon_url = coupon_url;
        }

        public String getMerch_url() {
            return merch_url;
        }

        public void setMerch_url(String merch_url) {
            this.merch_url = merch_url;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getNickname() {
            try {
                return URLDecoder.decode(nickname, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public double getPoints() {
            return points;
        }

        public void setPoints(double points) {
            this.points = points;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String user_token) {
            this.avatar = user_token;
        }
    }
}
