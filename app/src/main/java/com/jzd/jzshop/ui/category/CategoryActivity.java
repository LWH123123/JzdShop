package com.jzd.jzshop.ui.category;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCategoryBinding;
import com.jzd.jzshop.entity.CategoryEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListFragment;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.message.MessageCenterActivity;
import com.jzd.jzshop.ui.mine.merch.MerchantEntryAty;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.ui.website.WebSiteActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class CategoryActivity extends BaseActivity<ActivityCategoryBinding, CategoryViewModel> implements View.OnClickListener {
    private NavigationController build;
    private int page = 1;   //分页页码

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_category;
    }

    @Override
    public CategoryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CategoryViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.categoryVM;
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.pageBottomTab.setFocusable(true);
        binding.pageBottomTab.setFocusableInTouchMode(true);
        binding.pageBottomTab.requestFocus();

        int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        if (anInt != 0) {
            build.setMessageNumber(3, anInt);
        }
        build.setSelect(1);
    }

    @Override
    public void initData() {
        super.initData();
        ArrayList<CategoryEntity.ResultBean> entity = (ArrayList<CategoryEntity.ResultBean>) SPUtils.getInstance().getSerializableEntity(Constants.SP.CACHE_CATEGORY_DATA);
        if(entity!=null&&entity.size()!=0){
            KLog.a("我使用的是缓冲中的数据");
            viewModel.updateData(entity);
        }
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        initBottomTab();
        binding.setAdapter(new CategoryAdapter());
        mKeywordSearch();
        viewModel.requestNetWork();
        //设置消息数
        if (!TextUtils.isEmpty(viewModel.getUserToken())) {
            viewModel.requestMessageData(page);
        } else {
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mMessageLiveData.observe(this, new Observer<MessageCenterEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable MessageCenterEntity.ResultBean resultBean) {
                int total_noread = resultBean.getTotal_noread();
                if (total_noread == 0) {
                    binding.ivMessageNumber.setVisibility(View.GONE);
                } else {
                    binding.ivMessageNumber.setText(String.valueOf(total_noread));
                    binding.ivMessageNumber.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).init(binding.pageBottomTab, new OnTabItemSelectedListener() {
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
                startActivity(HomePageActivity.class);
                break;
            case 1:
                break;
            case 2:
                startActivity(WebSiteActivity.class);
                break;
            case 3:
                startActivity(ShoppingCarActivity.class);
                break;
            case 4:
                startActivity(MyPageActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    /**
     * 关键词搜索
     */
    private void mKeywordSearch() {
        SystemUtils.customeStyle(this, binding.acIncludeToolbar);
        binding.acIncludeToolbar.clearFocus();
        binding.acIncludeToolbar.onActionViewExpanded();
        binding.acIncludeToolbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_KEYWORDS, s);
                startContainerActivity(GoodsListFragment.class.getCanonicalName(), bundle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        binding.ivSearch.setOnClickListener(this);
        binding.etSearchShop.setOnClickListener(this);
        binding.rlSearch.setOnClickListener(this);
        binding.ivMessage.setOnClickListener(this);
        binding.ivMessageNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
            case R.id.et_search_shop:
            case R.id.rl_search:
                KLog.a("Hello");
                RelativeLayout view =binding.rlSearch;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_KEYWORDS, "");
                startContainerActivity(GoodsListFragment.class.getCanonicalName());
//                startActivity(MerchantEntryAty.class);
//                startActivity(TestActivity.class);

                break;
            case R.id.iv_message:
            case R.id.iv_message_number:
                if (!TextUtils.isEmpty(viewModel.getUserToken())) {
                    startActivity(MessageCenterActivity.class);
                } else {
                    SystemUtils.openActivityByAnimation(CategoryActivity.this,mContext, LoginVertifyActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build = null;
    }

}
