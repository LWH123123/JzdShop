package com.jzd.jzshop.utils;

import android.util.Log;

import com.jzd.jzshop.utils.net_utils.RSASignature;
import com.jzd.jzshop.utils.net_utils.RSAUtils2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author LXB
 * @description: 请求参数转json 数据   只针对图片上传处理
 * 后台需要把参数拼接成json串的方式提交，或者参数需要拦截加密做验签
 * @date :2019/12/24 16:18
 * 参阅博文：https://blog.csdn.net/sinat_32194985/article/details/77866131
 */
public class ParameterToJsonUtils {
    private static final String TAG = ParameterToJsonUtils.class.getSimpleName();

    /**
     * 完善资料 多图上传
     *
     * @param userToken
     * @param myDeviceID
     * @param myself
     * @return
     */

    public static String createJsonString(String userToken, String myDeviceID,
                                           HashMap<String,String> myself) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("os","android");
            json.put("guid", myDeviceID);
            json.put("user_token", userToken);
            for (HashMap.Entry<String, String> entry : myself.entrySet()) {
                json.put(entry.getKey(),entry.getValue());
            }
            /*json.put("diyziwojieshao", diyziwojieshao);
            json.put("diynidemingzi", name);
            json.put("diyxueli", education);
            json.put("diyxingquaihao", selfintroduce);
            json.put("diyshenfenzhenghaoma", idcard);
            json.put("diychushengriqi", databrith);
            json.put("diybiyeriqi", graduatestart);
            json.put("diyhujidi", places);
            json.put("diyzhuzhisuozaidi", location);
            json.put("diymima", email);
            json.put("diyshuijueshijian", sacktime);
            json.put("diyshangbanshijian", workstart);
            json.put("diynianling", selectList);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
        String postBodyString = bodyToString(requestBody);
        Log.d(TAG, "postBodyString====:" + postBodyString);
        return postBodyString;
    }


    /**
     * 参数转字符串
     *
     * @param userToken
     * @param myDeviceID
     * @return
     */
    public static String createJsonString(String userToken, String myDeviceID) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("user_token", userToken);
            json.put("guid", myDeviceID);
            json.put("os", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
        String postBodyString = bodyToString(requestBody);
        Log.d(TAG, "postBodyString====:" + postBodyString);
        return postBodyString;
    }

    /**
     * 获取加密reqData
     *
     * @param userToken
     * @param myDeviceID
     * @return
     */
    public static String encrypReqData(String userToken, String myDeviceID) {
        String postBodyString = createJsonString(userToken, myDeviceID);
        String reqData = "";
        try {
            reqData = RSAUtils2.encode(postBodyString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        KLog.d(TAG + "reqdata====" + reqData);
        return reqData;
    }

    /**
     * 获取加密sign
     *
     * @param userToken
     * @param myDeviceID
     * @return
     */
    public static String encrypSign(String userToken, String myDeviceID) {
        String postBodyString = createJsonString(userToken, myDeviceID);
        String sign = RSASignature.sign(postBodyString);
        KLog.d(TAG + "sign====" + sign);
        return sign;
    }

    public static String encrypReqData(String postBodyString) {
        String reqData = "";
        try {
            reqData = RSAUtils2.encode(postBodyString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        KLog.d(TAG + "reqdata====" + reqData);
        return reqData;
    }

    public static String encrypSign(String postBodyString) {
        String sign = RSASignature.sign(postBodyString);
        KLog.d(TAG + "sign====" + sign);
        return sign;
    }


    /**
     * @param request
     * @return
     */
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


}
