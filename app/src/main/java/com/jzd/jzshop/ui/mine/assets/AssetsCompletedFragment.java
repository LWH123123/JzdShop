package com.jzd.jzshop.ui.mine.assets;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentAssetsRecordPageBinding;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by lxb on 2020/2/19.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsCompletedFragment extends BaseFragment<FragmentAssetsRecordPageBinding, AssetsRecoAllFragmViewModel>
        implements OnRefreshListener, OnLoadmoreListener {
    private int status = 3;
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private List<AssetsRecordEntity.ResultBean.AssetsRecordBean> dataList;
    private List<AssetsRecordEntity.ResultBean.AssetsRecordBean.GoodBean> chidList;
    private AssetsRecordPageFragAdapter mAdapter;
    private AssetsRecordEntity.ResultBean.AssetsRecordBean data;

    public AssetsCompletedFragment() {

    }

    /**
     * 外部传递参数
     *
     * @param dataList
     * @return
     */
    public Fragment newInstance(List<AssetsRecordEntity.ResultBean.AssetsRecordBean> dataList) {
        AssetsCompletedFragment fragment = new AssetsCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) dataList);
        fragment.setArguments(bundle);
        return this;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();
        if (arguments != null) {
            dataList = (List<AssetsRecordEntity.ResultBean.AssetsRecordBean>) arguments.getSerializable("data");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_assets_record_page;
    }

    @Override
    public int initVariableId() {
        return com.jzd.jzshop.BR.assetsRecordFragmPageVM;
    }

    @Override
    public AssetsRecoAllFragmViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AssetsRecoAllFragmViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(getActivity(), binding);
        viewModel.requestData(status, binding.refreshLayout, page, isRefresh);

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<List<AssetsRecordEntity.ResultBean.AssetsRecordBean>>() {
            @Override
            public void onChanged(@Nullable List<AssetsRecordEntity.ResultBean.AssetsRecordBean> result) {
                if (page == 1 && result.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }
                if (mAdapter==null){
                    mAdapter = new AssetsRecordPageFragAdapter(getActivity(),
                            viewModel, result, R.layout.item_rv_assets_record_fragm_page);
                    binding.rv.setAdapter(mAdapter);
                    binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                }else {
                    mAdapter.notifyDataSetChanged();
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
        viewModel.requestData(status, binding.refreshLayout, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestData(status, binding.refreshLayout, page, isRefresh);
    }

}
