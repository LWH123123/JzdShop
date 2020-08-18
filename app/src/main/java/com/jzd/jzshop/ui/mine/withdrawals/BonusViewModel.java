package com.jzd.jzshop.ui.mine.withdrawals;

import android.app.Application;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentBonusBinding;
import com.jzd.jzshop.entity.BonuswithdrawEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.utils.KLog;

public class BonusViewModel extends ToolbarViewModel<Repository> {
    private FragmentBonusBinding binding;

    public ObservableField<BonuswithdrawEntity.ResultBean> bonus =new ObservableField<>();
    public BonusViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public void setBinding(FragmentBonusBinding binding){
       this.binding=binding;

    }
    public void initToolbar(){
        setTitleText("奖金提现");
        setIvBackWhiteIsVisible(View.VISIBLE);
        setIvBackIsVisible(View.GONE);
        setTitleTextColor(Color.parseColor("#ffffff"));
        setToolbarBgColor(Color.parseColor("#D90804"));
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void request(){
        addSubscribe(model.postBonuswithdraw(model.getUserToken()),new ParseDisposableTokenErrorObserver<BonuswithdrawEntity>(){
            @Override
            public void onResult(BonuswithdrawEntity o) {
                super.onResult(o);
                dismissDialog();
                bonus.set(o.getResult());
                String desc = bonus.get().getDesc();
                desc.replace("收货后","哈哈");

                KLog.a("====>>>",desc);
                KLog.a("用户须知==:","====>>>>>"+desc);
                String a="买家确认收货后，立即获得分销佣金\n注意：可用佣金满%s 后才能申请提现";

                binding.tvDesc.setText(a);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
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
