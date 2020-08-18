package com.jzd.jzshop.ui.goods;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.Map;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;

public class PopGoodsParameterItemViewModel extends ItemViewModel<GoodsViewModel> {
    public ObservableField<IKeyAndValue> entity=new ObservableField<>();
    public PopGoodsParameterItemViewModel(@NonNull GoodsViewModel viewModel, IKeyAndValue text) {
        super(viewModel);
        entity.set(text);
    }
}
