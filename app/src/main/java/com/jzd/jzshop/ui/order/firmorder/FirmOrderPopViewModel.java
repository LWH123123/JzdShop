package com.jzd.jzshop.ui.order.firmorder;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.FirmOrderEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class FirmOrderPopViewModel extends ItemViewModel<FirmOrderViewModel> {
    public ObservableField<FirmOrderEntity.ResultBean.CouponDataBean> entity=new ObservableField<>();
    public FirmOrderPopViewModel(@NonNull FirmOrderViewModel viewModel, FirmOrderEntity.ResultBean.CouponDataBean dataBean) {
        super(viewModel);
        entity.set(dataBean);
    }

    public BindingCommand onClickSelectCoupon =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           viewModel.selectCoupon(entity.get().getCoupon_log_id());
           entity.notifyChange();
        }
    });


}
