package com.jzd.jzshop.chat.http;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.google.gson.Gson;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.chat.model.ChatHistoryModel;
import com.jzd.jzshop.chat.model.GoodsAddShopCarEntity;
import com.jzd.jzshop.entity.GoodsEntity;
import com.jzd.jzshop.entity.GoodsSpecEntity;
import com.jzd.jzshop.ui.goods.GoodsViewModel;
import com.jzd.jzshop.ui.goods.PopGoodsSpecItemViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.net_utils.RSASignature;
import com.jzd.jzshop.utils.net_utils.RSAUtils2;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LXB
 * @description:
 * @date :2020/4/26 15:58
 */
public class HttpRequestData {
    private static final String TAG = HttpRequestData.class.getSimpleName();
    private GoodsEntity.ResultBean goodsEntity;
    public ObservableField<GoodsEntity.ResultBean> entity = new ObservableField<>();
    public  ObservableField<GoodsSpecEntity.ResultBean> specEntity = new ObservableField<>();
    //属性
    public ObservableList<GoodsSpecEntity.ResultBean.ListBean> pg_spec_ob = new ObservableArrayList<>();
    public ObservableField<Integer> badgenumber = new ObservableField<>();
    private int myTotal;

    public HttpRequestData() {
    }

    public void setParams(GoodsEntity.ResultBean goodsInfo){
        this.goodsEntity = goodsInfo;
        entity.set(goodsInfo);
    }

    /**
     *  获取 商品规格
     * @param token
     * @param gid
     */
    public  void doPostGoodsSpce(String token, String gid) {
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        Map<String, String> params = new HashMap<>();

        HashMap<String, String> otherParams = new HashMap<>();
        otherParams.put("gid", gid);
        String postBodyString = ParameterToJsonUtils.createJsonString(token, AppApplication.myDeviceID, otherParams);
        String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
        String sign = ParameterToJsonUtils.encrypSign(postBodyString);

        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);
        OkHttpUtils.post()
                .url(RetrofitClient.baseUrl + Constants.URL.goodsSpce)
                .params(params)
                .headers(headers)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     *  把商品加入购物车
     * @param userToken
     * @param gid
     * @param optionID
     * @param myTotal
     */
    public void doPostAddShoppingcar(String userToken, String gid, String optionID, int myTotal) {
        this.myTotal = myTotal;
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        Map<String, String> params = new HashMap<>();

        HashMap<String, String> otherParams = new HashMap<>();
        otherParams.put("gid", gid);
        otherParams.put("optionid", optionID);
        otherParams.put("total", String.valueOf(myTotal));
        String postBodyString = ParameterToJsonUtils.createJsonString(userToken, AppApplication.myDeviceID, otherParams);
        String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
        String sign = ParameterToJsonUtils.encrypSign(postBodyString);

        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);
        OkHttpUtils.post()
                .url(RetrofitClient.baseUrl + Constants.URL.goodsAddShopcar)
                .params(params)
                .headers(headers)
                .build()
                .execute(new AddShoppingcarCallback());
    }

    public  class AddShoppingcarCallback extends StringCallback {
        public AddShoppingcarCallback() {
        }

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "客服对话 加入购物车 onResponse：complete" + response);
            Gson gson = new Gson();
            ChatHistoryModel chatHistoryModel = gson.fromJson(response, ChatHistoryModel.class); //解密前的模型 都一样，无需重复定义
            if (chatHistoryModel != null) {
                int code = chatHistoryModel.getCode();
                if (code == 200) {
                    String resJson = new String(RSAUtils2.decode(chatHistoryModel.getResData()));
                    if (StringUtils.isEmpty(resJson)) {
                        ToastUtils.showShort("解密异常");
                        return;
                    }
                    boolean sign = RSASignature.doCheck(resJson, chatHistoryModel.getSign());
                    if (!sign) {
                        ToastUtils.showShort("签名异常");
                        return;
                    }
                    KLog.d(TAG, "客服对话 加入购物车 解密后数据：" + resJson);
                    //==========解密后真正需要的数据实体=======start======
                    GoodsAddShopCarEntity goodsAddShopCarEntity = new Gson().fromJson(resJson, GoodsAddShopCarEntity.class);
                    if (goodsAddShopCarEntity != null) {
                        ToastUtils.showLong("商品已加入购物车");

//                        uc.closePopwindow.call();
                        int shopCarnumber = SPUtils.getInstance().getInt(Constants.SP.SHOPCAR_NUMBER);
                        shopCarnumber += myTotal;
                        badgenumber.set(shopCarnumber);
                        badgenumber.notifyChange();
                        SPUtils.getInstance().put(Constants.SP.SHOPCAR_NUMBER,shopCarnumber);
                    }
                    //==========解密后真正需要的数据实体=========end======
                } else {
                    ToastUtils.showShort(chatHistoryModel.getMsg());
                    return;
                }
            }
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void setDefault() {
        if (specEntity.get() != null) {
            entity.get().setPrice(specEntity.get().getDefault_price());
            entity.get().setTotal(specEntity.get().getTotal());
            entity.get().setUrl(specEntity.get().getThumb());
            entity.notifyChange();
        }
    }

    public ObservableField<GoodsSpecEntity.ResultBean> getSpecEntity() {
        return specEntity;
    }

    public  class MyStringCallback extends StringCallback {
        public MyStringCallback() {
        }

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "获取商品规格 onResponse：complete" + response);
            Gson gson = new Gson();
            ChatHistoryModel chatHistoryModel = gson.fromJson(response, ChatHistoryModel.class); //解密前的模型 都一样，无需重复定义
            if (chatHistoryModel != null) {
                int code = chatHistoryModel.getCode();
                if (code == 200) {
                    String resJson = new String(RSAUtils2.decode(chatHistoryModel.getResData()));
                    if (StringUtils.isEmpty(resJson)) {
                        ToastUtils.showShort("解密异常");
                        return;
                    }
                    boolean sign = RSASignature.doCheck(resJson, chatHistoryModel.getSign());
                    if (!sign) {
                        ToastUtils.showShort("签名异常");
                        return;
                    }
                    KLog.d(TAG, "获取商品规格 解密后数据：" + resJson);
                    //==========解密后真正需要的数据实体=======start======
                    GoodsSpecEntity goodsSpecEntity = new Gson().fromJson(resJson, GoodsSpecEntity.class);
                    if (goodsSpecEntity != null) {
                        specEntity.set(goodsSpecEntity.getResult());
                        setDefault();
                        for (GoodsSpecEntity.ResultBean.ListBean listBean : goodsSpecEntity.getResult().getList()) {
                            pg_spec_ob.add(listBean);
                        }
                    }
                    //==========解密后真正需要的数据实体=========end======
                } else {
                    ToastUtils.showShort(chatHistoryModel.getMsg());
                    return;
                }
            }
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }
}
