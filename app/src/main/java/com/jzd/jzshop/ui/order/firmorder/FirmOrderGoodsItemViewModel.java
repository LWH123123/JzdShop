package com.jzd.jzshop.ui.order.firmorder;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.FirmOrderEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

//第一版本暂时没用上
public class FirmOrderGoodsItemViewModel extends ItemViewModel<FirmOrderViewModel> {
    public ObservableField<FirmOrderEntity.ResultBean.DataBean.GoodsBean> entity = new ObservableField<>();
    public FirmOrderGoodsItemViewModel(@NonNull FirmOrderViewModel viewModel, FirmOrderEntity.ResultBean.DataBean.GoodsBean goodsBean) {
        super(viewModel);
        this.entity.set(goodsBean);
    }

}
