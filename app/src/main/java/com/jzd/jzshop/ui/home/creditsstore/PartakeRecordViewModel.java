package com.jzd.jzshop.ui.home.creditsstore;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityPartakeRecordBinding;
import com.jzd.jzshop.entity.PartakeRecordEntity;
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
 * @date :2020/5/11 9:27
 */
public class PartakeRecordViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = PartakeRecordViewModel.class.getSimpleName();
    private Context context;
    private ActivityPartakeRecordBinding mBinding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<PartakeRecordEntity.ResultBean.DataBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();
    private PartakeRecordEntity.ResultBean.MemberBean member;

    public class UIChangeObservable {
        public SingleLiveEvent<PartakeRecordEntity.ResultBean.MemberBean> mMemberLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<List<PartakeRecordEntity.ResultBean.DataBean>> mLiveData = new SingleLiveEvent<>();
    }

    public PartakeRecordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityPartakeRecordBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     * 获取 积分商城 参与记录
     */
    public void requestData(final SmartRefreshLayout refreshLayout, String merch_id, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postPartakeRecord(model.getUserToken(), merch_id, page, pagesize), new ParseDisposableTokenErrorObserver<PartakeRecordEntity>() {
            @Override
            public void onResult(PartakeRecordEntity partakerecordentity) {
                super.onResult(partakerecordentity);
                Log.d(TAG, "onSuccess:---->>>");
                if (partakerecordentity != null) {
                    member = partakerecordentity.getResult().getMember();
                    if (member != null) {
                        uc.mMemberLiveData.setValue(member);
                    }
                    List<PartakeRecordEntity.ResultBean.DataBean> data = partakerecordentity.getResult().getData();
                    if (data != null && data.size() > 0) {
                        if (page == 1){
                            totalList.clear();
                            totalList.addAll(data);
                            uc.mLiveData.setValue(totalList);
                        }else{}
                    } else if (partakerecordentity.getResult().getData() == null ||
                            partakerecordentity.getResult().getData().size() == 0) {//空页面处理
                        mBackResultNull(partakerecordentity.getResult(), page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(partakerecordentity.getResult(), page);
                        return;
                    }
                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (partakerecordentity.getResult().getData() != null && partakerecordentity.getResult().getData().size() == 0) {
                            totalList.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalList.addAll(partakerecordentity.getResult().getData());
                        mRefreshLayout.finishLoadmore();
                        uc.mLiveData.setValue(totalList);
                    }
                    int currentCountSize = partakerecordentity.getResult().getData().size();
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
     * @param resultBean
     * @param page
     */
    private void mBackResultNull(PartakeRecordEntity.ResultBean resultBean, int page) {
        if (page == 1 && resultBean.getData().size() == 0) {
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


    public String getCreditsNum(){
        int points = uc.mMemberLiveData.getValue().getPoints();
        return String.valueOf(points);
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
