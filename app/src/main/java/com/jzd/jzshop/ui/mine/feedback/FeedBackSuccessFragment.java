package com.jzd.jzshop.ui.mine.feedback;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentFeedBackSuccessBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackSuccessFragment extends BaseFragment<FragmentFeedBackSuccessBinding,FeedBackSuccessViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_feed_back_success;
    }

    @Override
    public int initVariableId() {
        return BR.feedbacksuccessVM;
    }


    @Override
    public FeedBackSuccessViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(FeedBackSuccessViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.settool();
    }
}
