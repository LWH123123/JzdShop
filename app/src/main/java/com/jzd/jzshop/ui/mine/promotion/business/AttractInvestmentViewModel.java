package com.jzd.jzshop.ui.mine.promotion.business;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityAttractInvestmentBinding;
import com.jzd.jzshop.entity.AttractInvestmentEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 11:56
 */
public class AttractInvestmentViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = AttractInvestmentViewModel.class.getSimpleName();
    private Context context;
    private ActivityAttractInvestmentBinding mBinding;
    public int pagesize = 10; //默认每页页数

    public ObservableField<AttractInvestmentEntity.ResultBean> data=new ObservableField<>();
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<AttractInvestmentEntity.ResultBean.DataBean>> mLiveData = new SingleLiveEvent<>();
    }

    public AttractInvestmentViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityAttractInvestmentBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleTextColor(Color.WHITE);
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);
        setToolbarBgColor(Color.parseColor("#D90804"));
    }

    /**
     * 获取 推广佣金
     */
    public void requestData() {
        isShowDialog(false);
        addSubscribe(model.postBusiness(model.getUserToken()), new ParseDisposableTokenErrorObserver<AttractInvestmentEntity>() {
            @Override
            public void onResult(AttractInvestmentEntity attractInvestmentEntity) {
                super.onResult(attractInvestmentEntity);
                Log.d(TAG, "onSuccess:---->>>"+attractInvestmentEntity.getResult().getDataBeans().size());
                if (attractInvestmentEntity != null) {
                data.set(attractInvestmentEntity.getResult());
                    if (attractInvestmentEntity.getResult() != null) {
                        uc.mLiveData.setValue(attractInvestmentEntity.getResult().getDataBeans());
                    }
                }
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
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
