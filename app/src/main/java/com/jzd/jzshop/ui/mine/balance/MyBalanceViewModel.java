package com.jzd.jzshop.ui.mine.balance;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentMyBalanceBinding;
import com.jzd.jzshop.entity.BalanceDataEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.recharge.RechargeFragment;
import com.jzd.jzshop.ui.mine.withdrawals.BalanceFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class MyBalanceViewModel extends ToolbarViewModel<Repository> {

    public MyBalanceViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private FragmentMyBalanceBinding binding;
    public void setbing(FragmentMyBalanceBinding binding){
        setTitleText("余额");
        this.binding=binding;
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    //提现
    public BindingCommand onTixianClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(BalanceFragment.class.getCanonicalName());
        }
    });

    //充值
    public BindingCommand onChongzhiClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putDouble(Constants.MINE_RECHARGE,money);
            startContainerActivity(RechargeFragment.class.getCanonicalName(),bundle);
        }
    });


    private double money;
    public void setMoney(double string) {
        this.money=string;
    }


    //可见时更新余额中的金额
    public void requestWork(){
        isShowDialog(false);
        addSubscribe(model.postGetwithDraw(model.getUserToken()),new ParseDisposableTokenErrorObserver<BalanceDataEntity>(){
            @Override
            public void onResult(BalanceDataEntity o) {
                super.onResult(o);
                dismissDialog();
                if(!TextUtils.isEmpty(o.getResult().getMoney())){
                    double balance = Double.parseDouble(o.getResult().getMoney());
                    setMoney(balance);
                    binding.mybalance.setText("¥"+o.getResult().getMoney());
                }
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
