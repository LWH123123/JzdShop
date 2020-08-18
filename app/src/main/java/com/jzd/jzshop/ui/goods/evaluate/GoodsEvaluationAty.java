package com.jzd.jzshop.ui.goods.evaluate;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityGoodsEvaluationBinding;
import com.jzd.jzshop.entity.GoodsEvaluationEntity;
import com.jzd.jzshop.entity.GoodsEvaluationTypeEntity;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * @author LXB
 * @description: 宝贝评价
 * @date :2019/12/21 13:50
 */
public class GoodsEvaluationAty extends BaseActivity<ActivityGoodsEvaluationBinding, GoodsEvaluationViewModel> implements OnRefreshListener, OnLoadmoreListener {
    private String gid;
    private String goodsType; //默认评价类型
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private GoodsEvaluationAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_evaluation;
    }

    @Override
    public int initVariableId() {
        return BR.goodsEvaluationVM;  //需注意这里 导入不带全包名路径BR，否则引用会有问题
    }

    @Override
    public GoodsEvaluationViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GoodsEvaluationViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.goods_evaluation));
        getIntentData();
        //本地重组评价类型数据
        List<GoodsEvaluationTypeEntity> dataList = new ArrayList<>();
        dataList.add(new GoodsEvaluationTypeEntity(getResources().getString(R.string.goods_evaluat_all)));
        dataList.add(new GoodsEvaluationTypeEntity(getResources().getString(R.string.goods_evaluat_good)));
        dataList.add(new GoodsEvaluationTypeEntity(getResources().getString(R.string.goods_evaluat_middle)));
        dataList.add(new GoodsEvaluationTypeEntity(getResources().getString(R.string.goods_evaluat_bad)));
        dataList.add(new GoodsEvaluationTypeEntity(getResources().getString(R.string.goods_evaluat_slide_pic)));
        viewModel.setGoodsTypeData(dataList);
        viewModel.requestNetWork(gid, goodsType, binding.mallRefreshLayout, page, isRefresh);
        initMallRefresh(); //配置刷新


    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gid = bundle.getString(Constants.GOODS_KEY_GID);
            goodsType = bundle.getString(Constants.GOODS_KEY_TYPE);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.noDataNotify.observe(this, new Observer<List<GoodsEvaluationEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<GoodsEvaluationEntity.ResultBean.DataBean> dataBeans) {
//                binding.rvType.setVisibility(View.GONE);
                binding.rvSrceenOut.setVisibility(View.GONE);
                binding.emptyView.setVisibility(View.VISIBLE);
                binding.mallRefreshLayout.setEnableRefresh(false);
                binding.mallRefreshLayout.setEnableLoadmore(false);

            }
        });

        viewModel.uc.mLiveData.observe(this, new Observer<List<GoodsEvaluationEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<GoodsEvaluationEntity.ResultBean.DataBean> result) {
                if (page == 1 && result.size() == 0) {
                    binding.mallRefreshLayout.setEnableRefresh(false);
                    binding.mallRefreshLayout.setEnableAutoLoadmore(false);
                    binding.mallRefreshLayout.setEnableLoadmore(false);
                } else {
                    binding.mallRefreshLayout.setEnableRefresh(true);
                    binding.mallRefreshLayout.setEnableAutoLoadmore(true);
                }
                binding.rvSrceenOut.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);
                if (mAdapter == null) {
                    mAdapter = new GoodsEvaluationAdapter(mContext, result, R.layout.item_rv_goods_evaluation_new);
                    binding.rvSrceenOut.setAdapter(mAdapter);
                    binding.rvSrceenOut.setLayoutManager(new LinearLayoutManager(mContext));
                } else {
                    mAdapter.notifyDataSetChanged();
                }
                if (isRefresh){//点击筛选后，数据置顶显示
                    binding.rvSrceenOut.smoothScrollToPosition(0);
                }else {
                }
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        isRefresh = true;
        String type = SPUtils.getInstance().getString(Constants.SP.CACHE_HOME_BANNER_DATA, goodsType);
        viewModel.requestNetWork(gid, type, binding.mallRefreshLayout, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        String type = SPUtils.getInstance().getString(Constants.SP.CACHE_HOME_BANNER_DATA, goodsType);
        viewModel.requestNetWork(gid, type, binding.mallRefreshLayout, page, isRefresh);
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
}
