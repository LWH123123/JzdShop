package com.jzd.jzshop.ui.mine.mineshop.regist;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.home.news.HomePageActivity;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author lwh
 * @description :
 * @date 2020/4/3.
 */
public class MineShopRegistViewModel extends BaseViewModel {
    public MineShopRegistViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    //去逛逛
    public BindingCommand onToHomeClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           startActivity(HomePageActivity.class);
        }
    });

    public BindingCommand onBackClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


}
