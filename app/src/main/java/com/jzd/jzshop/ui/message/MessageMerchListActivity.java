package com.jzd.jzshop.ui.message;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMessageMerchListBinding;
import com.jzd.jzshop.entity.MessageMerchEntity;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author LXB
 * @description: 商户消息详情
 * @date :2020/4/2 10:54
 */
public class MessageMerchListActivity extends BaseActivity<ActivityMessageMerchListBinding, MessageMerchViewModel> implements
        OnRefreshListener {
    private MessageMerchListAdapter adapter;
    private String order_id, refund_id, mesageType;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_message_merch_list;
    }

    @Override
    public int initVariableId() {
        return BR.vmMessageMerch;
    }

    @Override
    public MessageMerchViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MessageMerchViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initToolBar(getResources().getString(R.string.message_merch));
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(binding.refreshLayout, order_id, mesageType, refund_id);
        initMallRefresh(); //配置刷新
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getString(Constants.ORDER_ID);
            refund_id = bundle.getString(Constants.REFUND_ID);
            mesageType = bundle.getString(Constants.BUNDLE_KEY_JPUSH_MESSAGE_TYPE);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<MessageMerchEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable MessageMerchEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    initMessageMerchData(resultBean);
                }
            }
        });

    }

    private void initMessageMerchData(MessageMerchEntity.ResultBean resultBean) {
        if (!TextUtils.isEmpty(resultBean.getTime())) {
            binding.tvTime.setText(resultBean.getTime());
        }
        List<MessageMerchEntity.ResultBean.GoodsBean> goods = resultBean.getGoods();
        if (goods != null && goods.size() > 0) {
            binding.recyView.setLayoutManager(new LinearLayoutManager(mContext));
            adapter = new MessageMerchListAdapter(mContext, goods, R.layout.item_recy_message_merch);
            binding.recyView.setAdapter(adapter);
            View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_recy_message_merch_header_view, null);
            adapter.addHeaderView(headerView);
            View footerView = LayoutInflater.from(mContext).inflate(R.layout.item_recy_message_merch_footer_view, null);
            adapter.addFooterView(footerView);
            initHeaderFooterData(headerView, footerView, resultBean);
        } else {
        }
    }

    private void initHeaderFooterData(View headerView, View footerView, MessageMerchEntity.ResultBean resultBean) {
        TextView tv_ordersn = headerView.findViewById(R.id.tv_ordersn);
        tv_ordersn.setText("订单单号：".concat(resultBean.getOrdersn()));
        TextView tv_name = footerView.findViewById(R.id.tv_name);
        TextView tv_mobile = footerView.findViewById(R.id.tv_mobile);
        TextView tv_address = footerView.findViewById(R.id.tv_address);
        TextView tv_totalPrice = footerView.findViewById(R.id.tv_totalPrice);
        tv_totalPrice.setText("合计：¥".concat(resultBean.getOrder_price()));
        MessageMerchEntity.ResultBean.AddressBean address = resultBean.getAddress();
        if (address != null) {
            tv_name.setText("收货人姓名：".concat(address.getReceive_name()));
            tv_mobile.setText("收货人电话：".concat(address.getReceive_mobile()));
            tv_address.setText("收货人地址：".concat(address.getReceive_address()));
        }
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
        binding.refreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestData(binding.refreshLayout, order_id, mesageType, refund_id);
    }

}
