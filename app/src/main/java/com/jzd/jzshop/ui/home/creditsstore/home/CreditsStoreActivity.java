package com.jzd.jzshop.ui.home.creditsstore.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditsStoreBinding;
import com.jzd.jzshop.entity.CreditsStoreEntity;
import com.jzd.jzshop.entity.bean.CreditsStoreEntityLocal;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.home.creditsstore.PartakeRecordActivity;
import com.jzd.jzshop.ui.home.creditsstore.all_shop.AllCreditGoodsActivity;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.home.creditsstore.mine.CreditsMineActivity;
import com.jzd.jzshop.ui.home.news.RecyclerViewSpacesItemDecorationUtils;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 积分商城
 * @date :2020/5/8 14:44
 */
public class CreditsStoreActivity extends BaseActivity<ActivityCreditsStoreBinding, CreditsStoreViewModel> implements OnRefreshListener {
    private NavigationController build;
    private List<CreditsStoreEntityLocal> localData;
    private CreditsStoreEntity.ResultBean.MemberBean member;
    private List<CreditsStoreEntity.ResultBean.CategoryBean> category;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credits_store;
    }

    @Override
    public int initVariableId() {
        return BR.creditsStoreVM;
    }

    @Override
    public CreditsStoreViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditsStoreViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(0);
