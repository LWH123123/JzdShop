package com.jzd.jzshop.ui.mine.creditsign.ranking;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivitySignRankingBinding;
import com.jzd.jzshop.entity.SignRankingEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreActivity;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordPagerAdapter;
import com.jzd.jzshop.ui.mine.creditsign.CreditSignNewActivity;
import com.jzd.jzshop.ui.mine.creditsign.shop.CreditShopActivity;

import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 签到排行
 * @date :2020/4/7 10:07
 */
public class SignRankingActivity extends BaseActivity<ActivitySignRankingBinding, SignRankingViewModel> {
    private NavigationController build;
    private String type = "continue";      //默认选中第一个tab
    private int currentTab = 0;      //默认选中第一个tab
    private List<String> tabList;
    private List<SignRankingEntity.ResultBean.DaatBean> dataList;
    private SignRankingEntity.ResultBean result;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sign_ranking;
    }

    @Override
    public int initVariableId() {
        return BR.signRankingVM;
    }

    @Override
    public SignRankingViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SignRankingViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(1);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.sign_ranking));
        viewModel.requestData(type, 1, 10);
        initBottomTab();
    }

    private void getIntentData() {
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<SignRankingEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable SignRankingEntity.ResultBean resultBean) {
                result = resultBean;
                dataList = result.getDaat(); //list data
                mSetTabViewPagerData(dataList);
            }
        });

    }

    private void mSetTabViewPagerData(final List<SignRankingEntity.ResultBean.DaatBean> dataList) {
        initTabLayout(binding.slidingTabs);
        setupViewPager(binding.viewpager, dataList);
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
        tabList = Arrays.asList(getResources().getStringArray(R.array.sign_ranking_tab_txt));
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }
        tabLayout.setupWithViewPager(binding.viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabRippleColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
    }

    private void setupViewPager(ViewPager viewpager, List<SignRankingEntity.ResultBean.DaatBean> dataList) {
        AssetsRecordPagerAdapter assetsRecordPagerAdapter = new AssetsRecordPagerAdapter(getSupportFragmentManager());
        assetsRecordPagerAdapter.addFragment(new ContinuousSignInFragment().newInstance(dataList), tabList.get(0));
        assetsRecordPagerAdapter.addFragment(new TotalSignInFragment().newInstance(dataList), tabList.get(1));

        viewpager.setAdapter(assetsRecordPagerAdapter);
    }

    public void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).initCreditSign(binding.pagerBottomTab, new OnTabItemSelectedListener() {
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
                startActivity(CreditSignNewActivity.class);
                break;
            case 1:
                break;
            case 2:
                startActivity(CreditsStoreActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build = null;
    }
}
