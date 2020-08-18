package com.jzd.jzshop.ui.home.creditsstore.mine;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentCreditExchangeRecodBinding;
import com.jzd.jzshop.entity.CreditExchangeRecoEntity;
import com.jzd.jzshop.entity.CreditGetRedBagsEntity;
import com.jzd.jzshop.entity.CreditToSureReceiptEntity;
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
 * @date :2020/5/9 17:06
 */
public class CreditExchangeRecoViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditExchangeRecoViewModel.class.getSimpleName();
    private Context mContext;
    private FragmentCreditExchangeRecodBinding binding;
    private String log_id;
    private String merch_id;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<CreditExchangeRecoEntity.ResultBean.DataBean> totalList = new ArrayList();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<CreditExchangeRecoEntity.ResultBean.DataBean>> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<CreditToSureReceiptEntity> mToSureReceiptLiveData = new SingleLiveEvent<>();
    }

    public CreditExchangeRecoViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentActivity activity, FragmentCreditExchangeRecodBinding binding) {
        this.mContext = activity;
        this.binding = binding;
    }

    /**
     * 获取 兑换记录
     */
    public void requestData(int status, String merch_id, final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        isShowDialog(false);
        addSubscribe(model.postCreditExchangeRecod(model.getUserToken(), merch_id, status, page), new ParseDisposableTokenErrorObserver<CreditExchangeRecoEntity>() {
            @Override
            public void onResult(CreditExchangeRecoEntity creditExchangeRecoEntity) {
                super.onResult(creditExchangeRecoEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditExchangeRecoEntity != null) {
                    if (creditExchangeRecoEntity.getResult() != null) {
                        if (creditExchangeRecoEntity.getResult().getData() != null && creditExchangeRecoEntity.getResult().getData().size() > 0) {
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(creditExchangeRecoEntity.getResult().getData());
                                uc.mLiveData.setValue(totalList);
                            } else {
                            }
                        } else if (creditExchangeRecoEntity.getResult() == null ||
                                creditExchangeRecoEntity.getResult().getData().size() == 0) {//空页面处理
                            mBackResultNull(creditExchangeRecoEntity, page,status);
                            mRefreshLayout.setEnableRefresh(false);
                            mRefreshLayout.setEnableAutoLoadmore(false);
                            mRefreshLayout.setEnableLoadmore(false);
                            return;
                        } else {
                            mBackResultNull(creditExchangeRecoEntity, page,status);
                            return;
                        }

                        if (isRefresh && mRefreshLayout != null) { //刷新
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.setLoadmoreFinished(false);
                        } else if (mRefreshLayout != null) {  //加载更多
                            if (creditExchangeRecoEntity.getResult() != null && creditExchangeRecoEntity.getResult().getData().size() == 0) {
                                totalList.clear();
                                mRefreshLayout.finishLoadmoreWithNoMoreData();
                            }
                            totalList.addAll(creditExchangeRecoEntity.getResult().getData());
                            mRefreshLayout.finishLoadmore();
                            uc.mLiveData.setValue(totalList);
                        }
                        int currentCountSize = creditExchangeRecoEntity.getResult().getData().size();
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
     *  @param creditExchangeRecoEntity
     * @param page
     * @param status
     */
    private void mBackResultNull(CreditExchangeRecoEntity creditExchangeRecoEntity, int page, int status) {
        if (page == 1 && creditExchangeRecoEntity.getResult().getData().size() == 0) {
            binding.rv.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
            if (status ==1){
                binding. tvEmptyTxt.setText(mContext.getResources().getString(R.string.empty_text_exchage_record));
            }else {
                binding. tvEmptyTxt.setText(mContext.getResources().getString(R.string.empty_text_lottery_record));
            }
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

    /**
     * 领取红包
     *
     * @param log_id
     * @param merch_id
     */
    public void postGetRedBags(String log_id, String merch_id) {
        this.merch_id = merch_id;
        this.log_id = log_id;
        isShowDialog(false);
        addSubscribe(model.postGetRedBags(model.getUserToken(), log_id, merch_id), new ParseDisposableTokenErrorObserver<CreditGetRedBagsEntity>() {
            @Override
            public void onResult(CreditGetRedBagsEntity creditGetRedBagsEntity) {
                super.onResult(creditGetRedBagsEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditGetRedBagsEntity != null) {
                    // TODO: 2020/5/15  领取红包功能 接口返回 error, 功能尚不完善
//                    CreditGetRedBagsEntity.ResultBean result = creditToSureReceiptEntity.getResult();
//                    if (result != null) {
//                        uc.mGetRedBagsLiveData.setValue(result);
//                    }
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
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });

    }

    /**
     * 确认收货
     *
     * @param log_id
     * @param merch_id
     */
    public void postToSureReceipt(String log_id, String merch_id) {
        this.merch_id = merch_id;
        this.log_id = log_id;
        addSubscribe(model.postToSureReceipt(model.getUserToken(), log_id, merch_id), new ParseDisposableTokenErrorObserver<CreditToSureReceiptEntity>() {
            @Override
            public void onResult(CreditToSureReceiptEntity creditToSureReceiptEntity) {
                super.onResult(creditToSureReceiptEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditToSureReceiptEntity != null) {
                    uc.mToSureReceiptLiveData.setValue(creditToSureReceiptEntity);
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
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });

    }

}
