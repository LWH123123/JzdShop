package com.jzd.jzshop.ui.home.creditsstore.lottery;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditLotteryAtyBinding;
import com.jzd.jzshop.entity.LotteryEntity;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.ui.home.creditsstore.pay.CreditPaySuccessAty;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.slodonapp.ywj_release.wxapi.WXPayEntryActivity;

import java.io.IOException;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description :
 * @date 2020/5/19.
 */
public class CreditLotteryViewModel extends BaseViewModel<Repository> {

    public CreditLotteryViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private ActivityCreditLotteryAtyBinding binding;
    public void setbinding(ActivityCreditLotteryAtyBinding binding) {
        this.binding=binding;
    }

    //预支付接口
    public void requestPayOrder(String type,String gid,int potionid,String addid,int num){
        isShowDialog(false);
        addSubscribe(model.postCreditTopay(model.getUserToken(),type,gid,potionid,addid,num),new ParseDisposableTokenErrorObserver<OrderToPayEntity>(){

            @Override
            public void onResult(OrderToPayEntity o) {
                super.onResult(o);
                dismissDialog();
                binding.conlt.setVisibility(View.VISIBLE);


                    //正式兑换
                    reuquestLottery();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                finish();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }
        });



    }


    //正式兑换
    public void reuquestLottery (){
        binding.conlt.setVisibility(View.VISIBLE);
        isShowDialog(false);
        addSubscribe(model.postCreditLottery(model.getUserToken()),new ParseDisposableTokenErrorObserver<LotteryEntity>(){
            @Override
            public void onResult(LotteryEntity o) {
                super.onResult(o);
                dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GOODS_KEY_CREDIT_PAY_RESULT,o.getResult().getStatus());
                bundle.putString(Constants.GOODS_KEY_CREDIT_PAY_LOGID,o.getResult().getLog_id());
                bundle.putInt(Constants.GOODS_KEY_CREDIT_ISLOTTERY,1);
                startActivity(CreditPaySuccessAty.class,bundle);
                finish();
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
