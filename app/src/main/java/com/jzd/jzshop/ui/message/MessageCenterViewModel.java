package com.jzd.jzshop.ui.message;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMessageCenterBinding;
import com.jzd.jzshop.entity.MessageCenterChatEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.home.news.HomePageViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;


/**
 * @author LXB
 * @description:
 * @date :2020/3/26 10:48
 */
public class MessageCenterViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = MessageCenterViewModel.class.getSimpleName();
    private Context context;
    private ActivityMessageCenterBinding binding;
    private boolean defaultSelectedTab = true; // 默认选中 互动消息
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<MessageCenterEntity.ResultBean.DataBean> totalList = new ArrayList();
    private List<MessageCenterChatEntity.ResultBean.DataBean> totalListChat = new ArrayList();

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<MessageCenterEntity.ResultBean.DataBean>> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<List<MessageCenterChatEntity.ResultBean.DataBean>> mChatLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent clearMessageLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent readrMessageLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent chatMessageLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent notifyMessageLiveData = new SingleLiveEvent<>();

    }

    public MessageCenterViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public String getToken(){
        return model.getUserToken();
    }

    public void setBinding(Context context, ActivityMessageCenterBinding binding, boolean defaultSelectedTab) {
        this.context = context;
        this.binding = binding;
        this.defaultSelectedTab = defaultSelectedTab;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
//        setToolbarBgColor(Color.parseColor("#D90804"));
//        setTitleTextColor(Color.parseColor("#FFFFFF"));
//        setIvBackIsVisible(View.GONE);
//        setIvBackWhiteIsVisible(View.VISIBLE);
    }


    /**
     * 获取 互动消息 list
     *
     * @param refreshLayout
     * @param page
     * @param isRefresh
     */
    public void requestChatData(final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        isShowDialog(false);
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postMessageChatData(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<MessageCenterChatEntity>() {
            @Override
            public void onResult(MessageCenterChatEntity messageCenterChatEntity) {
                super.onResult(messageCenterChatEntity);
                Log.d(TAG, "postMessageChatData onSuccess:---->>>");
                if (messageCenterChatEntity != null) {
                    if (messageCenterChatEntity.getResult() != null && messageCenterChatEntity.getResult().getData().size() > 0) {
//                        binding.tvUnreadMessage.setText(messageCenterChatEntity.getResult().get() +"条未读消息");
                        if (page == 1){
                            totalListChat.clear();
                            totalListChat.addAll(messageCenterChatEntity.getResult().getData());
                            uc.mChatLiveData.setValue(totalListChat);
                        }else{}
                    } else if (messageCenterChatEntity.getResult() == null ||
                            messageCenterChatEntity.getResult().getData().size() == 0) {//空页面处理
                        mBackResultNull(messageCenterChatEntity, page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(messageCenterChatEntity, page);
                        return;
                    }
                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (messageCenterChatEntity.getResult() != null && messageCenterChatEntity.getResult().getData().size() == 0) {
                            totalListChat.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalListChat.addAll(messageCenterChatEntity.getResult().getData());
                        mRefreshLayout.finishLoadmore();
                        uc.mChatLiveData.setValue(totalListChat);
                    }
                    int currentCountSize = messageCenterChatEntity.getResult().getData().size();
                    if (currentCountSize < 10 || currentCountSize == 0) {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                        }
                    }
                }

                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                loadError(isRefresh);
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });
    }


    /**
     * 获取 通知消息  list
     *
     * @param refreshLayout
     * @param page
     * @param isRefresh
     */
    public void requestData(final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        isShowDialog(false);
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postMessageData(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<MessageCenterEntity>() {
            @Override
            public void onResult(MessageCenterEntity messageCenterEntity) {
                super.onResult(messageCenterEntity);
                Log.d(TAG, "postMessageData onSuccess:---->>>");
                if (messageCenterEntity != null) {
                    if (messageCenterEntity.getResult() != null && messageCenterEntity.getResult().getData().size() > 0) {
                        binding.tvUnreadMessage.setText(messageCenterEntity.getResult().getTotal_noread() +"条未读消息");
                        if (page == 1){
                            totalList.clear();
                            totalList.addAll(messageCenterEntity.getResult().getData());
                            uc.mLiveData.setValue(totalList);
                        }else{}
                    } else if (messageCenterEntity.getResult() == null ||
                            messageCenterEntity.getResult().getData().size() == 0) {//空页面处理
                        mBackResultNull(messageCenterEntity, page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(messageCenterEntity, page);
                        return;
                    }
                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (messageCenterEntity.getResult() != null && messageCenterEntity.getResult().getData().size() == 0) {
                            totalList.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalList.addAll(messageCenterEntity.getResult().getData());
                        mRefreshLayout.finishLoadmore();
                        uc.mLiveData.setValue(totalList);
                    }
                    int currentCountSize = messageCenterEntity.getResult().getData().size();
                    if (currentCountSize < 10 || currentCountSize == 0) {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                        }
                    }
                }

                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                loadError(isRefresh);
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });
    }


    //互动消息
    public BindingCommand chatTabOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.chatMessageLiveData.setValue(null);
        }
    });
    //通知消息
    public BindingCommand notiyTabOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.notifyMessageLiveData.setValue(null);
        }
    });


    /**
     * 读取消息
     * @param order_id
     */
    public void postReadMessage(String order_id) {
        isShowDialog(false);
        addSubscribe(model.postReadMessage(model.getUserToken(),order_id), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                Log.d(TAG, "postReadMessage onSuccess:---->>>");
                Messenger.getDefault().send("message_num",  HomePageViewModel.TOKEN_VIEWMODEL_HOME_MESSAGENUMBER_REFRESH);  //发送刷新
                uc.readrMessageLiveData.setValue(o);
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });
    }

    //清空所有未读消息
    public BindingCommand clearMessageOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            List<MessageCenterEntity.ResultBean.DataBean> value = uc.mLiveData.getValue();
            if (value!=null && value.size() > 0){
                MessageDialog.showCancelAndSureDialog(context,"提示","确认要将未读消息标记为已读吗?",
                        R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                            @Override
                            public void onSureClickListener() {
                                clearAllMessage();
                            }
                            @Override
                            public void onCancelClickListener() {
                            }
                        });
            }else {}
        }
    });

    /**
     * 清空 所有新消息
     */
    private void clearAllMessage() {
        isShowDialog(false);
        addSubscribe(model.clearAllMessage(model.getUserToken()), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                Log.d(TAG, "clearAllMessage onSuccess:---->>>");
                dismissDialog();
                uc.clearMessageLiveData.setValue(o);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });
    }


    /**
     * 接口返回数据为null  或者 空[] 处理
     *
     * @param object
     * @param page
     */
    private void mBackResultNull(Object object, int page) {
        if (object instanceof MessageCenterChatEntity ){
            MessageCenterChatEntity messageCenterChatEntity = (MessageCenterChatEntity) object;
            if (page == 1 && messageCenterChatEntity.getResult().getData().size() == 0) {
                binding.recyViewChat.setVisibility(View.GONE);
//                binding.emptyView.setVisibility(View.VISIBLE);
//                ToastUtils.showShort("暂无互动消息哦！");
                return;
            } else {
                binding.recyViewChat.setVisibility(View.VISIBLE);
//                binding.emptyView.setVisibility(View.GONE);
            }
        }else if (object instanceof MessageCenterEntity){
            MessageCenterEntity messageCenterEntity = (MessageCenterEntity) object;
            if (page == 1 && messageCenterEntity.getResult().getData().size() == 0) {
                binding.recyView.setVisibility(View.GONE);
//                binding.emptyView.setVisibility(View.VISIBLE);
                ToastUtils.showShort("暂无通知消息哦！");
                return;
            } else {
                binding.recyView.setVisibility(View.VISIBLE);
//                binding.emptyView.setVisibility(View.GONE);
            }
        }
        mRefreshLayout.finishLoadmore(true);
        mRefreshLayout.finishLoadmoreWithNoMoreData();
        dismissDialog();
        return;
    }


    /**
     * 网络请求，加载失败
     */
    public void loadError(boolean isRefresh) {
        if (mRefreshLayout != null) {
            if (isRefresh) {
                mRefreshLayout.finishRefresh(false);  //结束刷新（刷新失败）
                mRefreshLayout.setLoadmoreFinished(false);
            } else {
                mRefreshLayout.finishLoadmore(false); //结束加载（加载失败）
            }
        }
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
