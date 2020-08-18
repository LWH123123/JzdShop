package com.jzd.jzshop.ui.mine.mineteam;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.MineTeamEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author lwh
 * @description :
 * @date 2020/4/22.
 */
public class MineTeamViewModel extends ToolbarViewModel<Repository> {

    public ObservableList<MineTeamItemVIewModel> mineteamlist = new ObservableArrayList<>();
    public ItemBinding<MineTeamItemVIewModel> mineteamview = ItemBinding.of(BR.mineteamitemVM, R.layout.item_mine_team);

    private SmartRefreshLayout mallRefreshLayout;

    public MineTeamViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public ObservableList<MineTeamEntity.ResultBean.DataBean> mineteamentity=new ObservableArrayList<>();

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }



    public void request(int page, boolean isrefresh,SmartRefreshLayout refreshLayout){
        this.mallRefreshLayout=refreshLayout;
        addSubscribe(model.postCommissionDown(model.getUserToken(),page),new ParseDisposableTokenErrorObserver<MineTeamEntity>(){
            @Override
            public void onResult(MineTeamEntity o) {
                super.onResult(o);
                if (o != null) {
                    if (o.getResult() != null) {
                        if (o.getResult().getData() != null && o.getResult().getData().size() > 0) {
                            if (page == 1) {
                                mineteamentity.clear();
                                mineteamlist.clear();
                                mineteamentity.addAll(o.getResult().getData());
                                bindData(mineteamentity);
                            } else {
                            }
                        } else if (page==1&&o.getResult() == null) {//空页面处理
                            mBackResultNull(o, page);
                            mallRefreshLayout.setEnableRefresh(false);
                            mallRefreshLayout.setEnableAutoLoadmore(false);
                            mallRefreshLayout.setEnableLoadmore(false);
                            return;
                        } else {
                            mBackResultNull(o, page);
                            return;
                        }

                        if (isrefresh && mallRefreshLayout != null) { //刷新
                            mallRefreshLayout.finishRefresh();
                            mallRefreshLayout.setLoadmoreFinished(false);
                        } else if (mallRefreshLayout != null) {  //加载更多
                            if (o.getResult() != null && o.getResult().getData().size() == 0) {
//                                mineteamentity.clear();
                                mallRefreshLayout.finishLoadmoreWithNoMoreData();
                            }
                            mineteamentity.addAll(o.getResult().getData());
                            mallRefreshLayout.finishLoadmore();
                            bindData(mineteamentity);
                        }
                        int currentCountSize = o.getResult().getTotal();
                        if (currentCountSize < 10) {
                            if (mallRefreshLayout != null) {
                                mallRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                            }
                        }

                    }
                }
                dismissDialog();


            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                dismissDialog();

            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

        });

    }

    private void bindData(List<MineTeamEntity.ResultBean.DataBean> mineteamentity) {
        for (int i = 0; i <mineteamentity.size() ; i++) {
            MineTeamItemVIewModel mineTeamItemVIewModel = new MineTeamItemVIewModel(MineTeamViewModel.this,mineteamentity.get(i));
            mineteamlist.add(mineTeamItemVIewModel);
        }


    }


    private void mBackResultNull(MineTeamEntity mineStoreEntity, int page) {
        mallRefreshLayout.finishLoadmore();
        mallRefreshLayout.finishRefresh();
        if (page == 1 && mineStoreEntity.getResult().getData()==null) {
            //todo 隐藏列表
//            binding.rv.setVisibility(View.GONE);
//            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
//            binding.rv.setVisibility(View.VISIBLE);
//            binding.emptyView.setVisibility(View.GONE);
        }
        mallRefreshLayout.finishLoadmore(true);
        mallRefreshLayout.finishLoadmoreWithNoMoreData();
        dismissDialog();
        return;
    }

    /**
     * 网络请求，加载失败
     */
    public void loadError(boolean isRefresh) {
        if (mallRefreshLayout != null) {
            if (isRefresh) {
                mallRefreshLayout.finishRefresh(false);  //结束刷新（刷新失败）
                mallRefreshLayout.setLoadmoreFinished(false);
            } else {
                mallRefreshLayout.finishLoadmore(false); //结束加载（加载失败）
            }
        }
    }


}
