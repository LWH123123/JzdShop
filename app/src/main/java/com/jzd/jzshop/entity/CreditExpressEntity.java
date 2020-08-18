package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/5/15.
 */
public class CreditExpressEntity {

    /**
     * result : {"thumb":"http://test.gtt20.com/attachment/images/2/2020/05/UtR25Z7c3g96k593k7mat956g99rm9.jpg","express_com":"圆通速递","express_sn":"YT4367920064064","status":3,"data":[{"time":"2020-02-27 14:52:59","step":"客户签收人: 李武辉 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18001330306，投诉电话：010-67383552"},{"time":"2020-02-27 12:44:15","step":"【北京市朝阳区欢乐谷公司】 派件中  派件人: 尹立国 电话 18001330306  如有疑问，请联系：010-67383552"},{"time":"2020-02-27 06:35:58","step":"【北京市朝阳区欢乐谷公司】 已收入"},{"time":"2020-02-26 18:47:51","step":"【北京转运中心】 已发出 下一站 【北京市朝阳区欢乐谷公司】"},{"time":"2020-02-26 18:20:35","step":"【北京转运中心公司】 已收入"},{"time":"2020-02-25 01:49:38","step":"【广州转运中心】 已发出 下一站 【北京转运中心公司】"},{"time":"2020-02-25 01:28:42","step":"【广州转运中心公司】 已收入"},{"time":"2020-02-25 00:05:57","step":"【广东省广州市白云区罗冲围公司】 已打包"},{"time":"2020-02-24 23:49:12","step":"【广东省广州市白云区罗冲围】 已发出 下一站 【广州转运中心公司】"},{"time":"2020-02-24 18:33:45","step":"【广东省广州市白云区罗冲围公司】 已收件 取件人: 陈宇志 (19927534295)"}]}
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
         * thumb : http://test.gtt20.com/attachment/images/2/2020/05/UtR25Z7c3g96k593k7mat956g99rm9.jpg
         * express_com : 圆通速递
         * express_sn : YT4367920064064
         * status : 3
         * data : [{"time":"2020-02-27 14:52:59","step":"客户签收人: 李武辉 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18001330306，投诉电话：010-67383552"},{"time":"2020-02-27 12:44:15","step":"【北京市朝阳区欢乐谷公司】 派件中  派件人: 尹立国 电话 18001330306  如有疑问，请联系：010-67383552"},{"time":"2020-02-27 06:35:58","step":"【北京市朝阳区欢乐谷公司】 已收入"},{"time":"2020-02-26 18:47:51","step":"【北京转运中心】 已发出 下一站 【北京市朝阳区欢乐谷公司】"},{"time":"2020-02-26 18:20:35","step":"【北京转运中心公司】 已收入"},{"time":"2020-02-25 01:49:38","step":"【广州转运中心】 已发出 下一站 【北京转运中心公司】"},{"time":"2020-02-25 01:28:42","step":"【广州转运中心公司】 已收入"},{"time":"2020-02-25 00:05:57","step":"【广东省广州市白云区罗冲围公司】 已打包"},{"time":"2020-02-24 23:49:12","step":"【广东省广州市白云区罗冲围】 已发出 下一站 【广州转运中心公司】"},{"time":"2020-02-24 18:33:45","step":"【广东省广州市白云区罗冲围公司】 已收件 取件人: 陈宇志 (19927534295)"}]
         */

        private String thumb;
        private String express_com;
        private String express_sn;
        private int status;
        private List<DataBean> data;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * time : 2020-02-27 14:52:59
             * step : 客户签收人: 李武辉 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：18001330306，投诉电话：010-67383552
             */

            private String time;
            private String step;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStep() {
                return step;
            }

            public void setStep(String step) {
                this.step = step;
            }
        }
    }
}
