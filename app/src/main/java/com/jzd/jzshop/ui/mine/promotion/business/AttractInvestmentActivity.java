package com.jzd.jzshop.ui.mine.promotion.business;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityAttractInvestmentBinding;
import com.jzd.jzshop.entity.AttractInvestmentEntity;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 11:55
 */
public class AttractInvestmentActivity extends BaseActivity<ActivityAttractInvestmentBinding, AttractInvestmentViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_attract_investment;
    }

    @Override
    public int initVariableId() {
        return BR.attractInvestmentVM;
    }

    @Override
    public AttractInvestmentViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AttractInvestmentViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, false);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#D90804"));
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.attract_investment));
        viewModel.requestData();

    }

    private void getIntentData() {
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<List<AttractInvestmentEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable final List<AttractInvestmentEntity.ResultBean.DataBean> result) {
                KLog.a("当前的数据个数有"+result.size());
                if (result != null && result.size() > 0) {
                    binding.rvc.setVisibility(View.VISIBLE);
                    binding.emptyView.setVisibility(View.GONE);
                    AttractInvestmentAdapter attractInvestmentAdapter = new AttractInvestmentAdapter(mContext, result,
                            R.layout.item_rv_attract_investment);
                    binding.rvc.setAdapter(attractInvestmentAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    binding.rvc.setLayoutManager(linearLayoutManager);
                } else {
//                    binding.rvc.setVisibility(View.GONE);
//                    binding.emptyView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
