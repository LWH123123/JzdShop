package com.jzd.jzshop.ui.order.aftersale;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityFillExpressBillBinding;
import com.jzd.jzshop.entity.OrderRefundProgressEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/1/17 16:54
 */
public class FillExpressBillViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = FillExpressBillViewModel.class.getSimpleName();
    private Context context;
    private ActivityFillExpressBillBinding fillExpressBillBinding;
    public static final String TOKEN_VIEWMODEL_SUBMIT_EXPRESS_REFRESH = "token_viewmodel_submit_express_refresh_list_data";
    private String order_id, refund_id;
    private OrderRefundProgressEntity.ResultBean.ExpressInfoBean expressInfoBean;
    private List<OrderRefundProgressEntity.ResultBean.ExpressDataBean> expressData;

    //  传统 databinding 绑定数据
//    public ObservableField<String> expresssn = new ObservableField<>("");
//    public ObservableField<String> expressm = new ObservableField<>("");
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent expresssn = new SingleLiveEvent<String>();
        public SingleLiveEvent expressm = new SingleLiveEvent<String>();
    }

    public FillExpressBillViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityFillExpressBillBinding binding) {
        this.context = context;
        this.fillExpressBillBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    public void requestData(OrderRefundProgressEntity.ResultBean.ExpressInfoBean expressInfoBean, String order_id, String refund_id,
                            List<OrderRefundProgressEntity.ResultBean.ExpressDataBean> expressData) {
        this.expressInfoBean = expressInfoBean;
        this.order_id = order_id;
        this.refund_id = refund_id;
        this.expressData = expressData;
        if (expressInfoBean != null) {
//            expressm.set(expressInfoBean.getExpresscom());
//            expresssn.set(expressInfoBean.getExpresssn());
            uc.expressm.setValue(expressInfoBean.getExpresscom());
            uc.expresssn.setValue(expressInfoBean.getExpresssn());
            //expressInfoBean.getExpress()  物流公司简称
        }

    }

    /**
     * 申请退款、售后（填写物流单号）
     *
     * @param express
     * @param expresscom
     * @param expresssn
     */
    public void postSubmitExpress(String express, String expresscom, String expresssn) {
        addSubscribe(model.postSubmitExpress(model.getUserToken(), order_id, refund_id, express, expresscom, expresssn),
                new ParseDisposableTokenErrorObserver<Object>() {
                    @Override
                    public void onResult(Object o) {
                        super.onResult(o);
                        Log.d(TAG, "onSuccess:---->>>");
                        ToastUtils.showShort("快递单号提交成功！");
                        uc.mLiveData.setValue(o);
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

    //提交快递单号
    public BindingCommand submitExpressBillOnClick = new BindingCommand(new BindingAction() {
        private String express;

        @Override
        public void call() {
            if (expressInfoBean != null) {
                if (!TextUtils.isEmpty(expressInfoBean.getExpress()) && !TextUtils.isEmpty(expressInfoBean.getExpresscom())
                        && !TextUtils.isEmpty(expressInfoBean.getRexpresssn())) {
                    postSubmitExpress(expressInfoBean.getExpress(), expressInfoBean.getExpresscom(), expressInfoBean.getRexpresssn());
                } else { //首次填写
                    //物流公司简称
//                    String expressms = expressm.get();
                    String expressms = (String) uc.expressm.getValue();
                    for (int i = 0; i < expressData.size(); i++) {
                        if (expressms.equals(expressData.get(i).getExpress_name())) {
                            express = expressData.get(i).getExpress_id();
                            break;
                        }
                    }
//                    String expressn = expresssn.get();
                    String expressn = (String) uc.expresssn.getValue();
                    if (TextUtils.isEmpty(expressms)) {
                        ToastUtils.showShort("请填写快递公司！");
                        return;
                    }
                    if (TextUtils.isEmpty(expressn)) {
                        ToastUtils.showShort("请填写快递单号！");
                        return;
                    }
                    postSubmitExpress(express, expressms, expressn);
                }
            }
        }
    });
    //返回
    public BindingCommand goBackOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

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
