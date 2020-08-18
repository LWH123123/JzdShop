package com.jzd.jzshop.ui.home.creditsstore.goods_details.details;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentCreditDetailsWebBinding;
import com.jzd.jzshop.ui.home.merchantalliance.MerchantAllianceViewModel;
import com.jzd.jzshop.utils.BaseWebviewUtils;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditDetailsWebFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditDetailsWebFrag extends BaseFragment<FragmentCreditDetailsWebBinding,CreditDetailsWebViewModel> {

    private static final String WEB_DATA = "param1";


    public  CreditDetailsWebFrag newInstance(String web) {
        CreditDetailsWebFrag fragment = new CreditDetailsWebFrag();
        Bundle args = new Bundle();
        args.putString(WEB_DATA, web);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_credit_details_web;
    }

    @Override
    public int initVariableId() {
        return BR.creditwebViewModel;
    }

    @Override
    public CreditDetailsWebViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CreditDetailsWebViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        getIntentData();
    }

    private void getIntentData() {
        if(getArguments()!=null){
            String webdata = getArguments().getString(WEB_DATA);
            KLog.a("我收到的数据为"+webdata);
             if(!TextUtils.isEmpty(webdata)){
                 binding.web.setVisibility(View.VISIBLE);
                 binding.tvDefault.setVisibility(View.GONE);
                 String htmlData = BaseWebviewUtils.getHtmlData(webdata);
                 binding.web.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
             }else {
                 binding.tvDefault.setVisibility(View.VISIBLE);
                 binding.web.setVisibility(View.GONE);
             }
        }else {
            binding.tvDefault.setVisibility(View.VISIBLE);
            binding.web.setVisibility(View.GONE);
        }
    }
}
