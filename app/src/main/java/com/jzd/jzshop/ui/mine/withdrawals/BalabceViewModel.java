package com.jzd.jzshop.ui.mine.withdrawals;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentBalanceBinding;
import com.jzd.jzshop.entity.BalanceDataEntity;
import com.jzd.jzshop.entity.PayShowEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class BalabceViewModel extends BaseViewModel<Repository> {

    private FragmentBalanceBinding binding;

    public ObservableField<PayShowEntity> balanceshow =new ObservableField<>(new PayShowEntity(0,0,0));
    public ObservableField<BalanceDataEntity.ResultBean> balancedata =new ObservableField<>();
    public ObservableField<String> balacemoney =new ObservableField<>();
    public BalabceViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private int selectbank=-2;
    private BalanceFragment fragment;
    public void setBinding(FragmentBalanceBinding binding, BalanceFragment context){
        this.binding=binding;
        this.fragment=context;
    }

    //支付宝 姓名
    public ObservableField<String> zfbName  =new ObservableField<>();
    //支付宝账号
    public ObservableField<String> zfbAccount  =new ObservableField<>();
    //支付宝确认密码
    public ObservableField<String> zfbCofirmAccount  =new ObservableField<>();

    //银行卡姓名
    public ObservableField<String> bankName =new ObservableField<>();
    //银行卡号
    public ObservableField<String> bankCard =new ObservableField<>();
    //确认银行卡号
    public ObservableField<String> bankConfrimCard =new ObservableField<>();

    public void request(){
        addSubscribe(model.postGetwithDraw(model.getUserToken()),new ParseDisposableTokenErrorObserver<BalanceDataEntity>(){
            @Override
            public void onResult(BalanceDataEntity o) {
                super.onResult(o);
                dismissDialog();
                balancedata.set(o.getResult());
                String withdrawmoney = balancedata.get().getWithdrawmoney();
                Double aDouble = Double.valueOf(withdrawmoney);
                if(withdrawmoney!=null&&aDouble!=0){
                    binding.tvWithdrawmoney.setText("最小提现金额为: ¥"+withdrawmoney);
                }else {
                    binding.tvWithdrawmoney.setVisibility(View.GONE);
                }

                List<BalanceDataEntity.ResultBean.ApplytypeBean> applytype = balancedata.get().getApplytype();
                for (BalanceDataEntity.ResultBean.ApplytypeBean applytypeBean : applytype) {
                    String id = applytypeBean.getId();
                    if(id.equalsIgnoreCase("wechat")){
                        binding.wx.setVisibility(View.VISIBLE);
                    }
                    if(id.equalsIgnoreCase("alipay")){
                        binding.zfb.setVisibility(View.VISIBLE);
                    }
//                    binding.tvPercent.setText(tvpercent);
                    /*if(id.equalsIgnoreCase("bankcard")){
                        binding.bank.setVisibility(View.VISIBLE);
                    }*/
                }
                BalanceDataEntity.ResultBean.PoundageBean poundage = balancedata.get().getPoundage();

                String percents = poundage.getPercent();
                if(percents.equals("0")){
                    // TODO: 2020/2/19   隐藏所有的说明
                    binding.explain.setVisibility(View.GONE);
                }else {
                    String exapin = exapin(percents, poundage.getBegin(), poundage.getEnd());
                    binding.tvPercent.setText(exapin);
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

    private String  exapin(String percent,String begin,String end){
        String exapin="提现手续费为"+percent+"%\n手续费金额在¥"+begin+"到¥"+end+"间免收";
          return exapin;
    }

    //全部提现
    public BindingCommand onAllBalanceClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           if(balancedata.get()!=null) {
                   binding.edBalacemoney.setText(balancedata.get().getMoney());
               if (!balancedata.get().getPoundage().getPercent().equals("0")) {
                   BalanceDataEntity.ResultBean.PoundageBean poundage = balancedata.get().getPoundage();
                   Double percent = Double.valueOf(poundage.getPercent());
                   Double begin = Double.valueOf(poundage.getBegin());
                   Double end = Double.valueOf(poundage.getEnd());
                   Double money = Double.valueOf(balancedata.get().getMoney());
                   if (begin <= money && money <= end) {
                       binding.tvCommission.setText("本次提现将扣除手续费 ¥0.00");
                       binding.tvMoney.setText("本次提现实际到账金额 ¥" + money);
                   } else {
                       double news = money * percent * 0.01;
                       double last = money - news;
                       String deduct = MoneyFormatUtils.keepTwoDecimal(news);
                       String practical = MoneyFormatUtils.keepTwoDecimal(last);
                       binding.tvCommission.setText("本次提现将扣除手续费 ¥" + deduct);
                       binding.tvMoney.setText("本次提现实际到账金额 ¥" + practical);
                   }
               }
           }
        }
    });

    //提现记录
    public BindingCommand WithdraRecordOnClickCommand =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(WithdrawalsRecordAty.class);
        }
    });

    public BindingCommand imback =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    public BindingCommand onBalanceClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(balacemoney.get()==null){
                ToastUtils.showLong("请输入您要提现的金额!");
                return;
            }
            String moneys = balacemoney.get();
            if(TextUtils.isEmpty(moneys)){
                return;
            }
            Double aDouble = Double.valueOf(moneys);
            if(balancedata.get()==null){
                return;
            }
            String money = balancedata.get().getMoney();
            Double aDouble1 = Double.valueOf(money);
            String withdrawmoney = balancedata.get().getWithdrawmoney();
            if(withdrawmoney!=null&&!TextUtils.isEmpty(withdrawmoney)){
                Double aDouble2 = Double.valueOf(withdrawmoney);
                if(aDouble1<aDouble2){
                    ToastUtils.showLong("余额满"+withdrawmoney+"可以提现");
                    return;
                }
            }
            if(aDouble1>=aDouble&&aDouble!=0){

                int getbalancetype = getbalancetype();
                 switch (getbalancetype){
                     case 0:
                         ToastUtils.showLong("请选择提现方式!");
                         break;
                     case 5://微信
                        balanceSubmit(moneys,"wechat",null,null,null);
                         break;
                     case 6://支付宝
                         if(!TextUtils.isEmpty(zfbName.get())&&!TextUtils.isEmpty(zfbAccount.get())&&!TextUtils.isEmpty(zfbCofirmAccount.get())){
                             if(zfbAccount.get().equals(zfbCofirmAccount.get())){
                              balanceSubmit(moneys,"alipay",zfbName.get(),zfbAccount.get(),null);
                             }else {
                               ToastUtils.showLong("俩次账号不一致,请重新输入");
                             }
                         }else {
                             ToastUtils.showLong("请完善相关信息!");
                         }
                         break;
                     case 7://银行卡
                         // TODO: 2020/2/19  提现到银行卡暂时不用了
                        /* if(!TextUtils.isEmpty(bankName.get())&&!TextUtils.isEmpty(bankCard.get())&&!TextUtils.isEmpty(bankConfrimCard.get())){
                             if(selectbank!=-2){
                                 if(bankCard.get().equals(bankConfrimCard.get())){
                                     // TODO: 2020/2/18 效验银行卡
                                     if(CheckIdCard.checkBankCard(bankCard.get())){
                                         ToastUtils.showLong("银行卡效验成功");
                                     }else {
                                         ToastUtils.showLong("银行卡有误");
                                     }


                                 }else {
                                     ToastUtils.showLong("银行卡号不一致,请重新输入!");
                                 }
                             }else {
                                 ToastUtils.showLong("请选择相关银行");
                             }
                         }else {
                             ToastUtils.showLong("请完善相关信息!");
                         }*/
                         break;

                 }

            }else {
                ToastUtils.showLong("提现金额超限");
            }
        }
    });





    //提现微信
    public BindingCommand onBalanceToWX =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setfalse();
            if(binding.checkBox5.isChecked()){
                binding.checkBox5.setEnabled(false);
            balanceshow.get().setWx(1);
            }
            balanceshow.notifyChange();
        }
    });

    //提现支付宝
    public BindingCommand onBalanceToZfb =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setfalse();
            if(binding.checkBox6.isChecked()) {
                binding.checkBox6.setEnabled(false);
                binding.zfbData.setVisibility(View.VISIBLE);
                balanceshow.get().setZfb(1);
            }else {
                binding.zfbData.setVisibility(View.GONE);
            }
                balanceshow.notifyChange();

        }
    });

    //提现银行卡
    public BindingCommand onBalanceToBank =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setfalse();
            if(binding.checkBox7.isChecked()) {
                binding.checkBox7.setEnabled(false);
                binding.bankData.setVisibility(View.VISIBLE);
                balanceshow.get().setBalance(1);
            }else {
                binding.bankData.setVisibility(View.GONE);
            }
                balanceshow.notifyChange();
        }
    });



    public BindingCommand onSelectBankClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            List<BalanceDataEntity.ResultBean.BanklistBean> banklist = balancedata.get().getBanklist();
            ArrayList<String> bank = new ArrayList<>();
            for (BalanceDataEntity.ResultBean.BanklistBean banklistBean : banklist) {
                bank.add(banklistBean.getBank_name());
            }
            MaterialDialog.Builder builder = new MaterialDialog.Builder(fragment.getContext())
                    .positiveText(null)
                    .items(bank)
                    .itemsCallbackSingleChoice(selectbank, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            selectbank = which;
                            KLog.a("我选择的数字",selectbank);
                            binding.textView194.setText(text);
                            return false;
                        }
                    });
            builder.titleGravity(GravityEnum.CENTER);
            builder.widgetColor(fragment.getContext().getResources().getColor(R.color.colorPrimary));
            builder.build().show();
        }
    });

    private void balanceSubmit(String money,String type,String name,String username,String bank_name){
        addSubscribe(model.postBalanceSubmit(model.getUserToken(),money,type,name,username,bank_name),new ParseDisposableTokenErrorObserver(){
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.RECHARGE_TIME, SystemUtils.getTime());
                startContainerActivity(BalanceApplyFragment.class.getCanonicalName(),bundle);
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


    private void setfalse(){
        binding.zfbData.setVisibility(View.GONE);
//        binding.bankData.setVisibility(View.GONE);
        balanceshow.get().setWx(0);
        balanceshow.get().setZfb(0);
//        balanceshow.get().setBalance(0);
        binding.checkBox5.setEnabled(true);
        binding.checkBox6.setEnabled(true);
//        binding.checkBox7.setEnabled(true);
    }


    private int getbalancetype(){
        PayShowEntity payShowEntity = balanceshow.get();
        if(payShowEntity.getWx()==1){
            return 5;
        }
        if(payShowEntity.getZfb()==1){
            return 6;
        }
        /*if(payShowEntity.getBalance()==1){
            return 7;
        }*/
        return 0;
    }
}
