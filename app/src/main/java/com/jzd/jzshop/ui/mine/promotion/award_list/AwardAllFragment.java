package com.jzd.jzshop.ui.mine.promotion.award_list;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentAwardListPageBinding;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardAllFragment extends BaseFragment<FragmentAwardListPageBinding, AwardAllFragmViewModel> {
    private List<AwardListEntity.ResultBean.AssetsRecordBean> dataList;
    private AwardListPageFragAdapter mAdapter;
    private AwardListEntity.ResultBean result;

    public AwardAllFragment() {

    }

    /**
     * 外部传递参数
     *
     * @param dataList
     * @return
     */
    public Fragment newInstance(List<AwardListEntity.ResultBean.AssetsRecordBean> dataList) {
        AwardAllFragment fragment = new AwardAllFragment();
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
            dataList = (List<AwardListEntity.ResultBean.AssetsRecordBean>) arguments.getSerializable("data");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_award_list_page;
    }

    @Override
    public int initVariableId() {
        return BR.awardAllFragmPageVM;
    }

    @Override
    public AwardAllFragmViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AwardAllFragmViewModel.class);
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
        viewModel.uc.mLiveData.observe(this, new Observer<AwardListEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable AwardListEntity.ResultBean resultBean) {
                result = resultBean;
                dataList = result.getData(); //list data
                // TODO: 2020/2/12  账号没数据  ，先构建本地数据测试
                dataList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    dataList.add(new AwardListEntity.ResultBean.AssetsRecordBean(
                            "98", 2, 3, "2020-01-19 13:56",
                            90.00, 90.00, 90.00));
                }

                mAdapter = new AwardListPageFragAdapter(getActivity(), viewModel,
                        dataList, R.layout.item_rv_award_list_fragm_page);
                binding.rv.setAdapter(mAdapter);
                binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_rv_award_list_header_view, null);
                initHeaderData(headerView);
                mAdapter.addHeaderView(headerView);
//                mAdapter.setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View itemView, int viewType, int position) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(Constants.BUNDLE_KEY_LOG_ID, dataList.get(position).getLog_id());
//                        startContainerActivity(AwardDetailFragment.class.getCanonicalName(),bundle);
//                    }
//                });
            }
        });
    }

    private void initHeaderData(View headerView) {
        TextView tv_amount = headerView.findViewById(R.id.tv_amount);
        double commission_total = result.getCommission_total(); //累计奖励
        String money = MoneyFormatUtils.keepTwoDecimal(commission_total);
        tv_amount.setText(money.concat("元"));
    }


}
