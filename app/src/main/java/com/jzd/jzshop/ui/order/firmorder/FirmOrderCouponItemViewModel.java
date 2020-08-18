package com.jzd.jzshop.ui.order.firmorder;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.FirmOrderEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class FirmOrderCouponItemViewModel extends ItemViewModel<FirmOrderViewModel> {
    public ObservableField<FirmOrderEntity.ResultBean.CouponDataBean> entity = new ObservableField<>();

    public FirmOrderCouponItemViewModel(@NonNull FirmOrderViewModel viewModel, FirmOrderEntity.ResultBean.CouponDataBean goodsBean) {
        super(viewModel);
        this.entity.set(goodsBean);
    }


}
