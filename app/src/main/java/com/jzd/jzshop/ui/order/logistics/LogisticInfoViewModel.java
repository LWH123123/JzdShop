package com.jzd.jzshop.ui.order.logistics;

import android.app.Application;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.LogisticInfoEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 15:02
 */
public class LogisticInfoViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = LogisticInfoViewModel.class.getSimpleName();
    private String order_id, refund_id;
    public LogisticInfoViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public SingleLiveEvent update = new SingleLiveEvent();

    public ObservableField<LogisticInfoEntity.ResultBean> logistic = new ObservableField<>();

    public void setToolbar() {
        setTitleText("物流信息");
        setToolbarBgColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    public void requestword(String order, String sendtype, String bundle) {
//        城煦源:
//75327394316604

        addSubscribe(model.postLogisticDetails(model.getUserToken(), order, sendtype, bundle), new ParseDisposableTokenErrorObserver<LogisticInfoEntity>() {
            @Override
            public void onResult(LogisticInfoEntity o) {
                super.onResult(o);
                dismissDialog();
                LogisticInfoEntity.ResultBean result = o.getResult();
                result.setImaurl(result.getGoods().get(0).getThumb());
                result.setNumber(String.valueOf(result.getGoods().size()));
                logistic.set(result);
                update.call();

            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });
    }

    /**
     * 申请退款、售后（查看物流信息）
     * @param order_id
     * @param refund_id
     */
    public void postExchangeLogistics(String order_id, String refund_id) {
        this.order_id = order_id;
        this.refund_id = refund_id;
        addSubscribe(model.postExchangeLogistics(model.getUserToken(), order_id, refund_id),
                new ParseDisposableTokenErrorObserver<Object>() {
                    @Override
                    public void onResult(Object o) {
                        super.onResult(o);
                        Log.d(TAG, "onSuccess:---->>>");
                        Log.d(TAG, "==换货后物流信息==");
                        dismissDialog();
                    }

                    @Override
                    public void onError(String errarMessage) {
                        super.onError(errarMessage);
                        dismissDialog();
                    }

                    @Override
                    protected void onTokenError() {
                        super.onTokenError();
                        dismissDialog();
                        startActivity(LoginVertifyActivity.class);
                    }
                });

    }


}
