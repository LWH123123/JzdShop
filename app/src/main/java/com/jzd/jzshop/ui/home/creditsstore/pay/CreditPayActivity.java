package com.jzd.jzshop.ui.home.creditsstore.pay;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditPayBinding;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsViewModel;
import com.jzd.jzshop.ui.home.creditsstore.lottery.CreditLotteryAty;
import com.jzd.jzshop.ui.pay.PayResult;
import com.jzd.jzshop.utils.Constants;

import java.util.Map;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

public class CreditPayActivity extends BaseActivity<ActivityCreditPayBinding,CreditPayViewModel> {
    public String gid;
    public String addid;
    public int num;
    public int islottery;//是否为抽奖特殊处理
    public String optionid;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_pay;
    }

    @Override
    public int initVariableId() {
        return BR.creditpayVM;
    }

    @Override
    public CreditPayViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditPayViewModel.class);
    }

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mhandle =new Handler(){
        @SuppressWarnings("unused")
        public void handleMessage(Message msg){
            if (msg ==null)return;
            switch (msg.what){
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if(resultStatus.equals("9000")){//支付成功
                        if(islottery==0) {//不是抽奖 直接扣钱
                            viewModel.reuquestLottery();
                        }else {
                            //todo 去抽奖 调用正式兑换
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constants.GOODS_KEY_CREDIT_ISPAY,1);
                            startActivity(CreditLotteryAty.class,bundle);
                            finish();
                        }
                    }else {
                        ToastUtils.showLong("支付失败!");
                    }
                    break;
            }
        }
    };




    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(CreditPayActivity.this,false);
        StatusBarUtil.setStatusBarColor(CreditPayActivity.this, Color.parseColor("#D90804"));
        getIntentData();
        viewModel.setbinding(binding,CreditPayActivity.this,islottery);
        viewModel.requestWork(gid,optionid,addid,num);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gid = bundle.getString(Constants.GOODS_KEY_GID);
            optionid = bundle.getString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE);
            addid = bundle.getString(Constants.GOODS_KEY_CREDIT_ADDR_ID);
            num = bundle.getInt(Constants.GOODS_KEY_CREDIT_GOODS_NUMBER);
            islottery = bundle.getInt(Constants.GOODS_KEY_CREDIT_ISLOTTERY);
        }
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.zfbPay.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask payTask = new PayTask(CreditPayActivity.this);
                        Map<String, String> result = payTask.payV2(s, true);
                        String error = "aliPay error:(" + ")" + result.get("resultStatus") + "-" + result.get("result") + "-" + result.get("memo");
                        Log.d("PayFragment PayTask :", error);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mhandle.sendMessage(msg);
                    }
                };
                //异步调用
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
