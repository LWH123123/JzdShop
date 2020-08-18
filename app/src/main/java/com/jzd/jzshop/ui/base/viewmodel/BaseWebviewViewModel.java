package com.jzd.jzshop.ui.base.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.EntityH5ToConfirmOrder;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class BaseWebviewViewModel extends ToolbarViewModel<Repository> {

    public SingleLiveEvent<String> goBack = new SingleLiveEvent<>();

    public BaseWebviewViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void initToolbar() {
        setRightIconVisible(View.GONE);
    }

    public void saveConfirmOrderParams(EntityH5ToConfirmOrder entityH5ToConfirmOrder) {
        model.saveShopID(entityH5ToConfirmOrder.getGid());
        model.saveOptionID(String.valueOf(entityH5ToConfirmOrder.getOptionid()));
        model.saveGoodsNumber(String.valueOf(entityH5ToConfirmOrder.getTotal()));
        model.saveGiftID(entityH5ToConfirmOrder.getGift_id());
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        goBack.call();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack.call();
    }
}
