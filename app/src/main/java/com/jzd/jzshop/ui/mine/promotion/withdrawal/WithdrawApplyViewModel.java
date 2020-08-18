package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.app.Application;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentWithdrawApplyBinding;
import com.jzd.jzshop.entity.PayShowEntity;
import com.jzd.jzshop.entity.WithdrawApplyEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import java.text.DecimalFormat;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description :推广中心-提现申请
 * @date 2020/4/13.
 */
public class WithdrawApplyViewModel extends BaseViewModel<Repository> {


    public ObservableField<PayShowEntity> balance =new ObservableField<>(new PayShowEntity(0,0,0));
    DecimalFormat fnum = new DecimalFormat("##0.00");
    String format = fnum.format(0.00);
    public ObservableField<String> money=new ObservableField<>(format);

    private FragmentWithdrawApplyBinding binding;

    public WithdrawApplyViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentWithdrawApplyBinding binding) {
        this.binding=binding;
    }

    public void request(){
        isShowDialog(false);
        addSubscribe(model.postGetWithdrawData(model.getUserToken()),new ParseDisposableTokenErrorObserver<WithdrawApplyEntity>(){
            @Override
            public void onResult(WithdrawApplyEntity o) {
                super.onResult(o);
                dismissDialog();
                if(o.getResult()!=null){
                    DecimalFormat fnum = new DecimalFormat("##0.00");
                    if(o.getResult().getMoney() == 0){
                        String format = fnum.format(0.00);
                         money.set(format);
                    }else {
                        String format = fnum.format(o.getResult().getMoney());
                        money.set(format);
                    }
                }
                if(!money.get().equals("0")){
                    binding.card.setCardBackgroundColor(Color.parseColor("#CC0000"));
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


    //提现到支付宝
    public BindingCommand onClickListerenZfb =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
             setNoStatus();
             if(binding.chZfb.isChecked()){
                 binding.chZfb.setEnabled(false);
             }
            balance.get().setZfb(1);
            balance.notifyChange();
            binding.zfbData.setVisibility(View.VISIBLE);

        }
    });

    //提现到微信
    public BindingCommand onCLickListerenWX =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setNoStatus();
            if(binding.chWx.isChecked()){
                binding.chWx.setEnabled(false);
            }
            balance.get().setWx(1);
            balance.notifyChange();
        }
    });


    //提现到余额
    public BindingCommand onClickListerenBalance =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setNoStatus();
            if(binding.chBalance.isChecked()){
                binding.chBalance.setEnabled(false);
            }
            balance.get().setBalance(1);
            balance.notifyChange();
        }
    });

    //提现按钮
    public BindingCommand onClickListerenWithDraw =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(money.get().equals("0")){
                ToastUtils.showLong("可提现金额为0");
                return;
            }
            int getbalancetype = getbalancetype();
            if(getbalancetype==0){
                ToastUtils.showLong("请选择提现方式");
                return;
            }else if(getbalancetype==7){
                submitwithdraw();
            }
        }
    });



    //提现接口
    public void submitwithdraw(){
        isShowDialog(false);
        addSubscribe(model.postWithDraw(model.getUserToken()),new ParseDisposableTokenErrorObserver(){
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                dismissDialog();
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String format = fnum.format(0.00);
                money.set(format);
                binding.card.setCardBackgroundColor(Color.parseColor("#F89B9B"));
                money.notifyChange();
                ToastUtils.showLong("提现成功!");

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


    public void setNoStatus(){
        KeyboardUtils.hideKeyboard(binding.edMoney);
        binding.zfbData.setVisibility(View.GONE);
        PayShowEntity payShowEntity = balance.get();
        if(payShowEntity!=null){
            payShowEntity.setStatus();
        }
        binding.chZfb.setEnabled(true);
        binding.chBalance.setEnabled(true);
        binding.chWx.setEnabled(true);
    }


    private int getbalancetype(){
        PayShowEntity payShowEntity = balance.get();
        if(payShowEntity.getWx()==1){
            return 5;
        }
        if(payShowEntity.getZfb()==1){
            return 6;
        }
        if(payShowEntity.getBalance()==1){
            return 7;
        }
        return 0;
    }



}
