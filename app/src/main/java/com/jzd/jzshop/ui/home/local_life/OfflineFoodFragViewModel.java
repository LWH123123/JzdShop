package com.jzd.jzshop.ui.home.local_life;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentOfflineFoodBinding;
import com.jzd.jzshop.entity.LocalLifeEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class OfflineFoodFragViewModel extends BaseViewModel<Repository> {
    private static final String TAG = OfflineFoodFragViewModel.class.getSimpleName();
    private Context mContext;
    private FragmentOfflineFoodBinding binding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<LocalLifeEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public OfflineFoodFragViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentActivity activity, FragmentOfflineFoodBinding binding) {
        this.mContext = activity;
        this.binding = binding;
    }

    /**
     * 获取 线下美食
     */
    public void requestData(int status) {
        isShowDialog(false);
        addSubscribe(model.postLocalLife(model.getUserToken(), status), new ParseDisposableTokenErrorObserver<LocalLifeEntity>() {
            @Override
            public void onResult(LocalLifeEntity localLifeEntity) {
                super.onResult(localLifeEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (localLifeEntity != null) {
                    if (localLifeEntity.getResult() != null) {
                        uc.mLiveData.setValue(localLifeEntity.getResult());
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
}
