package com.jzd.jzshop.ui.mine.creditsign;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentSignRecordBinding;
import com.jzd.jzshop.entity.SignRecordEntity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * 签到记录
 * A simple {@link Fragment} subclass.
 */
public class SignRecordFragment extends BaseFragment<FragmentSignRecordBinding, SignRecordViewModel>
        implements OnRefreshListener, OnLoadmoreListener {
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private SignRecordAdapter signRecordAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_sign_record;
    }

    @Override
    public int initVariableId() {
        return BR.signrecordVM;
    }


    @Override
    public SignRecordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(SignRecordViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
        viewModel.initToolBar(getResources().getString(R.string.sign_record));
        viewModel.setBinding(getActivity(), binding);
        viewModel.requestData(binding.refreshLayout, page, isRefresh);
        initMallRefresh();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.datacall.observe(this, new Observer<List<SignRecordEntity.ResultBean.DaatBean>>() {
            @Override
            public void onChanged(@Nullable List<SignRecordEntity.ResultBean.DaatBean> result) {
                KLog.a("数据长度===>>>" + result.size());
                if (page == 1 && result.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }

                if (signRecordAdapter != null) {
                    signRecordAdapter.notifyDataSetChanged();
                }else {
                    signRecordAdapter = new SignRecordAdapter(getContext(), result, R.layout.item_sign_record);
                    binding.rvc.setAdapter(signRecordAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    binding.rvc.setLayoutManager(linearLayoutManager);
                    signRecordAdapter.notifyDataSetChanged();
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
        viewModel.requestData(binding.refreshLayout, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestData(binding.refreshLayout, page, isRefresh);
    }
}
