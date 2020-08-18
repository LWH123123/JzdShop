package com.jzd.jzshop.ui.order.logistics;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.LogisticListEntity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 14:03
 */
public class LogisticItemViewModel extends ItemViewModel<LogisticsViewModel> {

    public ObservableField<LogisticListEntity.ResultBean> entity =new ObservableField<>();

    public LogisticItemViewModel(@NonNull LogisticsViewModel viewModel,LogisticListEntity.ResultBean dataBean) {
        super(viewModel);
        this.entity.set(dataBean);
    }
    
    public BindingCommand onLookLogisticListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/1/16 查看具体物流
            Bundle bundle = new Bundle();
            bundle.putString(Constants.MyOrder,String.valueOf(entity.get().getOrder_id()));
            bundle.putString(Constants.ORDER_SEND_TYPE,String.valueOf(entity.get().getSendtype()));
            bundle.putString(Constants.ORDER_BUNDLE,String.valueOf(entity.get().getBundle()));
            viewModel.startContainerActivity(LogisticInfoFragment.class.getCanonicalName(),bundle);

        }
    });
    
    
    
}
