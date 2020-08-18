package com.jzd.jzshop.ui.base;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;


import com.jzd.jzshop.entity.CouponEntity;
import com.jzd.jzshop.utils.widget.CouponPop;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class CouponItemViewModel extends ItemViewModel {
    private static final String TAG = CouponItemViewModel.class.getSimpleName();
    public ObservableField<CouponEntity> entity = new ObservableField<>();

    public BindingCommand getOnclick() {
        return onclick;
    }

    public void setOnclick(BindingCommand onclick) {
        this.onclick = onclick;
    }

    public BindingCommand onclick;

    public CouponItemViewModel(@NonNull BaseViewModel viewModel, CouponEntity couponEntity) {
        super(viewModel);
        this.entity.set(couponEntity);
    }

    //立即领取
    public BindingCommand onClickGetCoupon = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.d(TAG, "onClickGetCoupon:--->>> get coupon");
            if (onclick != null) onclick.execute();
            //发送一个空消息
            //参数1：定义的token
            entity.notifyChange();
            Messenger.getDefault().sendNoMsg(CouponPop.TOKEN_COUPONPOP_DISMISS);  //发送领取购物券标识消息
        }
    });


    public BindingCommand onClickListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (onclick != null) onclick.execute();
        }
    });


}
