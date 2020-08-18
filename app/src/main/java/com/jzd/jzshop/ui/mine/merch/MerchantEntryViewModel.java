package com.jzd.jzshop.ui.mine.merch;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMerchantEntryBinding;
import com.jzd.jzshop.entity.MerchEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.SystemUtils;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2019/12/18 17:11
 */
public class MerchantEntryViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = MerchantEntryViewModel.class.getSimpleName();
    private Context mContext;
    private ActivityMerchantEntryBinding mBinding;
    public SingleLiveEvent<String> goBack = new SingleLiveEvent<>();

    public SingleLiveEvent<MerchEntity> update = new SingleLiveEvent();


    public ObservableField<MerchEntity> merchList = new ObservableField<>();

    public MerchantEntryViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityMerchantEntryBinding binding) {
        this.mContext = context;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        goBack.call();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack.call();
    }

    //商家入驻的第一步
    public BindingCommand onClickMerchantListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(merchList.get()==null||merchList.get().getResult()==null){
                ToastUtils.showLong("网络异常,重新连接");
                requestword();
                return;
            }

            String name = mBinding.miName.getMessage();
            String phone = mBinding.miPhone.getMessage();
            String storename = mBinding.miStorename.getMessage();
            String desc = mBinding.miDesc.getMessage();
            String salecate = mBinding.miSalecate.getMessage();
            MerchEntity.ResultBean resultBean =null;
             resultBean =merchList.get().getResult();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(storename) && !TextUtils.isEmpty(salecate)) {
                if (!SystemUtils.isMobileNO(phone)) {
                    ToastUtils.showLong("手机号格式不正确,请重新输入");
                    return;
                }
                resultBean.setRealname(name);
                resultBean.setDesc(desc);
                resultBean.setMobile(phone);
                resultBean.setSalecate(salecate);
                resultBean.setMerchname(storename);
            } else {
                ToastUtils.showLong("请完善资料!");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.MerchFirst, resultBean);
            bundle.putParcelableArrayList(Constants.MerchFirstList, merchList.get().getResult().getFields());
            startContainerActivity(MerchNextFragment.class.getCanonicalName(), bundle);
        }
    });

    public void requestword() {
        addSubscribe(model.postMerch(model.getUserToken()), new ParseDisposableTokenErrorObserver<MerchEntity>() {
            @Override
            public void onResult(MerchEntity o) {
                super.onResult(o);
                dismissDialog();
                merchList.set(o);
                if (merchList != null) {
                    update.setValue(o);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                dismissDialog();

            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }
        });
    }

}
