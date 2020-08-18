package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCommissionWithdrawalBinding;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 9:13
 */
public class CommissionWithdrawalViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CommissionWithdrawalViewModel.class.getSimpleName();
    private Context context;
    private ActivityCommissionWithdrawalBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        public SingleLiveEvent<AwardListEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public CommissionWithdrawalViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityCommissionWithdrawalBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
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
