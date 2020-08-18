package com.jzd.jzshop.ui.home.creditsstore.home;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditsStoreBinding;
import com.jzd.jzshop.entity.CreditsStoreEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/5/8 14:46
 */
public class CreditsStoreViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditsStoreViewModel.class.getSimpleName();
    private Context context;
    private ActivityCreditsStoreBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CreditsStoreEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public CreditsStoreViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityCreditsStoreBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    public void requestData(SmartRefreshLayout mallRefreshLayout) {
        isShowDialog(false);
        addSubscribe(model.postCreditShop(model.getUserToken(), ""), new ParseDisposableTokenErrorObserver<CreditsStoreEntity>() {
            @Override
            public void onResult(CreditsStoreEntity creditsStoreEntity) {
                super.onResult(creditsStoreEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditsStoreEntity != null) {
                    if (creditsStoreEntity.getResult() != null) {
                        uc.mLiveData.setValue(creditsStoreEntity.getResult());
                    }
                }
                mallRefreshLayout.finishRefresh();
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
                mallRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                mallRefreshLayout.finishRefresh();
            }
        });

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
