package com.jzd.jzshop.ui.home.creditsstore.mine;

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
import com.jzd.jzshop.databinding.FragmentCreditExchangeRecodBinding;
import com.jzd.jzshop.entity.CreditExchangeRecoEntity;
import com.jzd.jzshop.entity.CreditToSureReceiptEntity;
import com.jzd.jzshop.ui.home.creditsstore.order_detail.CreditOrderDetailActivity;
import com.jzd.jzshop.ui.home.creditsstore.order_detail.CreditOrderDetailViewModel;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description: 兑换记录
 * @date :2020/5/9 17:03
 */
public class CreditExchangeRecoFragment extends BaseFragment<FragmentCreditExchangeRecodBinding, CreditExchangeRecoViewModel>
        implements OnRefreshListener, OnLoadmoreListener {
    private int status = 1;
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private CreditExchangeRecoFragAdapter mAdapter;

    public CreditExchangeRecoFragment() {
    }

    /**
     * 外部传递参数
     *
     * @return
     */
    public Fragment newInstance() {
        CreditExchangeRecoFragment fragment = new CreditExchangeRecoFragment();
        return this;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_credit_exchange_recod;
    }

    @Override
    public int initVariableId() {
        return BR.creditExchangeRecoVM;
    }

    @Override
    public CreditExchangeRecoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CreditExchangeRecoViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(getActivity(), binding);
        viewModel.requestData(status, "", binding.refreshLayout, page, isRefresh);
        initMallRefresh(); //配置刷新
        //刷新退换记录 列表按钮状态
        Messenger.getDefault().register(this, CreditOrderDetailViewModel.TOKEN_VIEWMODEL_EVALUATION_REFRESH, String.class,
                new BindingConsumer<String>() {
                    @Override
                    public void call(String state) {//刷新
                        if (state.equals("evaluation")) {
                            viewModel.requestData(status, "", binding.refreshLayout, page, isRefresh);
                        } else {
                        }
                    }
                });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<List<CreditExchangeRecoEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<CreditExchangeRecoEntity.ResultBean.DataBean> result) {
                if (page == 1 && result.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }
                initRecycleData(result);
            }
        });

        // 确认收货成功后,刷新数据
        viewModel.uc.mToSureReceiptLiveData.observe(this, new Observer<CreditToSureReceiptEntity>() {
            @Override
            public void onChanged(@Nullable CreditToSureReceiptEntity creditToSureReceiptEntity) {
                viewModel.requestData(status, "", binding.refreshLayout, page, isRefresh);
                ToastUtils.showShort("确认收货成功！");
            }
        });
    }

    private void initRecycleData(List<CreditExchangeRecoEntity.ResultBean.DataBean> result) {
        if (mAdapter ==null){
            mAdapter = new CreditExchangeRecoFragAdapter(getActivity(),
                    viewModel, result, R.layout.item_rv_credit_exchange_reco);
            binding.rv.setAdapter(mAdapter);
            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                String log_id = result.get(position).getLog_id();
                int type1 = result.get(position).getType1();
                bundle.putString(Constants.GOODS_KEY_GID, log_id);
                startActivity(CreditOrderDetailActivity.class, bundle);
            }
        });
    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.refreshLayout.setHeaderHeight(68);          //设置header高度
        binding.refreshLayout.setFooterHeight(68);          //设置footer高度

        binding.refreshLayout.setEnableRefresh(true);
        binding.refreshLayout.setEnableAutoLoadmore(true);  //开启自动加载功能
        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        isRefresh = true;
        viewModel.requestData(status, "", binding.refreshLayout, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestData(status, "", binding.refreshLayout, page, isRefresh);
    }
}
