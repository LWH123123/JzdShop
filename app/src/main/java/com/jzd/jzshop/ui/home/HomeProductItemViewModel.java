package com.jzd.jzshop.ui.home;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class HomeProductItemViewModel extends ItemViewModel<HomeViewModel> {
    public ObservableField<HomeEntity.ResultBean.DataBeanX> entity=new ObservableField<>();
    public HomeProductItemViewModel(@NonNull HomeViewModel viewModel,HomeEntity.ResultBean.DataBeanX bean) {
        super(viewModel);
        entity.set(bean);
    }
    //条目的点击事件
    public BindingCommand itemClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString(Constants.GOODS_KEY_GID,entity.get().getGid());
            bundle.putString(Constants.GOODS_OPEN_FLAG, "0");
            viewModel.startActivity(GoodsActivity.class,bundle);
        }
    });


}
