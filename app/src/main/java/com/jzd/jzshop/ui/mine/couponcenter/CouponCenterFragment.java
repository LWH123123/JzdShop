package com.jzd.jzshop.ui.mine.couponcenter;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentCouponCenterBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CouponCenterFragment extends BaseFragment<FragmentCouponCenterBinding,CouponCenterViewModel> implements OnRefreshListener, OnLoadmoreListener {

    private int page=1;
    private boolean isrefresh=true;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_coupon_center;
    }

    @Override
    public int initVariableId() {
        return BR.couponcenterVM;
    }

    @Override
    public CouponCenterViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CouponCenterViewModel.class);
    }
    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        CouponCenterAdapter couponCenterAdapter = new CouponCenterAdapter();

        binding.setAdapter(couponCenterAdapter);
        viewModel.requestwork(binding.smartrefreshlayout,page,isrefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isrefresh=false;
        viewModel.requestwork(binding.smartrefreshlayout,page,isrefresh);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page=1;
        isrefresh=true;
        viewModel.requestwork(binding.smartrefreshlayout,page,isrefresh);
    }
}
