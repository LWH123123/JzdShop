package com.jzd.jzshop.ui.home.creditsstore.all_shop;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityAllCredityGoodsBinding;
import com.jzd.jzshop.entity.CreditsAllGoodsEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreActivity;
import com.jzd.jzshop.ui.home.creditsstore.mine.CreditsMineActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 积分商城 - 全部商品
 * @date :2020/5/9 15:18
 */
public class AllCreditGoodsActivity extends BaseActivity<ActivityAllCredityGoodsBinding, AllCreditGoodsViewModel> implements OnRefreshListener
        , View.OnClickListener, OnLoadmoreListener {
    private NavigationController build;
    private int page = 1;
    private boolean isRefresh = true; //是否是刷新
    private String cate_id, cate_name;
    private CreditsStoreAllGoodsAdapter adapter;
    private boolean isUp = true;
    private List<CreditsAllGoodsEntity.ResultBean.CategoryBean> category;
    private String menuText;
    private CreditsGoodsMenuAdapter creditsGoodsMenuAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_all_credity_goods;
    }

    @Override
    public int initVariableId() {
        return BR.allCreditGoodsVM;
    }

    @Override
    public AllCreditGoodsViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AllCreditGoodsViewModel.class);
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
//        viewModel.initToolBar(mContext.getResources().getString(R.string.credits_store_all_goods));
        if (!TextUtils.isEmpty(cate_name)) {
            binding.tvTitle.setText(cate_name);
        } else {
            binding.tvTitle.setText(mContext.getResources().getString(R.string.credits_store_all_goods));
        }
        initBottomTab();
        mKeywordSearch();
        viewModel.requestData(binding.mallRefreshLayout, cate_id, "", "", page, isRefresh);
        initMallRefresh();
        setClickEvent();
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cate_id = bundle.getString(Constants.BUNDLE_KEY_CATE_ID);
            cate_name = bundle.getString(Constants.BUNDLE_KEY_CATE_NAME);
            Log.d(TAG, "cate_name:" + cate_name + "cate_id" + cate_id);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<CreditsAllGoodsEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditsAllGoodsEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    category = resultBean.getCategory();
                }

            }
        });
        viewModel.uc.mAllLiveData.observe(this, new Observer<List<CreditsAllGoodsEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<CreditsAllGoodsEntity.ResultBean.DataBean> result) {
                if (page == 1 && result.size() == 0) {
                    binding.mallRefreshLayout.setEnableRefresh(false);
                    binding.mallRefreshLayout.setEnableAutoLoadmore(false);
                    binding.mallRefreshLayout.setEnableLoadmore(false);
                } else {
                    binding.mallRefreshLayout.setEnableRefresh(true);
                    binding.mallRefreshLayout.setEnableAutoLoadmore(true);
                }
                initRecycleView(result);
            }
        });

    }

    private void initRecycleView(List<CreditsAllGoodsEntity.ResultBean.DataBean> data) {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.emptyView.setVisibility(View.GONE);
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            binding.recyclerView.setLayoutManager(gridLayoutManager);
            adapter = new CreditsStoreAllGoodsAdapter(mContext, data, R.layout.item_credits_goods_all);
            binding.recyclerView.setAdapter(adapter);
//        if (page > 1){
//            binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
//        }
        }
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if (position > data.size())return;
                String gid = data.get(position).getGid();
                Log.d(TAG, "商品gid===>>>:" + gid);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CREDIT_GOODS_DETAIL, gid);
                startActivity(CreditGoodsDetailsActivity.class, bundle);
            }
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                //获取RecyclerView当前顶部显示的第一个条目对应的索引
                int position = layoutManager.findFirstVisibleItemPosition();
                //根据索引来获取对应的itemView
                View firstVisiableChildView = layoutManager.findViewByPosition(position);
                //获取当前显示条目的高度
                int itemHeight = firstVisiableChildView.getHeight();
                //获取当前Recyclerview 偏移量
                int flag = (position) * itemHeight - firstVisiableChildView.getTop();
                //注意事项：recyclerView不要设置padding
                if(flag==0)
                    binding.ivGoTop.setVisibility(View.GONE);
                else
                    binding.ivGoTop.setVisibility(View.VISIBLE);
            }
        });
    }


    private void setClickEvent() {
        binding.tvTitle.setOnClickListener(this);
        binding.consArrows.setOnClickListener(this);
//        binding.etSearchShop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){//获取焦点
//                    binding.pagerBottomTab.setVisibility(View.GONE);
//                }else {
//                    binding.pagerBottomTab.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    private void mKeywordSearch() {
        binding.etSearchShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideKeyboard(binding.etSearchShop);//点击搜索的时候隐藏软键盘
//                    binding.pagerBottomTab.setVisibility(View.VISIBLE);
                    String keyWord = binding.etSearchShop.getText().toString().trim();
                    viewModel.requestDataByKeywords(binding.mallRefreshLayout, keyWord, cate_id, page, isRefresh);
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
            case R.id.cons_arrows: //下拉筛选菜单
                if (isUp) {
                    isUp = false;
                    binding.ivArrowsDown.setBackgroundResource(R.mipmap.ic_arrows_menu_up);
                    Animation animation = AnimationUtils.loadAnimation(AllCreditGoodsActivity.this, R.anim.dropdown_in);
                    binding.ivArrowsDown.startAnimation(animation);
                    showDropDownMenu();
                    KeyboardUtils.hideKeyboard(binding.etSearchShop);//点击搜索的时候隐藏软键盘
                } else {
                    mCommon();
                    hideDropDownMenu();
                }
                break;
        }
    }

    private void mCommon() {
        isUp = true;
        binding.ivArrowsDown.setBackgroundResource(R.mipmap.ic_arrows_menu_down);
        Animation animation = AnimationUtils.loadAnimation(AllCreditGoodsActivity.this, R.anim.dropdown_out);
        binding.ivArrowsDown.startAnimation(animation);
    }

    /**
     * 显示 下拉筛选菜单
     */
    private void showDropDownMenu() {
        binding.consPullMenu.setVisibility(View.VISIBLE);
        binding.consPullMenu.setEnabled(false);
        String trim = binding.tvTitle.getText().toString().trim();
        if (category != null && category.size() > 0) {
            binding.recyMenu.setVisibility(View.VISIBLE);
            binding.tvMenuEmptyTxt.setVisibility(View.GONE);
            creditsGoodsMenuAdapter = new CreditsGoodsMenuAdapter(mContext, trim, category, R.layout.item_rv_credits_goods_menu);
            binding.recyMenu.setAdapter(creditsGoodsMenuAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            binding.recyMenu.setLayoutManager(gridLayoutManager);
            creditsGoodsMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int viewType, int position) {//类型
                    creditsGoodsMenuAdapter.setSelectedPosition(position);     //选中，设置背景颜色
                    CreditsAllGoodsEntity.ResultBean.CategoryBean categoryBean = category.get(position);
                    if (categoryBean != null) {
                        if (!TextUtils.isEmpty(categoryBean.getCate_name())) {
                            menuText = categoryBean.getCate_name();
                            cate_id = categoryBean.getCate_id();
                            binding.tvTitle.setText(menuText);
                            hideDropDownMenu();
                            mCommon();
                            //刷新筛选后的数据
                            String keyWord = binding.etSearchShop.getText().toString().trim();
                            page = 1;
                            isRefresh = true;
                            if (!TextUtils.isEmpty(keyWord)) {
                                viewModel.requestData(binding.mallRefreshLayout, cate_id, keyWord, "", page, isRefresh);
                            } else {
                                viewModel.requestData(binding.mallRefreshLayout, cate_id, "", "", page, isRefresh);
                            }
                        }
                    }
                }
            });
        } else {
            binding.tvMenuEmptyTxt.setVisibility(View.VISIBLE);
            binding.recyMenu.setVisibility(View.GONE);

        }
    }

    /**
     * 隐藏 下拉筛选菜单
     */
    private void hideDropDownMenu() {
        binding.consPullMenu.setVisibility(View.GONE);
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
                break;
            case 2:
                startActivity(CreditsMineActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCommon();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build.hideBottomLayout();
    }


    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);    //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);        //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);           //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);     //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);       //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);   //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);   //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(true);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
        binding.mallRefreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        isRefresh = true;
        viewModel.requestData(binding.mallRefreshLayout, cate_id, "", "", page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestData(binding.mallRefreshLayout, cate_id, "", "", page, isRefresh);
    }
}
