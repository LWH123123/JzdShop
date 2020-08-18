package com.jzd.jzshop.ui.mine.promotion.awarddetail;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentAwarddetailBinding;
import com.jzd.jzshop.entity.AwarddetailEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LWH
 * @description:
 * @date :2020/1/3 17:11
 */
public class AwardDerailViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = AwardDerailViewModel.class.getSimpleName();
    private Context context;
    private FragmentAwarddetailBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AwarddetailEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public AwardDerailViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, FragmentAwarddetailBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);
    }

    /**
     * 获取 查看提现详情
     */
    public void requestData(String log_id,int page) {
        isShowDialog(false);
        addSubscribe(model.postAwardDetail(model.getUserToken(),log_id,page), new ParseDisposableTokenErrorObserver<AwarddetailEntity>() {
            @Override
            public void onResult(AwarddetailEntity awarddetailEntity) {
                super.onResult(awarddetailEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (awarddetailEntity != null) {
                    if (awarddetailEntity.getResult() != null) {
                        uc.mLiveData.setValue(awarddetailEntity.getResult());
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
