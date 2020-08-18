package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCommissionWithdrawalBinding;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordPagerAdapter;

import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 佣金提现
 * @date :2020/4/13 9:12
 */
public class CommissionWithdrawalActivity extends BaseActivity<ActivityCommissionWithdrawalBinding, CommissionWithdrawalViewModel> {
    private int currentTab = 0;   //默认选中第一个tab
    private List<String> tabList;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_commission_withdrawal;
    }

    @Override
    public int initVariableId() {
        return BR.commissionWithdraVM;
    }

    @Override
    public CommissionWithdrawalViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CommissionWithdrawalViewModel.class);
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initToolBar(getResources().getString(R.string.tixian));
        viewModel.setBinding(mContext, binding);
        getIntentData();
        viewModel.requestData();
    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mSetTabViewPagerData();

    }

    private void mSetTabViewPagerData() {
        initTabLayout(binding.slidingTabs);
        setupViewPager(binding.viewpager);
        binding.slidingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                Log.d(TAG, "onTabSelected:-->>" + currentTab);
                binding.viewpager.setCurrentItem(currentTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabLayout(TabLayout tabLayout) {
        tabList = Arrays.asList(getResources().getStringArray(R.array.commission_withdr_tab_txt));
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }
        tabLayout.setupWithViewPager(binding.viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabRippleColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
    }

    private void setupViewPager(ViewPager viewpager) {
        AssetsRecordPagerAdapter assetsRecordPagerAdapter = new AssetsRecordPagerAdapter(getSupportFragmentManager());
        assetsRecordPagerAdapter.addFragment(new WithdrawApplyFragment(), tabList.get(0));
        assetsRecordPagerAdapter.addFragment(new InCommisWithdrawFragment(), tabList.get(1));
        assetsRecordPagerAdapter.addFragment(new CommissiWithdrawRecordFragment(), tabList.get(2));
        viewpager.setAdapter(assetsRecordPagerAdapter);
    }
}
