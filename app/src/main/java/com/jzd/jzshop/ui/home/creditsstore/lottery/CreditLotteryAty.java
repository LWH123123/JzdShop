package com.jzd.jzshop.ui.home.creditsstore.lottery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditLotteryAtyBinding;
import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderActivity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;

public class CreditLotteryAty extends BaseActivity<ActivityCreditLotteryAtyBinding,CreditLotteryViewModel> {


    private Animation animation;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_lottery_aty;
    }

    @Override
    public int initVariableId() {
        return BR.creditlotteryVM;
    }

    String addid;
    int optionid;
    String gid;
    int num;
    int ispay;//是否为收银台过来的

    @Override
    public CreditLotteryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditLotteryViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setbinding(binding);
        //开启动画
        startAnimation();


        getIntentData();
        if(ispay==0){//如果从确认订单过来  按正常顺序走
            viewModel.requestPayOrder("default", gid, optionid, addid, num);
        }else if(ispay==1){//如果是抽奖 调用正式兑换
            viewModel.reuquestLottery();//正式兑换;
        }

    }

    private void startAnimation() {
        animation = AnimationUtils.loadAnimation(CreditLotteryAty.this, R.anim.anim_ratate_point);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        binding.ivPoint.startAnimation(animation);
    }

    private void getIntentData() {
        Bundle intent = getIntent().getExtras();
        if(intent==null){
            return;
        }

        gid=intent.getString(Constants.GOODS_KEY_GID);
        addid=intent.getString(Constants.GOODS_KEY_CREDIT_ADDR_ID);
        String option=intent.getString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE);
        if(!TextUtils.isEmpty(option)) {
            optionid = Integer.parseInt(option);
        }
        num=intent.getInt(Constants.GOODS_KEY_CREDIT_GOODS_NUMBER,0);
        ispay=intent.getInt(Constants.GOODS_KEY_CREDIT_ISPAY,0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        animation.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
        finish();
    }
}
