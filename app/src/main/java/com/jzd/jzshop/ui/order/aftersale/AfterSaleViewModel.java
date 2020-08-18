package com.jzd.jzshop.ui.order.aftersale;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityApplyForRefundBinding;
import com.jzd.jzshop.entity.OrderRefundEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LWH
 * @description:
 * @date :2020/1/15 11:19
 */
public class AfterSaleViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AfterSaleViewModel.class.getSimpleName();
    private Context context;
    private ActivityApplyForRefundBinding fragmentAfterSaleBinding;
    private String order_id;
    public static final String TOKEN_AFTERSALEVIEWMODEL_MODIFY_APPLY_REFRESH = "token_aftersaleviewmodel_modify_apply_refresh_list_data";

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<OrderRefundEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent cancleOrderLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent submitLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent modifyLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent confirmReceiptLiveData = new SingleLiveEvent<>();

    }

    public AfterSaleViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityApplyForRefundBinding binding) {
        this.context = context;
        this.fragmentAfterSaleBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);

    }

    /**
     * 申请退款、售后（展示）
     *
     * @param order_id
     */
    public void requestData(String order_id) {
        this.order_id = order_id;
        addSubscribe(model.postOrderRefund(model.getUserToken(), order_id), new ParseDisposableTokenErrorObserver<OrderRefundEntity>() {
            @Override
            public void onResult(OrderRefundEntity orderRefundEntity) {
                super.onResult(orderRefundEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (orderRefundEntity != null) {
                    OrderRefundEntity.ResultBean result = orderRefundEntity.getResult();
                    if (result != null) {
                        uc.mLiveData.setValue(result);
                    }
                }
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });

    }


    /**
     * 申请退款、售后（提交）
     *
     * @param order_id
     */
    public void submitApplayData(String order_id, String refund_type, String reason, String content, String image, GridImageAdapter adapter) {
        Map<String, String> params = new HashMap<>();
        List<LocalMedias> list = adapter.getList();
        HashMap<String, String> otherParams = new HashMap<>();
        otherParams.put("order_id", order_id);
        otherParams.put("refund_type", refund_type);
        otherParams.put("reason", reason);
        otherParams.put("content", content);
        otherParams.put("imageaddimage",adapter.httpImg());
        String postBodyString = ParameterToJsonUtils.createJsonString(model.getUserToken(), AppApplication.myDeviceID,
                otherParams);
        String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
        String sign = ParameterToJsonUtils.encrypSign(postBodyString);
        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);

        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                LocalMedias localMedias = list.get(i);
                if(localMedias.getFile()!=null){
                postFormBuilder.addFile("image_" + (i + 1), localMedias.getFile().getName(), localMedias.getFile());
                }
            }
        }
        postFormBuilder
                .addHeader("Content-Type", "multipart/form-data;")
                .url(RetrofitClient.baseUrl + Constants.URL.refund_submit)
                .params(params)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

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
            Log.d(TAG, "onResponse：complete" + response);
            ToastUtils.showShort("提交申请成功！");
            uc.submitLiveData.setValue(response);
            uc.modifyLiveData.setValue(response);
            finish();

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

    //取消
    public BindingCommand cancelOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    /**
     * 申请退款、售后（取消）
     */
    public void cancelRefund() {
        addSubscribe(model.postCancelRefundOrder(model.getUserToken(), order_id, ""), new ParseDisposableTokenErrorObserver<OrderRefundEntity>() {
            @Override
            public void onResult(OrderRefundEntity orderRefundEntity) {
                super.onResult(orderRefundEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (orderRefundEntity != null) {
                    OrderRefundEntity.ResultBean result = orderRefundEntity.getResult();
                    if (result != null) {
                        uc.mLiveData.setValue(result);
                    }
                }
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });
    }


    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
