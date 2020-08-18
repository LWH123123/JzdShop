package com.jzd.jzshop.entity;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsEntity {

    /**
     * result : {"jz_cnt":"0.00000000","jz_money":"0.00000000"}
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
         * jz_cnt : 0.00000000
         * jz_money : 0.00000000
         */

        private String jz_cnt;
        private String jz_money;

        public String getJz_cnt() {
            return jz_cnt;
        }

        public void setJz_cnt(String jz_cnt) {
            this.jz_cnt = jz_cnt;
        }

        public String getJz_money() {
            return jz_money;
        }

        public void setJz_money(String jz_money) {
            this.jz_money = jz_money;
        }
    }
}
