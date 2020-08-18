package com.jzd.jzshop.utils.net_utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jzd.jzshop.entity.FirmOrderEntity;
import com.jzd.jzshop.entity.json_typeadapter.FirmOrderEntityAdapter;
import com.jzd.jzshop.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;

public abstract class ParseDisposableObserver<T> extends DisposableObserver {

    protected int code;

    /**
     * 必须要添加泛型才能正确解析
     *
     * @param t
     */
    public abstract void onResult(T t);

    public abstract void onError(String errarMessage);


    @Override
    public void onStart() {
        super.onStart();
        KLog.d("http is start");
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            KLog.d("无网络，读取缓存数据");
            onComplete();
            ToastUtils.showShort("网络异常，请检查您的网络是否正常！");
            return;
        }
    }

    @Override
    public void onNext(Object baseResponse) {
        BaseResponse response = null;
        try {
            response = (BaseResponse) baseResponse;
            code = response.getCode();
            if (response.getCode() == 200) {
                try {
                    String res = new String(RSAUtils2.decode(response.getResData()));//分段
                    if (StringUtils.isEmpty(res)) {
                        sendMessage("解密异常");
                        return;
                    }
                    boolean sign = RSASignature.doCheck(res, response.getSign());
                    if (!sign) {
                        sendMessage("签名异常");
                        return;
                    }
                    JSONObject jsonObject = new JSONObject(res);
                    //===============缓冲首页数据json串===================
                    String home_url_flag = SPUtils.getInstance().getString("home_url_flag");
                    if (home_url_flag.equals("index")){//是首页才缓冲json
                        SPUtils.getInstance().put("json_home_data",res);
                    }
                    //===============缓冲首页数据json串===================
                    //输出解密内容 方便调试
                    KLog.json(jsonObject.toString(1));
                    Class tClass = getTClass();
                    if (tClass == null) {
                        onResult((T) jsonObject);
                    } else {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        FirmOrderEntityAdapter adapter = new FirmOrderEntityAdapter();
                        gsonBuilder.registerTypeAdapter(FirmOrderEntity.class, adapter);
                        Gson gson = gsonBuilder.create();
                        Object entity = gson.fromJson(jsonObject.toString(), getTClass());
                        Object entitys = new GsonBuilder()
                                .registerTypeAdapter(
                                        FirmOrderEntity.class,
                                        new FirmOrderEntityAdapter())
                                .create()
                                .fromJson(
                                        jsonObject.toString(),
                                        getTClass());

                        onResult((T) entity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    sendMessage("数据解析错误");
                } catch (Exception e) {
                    e.printStackTrace();
                    sendMessage("未知错误！");
                }
            } else if (response.getCode() == 0) {
                ToastUtils.showShort(response.getMsg());
                sendMessage(response.getMsg());
            } else {
                //code错误时也可以定义Observable回调到View层去处理
                KLog.a("数据错误");
                sendMessage("数据错误");
            }

        } catch (java.lang.ClassCastException e) {
            e.printStackTrace();
            onResult((T) baseResponse);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof ResponseThrowable) {
            sendMessage(((ResponseThrowable) throwable).message);
        }
        throwable.printStackTrace();
        onError(throwable.getMessage());
    }

    /**
     * 可选 请求结束时调用
     */
    @Override
    public void onComplete() {
    }

    private void sendMessage(String message) {
        KLog.e("error:" + message);
        onError(message);
    }

    /**
     * 获取泛型参数
     * Java获取泛型T的类型 T.class - hellozhxy的博客 - CSDN博客
     * https://blog.csdn.net/hellozhxy/article/details/82024712
     *
     * @return
     */
    public Class<T> getTClass() {
        try {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return tClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
