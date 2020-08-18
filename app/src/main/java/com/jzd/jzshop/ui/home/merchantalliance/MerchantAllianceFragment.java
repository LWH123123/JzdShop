package com.jzd.jzshop.ui.home.merchantalliance;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMerchantAllianceBinding;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * 商家联盟
 */
public class MerchantAllianceFragment extends BaseFragment<FragmentMerchantAllianceBinding, MerchantAllianceViewModel> implements OnRefreshListener, OnLoadmoreListener
        , XBanner.OnItemClickListener {
    private static final String TAG = MerchantAllianceFragment.class.getSimpleName();
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_merchant_alliance;
    }

    @Override
    public int initVariableId() {
        return BR.maVM;
    }

    @Override
    public MerchantAllianceViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MerchantAllianceViewModel.class);
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));
        viewModel.setBinding(binding);
        viewModel.initToolBar(mActivity.getResources().getString(R.string.merchant_alliance));
        viewModel.requestNetWork(binding.mallRefreshLayout, page, isRefresh);
        mKeywordSearch();
        initMallRefresh(); //配置刷新
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.xbannerRefreshing.observe(this, new Observer<List<BannEntity>>() {
            @Override
            public void onChanged(@Nullable List<BannEntity> bean) {
                if (bean != null && bean.size() == 0) {
                    binding.fmaXbanner.setVisibility(View.GONE);
                    return;
                }
                binding.fmaXbanner.setBannerData(bean);
                binding.fmaXbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        BannEntity ban = (BannEntity) model;
                        Glide.with(AppApplication.getInstance()).load(ban.getXBannerUrl()).into((ImageView) view);
                    }
                });
                binding.fmaXbanner.startAutoPlay();
            }
        });
        binding.fmaXbanner.setOnItemClickListener(this);

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        isRefresh = true;
        viewModel.fma_merch_ob.clear();
        viewModel.requestNetWork(binding.mallRefreshLayout, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestNetWork(binding.mallRefreshLayout, page, isRefresh);

    }

    /**
     * 自定义搜索框
     */
    private void mKeywordSearch() {
        SystemUtils.customeStyle(getActivity(), binding.fmaSearch);
        binding.fmaSearch.onActionViewExpanded();
        binding.fmaSearch.clearFocus();
        binding.fmaSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.requestNetWork(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        binding.etSearchShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideKeyboard(binding.etSearchShop);//点击搜索的时候隐藏软键盘
                    String keyWord = binding.etSearchShop.getText().toString().trim();
                    viewModel.requestNetWork(keyWord);
                    return true;
                }
                return false;
            }
        });

    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(true);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
        binding.mallRefreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }


    @Override
    public void onItemClick(XBanner banner, Object model, View view, int position) {
        BannEntity bannEntity = (BannEntity) model;
        if (bannEntity != null) {
            String linkurl = bannEntity.getUrl();
            Log.d(TAG, "linkUrl：" + linkurl);
            AppIdentityJumpUtils.homeMenujumpLinkUrl(linkurl,viewModel,mActivity);
        }
    }
}
