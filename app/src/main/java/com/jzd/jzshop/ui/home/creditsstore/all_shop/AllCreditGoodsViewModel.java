package com.jzd.jzshop.ui.home.creditsstore.all_shop;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityAllCredityGoodsBinding;
import com.jzd.jzshop.entity.CreditsAllGoodsEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/5/9 15:18
 */
public class AllCreditGoodsViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AllCreditGoodsViewModel.class.getSimpleName();
    private Context context;
    private ActivityAllCredityGoodsBinding mBinding;
    private String keywordsSearch;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<CreditsAllGoodsEntity.ResultBean.DataBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CreditsAllGoodsEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<List<CreditsAllGoodsEntity.ResultBean.DataBean>> mAllLiveData = new SingleLiveEvent<>();
    }

    public AllCreditGoodsViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityAllCredityGoodsBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

//    @Override
//    protected void initToolBar(String text) {
//        super.initToolBar(text);
//    }

    public void requestDataByKeywords(SmartRefreshLayout refreshLayout, String keyWord, String cate_id, int page, boolean isRefresh) {
        this.keywordsSearch = keyWord;
        this.mRefreshLayout = refreshLayout;
        requestData(refreshLayout, cate_id, keyWord, "", page, isRefresh);
    }

    /**
     * 积分商城 - 全部商品
     *
     * @param cate_id
     * @param keywords
     * @param merch_id
     * @param page
     */
    public void requestData(final SmartRefreshLayout refreshLayout, String cate_id, String keywords, String merch_id, int page, final boolean isRefresh) {
        isShowDialog(false);
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postCreditAllGoods(model.getUserToken(), cate_id, keywords, merch_id, page),
                new ParseDisposableTokenErrorObserver<CreditsAllGoodsEntity>() {
                    @Override
                    public void onResult(CreditsAllGoodsEntity creditsAllGoodsEntity) {
                        super.onResult(creditsAllGoodsEntity);
                        Log.d(TAG, "onSuccess:---->>>");
                        if (creditsAllGoodsEntity != null) {
                            if (creditsAllGoodsEntity.getResult() != null) {
                                uc.mLiveData.setValue(creditsAllGoodsEntity.getResult());
                            }
                            List<CreditsAllGoodsEntity.ResultBean.DataBean> data = creditsAllGoodsEntity.getResult().getData();
                            if (data != null && data.size() > 0) {
                                if (page == 1) {
                                    totalList.clear();
                                    totalList.addAll(data);
                                    uc.mAllLiveData.setValue(totalList);
                                } else {
                                }
                            } else if (creditsAllGoodsEntity.getResult().getData() == null ||
                                    creditsAllGoodsEntity.getResult().getData().size() == 0) {//空页面处理
                                mBackResultNull(creditsAllGoodsEntity.getResult(), page);
                                mRefreshLayout.setEnableRefresh(false);
                                mRefreshLayout.setEnableAutoLoadmore(false);
                                mRefreshLayout.setEnableLoadmore(false);
                                return;
                            } else {
                                mBackResultNull(creditsAllGoodsEntity.getResult(), page);
                                return;
                            }
                            if (isRefresh && mRefreshLayout != null) { //刷新
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.setLoadmoreFinished(false);
                            } else if (mRefreshLayout != null) {  //加载更多
                                if (creditsAllGoodsEntity.getResult().getData() != null && creditsAllGoodsEntity.getResult().getData().size() == 0) {
                                    totalList.clear();
                                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                                }
                                totalList.addAll(creditsAllGoodsEntity.getResult().getData());
                                mRefreshLayout.finishLoadmore();
                                uc.mAllLiveData.setValue(totalList);
                            }
                            int currentCountSize = creditsAllGoodsEntity.getResult().getData().size();
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
    private void mBackResultNull(CreditsAllGoodsEntity.ResultBean resultBean, int page) {
        if (page == 1 && resultBean.getData().size() == 0) {
            mBinding.recyclerView.setVisibility(View.GONE);
            mBinding.emptyView.setVisibility(View.VISIBLE);
        } else {
            mBinding.recyclerView.setVisibility(View.VISIBLE);
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

    //一键置顶
    public BindingCommand onClickGoTop = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mBinding.recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mBinding.recyclerView.scrollToPosition(0);
                }
            });
        }
    });


    public BindingCommand backOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

//    @Override
//    protected void setBackOnClick() {
//        super.setBackOnClick();
//        finish();
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
