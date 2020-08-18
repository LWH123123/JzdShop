package com.jzd.jzshop.ui.mine.recharge;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentRechargeBinding;
import com.jzd.jzshop.ui.pay.PayResult;

import java.util.Map;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 充值
 */
public class RechargeFragment extends BaseFragment<FragmentRechargeBinding,RechargeViewModel> {


    private double aDouble=0.0;

    public RechargeFragment() {
        // Required empty public constructor
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_recharge;
    }

    @Override
    public int initVariableId() {
        return BR.rechargeVM;
    }

    @Override
    public RechargeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(RechargeViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.setBind(binding,RechargeFragment.this);
        binding.editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    binding.card.setCardBackgroundColor(Color.parseColor("#D90804"));
                }else {
                    binding.card.setCardBackgroundColor(Color.parseColor("#FFC9C8"));
                }

            }
        });
    }


    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if (msg==null)return;
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        /*String s = viewModel.money.get();
                        KLog.a("我要充值的金额","===>>>"+s);
                        double v = Double.valueOf(s).doubleValue();
                        KLog.a("我已经拥有的金额","====>>>"+aDouble);
                       double news= aDouble+v;
                        String s1 = String.valueOf(news);
                        Messenger.getDefault().send(s1, RechargeViewModel.ALIPAY_REFRESH_MONEY);
                        getActivity().finish();*/
                        viewModel.rechargeSuccess("支付宝支付",viewModel.money.get());
                    } else {
                       ToastUtils.showLong("充值....");
                    }
                    break;
                }
            }
        }
    };

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.zfb.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String s) {
                Log.d("alipay入参：========", s);
                final Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(s, true);
                        String error = "aliPay error:(" + ")" + result.get("resultStatus") + "-" + result.get("result") + "-" + result.get("memo");
                        Log.d("PayFragment PayTask :", error);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

            }
        });

    }
}
