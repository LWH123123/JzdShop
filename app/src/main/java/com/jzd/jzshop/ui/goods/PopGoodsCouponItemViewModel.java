package com.jzd.jzshop.ui.goods;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.GoodsEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PopGoodsCouponItemViewModel extends ItemViewModel<GoodsViewModel> {
    public ObservableField<GoodsEntity.ResultBean.CouponDataBean> entity = new ObservableField<>();

    public PopGoodsCouponItemViewModel(@NonNull GoodsViewModel viewModel, GoodsEntity.ResultBean.CouponDataBean couponDataBean) {
        super(viewModel);
        entity.set(couponDataBean);
    }

    //立即领取
    public BindingCommand onClickGetCoupon=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.a("领取优惠券","popwindows");
        }
    });

    public BindingCommand onClickListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showLong("领取成功");
        }
    });

}
