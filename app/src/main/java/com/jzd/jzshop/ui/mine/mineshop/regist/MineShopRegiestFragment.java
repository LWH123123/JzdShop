package com.jzd.jzshop.ui.mine.mineshop.regist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMineShopRegiestBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 *
 * 我的小店申请成功页面
 * A simple {@link Fragment} subclass.
 */
public class MineShopRegiestFragment extends BaseFragment<FragmentMineShopRegiestBinding,MineShopRegistViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine_shop_regiest;
    }

    @Override
    public int initVariableId() {
        return BR.mineshopregistVM;
    }

    @Override
    public MineShopRegistViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineShopRegistViewModel.class);
    }




}
