package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityPromotionOrderBinding;
import com.jzd.jzshop.entity.PromotionOrderEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class PromotionOrderViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = PromotionOrderViewModel.class.getSimpleName();
    private Context context;
    private ActivityPromotionOrderBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<PromotionOrderEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public PromotionOrderViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityPromotionOrderBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     * 获取 奖励订单（推广订单）
     */
    public void requestData(int status) {
        isShowDialog(false);
        addSubscribe(model.postPromotionOrder(model.getUserToken(), 0), new ParseDisposableTokenErrorObserver<PromotionOrderEntity>() {
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
