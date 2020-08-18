package com.jzd.jzshop.ui.mine.couponcenter;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.CouponCenterEntity;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LWH
 * @description:
 * @date :2020/1/1 15:08
 */
public class CouponCenterItemViewModel extends ItemViewModel<CouponCenterViewModel> {

    public ObservableField<CouponCenterEntity.ResultBean.DataBean> entity = new ObservableField<>();

    public CouponCenterItemViewModel(@NonNull CouponCenterViewModel viewModel, CouponCenterEntity.ResultBean.DataBean dataBean) {
        super(viewModel);
        this.entity.set(dataBean);
    }

    public BindingCommand onClickdescribe = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int showdescribe = entity.get().getShowdescribe();
            if (showdescribe == 0) {
                entity.get().setShowdescribe(1);
            } else {
                entity.get().setShowdescribe(0);
            }
            entity.notifyChange();
        }
    });

    //领取优惠券
    public BindingCommand onClickGetCoupon = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showLong("领取优惠券");
        }
    });

}
