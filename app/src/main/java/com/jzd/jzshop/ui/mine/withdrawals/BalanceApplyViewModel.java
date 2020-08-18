package com.jzd.jzshop.ui.mine.withdrawals;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.home.news.HomePageActivity;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class BalanceApplyViewModel extends BaseViewModel {
    public BalanceApplyViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public BindingCommand onbackClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });




    public BindingCommand onBackHomeClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
             startActivity(HomePageActivity.class);
        }
    });

    /*查看提现记录*/
    public  BindingCommand onSeeBalanceRecord =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          startActivity(WithdrawalsRecordAty.class);
        }
    });

}
