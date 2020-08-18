package com.jzd.jzshop.ui.home.merchantalliance;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentMerchantAllianceBinding;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.MerchantAllianceEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 商家联盟
 */
public class MerchantAllianceViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = MerchantAllianceViewModel.class.getSimpleName();
    public UIChangeObservable uc = new UIChangeObservable();    //封装一个界面发生改变的观察者
    public int page = 1;
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private int mPage;
    private boolean mIsRefresh;
    private int total;
    private List<MerchantAllianceEntity.ResultBean.DataBean> totalList = new ArrayList();
    private FragmentMerchantAllianceBinding mBinding;
    private List<MerchantAllianceEntity.ResultBean.DataBean> banner;

    public void setBinding(FragmentMerchantAllianceBinding binding) {
        this.mBinding = binding;
    }

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
        //更新xbanner数据
        public SingleLiveEvent<List<BannEntity>> xbannerRefreshing = new SingleLiveEvent<>();
    }

    public ObservableList<MerchantAllianceItemViewModel> fma_merch_ob = new ObservableArrayList<>();

    public ItemBinding<MerchantAllianceItemViewModel> fma_merch_ib = ItemBinding.of(com.jzd.jzshop.BR.maIVM, R.layout.item_merchant_alliance);


    public MerchantAllianceViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);

    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void
    requestNetWork(SmartRefreshLayout refreshLayout, int page, boolean isRefresh) {
        mRefreshLayout = refreshLayout;
        this.mPage = page;
        this.mIsRefresh = isRefresh;
        requestNet(mPage, null, mIsRefresh);
    }

    public String getUserToken(){
        return model.getUserToken();
    }

    public void requestNetWork(String keys) {
        fma_merch_ob.clear();
        requestNet(mPage, keys, mIsRefresh);
    }

    public void requestNet(final int page, String keys, final boolean isRefresh) {
        isShowDialog(false);
        addSubscribe(model.postShopMerch(String.valueOf(page), keys, pagesize), new ParseDisposableObserver<MerchantAllianceEntity>() {
            @Override
            public void onResult(MerchantAllianceEntity entity) {
                dismissDialog();
                ArrayList<BannEntity> bannerData = new ArrayList<>();
                List<MerchantAllianceEntity.ResultBean> result = entity.getResult();
                List<MerchantAllianceEntity.ResultBean.DataBean> dataList = new ArrayList(); //重组数据集
                if (result != null && result.size() > 0) {
                    for (MerchantAllianceEntity.ResultBean resultBean : entity.getResult()) {
                        total = resultBean.getTotal();
                        for (MerchantAllianceEntity.ResultBean.DataBean dataBean : resultBean.getData()) {
                            if (!TextUtils.isEmpty(resultBean.getId()) && TextUtils.equals(resultBean.getId(), "banner")) {
                                banner = resultBean.getData();
                                bannerData.add(new BannEntity(dataBean.getImgurl(), dataBean.getLinkurl()));
                            } else if (!TextUtils.isEmpty(resultBean.getId()) && TextUtils.equals(resultBean.getId(), "merch")) {
                                dataList.add(dataBean);
                                if (page == 1) {
                                    fma_merch_ob.add(new MerchantAllianceItemViewModel(MerchantAllianceViewModel.this, dataBean));
                                } else {
//                                    totalList.addAll(dataList);
                                }
                            }
                        }
                    }
                } else if (result == null || result.size() == 0) {//空页面处理
                    mRefreshLayout.finishLoadmore(true);
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                    return;
                } else {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                    return;
                }


                if (isRefresh && mRefreshLayout != null) { //刷新
                    if (fma_merch_ob.size() == 0) {
                        ToastUtils.showShort("还没有开张的商铺哦!");
                        return;
                    }
                    if (page > 1) {
                        fma_merch_ob.clear();
                    }
                    uc.xbannerRefreshing.setValue(bannerData);

                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.setLoadmoreFinished(false);

                } else if (mRefreshLayout != null) {  //加载更多
                    // TODO: 2019/12/20  加载更多逻辑处理
                    if (dataList != null && dataList.size() == 0) {
                        totalList.clear();
                        mRefreshLayout.finishLoadmoreWithNoMoreData();
                    }
                    totalList.clear();
                    totalList.addAll(dataList);
                    for (MerchantAllianceEntity.ResultBean.DataBean vmData : totalList) {
                        fma_merch_ob.add(new MerchantAllianceItemViewModel(MerchantAllianceViewModel.this, vmData));
                    }
                    mRefreshLayout.finishLoadmore();
                }
                int currentCountSize = fma_merch_ob.size();
                if (currentCountSize < 10 || currentCountSize == 0) {
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                    }
                }
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                loadError(isRefresh);
            }
        });
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
