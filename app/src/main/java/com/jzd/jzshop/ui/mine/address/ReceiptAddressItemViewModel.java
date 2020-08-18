package com.jzd.jzshop.ui.mine.address;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AddressListEntity;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.slodonapp.ywj_release.wxapi.WechatInfoSpHelper;
import com.umeng.analytics.MobclickAgent;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.SPUtils;

public class ReceiptAddressItemViewModel extends ItemViewModel<ReceiptAddressViewModel> {

    public ObservableField<AddressListEntity.ResultBean.DataBean> entity = new ObservableField<>();
    private final SmartRefreshLayout smartRefreshLayout;

    public ReceiptAddressItemViewModel(@NonNull ReceiptAddressViewModel viewModel, AddressListEntity.ResultBean.DataBean entity, SmartRefreshLayout refreshLayout) {
        super(viewModel);
        this.entity.set(entity);
        smartRefreshLayout = refreshLayout;
    }


    public BindingCommand onItemCompile = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("address", entity.get());
            bundle.putString("compile", "编辑收货地址");
            viewModel.startContainerActivity(CompileAddressFragment.class.getCanonicalName(), bundle);
        }
    });

    public BindingCommand onItemDelete = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            viewModel.deleteAddress( entity.get().getAddr_id(),smartRefreshLayout);
            entity.notifyChange();
//            viewModel.requestWork();
        }
    });

    //設置默認地址
    public BindingCommand onItemSetDefault = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(entity.get()==null){
                return;
            }
            entity.get().setIsdefault(1);
            entity.notifyChange();
            viewModel.requestSetDefaultAddress(entity.get().getAddr_id(),smartRefreshLayout);
        }
    });


    public BindingCommand onSelectAddress =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.selectAddress(entity.get().getAddr_id());
        }
    });




}
