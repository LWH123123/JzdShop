package com.jzd.jzshop.ui.mine.withdrawals;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityWithdrawalsRecordBinding;
import com.jzd.jzshop.entity.WithdrawalsRecordEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class WithdrawalsRecordViewModel extends ToolbarViewModel<Repository> {

    private static final String TAG = WithdrawalsRecordViewModel.class.getSimpleName();
    private Context context;
    private ActivityWithdrawalsRecordBinding mBinding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<WithdrawalsRecordEntity.ResultBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<WithdrawalsRecordEntity.ResultBean>> mLiveData = new SingleLiveEvent<>();
    }

    public WithdrawalsRecordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityWithdrawalsRecordBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }


    /**
     * 获取 提现记录数据
     */
    public void requestData(final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postWithdrawalsRecord(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<WithdrawalsRecordEntity>() {
            @Override
            public void onResult(WithdrawalsRecordEntity withdrawalsRecordEntity) {
                super.onResult(withdrawalsRecordEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (withdrawalsRecordEntity != null) {
                    if (withdrawalsRecordEntity.getResult() != null && withdrawalsRecordEntity.getResult().size() > 0) {
                        if (page == 1) {
                            totalList.clear();
                            totalList.addAll(withdrawalsRecordEntity.getResult());
                            uc.mLiveData.setValue(totalList);
                        } else {
                        }
                    } else if (withdrawalsRecordEntity.getResult() == null ||
                            withdrawalsRecordEntity.getResult().size() == 0) {//空页面处理
                        mBackResultNull(withdrawalsRecordEntity, page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(withdrawalsRecordEntity, page);
                        return;
                    }

                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (withdrawalsRecordEntity.getResult() != null && withdrawalsRecordEntity.getResult().size() == 0) {
                            totalList.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalList.addAll(withdrawalsRecordEntity.getResult());
                        mRefreshLayout.finishLoadmore();
                        uc.mLiveData.setValue(totalList);
                    }
                    int currentCountSize = withdrawalsRecordEntity.getResult().size();
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
     * 接口返回数据为null  或者 空[] 处理
     *
     * @param withdrawalsRecordEntity
     * @param page
     */
    private void mBackResultNull(WithdrawalsRecordEntity withdrawalsRecordEntity, int page) {
        if (page == 1 && withdrawalsRecordEntity.getResult().size() == 0) {
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
