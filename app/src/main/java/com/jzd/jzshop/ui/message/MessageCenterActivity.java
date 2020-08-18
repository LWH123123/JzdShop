package com.jzd.jzshop.ui.message;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.chat.OpenChatActivity;
import com.jzd.jzshop.databinding.ActivityMessageCenterBinding;
import com.jzd.jzshop.entity.MessageCenterChatEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailAty;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.notification.NotificationsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 消息中心
 * @date :2020/3/26 10:47
 */
public class MessageCenterActivity extends BaseActivity<ActivityMessageCenterBinding, MessageCenterViewModel>
        implements
        OnRefreshListener, OnLoadmoreListener {
    private int page = 1;   //分页页码
    private boolean isRefresh = true; //是否是刷新
    private MessageCenterAdapter adapter;
    private MessageCenterChatAdapter chatAdapter;
    private boolean isRead = false; //本地是否阅读消息
    private boolean defaultSelectedTab = true; // 默认选中 互动消息

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_message_center;
    }

    @Override
    public int initVariableId() {
        return BR.messageVM;
    }

    @Override
    public MessageCenterViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MessageCenterViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isEnabled = NotificationsUtils.isNotificationEnabled(mContext);
        if (isEnabled) {
            binding.consMessageTip.setVisibility(View.GONE);
        } else {
            binding.consMessageTip.setVisibility(View.VISIBLE);
        }
        viewModel.requestChatData(binding.refreshLayout, 1, isRefresh);
