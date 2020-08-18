package com.jzd.jzshop.chat.model;

/**
 * @author LXB
 * @description:
 * @date :2020/4/22 17:28
 */
public class ChatMessageEntiity {

    /**
     * code : 200
     * msg : SUCCESS
     * resData : {"result":{"type":"send","data":{"is_self":1,"read":0,"ltype":"member","avatar":"http://test.gtt20.com/attachment/images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png","nickname":"power king","msg_id":"DyCYMeuyP_AXN_ACK4oZkWoGpWMoenIQMQ","msg_type":0,"msg":"龙膜","msg_time":1587548174}}}
     */

    private int code;
    private String msg;
    private ResDataBean resData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResDataBean getResData() {
        return resData;
    }

    public void setResData(ResDataBean resData) {
        this.resData = resData;
    }

    public static class ResDataBean {
        /**
         * result : {"type":"send","data":{"is_self":1,"read":0,"ltype":"member","avatar":"http://test.gtt20.com/attachment/images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png","nickname":"power king","msg_id":"DyCYMeuyP_AXN_ACK4oZkWoGpWMoenIQMQ","msg_type":0,"msg":"龙膜","msg_time":1587548174}}
         */

        private ResultBean result;

        public ResDataBean() {
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            public ResultBean() {
            }

            /**
             * type : send
             * data : {"is_self":1,"read":0,"ltype":"member","avatar":"http://test.gtt20.com/attachment/images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png","nickname":"power king","msg_id":"DyCYMeuyP_AXN_ACK4oZkWoGpWMoenIQMQ","msg_type":0,"msg":"龙膜","msg_time":1587548174}
             */


            private String type;
            private DataBean data;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean {
                public DataBean() {
                }

                /**
                 * is_self : 1
                 * read : 0
                 * ltype : member
                 * avatar : http://test.gtt20.com/attachment/images/2/2019/12/lH7q1Z8X7MIXdmXyC8AB1XDQ07H2Bx.png
                 * nickname : power king
                 * msg_id : DyCYMeuyP_AXN_ACK4oZkWoGpWMoenIQMQ
                 * msg_type : 0
                 * msg : 龙膜
                 * msg_time : 1587548174
                 */



                private int is_self; //0：非自己 1：自己
                private int read;
                private String ltype;
                private String avatar;
                private String nickname;
                private String msg_id;
                private int msg_type;
                private String msg;
                private String msg_time;
                // 获取窗口消息 中的字段，手动添加 在websocket收到消息实体中，可能会用到
                private int status;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }


                public int getIs_self() {
                    return is_self;
                }

                public void setIs_self(int is_self) {
                    this.is_self = is_self;
                }

                public int getRead() {
                    return read;
                }

                public void setRead(int read) {
                    this.read = read;
                }

                public String getLtype() {
                    return ltype;
                }

                public void setLtype(String ltype) {
                    this.ltype = ltype;
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

                public String getMsg_time() {
                    return msg_time;
                }

                public void setMsg_time(String msg_time) {
                    this.msg_time = msg_time;
                }
            }
        }
    }
}
