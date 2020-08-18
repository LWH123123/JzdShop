package com.jzd.jzshop.ui.mine.creditsign.ranking;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentContinuousSignInBinding;
import com.jzd.jzshop.entity.SignRankingEntity;
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
 * @date :2020/4/7 10:29
 */
public class ContinuousSignInViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = ContinuousSignInViewModel.class.getSimpleName();
    private Context mContext;
    private FragmentContinuousSignInBinding binding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<SignRankingEntity.ResultBean.DaatBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<SignRankingEntity.ResultBean.DaatBean>> mLiveData = new SingleLiveEvent<>();
    }

    public ContinuousSignInViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentActivity activity, FragmentContinuousSignInBinding binding) {
        this.mContext = activity;
        this.binding = binding;
    }

    /**
     * 获取 积分签到排行榜
     */
    public void requestData(String type, final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        isShowDialog(false);
        addSubscribe(model.postSignRanking(model.getUserToken(), type, page, pagesize), new ParseDisposableTokenErrorObserver<SignRankingEntity>() {
            @Override
            public void onResult(SignRankingEntity signRankingEntity) {
                super.onResult(signRankingEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (signRankingEntity != null) {
                    if (signRankingEntity.getResult() != null) {
                        if (signRankingEntity.getResult().getDaat() != null && signRankingEntity.getResult().getDaat().size() > 0) {
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(signRankingEntity.getResult().getDaat());
                                uc.mLiveData.setValue(totalList);
                            } else {
                            }
                        } else if (signRankingEntity.getResult() == null ||
                                signRankingEntity.getResult().getDaat().size()==0) {//空页面处理
                            mBackResultNull(signRankingEntity, page);
                            mRefreshLayout.setEnableRefresh(false);
                            mRefreshLayout.setEnableAutoLoadmore(false);
                            mRefreshLayout.setEnableLoadmore(false);
                            return;
                        } else {
                            mBackResultNull(signRankingEntity, page);
                            return;
                        }

                        if (isRefresh && mRefreshLayout != null) { //刷新
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.setLoadmoreFinished(false);
                        } else if (mRefreshLayout != null) {  //加载更多
                            if (signRankingEntity.getResult() != null && signRankingEntity.getResult().getDaat().size() == 0) {
                                totalList.clear();
                                mRefreshLayout.finishLoadmoreWithNoMoreData();
                            }
                            totalList.addAll(signRankingEntity.getResult().getDaat());
                            mRefreshLayout.finishLoadmore();
                            uc.mLiveData.setValue(totalList);
                        }
                        int currentCountSize = signRankingEntity.getResult().getDaat().size();
                        if (currentCountSize < 10 || currentCountSize == 0) {
                            if (mRefreshLayout != null) {
                                mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                            }
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
     * @param signRankingEntity
     * @param page
     */
    private void mBackResultNull(SignRankingEntity signRankingEntity, int page) {
        if (page == 1 && signRankingEntity.getResult().getDaat()==null) {
            binding.rv.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.rv.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
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


}
