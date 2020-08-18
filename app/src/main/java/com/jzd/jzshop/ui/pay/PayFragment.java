package com.jzd.jzshop.ui.pay;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentPayBinding;
import com.jzd.jzshop.entity.OrderPayEntity;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailAty;
import com.jzd.jzshop.utils.Constants;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;


/**
 * A simple {@link Fragment} subclass.
 * 收银台
 */
public class PayFragment extends BaseFragment<FragmentPayBinding, PayViewModel> {
    private static final String TAG = PayFragment.class.getSimpleName();
    private String orderid;
    private String money;
    private FragmentActivity mActivity;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_pay;
    }

    @Override
    public int initVariableId() {
        return BR.payVM;
    }

    @Override
    public void initParam() {
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            orderid = mBundle.getString(Constants.BUNDLE_KEY_ORDERID);
            money = mBundle.getString(Constants.BUNDLE_KEY_MONEY);
        }
    }

    @Override
    public PayViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(PayViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));
        Log.d(TAG, "orderid======:" + orderid);
        viewModel.order_id = orderid;
        viewModel.setFragment(PayFragment.this);
        SPUtils.getInstance().put(Constants.MINE_GOOD, 0);
        viewModel.requestOrderPay(orderid /*,Double.valueOf(money)*/);
    }

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if (msg ==null)return;
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
                        //---------------友盟自定义 支付成功 事件-------------
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("orderId",orderid);
                        map.put("payType",Constants.PAY_TYPE_ALIPAY);
                        MobclickAgent.onEvent(getActivity(), "pay_succ_id", map);
                        //---------------友盟自定义 支付成功 事件-------------
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_KEY_ORDERID, orderid);
                        bundle.putString(Constants.BUNDLE_KEY_PAY_TYPE, Constants.PAY_TYPE_ALIPAY);
                        bundle.putString(Constants.BUNDLE_KEY_ALI_RESULT, new JSONObject((Map) msg.obj).toString());
                        startContainerActivity(PaySuccessFragment.class.getCanonicalName(), bundle);
//                        getActivity().finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.ORDER_ID, viewModel.order_id);
//                        bundle.putString(Constants.ORDER_PRICE, viewModel.entity.get().getOrder_price());
                        startActivity(OrderDetailAty.class, bundle);
                        getActivity().finish();
                    }
                    break;
                }
            }
        }
    };


    @Override
    public void initViewObservable() {
        viewModel.uc.payAlipay.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String orderInfo) {
//                EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE);
                Log.d("alipay入参：========", orderInfo);
                final Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(orderInfo, true);
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

        viewModel.uc.updataItemUI.observe(this, new Observer<List<OrderPayEntity.ResultBean.PayDataBean>>() {
            @Override
            public void onChanged(@Nullable List<OrderPayEntity.ResultBean.PayDataBean> payDataBeans) {
                for (OrderPayEntity.ResultBean.PayDataBean payDataBean : payDataBeans) {
                    if (TextUtils.equals(payDataBean.getId(), Constants.PAY_TYPE_ALIPAY)) {
                        binding.fpAliGroup.setVisibility(View.VISIBLE);
                    } else if (TextUtils.equals(payDataBean.getId(), Constants.PAY_TYPE_WX)) {
                        binding.fpWxGroup.setVisibility(View.VISIBLE);
                    } else if (TextUtils.equals(payDataBean.getId(), Constants.PAY_TYPE_CREDIT)) {
                        binding.fpCreditGroup.setVisibility(View.VISIBLE);
                    } else if (TextUtils.equals(payDataBean.getId(), Constants.PAY_TYPE_OTHER)) {
                        binding.fpOtherGroup.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        viewModel.uc.onBackListener.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                viewModel.mCommonMethod();
                getActivity().finish();
            }
        });


        viewModel.uc.back.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ORDER_ID, viewModel.order_id);
                if(viewModel.entity.get().getOrder_price()!=null) {
                    bundle.putString(Constants.ORDER_PRICE, viewModel.entity.get().getOrder_price());
                }
                startActivity(OrderDetailAty.class, bundle);
                getActivity().finish();
            }
        });

    }

    @Override
    public boolean isBackPressed() {  //PayViewModel 监听home 返回失效处理
        viewModel.onBackPressed();
        return true;
    }
}
