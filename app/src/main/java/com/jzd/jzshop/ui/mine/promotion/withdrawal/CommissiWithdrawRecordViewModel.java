package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentCommissiWithdrawRecordBinding;
import com.jzd.jzshop.entity.CommisWithdraRecordEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 10:01
 */
public class CommissiWithdrawRecordViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CommissiWithdrawRecordViewModel.class.getSimpleName();
    private Context context;
    private FragmentCommissiWithdrawRecordBinding mBinding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<CommisWithdraRecordEntity.DataBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<CommisWithdraRecordEntity.DataBean>> mLiveData = new SingleLiveEvent<>();
    }

    public CommissiWithdrawRecordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, FragmentCommissiWithdrawRecordBinding binding) {
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
    public void requestData(final SmartRefreshLayout refreshLayout,String type, final int page, final boolean isRefresh) {
        isShowDialog(false);
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postCommisWithdraRecord(model.getUserToken(), type,page), new ParseDisposableTokenErrorObserver<CommisWithdraRecordEntity>() {
            @Override
            public void onResult(CommisWithdraRecordEntity commisWithdraRecordEntity) {
                super.onResult(commisWithdraRecordEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (commisWithdraRecordEntity != null) {
                    if (commisWithdraRecordEntity.getResult() != null && commisWithdraRecordEntity.getResult().getData().size() > 0) {
                        if (page == 1) {
                            totalList.clear();
                            totalList.addAll(commisWithdraRecordEntity.getResult().getData());
                            uc.mLiveData.setValue(totalList);
                        } else {
                        }
                    } else if (commisWithdraRecordEntity.getResult() == null ||
                            commisWithdraRecordEntity.getResult().getData().size() == 0) {//空页面处理
                        mBackResultNull(commisWithdraRecordEntity, page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(commisWithdraRecordEntity, page);
                        return;
                    }

                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (commisWithdraRecordEntity.getResult() != null && commisWithdraRecordEntity.getResult().getData().size() == 0) {
                            totalList.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalList.addAll(commisWithdraRecordEntity.getResult().getData());
                        mRefreshLayout.finishLoadmore();
                        uc.mLiveData.setValue(totalList);
                    }
                    int currentCountSize = commisWithdraRecordEntity.getResult().getData().size();
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
     * @param commisWithdraRecordEntity
     * @param page
     */
    private void mBackResultNull(CommisWithdraRecordEntity commisWithdraRecordEntity, int page) {
        if (page == 1 && commisWithdraRecordEntity.getResult().getData().size() == 0) {
            mBinding.rv.setVisibility(View.GONE);
            mBinding.emptyView.setVisibility(View.VISIBLE);
        } else {
            mBinding.rv.setVisibility(View.VISIBLE);
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
