package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityPromotionOrderNewBinding;
import com.jzd.jzshop.entity.PromotionListEntity;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 佣金-推广
 * @date :2020/4/13 13:42
 */
public class PromotionOrderNewAty extends BaseActivity<ActivityPromotionOrderNewBinding, PromotionOrderNewViewModel> {
    private PromoListAdapter mAdapter;
    private List<PromotionListEntity.ResultBean> result;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_promotion_order_new;
    }

    @Override
    public int initVariableId() {
        return BR.promoOrderNewVM;
    }

    @Override
    public PromotionOrderNewViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(PromotionOrderNewViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.promotion_order_new));
        viewModel.requestData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<List<PromotionListEntity.ResultBean>>() {
            @Override
            public void onChanged(@Nullable List<PromotionListEntity.ResultBean> resultBean) {
                result = resultBean;
                if (result != null && result.size() > 0) {
                    binding.rv.setVisibility(View.VISIBLE);
                    binding.emptyView.setVisibility(View.GONE);
                    mAdapter = new PromoListAdapter(mContext,
                            result, R.layout.item_rv_promo_order_fragm_page);
                    binding.rv.setAdapter(mAdapter);
                    binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
                } else {
                    binding.emptyView.setVisibility(View.VISIBLE);
                    binding.rv.setVisibility(View.GONE);
                }
            }
        });
    }
}
