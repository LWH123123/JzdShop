package com.jzd.jzshop.ui.mine.assets;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityAssetsRecordBinding;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsRecordViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AssetsRecordViewModel.class.getSimpleName();
    private Context context;
    private ActivityAssetsRecordBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AssetsRecordEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }
    public AssetsRecordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityAssetsRecordBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     *  获取 君子权记录 all
     */
    public void requestData(int status,int page) {
        isShowDialog(false);
        addSubscribe(model.postAssetsRecord(model.getUserToken(), status, page), new ParseDisposableTokenErrorObserver<AssetsRecordEntity>() {
            @Override
            public void onResult(AssetsRecordEntity assetsRecordEntity) {
                super.onResult(assetsRecordEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (assetsRecordEntity != null) {
                    if (assetsRecordEntity.getResult()!=null){
                        uc.mLiveData.setValue(assetsRecordEntity.getResult());
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
