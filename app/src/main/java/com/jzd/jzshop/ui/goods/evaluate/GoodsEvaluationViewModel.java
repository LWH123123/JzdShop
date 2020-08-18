package com.jzd.jzshop.ui.goods.evaluate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityGoodsEvaluationBinding;
import com.jzd.jzshop.entity.GoodsEvaluationEntity;
import com.jzd.jzshop.entity.GoodsEvaluationTypeEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/5/25 17:08
 */
public class GoodsEvaluationViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = GoodsEvaluationViewModel.class.getSimpleName();
    private Context mContext;
    private String gid;
    private int pagesize = 10;
    private String goodsType; //默认评价类型
    private SmartRefreshLayout mRefreshLayout;
    private int mPage;
    private boolean mIsRefresh;
    private ActivityGoodsEvaluationBinding mBinding;
    private GoodsEvaluationTypeAdapter typeAdapter;
    private List<GoodsEvaluationEntity.ResultBean.DataBean> totalList = new ArrayList<>();   //本地构建数据集;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //更新xbanner数据
        public SingleLiveEvent<List<GoodsEvaluationEntity.ResultBean.DataBean>> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<List<GoodsEvaluationEntity.ResultBean.DataBean>> noDataNotify = new SingleLiveEvent<>();

    }

    public GoodsEvaluationViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityGoodsEvaluationBinding binding) {
        this.mContext = context;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     * 设置评价类型数据
     *
     * @param dataList
     */
    public void setGoodsTypeData(final List<GoodsEvaluationTypeEntity> dataList) {
        typeAdapter = new GoodsEvaluationTypeAdapter(mContext, dataList, R.layout.item_rv_goods_type);
        mBinding.rvType.setAdapter(typeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.rvType.setLayoutManager(linearLayoutManager);
        typeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {//类型
                typeAdapter.setSelectedPosition(position);     //选中，设置背景颜色
                // TODO: 2019/12/21 刷新列表数据
                String type = dataList.get(position).getType();
                if (type != null && !TextUtils.isEmpty(type)) {
                    if (type.equals("全部")) {
                        type = "all";
                    } else if (type.equals("好评")) {
                        type = "good";
                    } else if (type.equals("中评")) {
                        type = "normal";
                    } else if (type.equals("差评")) {
                        type = "bad";
                    } else if (type.equals("晒图")) {
                        type = "pic";
                    }
                    SPUtils.getInstance().put(Constants.SP.CACHE_HOME_BANNER_DATA, type);
                    mIsRefresh = true;
                    mPage = 1;
                    requestNetWork(gid, type, mRefreshLayout, mPage, mIsRefresh);
                }
            }
        });
    }


    public void requestNetWork(String gid, String goodsType, final SmartRefreshLayout refreshLayout, int page, final boolean isRefresh) {
        isShowDialog(false);
        this.gid = gid;
        this.goodsType = goodsType;
        Log.d(TAG, "gid：" + gid);
        Log.d(TAG, "goodsType：" + gid);
        mRefreshLayout = refreshLayout;
        this.mPage = page;
        this.mIsRefresh = isRefresh;
        addSubscribe(model.postGoodsEvaluatList(gid, goodsType, page, pagesize), new ParseDisposableTokenErrorObserver<GoodsEvaluationEntity>() {
            @Override
            public void onResult(GoodsEvaluationEntity goodsEvaluationEntity) {
                Log.d(TAG, "onSuccess:---->>>");
                if (goodsEvaluationEntity != null) {
                    if (goodsEvaluationEntity.getResult() != null) {
                        if (goodsEvaluationEntity.getResult().getData() != null && goodsEvaluationEntity.getResult().getData().size() > 0) {
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(goodsEvaluationEntity.getResult().getData());
                                uc.mLiveData.setValue(totalList);
                            } else {
                            }
                        } else if (goodsEvaluationEntity.getResult() == null ||
                                goodsEvaluationEntity.getResult().getData().size() == 0 && isRefresh) {//空页面处理
                            mBackResultNull(goodsEvaluationEntity, page);
                            mRefreshLayout.setEnableRefresh(false);
                            mRefreshLayout.setEnableAutoLoadmore(false);
                            mRefreshLayout.setEnableLoadmore(false);
                            uc.noDataNotify.setValue(goodsEvaluationEntity.getResult().getData());
                            return;
                        } else {
                            mBackResultNull(goodsEvaluationEntity, page);
                            return;
                        }
                        if (isRefresh && mRefreshLayout != null) { //刷新
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.setLoadmoreFinished(false);
                        } else if (mRefreshLayout != null) {  //加载更多
                            if (goodsEvaluationEntity.getResult() != null && goodsEvaluationEntity.getResult().getData().size() == 0) {
                                totalList.clear();
                                mRefreshLayout.finishLoadmoreWithNoMoreData();
                            }
                            totalList.addAll(goodsEvaluationEntity.getResult().getData());
                            mRefreshLayout.finishLoadmore();
                            uc.mLiveData.setValue(totalList);
                        }
                        int currentCountSize = goodsEvaluationEntity.getResult().getData().size();
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
            public void onError(String errarMessage) {
                Log.d(TAG, "onError:" + errarMessage);
                dismissDialog();
                loadError(isRefresh);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                Log.d(TAG, "onTokenError:" + model.getUserToken());
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
                ToastUtils.showLong("请重新登录。");
            }
        });

    }

    /**
     * 接口返回数据为null  或者 空[] 处理
     *
     * @param goodsEvaluationEntity
     * @param page
     */
    private void mBackResultNull(GoodsEvaluationEntity goodsEvaluationEntity, int page) {
        if (page == 1 && goodsEvaluationEntity.getResult().getData().size() == 0 || goodsEvaluationEntity.getResult().getData() == null) {
            mBinding.rvSrceenOut.setVisibility(View.GONE);
            mBinding.emptyView.setVisibility(View.VISIBLE);
        } else {
            mBinding.rvSrceenOut.setVisibility(View.VISIBLE);
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
