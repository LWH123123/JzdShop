package com.jzd.jzshop.ui.mine.recharge;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.home.news.HomePageActivity;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class RechargeSuccessViewModel extends BaseViewModel {
    public RechargeSuccessViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    public BindingCommand onBackHomeClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(HomePageActivity.class);
        }
    });

    /*充值记录*/
    public BindingCommand onClikcRechargeRecord =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RechargeRecordAty.class);
        }
    });

    public BindingCommand onbackClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          finish();
        }
    });


}
