package com.jzd.jzshop.ui.mine.coupon;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMineCouponBinding;
import com.jzd.jzshop.utils.SetTablayout;

import me.goldze.mvvmhabit.base.BaseFragment;


/**我的优惠券
 * A simple {@link Fragment} subclass.
 */
public class MineCouponFragment extends BaseFragment<FragmentMineCouponBinding, MineCouponViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine_coupon;
    }

    @Override
    public int initVariableId() {
        return BR.minecouponVM;
    }

    @Override
    public MineCouponViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineCouponViewModel.class);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        viewModel.setBinding(binding);
        initTab();
    }

    public void initTab() {
        SetTablayout.setIndicatorWidth(binding.tabLayout, 90);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("未使用"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("已使用"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("已过期"));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
    }


}
