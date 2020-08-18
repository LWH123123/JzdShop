package com.jzd.jzshop.ui.mine.setting.aboutapp;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.MoreProductJumpLinkAty;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LWH
 * @description:
 * @date :2019/12/26 17:07
 */
public class AboutappViewModel extends BaseViewModel {
    public AboutappViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
    //特别声明
    public BindingCommand onClickSpecialstatement =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String special=Constants.URL.specialstatement;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_URL,RetrofitClient.baseUrl+special);
            bundle.putString(Constants.BUNDLE_KEY_TITLE,"特别声明");
            startActivity(MoreProductJumpLinkAty.class, bundle);
        }
    });


    //给我评分
    public BindingCommand onClickGivemegrade =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showLong("跳转商城给我评分!");
        }
    });

    //隐私政策
    public BindingCommand onClickPrivacypolicy =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String privacy=Constants.URL.privacypolicy;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_URL,RetrofitClient.baseUrl+privacy);
            bundle.putString(Constants.BUNDLE_KEY_TITLE,"隐私政策");
            startActivity(MoreProductJumpLinkAty.class, bundle);
        }
    });

    //软件许可服务协议
    public BindingCommand onClickLicence =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String xieyi=Constants.URL.service;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_URL,RetrofitClient.baseUrl+xieyi);
            bundle.putString(Constants.BUNDLE_KEY_TITLE,"软件服务许可协议");
            startActivity(MoreProductJumpLinkAty.class, bundle);
        }
    });

    //返回
    public BindingCommand onClickFinish =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

}
