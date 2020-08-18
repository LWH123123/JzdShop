package com.jzd.jzshop.ui.mine.creditsign.shop;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditShopBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/10 9:29
 */
public class CreditShopViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = CreditShopViewModel.class.getSimpleName();
    private Context context;
    private ActivityCreditShopBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Object> mLiveData = new SingleLiveEvent<>();
    }

    public CreditShopViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityCreditShopBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    public String getUserToken() {
        return model.getUserToken();
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
