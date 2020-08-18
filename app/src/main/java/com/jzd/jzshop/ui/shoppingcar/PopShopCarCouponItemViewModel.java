package com.jzd.jzshop.ui.shoppingcar;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.entity.CouponEntity;
import com.jzd.jzshop.entity.GoodsEntity;
import com.jzd.jzshop.entity.ShoppcarEntry;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

@Deprecated
public class PopShopCarCouponItemViewModel extends ItemViewModel<ShoppingCarViewModel> {

    public ObservableField<CouponEntity> entry = new ObservableField();
    public PopShopCarCouponItemViewModel(@NonNull ShoppingCarViewModel viewModel, CouponEntity bean) {
        super(viewModel);
        entry.set(bean);
    }

    //立即领取
    public BindingCommand onClickGetCoupon=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.a("领取优惠券","====>>>>111"+entry.get().getCanget());
            if(entry.get().getCanget()==0){
                ToastUtils.showLong("该优惠券已经领取过了");
            }else {
                entry.notifyChange();
            }
        }
    });






}
