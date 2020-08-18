package com.jzd.jzshop.ui.home.local_life;

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

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentOfflineFoodBinding;
import com.jzd.jzshop.entity.LocalLifeEntity;
import com.jzd.jzshop.ui.mine.assets.AssetsRecoAllFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：线下美食
 */
public class OfflineFoodFragment extends BaseFragment<FragmentOfflineFoodBinding, OfflineFoodFragViewModel> {
    private List<LocalLifeEntity.ResultBean.AssetsRecordBean> dataList;
    private LocalLifeFragAdapter mAdapter;

    public OfflineFoodFragment() {

    }

    /**
     * 外部传递参数
     *
     * @param dataList
     * @return
     */
    public Fragment newInstance(List<LocalLifeEntity.ResultBean.AssetsRecordBean> dataList) {
        AssetsRecoAllFragment fragment = new AssetsRecoAllFragment();
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
            dataList = (List<LocalLifeEntity.ResultBean.AssetsRecordBean>) arguments.getSerializable("data");
        }
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_offline_food;
    }

    @Override
    public int initVariableId() {
        return BR.offlineFoodVM;
    }

    @Override
    public OfflineFoodFragViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(OfflineFoodFragViewModel.class);
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
        viewModel.uc.mLiveData.observe(this, new Observer<LocalLifeEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable LocalLifeEntity.ResultBean resultBean) {
                dataList = resultBean.getData(); //list data
                // TODO: 2020/2/12  账号没数据  ，先构建本地数据测试
                dataList = new ArrayList<>();
//                for (int i = 0; i < 5; i++) {
//                    dataList.add(new LocalLifeEntity.ResultBean.AssetsRecordBean(
//                            "98", 2, 3, "2020-01-19 13:56",
//                            90.00, 90.00, 90.00));
//                }

                if (dataList != null && dataList.size() > 0) {
                    binding.rv.setVisibility(View.VISIBLE);
                    binding.emptyView.setVisibility(View.GONE);
                    mAdapter = new LocalLifeFragAdapter(getActivity(),
                            dataList, R.layout.item_rv_local_life_fragm);
                    binding.rv.setAdapter(mAdapter);
                    binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                }else {
                    binding.rv.setVisibility(View.GONE);
                    binding.emptyView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}
