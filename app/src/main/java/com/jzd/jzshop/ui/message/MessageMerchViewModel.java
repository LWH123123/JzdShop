package com.jzd.jzshop.ui.message;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMessageMerchListBinding;
import com.jzd.jzshop.entity.MessageMerchEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/2 10:56
 */
public class MessageMerchViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = MessageMerchViewModel.class.getSimpleName();
    private Context context;
    private ActivityMessageMerchListBinding binding;
    //分页加载字段-------------
    private SmartRefreshLayout mRefreshLayout;

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<MessageMerchEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public void setBinding(Context context, ActivityMessageMerchListBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public MessageMerchViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
    }

    public void requestData(SmartRefreshLayout refreshLayout, String orderid, String type, String refund_id) {
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postMessageMerchData(model.getUserToken(), orderid, type, refund_id), new ParseDisposableTokenErrorObserver<MessageMerchEntity>() {
            @Override
            public void onResult(MessageMerchEntity messageMerchEntity) {
                super.onResult(messageMerchEntity);
                Log.d(TAG, "postMessageData onSuccess:---->>>");
                if (messageMerchEntity != null) {
                    uc.mLiveData.setValue(messageMerchEntity.getResult());
                }
                dismissDialog();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
                mRefreshLayout.finishRefresh();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
                mRefreshLayout.finishRefresh();

            }
        });
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
