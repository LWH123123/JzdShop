package com.jzd.jzshop.ui.mine.recharge;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityRechargeRecordBinding;
import com.jzd.jzshop.entity.RechargeRecordEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/10.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class RechargeRecordViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = RechargeRecordViewModel.class.getSimpleName();
    private Context context;
    private ActivityRechargeRecordBinding mBinding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<RechargeRecordEntity.ResultBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<RechargeRecordEntity.ResultBean>> mLiveData = new SingleLiveEvent<>();
    }

    public RechargeRecordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityRechargeRecordBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     * 获取充值记录
     */
    public void requestData(final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postRechargeRecord(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<RechargeRecordEntity>() {
            @Override
            public void onResult(RechargeRecordEntity rechargeRecordEntity) {
                super.onResult(rechargeRecordEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (rechargeRecordEntity != null) {
                    if (rechargeRecordEntity.getResult() != null && rechargeRecordEntity.getResult().size() > 0) {
                        if (page == 1){
                            totalList.clear();
                            totalList.addAll(rechargeRecordEntity.getResult());
                            uc.mLiveData.setValue(totalList);
                        }else{}
                    } else if (rechargeRecordEntity.getResult() == null ||
                            rechargeRecordEntity.getResult().size() == 0) {//空页面处理
                        mBackResultNull(rechargeRecordEntity, page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(rechargeRecordEntity, page);
                        return;
                    }
                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (rechargeRecordEntity.getResult() != null && rechargeRecordEntity.getResult().size() == 0) {
                            totalList.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalList.addAll(rechargeRecordEntity.getResult());
                        mRefreshLayout.finishLoadmore();
                        uc.mLiveData.setValue(totalList);
                    }
                    int currentCountSize = rechargeRecordEntity.getResult().size();
                    if (currentCountSize < 10 || currentCountSize == 0) {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                        }
                    }
                }
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                loadError(isRefresh);
            }
        });
    }

    /**
     *  接口返回数据为null  或者 空[] 处理
     * @param rechargeRecordEntity
     * @param page
     */
    private void mBackResultNull(RechargeRecordEntity rechargeRecordEntity, int page) {
        if (page == 1 && rechargeRecordEntity.getResult().size() == 0) {
            mBinding.rvc.setVisibility(View.GONE);
            mBinding.emptyView.setVisibility(View.VISIBLE);
        } else {
            mBinding.rvc.setVisibility(View.VISIBLE);
            mBinding.emptyView.setVisibility(View.GONE);
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
