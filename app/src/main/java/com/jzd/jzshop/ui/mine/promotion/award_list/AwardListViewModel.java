package com.jzd.jzshop.ui.mine.promotion.award_list;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityAwardListBinding;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordViewModel;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardListViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AssetsRecordViewModel.class.getSimpleName();
    private Context context;
    private ActivityAwardListBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AwardListEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public AwardListViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityAwardListBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     *  获取 奖励明细
     */
    public void requestData(int status) {
        isShowDialog(false);
        addSubscribe(model.postAwardList(model.getUserToken(),0), new ParseDisposableTokenErrorObserver<AwardListEntity>() {
            @Override
            public void onResult(AwardListEntity awardListEntity) {
                super.onResult(awardListEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (awardListEntity != null) {
                    if (awardListEntity.getResult()!=null){
                        uc.mLiveData.setValue(awardListEntity.getResult());
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
