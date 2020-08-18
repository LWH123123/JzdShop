package com.jzd.jzshop.ui.merch_alliance;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.ShopEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 商家联盟 ———》》》 专卖店铺  item  viewModel
 */
public class ShopItemViewModel extends ItemViewModel<ShopViewModel> {

    public ObservableField<ShopEntity.ResultBean.DataBean> entity = new ObservableField<>();

    public ShopItemViewModel(@NonNull ShopViewModel viewModel, ShopEntity.ResultBean.DataBean dataBean) {
        super(viewModel);
        entity.set(dataBean);
    }

    //skip商品详情
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.startGood(entity.get().getGid(),"0");
        }
    });

}
