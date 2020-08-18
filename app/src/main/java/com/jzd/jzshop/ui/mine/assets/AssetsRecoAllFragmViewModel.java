package com.jzd.jzshop.ui.mine.assets;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentAssetsRecordPageBinding;
import com.jzd.jzshop.entity.AssetsRecordEntity;
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
public class AssetsRecoAllFragmViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AssetsRecoAllFragmViewModel.class.getSimpleName();
    private Context mContext;
    private FragmentAssetsRecordPageBinding binding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<AssetsRecordEntity.ResultBean.AssetsRecordBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<AssetsRecordEntity.ResultBean.AssetsRecordBean>> mLiveData = new SingleLiveEvent<>();
    }

    public AssetsRecoAllFragmViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentActivity activity, FragmentAssetsRecordPageBinding binding) {
        this.mContext = activity;
        this.binding = binding;
    }

    /**
     * 获取 君子权记录 all
     */
    public void requestData(int status, final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        isShowDialog(false);
        addSubscribe(model.postAssetsRecord(model.getUserToken(), status, page), new ParseDisposableTokenErrorObserver<AssetsRecordEntity>() {
            @Override
            public void onResult(AssetsRecordEntity assetsRecordEntity) {
                super.onResult(assetsRecordEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (assetsRecordEntity != null) {
                    if (assetsRecordEntity.getResult() != null) {
                        if (assetsRecordEntity.getResult().getData() != null && assetsRecordEntity.getResult().getData().size() > 0) {
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(assetsRecordEntity.getResult().getData());
                                uc.mLiveData.setValue(totalList);
                            } else {
                            }
                        } else if (assetsRecordEntity.getResult() == null ||
                                assetsRecordEntity.getResult().getData().size() == 0) {//空页面处理
                            mBackResultNull(assetsRecordEntity, page);
                            mRefreshLayout.setEnableRefresh(false);
                            mRefreshLayout.setEnableAutoLoadmore(false);
                            mRefreshLayout.setEnableLoadmore(false);
                            return;
                        } else {
                            mBackResultNull(assetsRecordEntity, page);
                            return;
                        }

                        if (isRefresh && mRefreshLayout != null) { //刷新
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.setLoadmoreFinished(false);
                        } else if (mRefreshLayout != null) {  //加载更多
                            if (assetsRecordEntity.getResult() != null && assetsRecordEntity.getResult().getData().size() == 0) {
                                totalList.clear();
                                mRefreshLayout.finishLoadmoreWithNoMoreData();
                            }
                            totalList.addAll(assetsRecordEntity.getResult().getData());
                            mRefreshLayout.finishLoadmore();
                            uc.mLiveData.setValue(totalList);
                        }
                        int currentCountSize = assetsRecordEntity.getResult().getData().size();
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
     * @param assetsRecordEntity
     * @param page
     */
    private void mBackResultNull(AssetsRecordEntity assetsRecordEntity, int page) {
        if (page == 1 && assetsRecordEntity.getResult().getData().size() == 0) {
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
