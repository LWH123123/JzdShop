package com.jzd.jzshop.ui.home.creditsstore;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityPartakeRecordBinding;
import com.jzd.jzshop.entity.PartakeRecordEntity;
import com.jzd.jzshop.ui.home.creditsstore.mine.CreditsMineActivity;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;


/**
 * @author LXB
 * @description: 积分参与记录
 * @date :2020/5/11 9:26
 */
public class PartakeRecordActivity extends BaseActivity<ActivityPartakeRecordBinding, PartakeRecordViewModel> implements
        OnRefreshListener, OnLoadmoreListener {
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private String merch_id = "";
    private String credits_num;
    private TextView tv_credit_num;
    private PartakeRecordAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_partake_record;
    }

    @Override
    public int initVariableId() {
        return BR.partakeRecordVM;
    }

    @Override
    public PartakeRecordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(PartakeRecordViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.initToolBar(getResources().getString(R.string.partake_record));
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(binding.refreshLayout, merch_id, page, isRefresh);
        initMallRefresh(); //配置刷新
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            credits_num = bundle.getString(Constants.BUNDLE_KEY_CREDITS_NUM);
            Log.d(TAG, "credits_num:" + credits_num);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<List<PartakeRecordEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<PartakeRecordEntity.ResultBean.DataBean> result) {
                if (page == 1 && result.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }
                initRecycleViewData(result);
            }
        });

    }

    private void initRecycleViewData(List<PartakeRecordEntity.ResultBean.DataBean> result) {
        if (adapter==null){
            adapter = new PartakeRecordAdapter(mContext, result, R.layout.item_rv_partake_record);
            binding.rvc.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.rvc.setLayoutManager(linearLayoutManager);
            View headView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_partake_record_head_view, null);
            tv_credit_num = headView.findViewById(R.id.tv_credit_num);
            if (TextUtils.isEmpty(credits_num)) {
                String creditsNum = viewModel.getCreditsNum();
                tv_credit_num.setText(creditsNum);
            } else {
                tv_credit_num.setText(credits_num);
            }
            adapter.addHeaderView(headView);
        }else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if (position >=result.size())return;
                //类型【0:兑换；1：抽奖】
                int type = result.get(position - 1).getType();
                Bundle bundle = new Bundle();
                if (type == 0) {
                    bundle.putInt(Constants.GOODS_OPEN_FLAG,0);
                } else if (type == 1) {
                    bundle.putInt(Constants.GOODS_OPEN_FLAG,1);
                } else {
                }
                startActivity(CreditsMineActivity.class,bundle);

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
        viewModel.requestData(binding.refreshLayout, merch_id, page, isRefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestData(binding.refreshLayout, merch_id, page, isRefresh);
    }
}
