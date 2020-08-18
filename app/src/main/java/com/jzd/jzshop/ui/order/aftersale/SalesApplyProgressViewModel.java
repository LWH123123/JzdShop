package com.jzd.jzshop.ui.order.aftersale;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivitySalesApplyProgressBinding;
import com.jzd.jzshop.entity.CancelRefundEntity;
import com.jzd.jzshop.entity.OrderRefundProgressEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.order.logistics.LogisticInfoFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import java.io.Serializable;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/1/16 11:33
 */
public class SalesApplyProgressViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = AfterSaleViewModel.class.getSimpleName();
    private Context context;
    private ActivitySalesApplyProgressBinding fragmentAfterSaleBinding;
    private String order_id;

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<OrderRefundProgressEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<CancelRefundEntity> cancleApplyLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent refundReceiveLiveData = new SingleLiveEvent<>();

    }

    public SalesApplyProgressViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public void setBinding(Context context, ActivitySalesApplyProgressBinding binding) {
        this.context = context;
        this.fragmentAfterSaleBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);

    }

    /**
     * 申请退款、售后（进度）
     *
     * @param order_id
     */
    public void requestData(String order_id) {
        this.order_id = order_id;
        Log.d(TAG, "order_id:====" + order_id);
        addSubscribe(model.postOrderRefundProgress(model.getUserToken(), order_id), new ParseDisposableTokenErrorObserver<OrderRefundProgressEntity>() {
            @Override
            public void onResult(OrderRefundProgressEntity orderRefundProgressEntity) {
                super.onResult(orderRefundProgressEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (orderRefundProgressEntity != null) {
                    OrderRefundProgressEntity.ResultBean result = orderRefundProgressEntity.getResult();
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


    //确认收到换货物品
    public BindingCommand confirmReceiptExchangeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderRefundProgressEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                String refund_id = value.getRefund_id();
                requestRefundReceive(refund_id);
            }

        }
    });

    //查看换货物流
    public BindingCommand exchangeLogisticsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderRefundProgressEntity.ResultBean value = uc.mLiveData.getValue();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEE_REFUND_EXPRESS, "1");
            bundle.putString(Constants.ORDER_ID, order_id);
            bundle.putString(Constants.REFUND_ID, value.getRefund_id());
            startContainerActivity(LogisticInfoFragment.class.getCanonicalName(), bundle);
        }
    });


    //填写快递单号 / 修改快递单号
    public BindingCommand fillExpressBillOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderRefundProgressEntity.ResultBean value = uc.mLiveData.getValue();
            OrderRefundProgressEntity.ResultBean.ExpressInfoBean express_info = value.getExpress_info();
            List<OrderRefundProgressEntity.ResultBean.ExpressDataBean> express_data = value.getExpress_data();
            if (express_info != null && express_data != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BUNDLE_KEY_EXPRESS_INFO, express_info);
                bundle.putSerializable(Constants.BUNDLE_KEY_EXPRESS_DATA, (Serializable) express_data);
                bundle.putString(Constants.ORDER_ID, order_id);
                bundle.putString(Constants.REFUND_ID, value.getRefund_id());
                startActivity(FillExpressBillAty.class, bundle);
            } else {
            }
        }
    });

    //修改申请
    public BindingCommand modifyApplyOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_TITLE, context.getResources().getString(R.string.apply_after_sale));
            bundle.putString(Constants.ORDER_ID, order_id);
            startActivity(ApplyForRefundAty.class, bundle);
        }
    });

    //取消申请
    public BindingCommand cancelOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderRefundProgressEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                String refund_id = value.getRefund_id();
                cancelRefund(refund_id);
            }
        }
    });


    /**
     * 申请退款、售后（取消）
     *
     * @param refund_id
     */
    public void cancelRefund(String refund_id) {
        addSubscribe(model.postCancelRefundOrder(model.getUserToken(), order_id, refund_id), new ParseDisposableTokenErrorObserver<CancelRefundEntity>() {
            @Override
            public void onResult(CancelRefundEntity cancelRefundEntity) {
                super.onResult(cancelRefundEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (cancelRefundEntity != null) {
                    ToastUtils.showShort("已取消申请！");
                    uc.cancleApplyLiveData.setValue(cancelRefundEntity);
                }
                dismissDialog();
                finish();
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
     * 申请退款、售后（收货）
     *
     * @param refund_id
     */
    public void requestRefundReceive(String refund_id) {
        addSubscribe(model.postRefundReceive(model.getUserToken(), order_id, refund_id), new ParseDisposableTokenErrorObserver<Object>() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                Log.d(TAG, "onSuccess:---->>>");
                uc.refundReceiveLiveData.setValue(o);
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
