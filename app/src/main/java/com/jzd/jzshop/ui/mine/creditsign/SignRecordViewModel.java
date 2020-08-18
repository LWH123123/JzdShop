package com.jzd.jzshop.ui.mine.creditsign;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentSignRecordBinding;
import com.jzd.jzshop.entity.SignRecordEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author lwh
 * @description :
 * @date 2020/3/24.
 */
public class SignRecordViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = SignRecordViewModel.class.getSimpleName();
    private Context context;
    private FragmentSignRecordBinding mBinding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<SignRecordEntity.ResultBean.DaatBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<SignRecordEntity.ResultBean.DaatBean>> datacall = new SingleLiveEvent<>();
    }

    public SignRecordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, FragmentSignRecordBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

//    public void request(){
////        for (int i = 0; i <10 ; i++) {
////            RechargeRecordEntity.ResultBean resultBean = new RechargeRecordEntity.ResultBean();
////            resultBean.setTitle("日常签到");
////            resultBean.setCreatetime("2020年3月24日15:51:0"+i);
////            resultBean.setStatus_str("+"+2*i);
////            data.add(resultBean);
////        }
////        datacall.setValue(data);
//
//
//    }

    /**
     * 获取充值记录
     */
    public void requestData(final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        addSubscribe(model.postSignRecord(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<SignRecordEntity>() {
            @Override
            public void onResult(SignRecordEntity signRecordEntity) {
                super.onResult(signRecordEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (signRecordEntity != null) {
                    if (signRecordEntity.getResult() != null && signRecordEntity.getResult().getDaat()!=null&&signRecordEntity.getResult().getDaat().size() > 0) {
                        if (page == 1) {
                            totalList.clear();
                            totalList.addAll(signRecordEntity.getResult().getDaat());
                            uc.datacall.setValue(totalList);
                        } else {
                        }
                    } else if (signRecordEntity.getResult() == null ||
                            signRecordEntity.getResult().getDaat().size() == 0) {//空页面处理
                        mBackResultNull(signRecordEntity, page);
                        mRefreshLayout.setEnableRefresh(false);
                        mRefreshLayout.setEnableAutoLoadmore(false);
                        mRefreshLayout.setEnableLoadmore(false);
                        return;
                    } else {
                        mBackResultNull(signRecordEntity, page);
                        return;
                    }
                    if (isRefresh && mRefreshLayout != null) { //刷新
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.setLoadmoreFinished(false);
                    } else if (mRefreshLayout != null) {  //加载更多
                        if (signRecordEntity.getResult() != null && signRecordEntity.getResult().getDaat().size() == 0) {
                            totalList.clear();
                            mRefreshLayout.finishLoadmoreWithNoMoreData();
                        }
                        totalList.addAll(signRecordEntity.getResult().getDaat());
                        mRefreshLayout.finishLoadmore();
                        uc.datacall.setValue(totalList);
                    }
                    int currentCountSize = signRecordEntity.getResult().getDaat().size();
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
     * @param signRecordEntity
     * @param page
     */
    private void mBackResultNull(SignRecordEntity signRecordEntity, int page) {
        if (page == 1 && signRecordEntity.getResult().getDaat().size() == 0) {
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
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


}
