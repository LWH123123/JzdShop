package com.jzd.jzshop.ui.mine.collect;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMineCollectBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineCollectFragment extends BaseFragment<FragmentMineCollectBinding, MineCollectViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine_collect;
    }

    @Override
    public int initVariableId() {
        return BR.minecollectVM;
    }

    @Override
    public MineCollectViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineCollectViewModel.class);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        Bundle arguments = getArguments();
        viewModel.setBing(binding);


    }
}
