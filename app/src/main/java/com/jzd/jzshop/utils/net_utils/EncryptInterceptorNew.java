package com.jzd.jzshop.utils.net_utils;

import android.util.Log;

import com.google.gson.JsonObject;
import com.jzd.jzshop.app.AppApplication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author LXB
 * @description: 此拦截器 对应okhttputils 图片上传
 * todo 上传图片  修改基类未解决，后期再处理
 * @date :2019/12/23 11:47
 */
@Deprecated
public class EncryptInterceptorNew implements Interceptor {

    private static final String TAG = EncryptInterceptorNew.class.getSimpleName();
    private static final String FORM_NAME = "content";
    private static final String CHARSET = "UTF-8";

    public static final String KEY_REQ_DATA = "reqData";
    public static final String KEY_SIGN = "sign";
    private Request newRequest;
    private FormBody.Builder bodyBuilder;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newRequestBuild = request.newBuilder();
        if (request.method().equals("GET")) {
            request = addGetParams(request);
            return chain.proceed(request);
        } else if (request.method().equals("POST")) {
            newRequest = addPostParams(request, newRequestBuild);
        }

        newRequest = newRequestBuild
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "zh")
                .build();

        Response response = chain.proceed(newRequest);
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
//        return chain.proceed(newRequest);

    }

    //get请求 添加公共参数 签名
    private Request addGetParams(Request request) {
        //添加公共参数
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("guid", AppApplication.myDeviceID)
                .addQueryParameter("os", "android")
                .build();

        //添加签名
//        Set<String> nameSet = httpUrl.queryParameterNames();
//        ArrayList<String> nameList = new ArrayList<>();
//        nameList.addAll(nameSet);
//        Collections.sort(nameList);
//
//        StringBuilder buffer = new StringBuilder();
//        for (int i = 0; i < nameList.size(); i++) {
//            buffer.append("&").append(nameList.get(i)).append("=").append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
//                    httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
//        }
//        httpUrl = httpUrl.newBuilder()
//                .addQueryParameter("sign", MD5Util.MD5(buffer.toString()))
//                .build();

        request = request.newBuilder().url(httpUrl).build();
        return request;
    }


    //post请求 添加公共参数 签名
    private Request addPostParams(Request request, Request.Builder newRequestBuild) throws UnsupportedEncodingException {
        String postBodyString = "";
        RequestBody body = request.body();
        postBodyString = bodyToString(body);
        Log.d("postBodyString:加密前===", postBodyString);
//        if (request.body() instanceof FormBody) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
//        FormBody formBody = (FormBody) request.body();
        //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
//        for (int i = 0; i < formBody.size(); i++) {
//            bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
//        }
//
//        formBody = bodyBuilder
//                .addEncoded("guid", AppApplication.myDeviceID)
//                .addEncoded("os", "android")
//                .build();
        //==============================添加签名start ==============================
//        Map<String, String> bodyMap = new HashMap<>();
//        List<String> nameList = new ArrayList<>();
//
//        for (int i = 0; i < formBody.size(); i++) {
//            nameList.add(formBody.encodedName(i));
//            bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
//        }
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < nameList.size(); i++) {
//            if (nameList.get(i) != null && nameList.get(i).equals("avatar")) { //拦截图片 不进行加密处理
//                Log.d(TAG, "avatar key:====" + formBody.encodedName(i));
//                Log.d(TAG, "avatar value:====" + formBody.encodedValue(i));
//                break;
//            } else {
//                builder.append("&").append(nameList.get(i)).append("=")
//                        .append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
//            }
//        }
        /**=============加密请求参数jsonString=============*/
        String jsonStr = createJsonString(postBodyString);
        KLog.json("jsonStr加密前===" + jsonStr);
        String reqData = "";
        try {
            reqData = RSAUtils2.encode(jsonStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sign = RSASignature.sign(jsonStr);
        KLog.d("reqdata=" + reqData);
        KLog.d("sign=" + sign);
        /**============加密请求参数jsonString==============*/

        bodyBuilder.add(KEY_REQ_DATA, reqData);
        bodyBuilder.add(KEY_SIGN, sign).build();
        newRequestBuild = request.newBuilder();

        RequestBody formBody = bodyBuilder.build();
        postBodyString = ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
        KLog.json("postBodyString:加密后===", postBodyString);
        newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));


//            formBody = bodyBuilder.
//                    addEncoded("sign", MD5Util.MD5(builder.toString()))
//                    .build();
        //==============================添加签名end ==============================
//            request = request.newBuilder().post(formBody).build();
//        }
        return newRequest;
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


    /**
     * 把参数封装成json
     *
     * @param postBodyString
     * @return
     */
    private static String createJsonString(String postBodyString) {
        JsonObject jsonObject = new JsonObject();
        try {
//            postBodyString = (URLDecoder.decode(postBodyString, "UTF-8"));
            postBodyString = (URLDecoder.decode(postBodyString.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        KLog.d(postBodyString);
        String[] params = postBodyString.split("&");
        for (String param : params) {
            String[] temp = param.split("=");
            if (temp == null || temp.length < 2)
                continue;
            StringBuffer block = new StringBuffer();
            for (int i = 1; i < temp.length; i++) {
                block.append(temp[i]);
            }
            jsonObject.addProperty(temp[0], block.toString());
//            jsonObject.addProperty(temp[0],temp[1]);
        }
        return jsonObject.toString();
    }


    ///==========================================================================


    /**
     * 重构Request增加公共参数
     *
     * @param request
     * @return
     */
    public Request handlerRequest(Request request) {
        Map<String, String> params = parseParams(request);
        if (params == null) {
            params = new HashMap<>();
        }
        //这里为公共的参数
        params.put("guid", AppApplication.myDeviceID);
        params.put("os", "android");
        String method = request.method();
        if ("GET".equals(method)) {
            //添加公共参数
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .addQueryParameter("guid", AppApplication.myDeviceID)
                    .addQueryParameter("os", "android")
                    .build();
            return request.newBuilder().url(httpUrl.toString()).build();


        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            if (request.body() instanceof FormBody) {
                bodyBuilder = new FormBody.Builder();
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                while (entryIterator.hasNext()) {
                    String key = entryIterator.next().getKey();
                    String value = entryIterator.next().getValue();
                    if (key != null && key.equals("avatar")) { //拦截图片 不进行加密处理
                        Log.d(TAG, "avatar key:====" + key);
                        Log.d(TAG, "avatar value:====" + key);
                        break;
                    } else {
                        bodyBuilder.add(key, value);
                    }
                }

            }
        }
        return request.newBuilder().method(method, bodyBuilder.build()).build();
    }


    /**
     * 解析出请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> parseParams(Request request) {
        //GET POST DELETE PUT PATCH
        String method = request.method();
        Map<String, String> params = null;
        if ("GET".equals(method)) {
            params = doGet(request);
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            RequestBody body = request.body();
            if (body != null && body instanceof FormBody) {
                params = doForm(request);
            }
        }
        return params;
    }

    /**
     * 获取get方式的请求参数
     *
     * @param request
     * @return
     */
    private static Map<String, String> doGet(Request request) {
        Map<String, String> params = null;
        HttpUrl url = request.url();
        Set<String> strings = url.queryParameterNames();
        if (strings != null) {
            Iterator<String> iterator = strings.iterator();
            params = new HashMap<>();
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = url.queryParameterValue(i);
                params.put(name, value);
                i++;
            }
        }
        return params;
    }

    /**
     * 获取表单的请求参数
     *
     * @param request
     * @return
     */
    private static Map<String, String> doForm(Request request) {
        Map<String, String> params = null;
        FormBody body = null;
        try {
            body = (FormBody) request.body();
        } catch (ClassCastException c) {
        }
        if (body != null) {
            int size = body.size();
            if (size > 0) {
                params = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    params.put(body.name(i), body.value(i));
                }
            }
        }
        return params;
    }

    /**
     * 保证所有的 head 都是符合编码要求
     *
     * @param headInfo
     * @return
     */
    private static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }


}
