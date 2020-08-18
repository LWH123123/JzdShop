package com.jzd.jzshop.ui.home.creditsstore.mine;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditsMineBinding;
import com.jzd.jzshop.entity.CreditsAllGoodsEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/5/9 16:36
 */
public class CreditsMineViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditsMineViewModel.class.getSimpleName();
    private Context context;
    private ActivityCreditsMineBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        public SingleLiveEvent<CreditsAllGoodsEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public CreditsMineViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityCreditsMineBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    public void requestData() {
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
