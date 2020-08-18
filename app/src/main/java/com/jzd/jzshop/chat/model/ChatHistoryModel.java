package com.jzd.jzshop.chat.model;

/**
 * @author LXB
 * @description:
 * @date :2020/4/24 16:26
 */
public class ChatHistoryModel {
    /**
     * code : 200
     * msg : SUCCESS
     * resData : p4l3GFqfwnAnL7J4K34LP7hIo1WWDzWL4+npmtybDa+rdAJEuIBhd/CzEWgeT0nTynR58Ojql1rAA+Unc8QRCcdwzurIlLyUTp/9eV3aPClh0mZUKhweom9AUzUQKeVKeqM8WIENaWq7KlnQOUsF28phVA7BZgu6pXHv7uCQn4xUvfMSeHAgzPZFw+AHIg8o6QWZfSMGnyJMkG07VVv/gQyW5XMkLQR31AchBGEm8fHdaCNRbllWSvYhjB0v+Wgpl0qBY67eaZyQd9hhj8Oy7wjq9CA6EZn6Ejhc8LZAqFFA51aA3XStlDzjh82/YWO2XkCBrIwz34pkyPQsCBQ==
     * sign : V7ZlUHJGkanc0gWVdknsiN9mz5DZNG2Hx/EaAXza
     */

    private int code;
    private String msg;
    private String resData;
    private String sign;

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

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
