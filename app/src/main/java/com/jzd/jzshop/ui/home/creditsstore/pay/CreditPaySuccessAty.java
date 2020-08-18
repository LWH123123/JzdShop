package com.jzd.jzshop.ui.home.creditsstore.pay;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditPaySuccessAtyBinding;
import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderActivity;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.home.creditsstore.lottery.CreditLotteryAty;
import com.jzd.jzshop.ui.home.creditsstore.order_detail.CreditOrderDetailActivity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;

public class CreditPaySuccessAty extends BaseActivity<ActivityCreditPaySuccessAtyBinding,CreditPaySuccessViewModel> {


    int status;
    String logid;
    int islottery;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_pay_success_aty;
    }

    @Override
    public int initVariableId() {
        return BR.creditpaysuccessVM;
    }

    @Override
    public CreditPaySuccessViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditPaySuccessViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        getIntentData();

        setData();

    }

    private void setData() {
        if(islottery==1){//抽奖
            if(status==1){//没中奖
                binding.ivPayok.setImageResource(R.mipmap.ic_error);
                binding.tvPaydata.setText(getResources().getText(R.string.nolottery));
                binding.tvPayok.setText("知道了");
            }else if(status==2){//中奖了
                binding.ivPayok.setImageResource(R.mipmap.paysuccess);
                binding.tvPaydata.setText(getResources().getText(R.string.oklottery));
            }
        }else if(islottery==0){
            if(status==1){//兑换失败
                binding.ivPayok.setImageResource(R.mipmap.ic_error);
                binding.tvPaydata.setText(getResources().getText(R.string.nopay));
            }else if(status==2){//兑换成功
                binding.ivPayok.setImageResource(R.mipmap.paysuccess);
                binding.tvPaydata.setText(getResources().getText(R.string.okpay));
            }
        }

    }

    private void getIntentData() {
        Bundle intent = getIntent().getExtras();
        if(intent==null){
            return;
        }
        status = intent.getInt(Constants.GOODS_KEY_CREDIT_PAY_RESULT,0);
        logid = intent.getString(Constants.GOODS_KEY_CREDIT_PAY_LOGID);
        islottery = intent.getInt(Constants.GOODS_KEY_CREDIT_ISLOTTERY,0);


    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.back.observe(CreditPaySuccessAty.this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if(status==1){//没中奖
                    /*AppManager.getAppManager().finishActivity(CreditLotteryAty.class);
                    AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
                    AppManager.getAppManager().finishActivity(CreditPayActivity.class);
                    AppManager.getAppManager().finishActivity(CreditGoodsDetailsActivity.class);*/
                    AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
                    finish();
                }else if(status==2){
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GOODS_KEY_GID,logid);
                    startActivity(CreditOrderDetailActivity.class,bundle);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
