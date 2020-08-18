package com.jzd.jzshop.ui.mine.recharge;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentRechargeBinding;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.SystemUtils;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.slodonapp.ywj_release.wxapi.WXPayEntryActivity;

import java.io.IOException;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class RechargeViewModel extends BaseViewModel<Repository> {

    public static final String ALIPAY_REFRESH_MONEY = "zfb_pay_refresh_money";
    public ObservableField<String> money =new ObservableField<>();
    public SingleLiveEvent<String> zfb =new SingleLiveEvent<>();

    private boolean ischeck;
    private FragmentRechargeBinding binding;
    private RechargeFragment fragment;

    public RechargeViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBind(FragmentRechargeBinding binding, RechargeFragment rechargeFragment) {
        this.binding=binding;
        this.fragment=rechargeFragment;
    }


    public BindingCommand onBackView =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //充值记录
    public BindingCommand RechargeRecordOnClickCommand =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RechargeRecordAty.class);
        }
    });


    /*微信充值*/
    public BindingCommand onWxClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(binding.chWx.isChecked()){
            binding.chZfb.setChecked(false);



            }

        }
    });

    /*支付宝充值*/

    public BindingCommand onZfbClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(binding.chZfb.isChecked()){
                binding.chWx.setChecked(false);


            }
        }
    });


    /*充值*/
    public BindingCommand onRechargeClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(!TextUtils.isEmpty(money.get())){
                if(binding.chWx.isChecked()){
                    recgarge(money.get(),"wechat");
                    return;
                }
                if(binding.chZfb.isChecked()){
                    recgarge(money.get(),"alipay");
                    return;
                }
                ToastUtils.showLong("请选择支付方式.");

            }else {
                ToastUtils.showLong("请输入充值金额!");
            }

        }
    });



    public void recgarge(final String money, String pay_type){
        addSubscribe(model.postRecharge(model.getUserToken(),money,null,pay_type),new ParseDisposableTokenErrorObserver<OrderToPayEntity>(){
            @Override
            public void onResult(OrderToPayEntity o) {
                super.onResult(o);
                dismissDialog();
                String pay_type1 = o.getResult().getPay_type();
                if(pay_type1.equals("wechat")){
                    WXPayEntryActivity.payWeixin(AppApplication.sApi, o.getResult().getData(), new WXEntryActivity.ApiCallback() {
                        @Override
                        public void onSuccess(Object response) {
                            rechargeSuccess("微信支付",money);
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
                        }
                    });

                }
                else if(pay_type1.equals("alipay")){
                    zfb.setValue(o.getResult().getData().getString());
                }

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



    public void rechargeSuccess(String type,String money){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RECHARGE_TIME,SystemUtils.getTime());
        bundle.putString(Constants.RECHARGE_TYPE,type);
        bundle.putString(Constants.RECHARGE_MONEY,money);
        startContainerActivity(RechargeSuccessFragment.class.getCanonicalName(),bundle);
    }



}
