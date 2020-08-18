package com.jzd.jzshop.ui.mine.merch;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class MerchSuccessViewModel extends BaseViewModel {
    public MerchSuccessViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }



    public BindingCommand onToHomeClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          startActivity(HomePageActivity.class);
        }
    });


    public BindingCommand onBackClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
         startActivity(MyPageActivity.class);
        }
    });

}
