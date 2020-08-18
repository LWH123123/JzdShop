package com.jzd.jzshop.chat.model;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/24 16:16
 */
public class ChatHistoryEntity {

    /**
     * result : {"total":"274","data":[{"is_self":1,"avatar":"http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg","nickname":"向阳而生","msg_id":"262","msg_type":1,"msg":"images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png","time":"2020-04-24 14:32:31","read":1,"status":0}]}
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
         * total : 274
         * data : [{"is_self":1,"avatar":"http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg","nickname":"向阳而生","msg_id":"262","msg_type":1,"msg":"images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png","time":"2020-04-24 14:32:31","read":1,"status":0}]
         */

        private String total;
        private List<DataBean> data;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
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
             * is_self : 1
             * avatar : http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg
             * nickname : 向阳而生
             * msg_id : 262
             * msg_type : 1
             * msg : images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png
             * time : 2020-04-24 14:32:31
             * read : 1
             * status : 0
             */

            private int is_self;
            private String avatar;
            private String nickname;
            private String msg_id;
            private int msg_type;
            private String msg;
            private String time;
            private int read;
            private int status;

            public int getIs_self() {
                return is_self;
            }

            public void setIs_self(int is_self) {
                this.is_self = is_self;
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

            public String getMsg_id() {
                return msg_id;
            }

            public void setMsg_id(String msg_id) {
                this.msg_id = msg_id;
            }

            public int getMsg_type() {
                return msg_type;
            }

            public void setMsg_type(int msg_type) {
                this.msg_type = msg_type;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getRead() {
                return read;
            }

            public void setRead(int read) {
                this.read = read;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
