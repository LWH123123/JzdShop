package com.jzd.jzshop.ui.home.creditsstore.pay;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderActivity;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;


/**
 * @author lwh
 * @description :
 * @date 2020/5/15.
 */
public class CreditPaySuccessViewModel extends BaseViewModel {
    public SingleLiveEvent back =new SingleLiveEvent();

    public CreditPaySuccessViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }



    public BindingCommand onClickPaySuccessListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            back.call();
        }
    });

}
