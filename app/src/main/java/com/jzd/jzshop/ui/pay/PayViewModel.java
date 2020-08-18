package com.jzd.jzshop.ui.pay;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.OrderPayEntity;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.entity.PayShowEntity;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.slodonapp.ywj_release.wxapi.WXPayEntryActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PayViewModel extends BaseViewModel<Repository> {
    private static final String TAG = PayViewModel.class.getSimpleName();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    private FragmentActivity mAty;

    private PayFragment payFragment;

    public void setFragment(PayFragment payFragment) {
        this.payFragment = payFragment;
    }

    public class UIChangeObservable {
        public SingleLiveEvent<List<OrderPayEntity.ResultBean.PayDataBean>> updataItemUI = new SingleLiveEvent<>();
        public SingleLiveEvent<String> payAlipay = new SingleLiveEvent<>();
        public SingleLiveEvent<String> onBackListener = new SingleLiveEvent<>();
        public SingleLiveEvent back = new SingleLiveEvent();
    }

    public ObservableField<OrderPayEntity.ResultBean> entity = new ObservableField<>();
    public ObservableField<PayShowEntity> payshow = new ObservableField<>(new PayShowEntity(0, 0, 0, 0));
    public String order_id;
    public String pay_type;
    private Context mContext;


    public PayViewModel(@NonNull Application application, Repository model) {
        super(application, model);
        mContext = application.getApplicationContext();
    }

    public void requestOrderPay(String order_id/*,double money*/) {
        addSubscribe(model.postOrderPay(model.getUserToken(), order_id), new ParseDisposableObserver<OrderPayEntity>() {
            @Override
            public void onResult(OrderPayEntity orderPayEntity) {
                dismissDialog();
                KLog.i("结算金额", orderPayEntity.getResult().getOrder_price());
                entity.set(orderPayEntity.getResult());
                uc.updataItemUI.setValue(orderPayEntity.getResult().getPay_data());
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });
    }

    public void requestOrderToPay(final String pay_type) {
        addSubscribe(model.postOrderToPay(model.getUserToken(), order_id, pay_type), new ParseDisposableObserver<OrderToPayEntity>() {
            @Override
            public void onResult(OrderToPayEntity orderToPayEntity) {
                dismissDialog();
                if (pay_type.equals("wechat")) {
                    WXPayEntryActivity.payWeixin(AppApplication.sApi, orderToPayEntity.getResult().getData(), new WXEntryActivity.ApiCallback() {
                        @Override
                        public void onSuccess(Object response) {
                            KLog.d(response);
                            KLog.a("微信支付", "成功了");
                            //---------------友盟自定义 支付成功 事件-------------
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("orderId", order_id);
                            map.put("payType", pay_type);
                            MobclickAgent.onEvent(mContext, "pay_succ_id", map);
                            //---------------友盟自定义 支付成功 事件-------------
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.BUNDLE_KEY_ORDERID, order_id);
                            bundle.putString(Constants.BUNDLE_KEY_PAY_TYPE, pay_type);
                            startContainerActivity(PaySuccessFragment.class.getCanonicalName(), bundle);
                            String openFlag = model.getOpenFlag();
                            if (openFlag != null && openFlag.equals("3")) {//关闭支付页和确认订单页
                                onBackPressed();  //h5过来
                            } else {//原生过来
                                AppManager.getAppManager().finishActivity(FirmOrderActivity.class);
                                AppManager.getAppManager().finishActivity(ContainerActivity.class);
                                Messenger.getDefault().register(this, PaySuccessViewModel.TOKEN_VIEWMODEL_OPENFLAG, new BindingAction() {
                                    @Override
                                    public void call() {//关闭收银台
                                        finish();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onError(int errorCode, String errorMsg) {
                            KLog.a("微信支付", "失败了");
                        }
                        @Override
                        public void onFailure(IOException e) {
                            KLog.a("微信支付", "异常了");
                        }
                        @Override
                        public void onPayError(String res) {
                            KLog.a("微信支付", res);
                            if (res.equals("支付失败")) {
                                uc.back.call();
                            }

                        }
                    });
                } else if (pay_type.equals("alipay")) {
                    uc.payAlipay.setValue(orderToPayEntity.getResult().getData().getString());
                }
            }
            @Override
            public void onError(String errarMessage) {
                dismissDialog();


            }
        });
    }


    public BindingCommand onClickFinsh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2019/12/12  根据标识skip  0 详情页skip 订单详情；1 购车车skip 我的订单页
            mCommonMethod();
        }
    });

    public void mCommonMethod() {
        uc.back.call();
        String openFlag = model.getOpenFlag();
        /*if (openFlag != null && openFlag.equals("3")) { //商品详情发送
            goToBaseWebviewNew(Constants.MY_ORDER_DETAILS, openFlag);
        } else if (openFlag != null && openFlag.equals("1")) {//购物车发送
            goToBaseWebviewNew(Constants.MY_ORDER, openFlag);
        } else {//原生过来
        }*/
//        payFragment.getActivity().finish();
    }

    //余额支付
    public BindingCommand onClickCreditPay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setpayShow();
            payshow.get().setBalance(1);
            payshow.notifyChange();
            //credit
            String user_money = entity.get().getUser_money();
            String order_price = entity.get().getOrder_price();
            double user = Double.parseDouble(user_money);
            double order = Double.parseDouble(order_price);
            if (user < order) {
                ToastUtils.showLong("余额不足,请选择其他支付方式");
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_ORDERID, order_id);
                bundle.putString(Constants.BUNDLE_KEY_PAY_TYPE, "credit");
                startContainerActivity(PaySuccessFragment.class.getCanonicalName(), bundle);
                payFragment.getActivity().finish();
            }
        }
    });


    public BindingCommand onClickPayWX = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setpayShow();
            payshow.get().setWx(1);
            payshow.notifyChange();
            requestOrderToPay("wechat");
        }
    });
    public BindingCommand onClickPayAlipay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setpayShow();
            payshow.get().setZfb(1);
            payshow.notifyChange();
            requestOrderToPay("alipay");
        }
    });

    @Override
    public void onBackPressed() {
        uc.back.call();
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish: ---->>>");
        uc.onBackListener.setValue("0");
        super.finish();
    }

    /**
     * 订单详情页
     *
     * @param orderStatus
     * @param openFlag
     */
    private void goToBaseWebviewNew(String orderStatus, String openFlag) {
        int netState = NetworkUtils.getNetWorkState(mContext);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        String encode;
        if (openFlag.equals("0")) {
            String urlF = orderStatus + order_id;
            encode = URLEncoder.encode(urlF); //url参数转义
        } else {
            encode = orderStatus;
        }
        if (!TextUtils.isEmpty(model.getUserToken())) {
            String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + encode + "&user_token=" + model.getUserToken();
            Log.d(TAG, "goToBaseWebviewNew:" + url);
            bundle.putString(Constants.BUNDLE_KEY_URL, url);
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }


    private void setpayShow() {
        payshow.get().setBalance(0);
        payshow.get().setForother(0);
        payshow.get().setWx(0);
        payshow.get().setZfb(0);
    }


}
