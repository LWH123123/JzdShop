package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/4/24.
 */
public class MineTeamEntity {

    /**
     * result : {"total":1,"data":[{"nickname":"向阳而生","avatar":"http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg","count":1,"time":"2019-12-05 20:08:41","isfans":1}]}
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
         * total : 1
         * data : [{"nickname":"向阳而生","avatar":"http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg","count":1,"time":"2019-12-05 20:08:41","isfans":1}]
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * nickname : 向阳而生
             * avatar : http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg
             * count : 1
             * time : 2019-12-05 20:08:41
             * isfans : 1
             */

            private String nickname;
            private String avatar;
            private int count;
            private String time;
            private int isfans;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getIsfans() {
                return isfans;
            }

            public void setIsfans(int isfans) {
                this.isfans = isfans;
            }
        }
    }
}
