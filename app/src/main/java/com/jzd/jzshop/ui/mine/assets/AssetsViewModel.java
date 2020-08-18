package com.jzd.jzshop.ui.mine.assets;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityAssetsBinding;
import com.jzd.jzshop.entity.AssetsEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AssetsViewModel.class.getSimpleName();
    private Context context;
    private ActivityAssetsBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AssetsEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public AssetsViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityAssetsBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setRightText(context.getResources().getString(R.string.self_purchase_details));
        setRightTextVisible(View.VISIBLE);

    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        startActivity(AssetsRecordAty.class);   //君子权记录
    }

    /**
     * 获取 君子权资产
     */
    public void requestData() {
        isShowDialog(false);
        addSubscribe(model.postAssetsData(model.getUserToken()), new ParseDisposableTokenErrorObserver<AssetsEntity>() {
            @Override
            public void onResult(AssetsEntity assetsEntity) {
                super.onResult(assetsEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (assetsEntity != null) {
                    if (assetsEntity.getResult() != null) {
                        uc.mLiveData.setValue(assetsEntity.getResult());
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

    //了解君子权
    public BindingCommand OnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(AssetsIntroductionFragment.class.getCanonicalName());
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
