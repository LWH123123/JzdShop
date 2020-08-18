package com.jzd.jzshop.ui.goods.goodslist;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.GoodsListEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class GoodsListItemViewModel extends MultiItemViewModel<GoodsListViewModel> {
    public static final String MultiRecyclerTypeList = "list";
    public static final String MultiRecyclerTypeGrid = "grid";
    public ObservableField<GoodsListEntity.ResultBean.DataBean> entity = new ObservableField<>();

    public GoodsListItemViewModel(@NonNull GoodsListViewModel viewModel, GoodsListEntity.ResultBean.DataBean bean) {
        super(viewModel);
        entity.set(bean);
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.startGood(entity.get().getGid(),"0");
        }
    });

}