package com.jzd.jzshop.ui.home.creditsstore.mine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditsMineBinding;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.home.creditsstore.all_shop.AllCreditGoodsActivity;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreActivity;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordPagerAdapter;
import com.jzd.jzshop.utils.Constants;

import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 积分商城 -- 我的
 * @date :2020/5/9 16:35
 */
public class CreditsMineActivity extends BaseActivity<ActivityCreditsMineBinding, CreditsMineViewModel> {
    private NavigationController build;
    private int currentTab = 0;      //默认选中第一个tab
    private List<String> tabList;
    private int openFlag;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credits_mine;
    }

    @Override
    public int initVariableId() {
        return BR.creditsMineVM;
    }

    @Override
    public CreditsMineViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditsMineViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(2);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(mContext.getResources().getString(R.string.credit_mine));
        initBottomTab();
        viewModel.requestData();
        mSetTabViewPagerData();
        binding.viewpager.setCurrentItem(currentTab);
    }


    private void getIntentData() {
        openFlag = getIntent().getIntExtra(Constants.GOODS_OPEN_FLAG, 0);
        Log.d(TAG, "openFlag:--->>" + openFlag);
        if (openFlag == 0) {
            currentTab = 0;
        } else if (openFlag == 1) {
            currentTab = 1;
        } else {
            currentTab = 0;
        }
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

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }


    private void initTabLayout(TabLayout tabLayout) {
        tabList = Arrays.asList(getResources().getStringArray(R.array.credits_tab_txt));
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
        assetsRecordPagerAdapter.addFragment(new CreditExchangeRecoFragment().newInstance(), tabList.get(0));
        assetsRecordPagerAdapter.addFragment(new CreditLotteryRecoFragment().newInstance(), tabList.get(1));
        viewpager.setAdapter(assetsRecordPagerAdapter);
    }

    public void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).initCreditStore(binding.pagerBottomTab,
                new OnTabItemSelectedListener() {
                    @Override
                    public void onSelected(int index, int old) {
                        startOtherActivity(index);
                    }

                    @Override
                    public void onRepeat(int index) {
                    }
                });
    }

    private void startOtherActivity(int index) {
        switch (index) {
            case 0:
                startActivity(CreditsStoreActivity.class);
                break;
            case 1:
                startActivity(AllCreditGoodsActivity.class);
                break;
            case 2:
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build.hideBottomLayout();
    }
}
