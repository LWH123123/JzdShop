package com.jzd.jzshop.chat.model;

import java.util.Objects;

/**
 * @author LXB
 * @description: 客服对话 消息实体
 * @date :2020/4/24 17:25
 */
public class ChatMessageResponse {
    private int is_self;
    private int read;
    private String ltype;
    private String avatar;
    private String nickname;
    private String msg_id;
    private int msg_type;
    private String msg;
    private String msg_time;
    private String sendMsg_type; //手动添加 发送消息类型
    private int status;

    public String getSendMsg_type() {
        return sendMsg_type;
    }

    public void setSendMsg_type(String sendMsg_type) {
        this.sendMsg_type = sendMsg_type;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ChatMessageResponse() {
    }

    public ChatMessageResponse(int is_self, int read, String ltype, String avatar, String nickname, String msg_id, int msg_type, String msg, String msg_time, int status) {
        this.is_self = is_self;
        this.read = read;
        this.ltype = ltype;
        this.avatar = avatar;
        this.nickname = nickname;
        this.msg_id = msg_id;
        this.msg_type = msg_type;
        this.msg = msg;
        this.msg_time = msg_time;
        this.status = status;
    }


    /**
     * 去掉重复的自定义的对象 需重写 equals 方法，否则通过 list.contains() 去重无效
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageResponse that = (ChatMessageResponse) o;
        return is_self == that.is_self &&
                read == that.read &&
                msg_type == that.msg_type &&
                status == that.status &&
                Objects.equals(ltype, that.ltype) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(msg_id, that.msg_id) &&
                Objects.equals(msg, that.msg) &&
                Objects.equals(msg_time, that.msg_time) &&
                Objects.equals(sendMsg_type, that.sendMsg_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(is_self, read, ltype, avatar, nickname, msg_id, msg_type, msg, msg_time, sendMsg_type, status);
    }
}
