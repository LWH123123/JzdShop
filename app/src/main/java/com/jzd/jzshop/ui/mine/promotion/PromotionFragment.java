package com.jzd.jzshop.ui.mine.promotion;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;


import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentPromotionBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 *  推广中心
 */
public class PromotionFragment extends BaseFragment<FragmentPromotionBinding, PromotionViewModel> {

    private WebView web;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_promotion;
    }

    @Override
    public int initVariableId() {
        return me.goldze.mvvmhabit.BR.promotionVM;
    }

    @Override
    public PromotionViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(PromotionViewModel.class);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();
        viewModel.initToolbar();
        viewModel.setBinding(binding);
        viewModel.requestwork();


    }




}
