package com.jzd.jzshop.ui.home.creditsstore.pay;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditPayBinding;
import com.jzd.jzshop.entity.CreditPayEntity;
import com.jzd.jzshop.entity.CreditToPayEntity;
import com.jzd.jzshop.entity.LotteryEntity;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.ui.home.creditsstore.lottery.CreditLotteryAty;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;
import com.jzd.jzshop.ui.pay.PaySuccessFragment;
import com.jzd.jzshop.ui.pay.PaySuccessViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.slodonapp.ywj_release.wxapi.WXPayEntryActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description :
 * @date 2020/5/14.
 */
public class CreditPayViewModel extends BaseViewModel<Repository> {

    public ObservableField<CreditPayEntity.ResultBean> creditpay =new ObservableField<CreditPayEntity.ResultBean>();
    public SingleLiveEvent<String> zfbPay =new SingleLiveEvent<>();

    public CreditPayViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }
    private ActivityCreditPayBinding binding;
    private CreditPayActivity creditPayActivity;
    private int islottery;
    public void setbinding(ActivityCreditPayBinding binding, CreditPayActivity creditPayActivity,int islottery) {
        this.binding=binding;
        this.creditPayActivity=creditPayActivity;
        this.islottery=islottery;
    }


    //收银台 接口
    public void requestWork(String gid,String optionid,String addid,int num) {
        addSubscribe(model.postCreditPay(model.getUserToken(),gid,optionid,addid,num,null),new ParseDisposableTokenErrorObserver<CreditPayEntity>(){
            @Override
            public void onResult(CreditPayEntity o) {
                super.onResult(o);
                dismissDialog();
                creditpay.set(o.getResult());
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                finish();

            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);


            }
        });



    }



    public BindingCommand onClickFinsh =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //微信支付
    public BindingCommand onWXPayClickListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setDefaultStatus();
            binding.chWx.setChecked(true);
            requestPayOrder("wechat");

        }
    });


    //支付宝
    public BindingCommand ZfbPayClickListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setDefaultStatus();
            binding.chZfb.setChecked(true);
            requestPayOrder("alipay");
        }
    });

    //余额
    public BindingCommand onYePayClickListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setDefaultStatus();
            binding.chYe.setChecked(true);
            requestPayOrder("credit");

        }
    });


    //设置支付方式为false
    private void  setDefaultStatus(){
           binding.chWx.setChecked(false);
           binding.chYe.setChecked(false);
           binding.chZfb.setChecked(false);
    }



    //预支付接口
    public void requestPayOrder(String type){
          addSubscribe(model.postCreditTopay(model.getUserToken(),type,null,0,null,1),new ParseDisposableTokenErrorObserver<OrderToPayEntity>(){

              @Override
              public void onResult(OrderToPayEntity o) {
                  super.onResult(o);
                  dismissDialog();
                  if(type.equals("wechat")){//假如是微信支付
                      WXPayEntryActivity.payWeixin(AppApplication.sApi, o.getResult().getData(), new WXEntryActivity.ApiCallback() {
                          @Override
                          public void onSuccess(Object response) {
                              KLog.d(response);
                              KLog.a("微信支付", "成功了");
                              if(islottery==0) {//不是抽奖 直接扣钱
                                  reuquestLottery();
                              }else {
                                  //todo 去抽奖 调用正式兑换
                                  Bundle bundle = new Bundle();
                                  bundle.putInt(Constants.GOODS_KEY_CREDIT_ISPAY,1);
                                  startActivity(CreditLotteryAty.class,bundle);
                                  finish();
                              }

                          }
                          @Override
                          public void onError(int errorCode, String errorMsg) {
                              KLog.a("微信支付", "失败了");
                              //TODO  跳订单详情

                          }
                          @Override
                          public void onFailure(IOException e) {
                              KLog.a("微信支付", "异常了");
                          }
                          @Override
                          public void onPayError(String res) {
                              KLog.a("微信支付", res);
                              //TODO  跳订单详情

                          }
                      });
                  }else if(type.equals("alipay")){//如果是支付宝支付
                      zfbPay.setValue(o.getResult().getData().getString());
                  }else if(type.equals("credit")){//余额支付
                      //todo  判断 余额是否足够
                      String smoney = creditpay.get().getMoney();
                      String price = creditpay.get().getPrice();
                      if(TextUtils.isEmpty(smoney)||TextUtils.isEmpty(price)){
                          return;
                      }
                          double dmoney = Double.parseDouble(smoney);//用户余额
                          double dprice = Double.parseDouble(price);//所需余额
                      if(dprice>dmoney){
                          ToastUtils.showLong("余额不足");
                          return;
                      }
                      if(islottery==0) {//不是抽奖 直接扣钱
                          reuquestLottery();
                      }else {
                          //todo 去抽奖 调用正式兑换
                          Bundle bundle = new Bundle();
                          bundle.putInt(Constants.GOODS_KEY_CREDIT_ISPAY,1);
                          startActivity(CreditLotteryAty.class,bundle);
                          finish();
                      }

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


    //正式兑换
    public void reuquestLottery (){
        addSubscribe(model.postCreditLottery(model.getUserToken()),new ParseDisposableTokenErrorObserver<LotteryEntity>(){
            @Override
            public void onResult(LotteryEntity o) {
                super.onResult(o);
                dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GOODS_KEY_CREDIT_PAY_RESULT,o.getResult().getStatus());
                bundle.putString(Constants.GOODS_KEY_CREDIT_PAY_LOGID,o.getResult().getLog_id());
                bundle.putInt(Constants.GOODS_KEY_CREDIT_ISLOTTERY,0);
                startActivity(CreditPaySuccessAty.class,bundle);
                finish();
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
