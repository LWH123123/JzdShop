package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentPromotOrderAllBinding;
import com.jzd.jzshop.entity.PromotionOrderEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class PromotOrderWaittingFragment extends BaseFragment<FragmentPromotOrderAllBinding, PromotOrderAllFragmViewModel> {
    private List<PromotionOrderEntity.ResultBean.PromoOrderBean> dataList;
    private PromoOrderPageFragAdapter mAdapter;
    private PromotionOrderEntity.ResultBean result;

    public PromotOrderWaittingFragment() {

    }

    /**
     * 外部传递参数
     *
     * @param dataList
     * @return
     */
    public Fragment newInstance(List<PromotionOrderEntity.ResultBean.PromoOrderBean> dataList) {
        PromotOrderWaittingFragment fragment = new PromotOrderWaittingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) dataList);
        fragment.setArguments(bundle);
        return this;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();
        if (arguments != null) {
            dataList = (List<PromotionOrderEntity.ResultBean.PromoOrderBean>) arguments.getSerializable("data");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_promot_order_all;
    }

    @Override

    public int initVariableId() {
        return BR.promoOrderAllFragmPageVM;
    }

    @Override
    public PromotOrderAllFragmViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(PromotOrderAllFragmViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(getActivity(), binding);
        viewModel.requestData(0);

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<PromotionOrderEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable PromotionOrderEntity.ResultBean resultBean) {
                result = resultBean;
                dataList = result.getData(); //list data
                // TODO: 2020/2/12  账号没数据  ，先构建本地数据测试
                dataList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    dataList.add(new PromotionOrderEntity.ResultBean.PromoOrderBean(
                            90.00, 2,"ME202001191356161643161","2020-01-19 13:56" ));
                }

                mAdapter = new PromoOrderPageFragAdapter(getContext(),
                        dataList, R.layout.item_rv_promo_order_fragm_page);
                binding.rv.setAdapter(mAdapter);
                binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
    }

}
