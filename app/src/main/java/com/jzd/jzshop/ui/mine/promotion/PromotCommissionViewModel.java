package com.jzd.jzshop.ui.mine.promotion;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityPromotCommissionBinding;
import com.jzd.jzshop.entity.PromotCommissionEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.promotion.withdrawal.CommissionWithdrawalActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/11 17:22
 */
public class PromotCommissionViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = PromotCommissionViewModel.class.getSimpleName();
    private Context context;
    private ActivityPromotCommissionBinding mBinding;
    public int pagesize = 10; //默认每页页数

    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<PromotCommissionEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public PromotCommissionViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityPromotCommissionBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);

    }

    /**
     *  获取 推广佣金
     */
    public void requestData() {
        addSubscribe(model.postPromotCommis(model.getUserToken()), new ParseDisposableTokenErrorObserver<PromotCommissionEntity>() {
            @Override
            public void onResult(PromotCommissionEntity promotCommissionEntity) {
                super.onResult(promotCommissionEntity);
                Log.d(TAG, "onSuccess:---->>>");
                mBinding.nestscroll.setVisibility(View.VISIBLE);
                if (promotCommissionEntity != null) {
                    if (promotCommissionEntity.getResult()!=null){
                        uc.mLiveData.setValue(promotCommissionEntity.getResult());
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

    //我要提现
    public BindingCommand onCashWithdrawalClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(CommissionWithdrawalActivity.class);
        }
    });


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
