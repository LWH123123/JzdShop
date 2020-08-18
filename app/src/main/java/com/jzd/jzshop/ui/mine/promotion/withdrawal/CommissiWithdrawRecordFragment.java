package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentCommissiWithdrawRecordBinding;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.entity.CommisWithdraRecordEntity;
import com.jzd.jzshop.entity.WithdrawalsRecordEntity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 9:53
 */
public class CommissiWithdrawRecordFragment extends BaseFragment<FragmentCommissiWithdrawRecordBinding, CommissiWithdrawRecordViewModel>
        implements OnRefreshListener, OnLoadmoreListener {
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private CommissiWithdrawRecordAdapter adapter;

    public CommissiWithdrawRecordFragment() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_commissi_withdraw_record;
    }

    @Override
    public int initVariableId() {
        return BR.commissiWithdrawRecordVM;
    }

    @Override
    public CommissiWithdrawRecordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CommissiWithdrawRecordViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(getActivity(),binding);
        viewModel.requestData(binding.refreshLayout, "2",page, isRefresh);
        initMallRefresh(); //配置刷新
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<List<CommisWithdraRecordEntity.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<CommisWithdraRecordEntity.DataBean> result) {
                if (page == 1 && result.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }
                KLog.a("绑定适配器 ==>>"+result.size());
                binding.rv.setVisibility(View.VISIBLE);
                if (adapter == null){
                    adapter = new CommissiWithdrawRecordAdapter(getActivity(),
                            result, R.layout.item_rv_commissi_withdrals_record);
                    binding.rv.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    binding.rv.setLayoutManager(linearLayoutManager);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.notifyDataSetChanged();
                }
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

        binding.refreshLayout.setHeaderHeight(68);          //设置header高度
        binding.refreshLayout.setFooterHeight(68);          //设置footer高度

        binding.refreshLayout.setEnableRefresh(true);
        binding.refreshLayout.setEnableAutoLoadmore(true);  //开启自动加载功能
        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        isRefresh = true;
        viewModel.requestData(binding.refreshLayout, "2",page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestData(binding.refreshLayout,"2" ,page, isRefresh);
    }
}
