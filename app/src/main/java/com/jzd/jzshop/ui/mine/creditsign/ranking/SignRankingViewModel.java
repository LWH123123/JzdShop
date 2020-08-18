package com.jzd.jzshop.ui.mine.creditsign.ranking;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivitySignRankingBinding;
import com.jzd.jzshop.entity.SignRankingEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/7 10:10
 */
public class SignRankingViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = SignRankingViewModel.class.getSimpleName();
    private Context context;
    private ActivitySignRankingBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<SignRankingEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public SignRankingViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivitySignRankingBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    public void requestData(String type, int page,int pagesize) {
        isShowDialog(false);
        addSubscribe(model.postSignRanking(model.getUserToken(), type, page,pagesize), new ParseDisposableTokenErrorObserver<SignRankingEntity>() {
            @Override
            public void onResult(SignRankingEntity signRankingEntity) {
                super.onResult(signRankingEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (signRankingEntity != null) {
                    if (signRankingEntity.getResult() != null) {
                        uc.mLiveData.setValue(signRankingEntity.getResult());
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
