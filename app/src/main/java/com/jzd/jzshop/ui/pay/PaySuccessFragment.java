package com.jzd.jzshop.ui.pay;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentPaySuccessBinding;
import com.jzd.jzshop.utils.Constants;

import cn.jpush.android.api.JPushInterface;
import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 * 支付成功页
 */
public class PaySuccessFragment extends BaseFragment<FragmentPaySuccessBinding, PaySuccessViewModel> {
    private static final String TAG = "PaySuccessFragment";
    private String orderid;
    private String pay_type;
    private String ali_result;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_pay_success;
    }

    @Override
    public int initVariableId() {
        return BR.paySuccessVM;
    }

    @Override
    public void initParam() {
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            orderid = mBundle.getString(Constants.BUNDLE_KEY_ORDERID);
            pay_type = mBundle.getString(Constants.BUNDLE_KEY_PAY_TYPE);
            ali_result = mBundle.getString(Constants.BUNDLE_KEY_ALI_RESULT);
        }
    }

    @Override
    public PaySuccessViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(AppApplication.getInstance());
        return ViewModelProviders.of(this, factory).get(PaySuccessViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        viewModel.requestNetWork(orderid, pay_type, ali_result);
        viewModel.setBinding(binding, getActivity());
        //推送 下单成功后 消息推送
//        String regid = JPushInterface.getRegistrationID(AppApplication.getInstance());
//        if (!regid.isEmpty()) {
//            Log.d(TAG, "RegId:" + regid);
//            viewModel.requestPushMessage(regid);
//        } else {
//            Log.e(TAG, "Get registration fail, JPush init failed!");
//        }

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }
}
