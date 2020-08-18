package com.jzd.jzshop.entity;

import android.text.TextUtils;


import com.jzd.jzshop.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import me.goldze.mvvmhabit.utils.Utils;

public class OrderPayEntity {

    /**
     * result : {"order_sn":"SH20191120114955342482","order_price":"0.01","user_money":"0.00","pay_data":[{"id":"wechat"},{"id":"credit"}]}
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
         * order_sn : SH20191120114955342482
         * order_price : 0.01
         * user_money : 0.00
         * pay_data : [{"id":"wechat"},{"id":"credit"}]
         */

        private String order_sn;
        private String order_price;
        private String user_money;
        private List<PayDataBean> pay_data;

        public String getOrder_sn() {
            return order_sn;
        }

        public String getOrder_snText() {
            return String.format(Utils.getContext().getResources().getString(R.string.orderNumber), order_sn);
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_price() {
            return order_price;
        }
        public String getOrder_priceText() {
            return num2thousand00(order_price);
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getUser_money() {
            return user_money;
        }
        public String getUser_moneyText() {
            return "¥"+user_money;
        }
        /**
         * 字符串 千位符  保留两位小数点后两位
         *
         * @param num
         * @return
         */
        public  String num2thousand00(String num) {
            String numStr = "";
            if (TextUtils.isEmpty(num)) {
                return numStr;
            }
            NumberFormat nf = NumberFormat.getInstance();
            try {
                DecimalFormat df = new DecimalFormat("#,##0.00");
                numStr = df.format(nf.parse(num));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return numStr;
        }
        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }

        public List<PayDataBean> getPay_data() {
            return pay_data;
        }

        public void setPay_data(List<PayDataBean> pay_data) {
            this.pay_data = pay_data;
        }

        public static class PayDataBean {
            /**
             * id : wechat
             */

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