//        viewModel.requestData(binding.mallRefreshLayout);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(mContext.getResources().getString(R.string.credits_store));
        initBottomTab();

        localData = new ArrayList<>();
        viewModel.requestData(binding.mallRefreshLayout);
        initMallRefresh();
    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<CreditsStoreEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditsStoreEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    //用户积分
                    member = resultBean.getMember();
                    //积分商城分类信息
                    category = resultBean.getCategory();
                    List<CreditsStoreEntity.ResultBean.DataBeanX> data = resultBean.getData(); //商品信息
                    localData.clear();
                    if (data != null && data.size() > 0) {
                        for (CreditsStoreEntity.ResultBean.DataBeanX datum : data) {
                            if (datum.getId().equals("lotterydraws")) { //抽奖专区
                                localData.add(new CreditsStoreEntityLocal("抽奖专区", datum.getData()));
                            } else if (datum.getId().equals("exchanges")) { //积分实物兑换
                                localData.add(new CreditsStoreEntityLocal("积分实物兑换", datum.getData()));
                            } else if (datum.getId().equals("coupons")) { //积分兑换券
                                localData.add(new CreditsStoreEntityLocal("积分兑换券", datum.getData()));
                            } else if (datum.getId().equals("balances")) { //余额兑换区
                                localData.add(new CreditsStoreEntityLocal("余额兑换区", datum.getData()));
                            } else if (datum.getId().equals("redbags")) { //红包兑换区
                                localData.add(new CreditsStoreEntityLocal("红包兑换区", datum.getData()));
                            } else {
                            }
                        }
                        initRecycleView(localData, resultBean);
                    }else {
                        binding.emptyView.setVisibility(View.VISIBLE);
                        initEmptylistHeaderData(member,category);
                        binding.recyclerView.setVisibility(View.GONE);
                    }
                }

            }
        });

    }

    /**
     * set Empty Heade data
     * @param member
     * @param category
     */
    private void initEmptylistHeaderData(CreditsStoreEntity.ResultBean.MemberBean member, List<CreditsStoreEntity.ResultBean.CategoryBean> category) {
        if (member != null) {
            binding.tvCreditNum.setText(String.valueOf(member.getPoints()));
        }
        binding.tvCreditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //参与记录
                if (member != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BUNDLE_KEY_CREDITS_NUM, String.valueOf(member.getPoints()));
                    startActivity(PartakeRecordActivity.class,bundle);
                }
            }
        });
        if (category != null && category.size() > 0) {
            binding.recycleCategory.setVisibility(View.VISIBLE);
            initCategoryRecycleView(binding.recycleCategory, category);
        } else {
            binding.recycleCategory.setVisibility(View.GONE);
        }

    }

    private void initRecycleView(List<CreditsStoreEntityLocal> localData, CreditsStoreEntity.ResultBean resultBean) {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.emptyView.setVisibility(View.GONE);
        if (localData != null && localData.size() > 0) {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            CreditsStoreRecycleAdapter mAdapter = new CreditsStoreRecycleAdapter(mContext,viewModel, localData, R.layout.item_credits_store);
            binding.recyclerView.setAdapter(mAdapter);
            View headView = LayoutInflater.from(mContext).inflate(R.layout.item_credits_store_head_view, null);
            mAdapter.addHeaderView(headView);
            initHeaderData(headView, resultBean);
            mAdapter.setOnitemClick(new CreditsStoreRecycleAdapter.OnitemClick() {
                @Override
                public void OnitemClick(CreditsStoreEntity.ResultBean.DataBeanX.DataBean dataBean) {
                    KLog.a("点击的商品gid为===>>>"+dataBean.getGid());
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CREDIT_GOODS_DETAIL,dataBean.getGid());
                    startActivity(CreditGoodsDetailsActivity.class,bundle);
                }
            });
        }

    }

    //set header data
    private void initHeaderData(View headView, CreditsStoreEntity.ResultBean resultBean) {
        TextView tv_credit_num = headView.findViewById(R.id.tv_credit_num);
        AppCompatTextView tv_credit_record = headView.findViewById(R.id.tv_credit_record);
        List<CreditsStoreEntity.ResultBean.CategoryBean> category = resultBean.getCategory();
        tv_credit_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //参与记录
                if (member != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BUNDLE_KEY_CREDITS_NUM, String.valueOf(member.getPoints()));
                    startActivity(PartakeRecordActivity.class,bundle);
                }
            }
        });
        RecyclerView recycle_category = headView.findViewById(R.id.recycle_category);
        CreditsStoreEntity.ResultBean.MemberBean member = resultBean.getMember();
        if (member != null) {
            tv_credit_num.setText(String.valueOf(member.getPoints()));
        }
        if (category != null && category.size() > 0) {
            recycle_category.setVisibility(View.VISIBLE);
            initCategoryRecycleView(recycle_category, category);
        } else {
            recycle_category.setVisibility(View.GONE);
        }

    }

    /**
     * set category data
     *
     * @param recycle_category
     * @param category
     */
    private void initCategoryRecycleView(RecyclerView recycle_category,
                                         List<CreditsStoreEntity.ResultBean.CategoryBean> category) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recycle_category.setLayoutManager(gridLayoutManager);
        CreditStoreCategoryAdapter categoryAdapter =
                new CreditStoreCategoryAdapter(mContext, viewModel, category, R.layout.item_credit_store_category);
        recycle_category.setAdapter(categoryAdapter);
        setRecySpaceLeft$Right(recycle_category, 20, 30);
        categoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if (position >= category.size())return;
                String cate_id = category.get(position).getCate_id();
                String cate_name = category.get(position).getCate_name();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_CATE_NAME, cate_name);
                bundle.putString(Constants.BUNDLE_KEY_CATE_ID, cate_id);
                startActivity(AllCreditGoodsActivity.class, bundle);
            }
        });
    }

    private void setRecySpaceLeft$Right(RecyclerView dyRecyclerView, int left, int right) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.RIGHT_DECORATION, right);//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.LEFT_DECORATION, left);//右间距
        dyRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecorationUtils(stringIntegerHashMap));
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
                break;
            case 1:
                startActivity(AllCreditGoodsActivity.class);
                break;
            case 2:
                startActivity(CreditsMineActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build.hideBottomLayout();
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
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
//        mallRefreshLayout.setOnLoadmoreListener()
        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestData(binding.mallRefreshLayout);
    }
}
