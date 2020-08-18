package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentPromotOrderAllBinding;
import com.jzd.jzshop.entity.PromotionOrderEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class PromotOrderAllFragmViewModel extends BaseViewModel<Repository> {

    private static final String TAG = PromotOrderAllFragmViewModel.class.getSimpleName();
    private Context mContext;
    private FragmentPromotOrderAllBinding binding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<PromotionOrderEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public PromotOrderAllFragmViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentActivity activity, FragmentPromotOrderAllBinding binding) {
        this.mContext = activity;
        this.binding = binding;
    }

    /**
     * 获取 君子权记录 all
     */
    public void requestData(int status) {
        isShowDialog(false);
        addSubscribe(model.postPromotionOrder(model.getUserToken(), status), new ParseDisposableTokenErrorObserver<PromotionOrderEntity>() {
            @Override
            public void onResult(PromotionOrderEntity promotionOrderEntity) {
                super.onResult(promotionOrderEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (promotionOrderEntity != null) {
                    if (promotionOrderEntity.getResult() != null) {
                        uc.mLiveData.setValue(promotionOrderEntity.getResult());
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
