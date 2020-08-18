package com.jzd.jzshop.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityHomeBinding;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListFragment;
import com.jzd.jzshop.ui.home.news.HomePageAdapter;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.UrlPreprocessingUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements OnRefreshListener, View.OnClickListener, XBanner.OnItemClickListener {
    private NavigationController build;
    //menu
    private HomePageAdapter homeMenuAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.homeVM;
    }

    @Override
    public HomeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        viewModel.setBinding(HomeActivity.this, binding);
        viewModel.requestHomeDataNetWork(binding.mallRefreshLayout);
        initMallRefresh(); //配置刷新
        initBottomTab();
        mKeywordSearch();
        String appVersionCode = SystemUtils.getVersion(mContext);
        viewModel.toAppUpdate(appVersionCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.constraintLayout.setFocusable(true);
        binding.constraintLayout.setFocusableInTouchMode(true);
        binding.constraintLayout.requestFocus();
        int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        build.setMessageNumber(2, anInt);
        build.setSelect(0);

//        String appVersionCode = SystemUtils.getVersion(mContext);
//        viewModel.toAppUpdate(appVersionCode);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.xbannerRefreshing.observe(this, new Observer<List<BannEntity>>() {

            @Override
            public void onChanged(@Nullable List<BannEntity> bean) {
                binding.xbanner.setBannerData(bean);
                binding.xbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        BannEntity ban = (BannEntity) model;
                        Glide.with(getApplication()).load(ban.getXBannerUrl()).into((ImageView) view);
                    }
                });
                binding.xbanner.startAutoPlay();
            }
        });
        binding.xbanner.setOnItemClickListener(this);

        viewModel.uc.appUpdate.observe(this, new Observer<AppUpdateEntity>() {
            @Override
            public void onChanged(@Nullable AppUpdateEntity appUpdateEntity) {
                if (appUpdateEntity != null) {
                    if (appUpdateEntity.getResult() != null) {
                        if (appUpdateEntity.getResult().getUrl() != null && !TextUtils.isEmpty(appUpdateEntity.getResult().getUrl())) {
                            mCommonMethod(appUpdateEntity.getResult().getUrl());
                        }
                    }
                }
            }
        });

    }

    public void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).init(binding.pagerBottomTab, new OnTabItemSelectedListener() {
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
                break;
            case 1:
                startActivity(CategoryActivity.class);
                break;
            case 2:
                startActivity(ShoppingCarActivity.class);
                break;
            case 3:
                startActivity(MyPageActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    /**
     * 关键词搜索
     */
    private void mKeywordSearch() {
        binding.ivSearch.setOnClickListener(this);
        binding.etSearchShop.setOnClickListener(this);
        binding.rlSearch.setOnClickListener(this);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestHomeDataNetWork(binding.mallRefreshLayout);
    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setTitleTextColors(R.color.white);
        binding.mallRefreshHeader.setTitleTimeColors(R.color.white);
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setmArrowViewWhite();
        ProgressDrawable progressDrawable = binding.mallRefreshHeader.getmProgressView();
        binding.mallRefreshHeader.setProgressDrawable(progressDrawable);
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
//        mallRefreshLayout.setOnLoadmoreListener()
        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
            case R.id.et_search_shop:
            case R.id.rl_search:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_KEYWORDS, "");
                startContainerActivity(GoodsListFragment.class.getCanonicalName());
                break;
            default:
                break;
        }
    }

    private void mCommonMethod(String url) {
        try {
            Intent intent = new Intent();    //调web浏览器
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(XBanner banner, Object object, View view, int position) {
        BannEntity xBannerData = (BannEntity) object;
        Bundle bundle = new Bundle();
        if (xBannerData != null && !TextUtils.isEmpty(viewModel.getUserToken())) {
            String linkurl = xBannerData.getUrl();
            Log.d(TAG, "linkUrl:" + linkurl);
            if (linkurl != null) {
                if (linkurl != null && !TextUtils.isEmpty(linkurl) && (linkurl.contains("http://") || linkurl.contains("https://"))) {
                    if (linkurl.contains("=goods.detail")) {
                        String param = SystemUtils.URLRequest(linkurl).get("id");   //android 获取url中的参数
                        KLog.d(TAG, "id===" + param);
                        //跳转需要跳转的页面
                        bundle.putString(Constants.GOODS_KEY_GID, param);
                        viewModel.startActivity(GoodsActivity.class, bundle);
                    } else {
                        bundle.putString(Constants.BUNDLE_KEY_URL, linkurl + viewModel.getUserToken());
                        startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
                    }
                } else {
                    int index = UrlPreprocessingUtils.interceptUrl(linkurl);
                    if (Constants.URL.APP_UI_SKIP_FLAG_FIRST == index) {
                        startActivity(HomeActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_CATEGORY == index) {
                        startActivity(CategoryActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_GOODS == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_NOTICE == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_CART == index) {
                        startActivity(ShoppingCarActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_DIVIDEND == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_EXCHANGE == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_ABONUS == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_MEMBER == index) {
                        startActivity(MyPageActivity.class);
                    } else {

                    }
                }
            }
        } else {
            ToastUtils.showLong("请登录！！");
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build = null;
    }
}
