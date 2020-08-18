package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityPromotionOrderNewBinding;
import com.jzd.jzshop.entity.PromotionListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 13:51
 */
public class PromotionOrderNewViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = PromotionOrderNewViewModel.class.getSimpleName();
    private Context context;
    private ActivityPromotionOrderNewBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<PromotionListEntity.ResultBean>> mLiveData = new SingleLiveEvent<>();
    }

    public PromotionOrderNewViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityPromotionOrderNewBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     * 获取 佣金 推广list
     */
    public void requestData() {
        isShowDialog(false);
        addSubscribe(model.postPromotOrder(model.getUserToken()), new ParseDisposableTokenErrorObserver<PromotionListEntity>() {
            @Override
            public void onResult(PromotionListEntity promotionListEntity) {
                super.onResult(promotionListEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (promotionListEntity != null) {
                    if (promotionListEntity.getResult() != null) {
                        uc.mLiveData.setValue(promotionListEntity.getResult());
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
