package com.jzd.jzshop.ui.home.merchantalliance;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.MerchantAllianceEntity;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 商家联盟 item  ViewModel
 */

public class MerchantAllianceItemViewModel extends ItemViewModel<MerchantAllianceViewModel> {
    public ObservableField<MerchantAllianceEntity.ResultBean.DataBean> entity = new ObservableField<>();

    public MerchantAllianceItemViewModel(@NonNull MerchantAllianceViewModel viewModel, MerchantAllianceEntity.ResultBean.DataBean bean) {
        super(viewModel);
        entity.set(bean);
    }

    public BindingCommand onClickGoTo = new BindingCommand(new BindingAction() {
        @Override
        public void call() {  // 商家联盟专卖店铺
            Bundle bundle = new Bundle();
            bundle.putString(Constants.MINE_SHOP, entity.get().getMerch_id());
            bundle.putString(Constants.BUNDLE_KEY_TITLE, entity.get().getMerch_name());
            viewModel.startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
        }
    });
}