package com.jzd.jzshop.ui.login.bindmobile;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;

import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LWH
 * @description:绑定手机号
 * @date :2019/12/13 13:38
 */
public class BindMobileBViewModel extends ToolbarViewModel<Repository> {

    public ObservableField<String> mobile=new ObservableField<>();
    public ObservableField<String> verfity=new ObservableField<>();

    public UIChangeObservable uc = new UIChangeObservable();

    private BindMobileFragment fragment;
    public void setbindinig(BindMobileFragment bindMobileFragment) {
        this.fragment=bindMobileFragment;
    }

    public class UIChangeObservable {
        public SingleLiveEvent getvertify = new SingleLiveEvent<>();
        public SingleLiveEvent<String> back = new SingleLiveEvent<>();
    }


    public BindMobileBViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public void setTool(){
        setToolbarBgColor(fragment.getResources().getColor(R.color.white));
        setTitleText("绑定手机号");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }




    //获取验证码
    public BindingCommand ongetClickVerfity =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String phone = mobile.get();
            boolean mobileNO = SystemUtils.isMobileNO(phone);
            if(!mobileNO) {
                ToastUtils.showLong("请填写正确的手机号");
                return;
            }
            addSubscribe(model.postSendVertifyCode(phone,"bind"), new ParseDisposableObserver<BaseResponse>() {
                @Override
                public void onResult(BaseResponse sendVertifyEntity) {
                    dismissDialog();
                    uc.getvertify.call();
                    ToastUtils.showLong("验证码发送成功!");
                }
                @Override
                public void onError(String errarMessage) {
                    KLog.e("sendVerifyCode　　onError: －－－－＞＞＞" + errarMessage);
                    dismissDialog();
                }
            });

        }
    });





    //绑定手机号
    public BindingCommand onNowBindClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            bindingMobile();

        }
    });

    private void bindingMobile() {
        final String phone = mobile.get();
        boolean mobileNO = SystemUtils.isMobileNO(phone);
        if(!mobileNO) {
            ToastUtils.showLong("请填写正确的手机号");
            return;
        }
        KLog.a("参数",mobile.get()+"---。。。...》》》"+verfity.get());
        addSubscribe(model.postbindmobile(model.getUserToken(),mobile.get(),verfity.get()),new ParseDisposableTokenErrorObserver<BaseResponse>(){

            @Override
            public void onResult(BaseResponse baseResponse) {
                super.onResult(baseResponse);
                dismissDialog();
                ToastUtils.showLong("绑定成功");
                uc.back.setValue(phone);

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
