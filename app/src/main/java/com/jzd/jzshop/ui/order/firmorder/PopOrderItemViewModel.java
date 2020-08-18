package com.jzd.jzshop.ui.order.firmorder;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.FirmOrderEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class PopOrderItemViewModel extends ItemViewModel<FirmOrderViewModel> {

    public ObservableField<FirmOrderEntity.ResultBean.CouponDataBean> entity=new ObservableField<>();
    public PopOrderItemViewModel(@NonNull FirmOrderViewModel viewModel, FirmOrderEntity.ResultBean.CouponDataBean dataBean) {
        super(viewModel);
        this.entity.set(dataBean);
    }
}
