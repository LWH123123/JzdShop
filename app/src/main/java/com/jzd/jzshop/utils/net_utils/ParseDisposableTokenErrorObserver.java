package com.jzd.jzshop.utils.net_utils;

import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;

import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * @author LXB
 * @description: 处理userToken 失效问题
 * @date :2019/12/3 17:37
 */
public class ParseDisposableTokenErrorObserver<T> extends ParseDisposableObserver<T> {

    protected void onTokenError() { }

    @Override
    public void onNext(Object baseResponse) {
        super.onNext(baseResponse);
        BaseResponse response = null;
        try {
            response = (BaseResponse) baseResponse;
            code = response.getCode();
            if (code == -1) {
                onTokenError();
            }
        } catch (java.lang.ClassCastException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResult(T t) {

    }


    @Override
    public void onError(String errarMessage) {

    }


}
