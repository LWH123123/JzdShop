package com.jzd.jzshop.test.net;



import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @author LXB
 * @description: 网路请求类
 * tip：测试 多图+ 其他字段 请求接口失败  创建该类
 * @date :2019/12/26 17:45
 */
public class APIWrapper extends RetrofitUtil {


    private static APIWrapper mAPIWrapper;

    public APIWrapper() {
    }

    public static APIWrapper getInstance() {
        if (mAPIWrapper == null) {
            mAPIWrapper = new APIWrapper();
        }
        return mAPIWrapper;
    }

    public Observable<HttpResponse<List<String>>> uploadMultipleTypeFile(String reqData, String sign, String des, Map<String, RequestBody> params) {
        return getAPIService().uploadMultipleTypeFile(reqData,sign,des, params);
    }
}
