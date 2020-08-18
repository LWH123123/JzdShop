package com.jzd.jzshop.ui.mine.setting.aboutapp;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentAboutappBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutappFragment extends BaseFragment<FragmentAboutappBinding,AboutappViewModel> {


    public AboutappFragment() {
        // Required empty public constructor
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_aboutapp;
    }

    @Override
    public int initVariableId() {
        return BR.aboutappVM;
    }


    @Override
    public AboutappViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AboutappViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));

    }
}
