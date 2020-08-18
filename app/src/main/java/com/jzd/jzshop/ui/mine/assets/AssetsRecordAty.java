package com.jzd.jzshop.ui.mine.assets;

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
import com.jzd.jzshop.databinding.ActivityAssetsRecordBinding;
import com.jzd.jzshop.entity.AssetsRecordEntity;

import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP： 君子权记录
 */
public class AssetsRecordAty extends BaseActivity<ActivityAssetsRecordBinding, AssetsRecordViewModel> {
    private int currentTab = 0;      //默认选中第一个tab
    private List<String> tabList;
    private List<AssetsRecordEntity.ResultBean.AssetsRecordBean> dataList;
    private AssetsRecordEntity.ResultBean result;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_assets_record;
    }

    @Override
    public int initVariableId() {
        return BR.assetsRecordVM;
    }

    @Override
    public AssetsRecordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AssetsRecordViewModel.class);

    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.assets_record));
        viewModel.requestData(currentTab, 1);
    }


    private void getIntentData() {
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<AssetsRecordEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable AssetsRecordEntity.ResultBean resultBean) {
                result = resultBean;
                dataList = result.getData(); //list data
                mSetTabViewPagerData(dataList);
            }
        });
    }

    private void mSetTabViewPagerData(final List<AssetsRecordEntity.ResultBean.AssetsRecordBean> dataList) {
        initTabLayout(binding.slidingTabs);
        setupViewPager(binding.viewpager, dataList);
        binding.slidingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                Log.d(TAG, "onTabSelected:-->>" + currentTab);
                binding.viewpager.setCurrentItem(currentTab);
//                if (currentTab ==0){
//                    binding.constrlRefuseTip.setVisibility(View.VISIBLE);
//                }else {
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
        tabList = Arrays.asList(getResources().getStringArray(R.array.assets_record_tab_txt));
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }
        tabLayout.setupWithViewPager(binding.viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabRippleColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
    }

    private void setupViewPager(ViewPager viewpager, List<AssetsRecordEntity.ResultBean.AssetsRecordBean> dataList) {
        AssetsRecordPagerAdapter assetsRecordPagerAdapter = new AssetsRecordPagerAdapter(getSupportFragmentManager());
        // TODO: 2020/2/12 后期优化
//        for (int i = 0; i < tabList.size(); i++) {
//            assetsRecordPagerAdapter.addFragment(new AwardAllFragment().newInstance(dataList), tabList.get(i));
//        }
        assetsRecordPagerAdapter.addFragment(new AssetsRecoAllFragment().newInstance(dataList), tabList.get(0));
        assetsRecordPagerAdapter.addFragment(new AssetsRecoPaidFragment().newInstance(dataList), tabList.get(1));
        assetsRecordPagerAdapter.addFragment(new AssetsRecoAlreadyPaidFragment().newInstance(dataList), tabList.get(2));
        assetsRecordPagerAdapter.addFragment(new AssetsCompletedFragment().newInstance(dataList), tabList.get(3));
        viewpager.setAdapter(assetsRecordPagerAdapter);
    }
}
