package com.jzd.jzshop.ui.mine.address;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentReceiptAddressBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 收货地址
 */
public class ReceiptAddressFragment extends BaseFragment<FragmentReceiptAddressBinding, ReceiptAddressViewModel> implements OnRefreshListener, OnLoadmoreListener {

    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_receipt_address;
    }

    @Override
    public int initVariableId() {
        return BR.receiptAddressVM;
    }

    @Override
    public ReceiptAddressViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ReceiptAddressViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        getIntentData();
        viewModel.setBind(binding,getContext());
        viewModel.requestWork(binding.mallRefreshLayout, page, isRefresh);
        initMallRefresh();
        Messenger.getDefault().register(this, CompileAddressFragment.ADDRESS_VIEWMODEL_REFRESH, new BindingAction() {
            @Override
            public void call() {//刷新我的数据
                ToastUtils.showLong("添加成功！");
                binding.mallRefreshLayout.autoRefresh();
            }
        });
    }

    private void getIntentData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int fired = arguments.getInt("FIRED");
            viewModel.sign.set(fired);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        isRefresh = true;
        viewModel.receiptlist.clear();
        viewModel.requestWork(binding.mallRefreshLayout, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if(viewModel.receiptlist.size()>=viewModel.pagesize){
        page++;
        }
        isRefresh = false;
        viewModel.requestWork(binding.mallRefreshLayout, page, isRefresh);
    }
}
