package com.jzd.jzshop.ui.mine.promotion.award_list;

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
import com.jzd.jzshop.databinding.ActivityAwardListBinding;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordPagerAdapter;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：奖励明细
 */
public class AwardListAty extends BaseActivity<ActivityAwardListBinding, AwardListViewModel> {
    private int currentTab = 0;      //默认选中第一个tab
    private List<String> tabList;
    private List<AwardListEntity.ResultBean.AssetsRecordBean> dataList;
    private AwardListEntity.ResultBean result;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_award_list;
    }

    @Override
    public int initVariableId() {
        return BR.awardListVM;
    }

    @Override
    public AwardListViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AwardListViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.award_list));
        viewModel.requestData(currentTab);
    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<AwardListEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable AwardListEntity.ResultBean resultBean) {
                result = resultBean;
                double commission_total = result.getCommission_total(); //累计奖励
                String money = MoneyFormatUtils.keepTwoDecimal(commission_total);
                binding.tvAmount.setText(money.concat("元"));
                dataList = result.getData(); //list data
                mSetTabViewPagerData(dataList);
            }
        });
    }

    private void mSetTabViewPagerData(final List<AwardListEntity.ResultBean.AssetsRecordBean> dataList) {
        initTabLayout(binding.slidingTabs);
        setupViewPager(binding.viewpager, dataList);
        binding.slidingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                Log.d(TAG, "onTabSelected:-->>" + currentTab);
                binding.viewpager.setCurrentItem(currentTab);
//                if (currentTab == 0) {
//                    binding.constrlRefuseTip.setVisibility(View.VISIBLE);
//                } else {
//                    binding.constrlRefuseTip.setVisibility(View.GONE);
//                }
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
        tabList = Arrays.asList(getResources().getStringArray(R.array.award_list_txt));
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }
        tabLayout.setupWithViewPager(binding.viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabRippleColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
    }

    private void setupViewPager(ViewPager viewpager, List<AwardListEntity.ResultBean.AssetsRecordBean> dataList) {
        AssetsRecordPagerAdapter assetsRecordPagerAdapter = new AssetsRecordPagerAdapter(getSupportFragmentManager());
        // TODO: 2020/2/12 后期优化
//        for (int i = 0; i < tabList.size(); i++) {
//            assetsRecordPagerAdapter.addFragment(new AwardAllFragment().newInstance(dataList), tabList.get(i));
//        }
        assetsRecordPagerAdapter.addFragment(new AwardAllFragment().newInstance(dataList), tabList.get(0));
        assetsRecordPagerAdapter.addFragment(new AwardWaittingFragment().newInstance(dataList), tabList.get(1));
        assetsRecordPagerAdapter.addFragment(new AwardRecoPaidFragment().newInstance(dataList), tabList.get(2));
        assetsRecordPagerAdapter.addFragment(new AwardAlreadyPaidFragment().newInstance(dataList), tabList.get(3));
        assetsRecordPagerAdapter.addFragment(new AwardInvalidFragment().newInstance(dataList), tabList.get(4));
        viewpager.setAdapter(assetsRecordPagerAdapter);
    }
}
