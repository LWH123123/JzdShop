package com.jzd.jzshop.ui.mine.promotion.awarddetail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentAwarddetailBinding;
import com.jzd.jzshop.entity.AwarddetailEntity;
import com.jzd.jzshop.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;


/**
 * LWH  奖励明细  -查看提现详情
 * A simple {@link Fragment} subclass.
 */
public class AwardDetailFragment extends BaseFragment<FragmentAwarddetailBinding, AwardDerailViewModel> {
    private List<AwarddetailEntity.ResultBean.AwardDetailBean> dataList;
    private List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> chidList;
    private AwardDetailFragmAdapter mAdapter;
    private String logId;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_awarddetail;
    }

    @Override
    public int initVariableId() {
        return BR.awardDerailVM;
    }


    @Override
    public AwardDerailViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AwardDerailViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));
        viewModel.initToolBar(getResources().getString(R.string.award_detail));
        getIntentData();
        viewModel.setBinding(getActivity(), binding);
        viewModel.requestData(logId,0);
    }

    private void getIntentData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            logId = bundle.getString(Constants.BUNDLE_KEY_LOG_ID);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<AwarddetailEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable AwarddetailEntity.ResultBean resultBean) {
//                dataList = resultBean.getData(); //list data
                // TODO: 2020/2/12 构建模拟数据
            }
        });

        dataList = new ArrayList<>();
        chidList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            chidList.add(new AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean(
                    "休闲百搭阔腿裤",
                    "http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png",
                    90.00,
                    1,-2,"审核内容"));
        }
        for (int i = 0; i < 5; i++) {
            dataList.add(new AwarddetailEntity.ResultBean.AwardDetailBean(
                    90.00, "ME202001191356161643161",109.00,chidList));
        }
        mAdapter = new AwardDetailFragmAdapter(getActivity(),
                dataList, R.layout.item_rv_award_detail);
        binding.rv.setAdapter(mAdapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