//        binding.refreshLayout.autoRefresh();
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
//        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#D90804"));
        viewModel.initToolBar(getResources().getString(R.string.message_center));
        viewModel.setBinding(mContext, binding,defaultSelectedTab);
        if (defaultSelectedTab){
            binding.recyViewChat.setVisibility(View.VISIBLE);
            binding.recyView.setVisibility(View.GONE);
            binding.addressTip.setVisibility(View.GONE);
            binding.tvUnreadMessage.setVisibility(View.GONE);
            binding.tvClearMessage.setVisibility(View.GONE);
            viewModel.requestChatData(binding.refreshLayout, page, isRefresh);
        }else {
            binding.tvUnreadMessage.setVisibility(View.VISIBLE);
            binding.recyView.setVisibility(View.VISIBLE);
            binding.addressTip.setVisibility(View.VISIBLE);
            binding.tvClearMessage.setVisibility(View.VISIBLE);
            binding.recyViewChat.setVisibility(View.GONE);
            viewModel.requestData(binding.refreshLayout, page, isRefresh);

        }
        initMallRefresh(); //配置刷新
        //是否允许打开消息通知
        boolean isEnabled = NotificationsUtils.isNotificationEnabled(mContext);
        if (isEnabled){
            binding.consMessageTip.setVisibility(View.GONE);
        }else {
            binding.consMessageTip.setVisibility(View.VISIBLE);
        }
        binding.consMessageTip.findViewById(R.id.iv_message_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.consMessageTip.setVisibility(View.GONE);
            }
        });
        binding.consMessageTip.findViewById(R.id.tv_handleWay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationsUtils.requestNotify(mContext);
            }
        });

    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.chatMessageLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                defaultSelectedTab = true;
                binding.recyViewChat.setVisibility(View.VISIBLE);
                binding.recyView.setVisibility(View.GONE);
                binding.tvUnreadMessage.setVisibility(View.GONE);
                binding.addressTip.setVisibility(View.GONE);
                binding.tvClearMessage.setVisibility(View.GONE);
                viewModel.requestChatData(binding.refreshLayout, page, isRefresh);
            }
        });
        viewModel.uc.notifyMessageLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                defaultSelectedTab = false;
                binding.recyViewChat.setVisibility(View.GONE);
                binding.recyView.setVisibility(View.VISIBLE);
                binding.tvUnreadMessage.setVisibility(View.VISIBLE);
                binding.addressTip.setVisibility(View.VISIBLE);
                binding.tvClearMessage.setVisibility(View.VISIBLE);
                viewModel.requestData(binding.refreshLayout, page, isRefresh);
            }
        });

        //通知消息
        viewModel.uc.mLiveData.observe(this, new Observer<List<MessageCenterEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<MessageCenterEntity.ResultBean.DataBean> dataBeans) {
                if (page == 1 && dataBeans.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }
                initMessageData(dataBeans);
            }
        });
        //互动消息
        viewModel.uc.mChatLiveData.observe(this, new Observer<List<MessageCenterChatEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<MessageCenterChatEntity.ResultBean.DataBean> dataBeans) {
                if (page == 1 && dataBeans.size() == 0) {
                    binding.refreshLayout.setEnableRefresh(false);
                    binding.refreshLayout.setEnableAutoLoadmore(false);
                    binding.refreshLayout.setEnableLoadmore(false);
                } else {
                    binding.refreshLayout.setEnableRefresh(true);
                    binding.refreshLayout.setEnableAutoLoadmore(true);
                }
                initMessageChatData(dataBeans);
            }
        });

        //读取消息
        viewModel.uc.readrMessageLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG, "消息已读");
                binding.refreshLayout.autoRefresh();
                adapter.notifyDataSetChanged();
            }
        });
        //清空所有未读消息
        viewModel.uc.clearMessageLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.refreshLayout.autoRefresh();
                adapter.notifyDataSetChanged();
                ToastUtils.showLong(R.string.clear_message_all);
            }
        });
    }

    private void initMessageChatData(List<MessageCenterChatEntity.ResultBean.DataBean> dataBeans) {
        if (chatAdapter==null){
            binding.recyViewChat.setLayoutManager(new LinearLayoutManager(mContext));
            chatAdapter = new MessageCenterChatAdapter(mContext, dataBeans, R.layout.item_recy_message_center);
            binding.recyViewChat.setAdapter(chatAdapter);
        }else {
            chatAdapter.notifyDataSetChanged();
        }
        binding.recyView.setFocusable(false);
        chatAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                //打开先先关闭，防止已经打开，打开多个会话页面，聊天数据会话错乱
                AppManager.getAppManager().finishActivity(OpenChatActivity.class);
                if (position >= dataBeans.size())return;
                String name = dataBeans.get(position).getName();
                String chat_id = dataBeans.get(position).getReceive_id();
                String ltype = dataBeans.get(position).getLtype();
                Intent intent =new Intent(mContext,OpenChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, "");
                bundle.putString(Constants.GOODS_KEY_CHAT_NAME, name);
                bundle.putString(Constants.GOODS_KEY_CHAT_ID, chat_id);
                bundle.putString(Constants.GOODS_KEY_CHAT_LTYPE, ltype);
                bundle.putString(Constants.SP.ACTIVITY_OPEN_FLAG, "message_center");
                intent.putExtras(bundle);
                startActivityForResult(intent,Constants.QUEST_CODE.REQUESTCODE_MESSAGE_CENTER);
            }
        });

    }

    private void initMessageData(final List<MessageCenterEntity.ResultBean.DataBean> dataBeans) {
        if (adapter==null){
            binding.recyView.setLayoutManager(new LinearLayoutManager(mContext));
            adapter = new MessageCenterAdapter(mContext, dataBeans, R.layout.item_recy_message_center);
            binding.recyView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
        binding.recyView.setFocusable(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if (dataBeans.get(position) != null) {
                    if (position >= dataBeans.size())return;
                    String id = dataBeans.get(position).getId();//消息id
                    viewModel.postReadMessage(id);
                    String type = dataBeans.get(position).getType();
                    if (!TextUtils.isEmpty(type)) {
                        if (type.equals("app:order.close") ||
                                type.equals("app:order.payed") ||
                                type.equals("app:order.send") ||
                                type.equals("app:order.end") ||
                                type.equals("app:order.refun") ||
                                type.equals("app:order.refund.nosend") ||
                                type.equals("app:order.refund.send") ||
                                type.equals("app:order.refund.ends") ||
                                type.equals("app:order.refund.refuse")) {    //会员消息
                            if (dataBeans != null && dataBeans.get(position).getOData() != null &&
                                    !TextUtils.isEmpty(dataBeans.get(position).getOData().getOrder_id())) {
                                String order_id = dataBeans.get(position).getOData().getOrder_id();
                                Log.d(TAG, "initMessageData　order_id－－－－＞＞:" + order_id);
                                Intent orderDetail = new Intent(mContext, OrderDetailAty.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.ORDER_ID, order_id);
                                orderDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                orderDetail.putExtras(bundle);
                                mContext.startActivity(orderDetail);
                            } else {
                                Log.e(TAG, "推送 会员消息异常");
                            }
                        } else {    //商户消息
                            Log.d(TAG, "initMessageData　onItemClick－－－－＞＞: 商户消息");
                            if (dataBeans != null && dataBeans.get(position).getOData() != null &&
                                    !TextUtils.isEmpty(dataBeans.get(position).getOData().getOrder_id())) {
                                String order_id = dataBeans.get(position).getOData().getOrder_id();
                                String refund_id = dataBeans.get(position).getOData().getRefund_id();
                                String jpush_type = dataBeans.get(position).getType();
                                Log.d(TAG, "order_id－－－－＞＞:" + order_id
                                        + "\n refund_id－－－－＞＞:" + refund_id
                                        + "\n jpush_type－－－－＞＞:" + jpush_type);
                                Intent intent = new Intent(mContext, MessageMerchListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.ORDER_ID, order_id);
                                if (refund_id != null) {
                                    bundle.putString(Constants.REFUND_ID, refund_id);
                                }
                                bundle.putString(Constants.BUNDLE_KEY_JPUSH_MESSAGE_TYPE, jpush_type);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtras(bundle);
                                mContext.startActivity(intent);
                            }else {
                                Log.e(TAG, "推送 商户消息异常");
                            }
                        }
                    }
                }

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
        if (defaultSelectedTab){
            viewModel.requestChatData(binding.refreshLayout, page, isRefresh);
        }else {
            viewModel.requestData(binding.refreshLayout, page, isRefresh);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        if (defaultSelectedTab){
            viewModel.requestChatData(binding.refreshLayout, page, isRefresh);
        }else {
            viewModel.requestData(binding.refreshLayout, page, isRefresh);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK){
            switch (requestCode){
                case Constants.QUEST_CODE.REQUESTCODE_MESSAGE_CENTER:
                    viewModel.requestChatData(binding.refreshLayout, page, isRefresh);
                    break;
            }
        }
    }
}
