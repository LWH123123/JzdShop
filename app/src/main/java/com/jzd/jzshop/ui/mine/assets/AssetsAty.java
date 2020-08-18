package com.jzd.jzshop.ui.mine.assets;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityAssetsBinding;
import com.jzd.jzshop.entity.AssetsEntity;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP： 君子权资产
 */
public class AssetsAty extends BaseActivity<ActivityAssetsBinding, AssetsViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_assets;
    }

    @Override
    public int initVariableId() {
        return BR.assetsVM;
    }

    @Override
    public AssetsViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AssetsViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar("");
        viewModel.requestData();
    }

    private void getIntentData() {
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        viewModel.uc.mLiveData.observe(this, new Observer<AssetsEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable AssetsEntity.ResultBean resultBean) {
                binding.tvNum.setText(resultBean.getJz_cnt());
                binding.tvRmb.setText(resultBean.getJz_money());
            }
        });


    }
}
