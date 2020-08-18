package com.jzd.jzshop.ui.mine.mineshop.shophome;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.chat.im.JWebSocketClient;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMineShopHomeBinding;
import com.jzd.jzshop.entity.MineStoreEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description :
 * @date 2020/4/3.
 */
public class MineShopHomeViewModel extends BaseViewModel<Repository> {


    private MineShopHomeAty mineShopHomeAty;
    private ActivityMineShopHomeBinding binding;
    public void setbind(MineShopHomeAty mineShopHomeAty, ActivityMineShopHomeBinding binding) {
        this.mineShopHomeAty=mineShopHomeAty;
        this.binding=binding;
    }
    private SmartRefreshLayout mRefreshLayout;
    public MineShopHomeViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }
    public SingleLiveEvent<MineStoreEntity.ResultBean> mineshopdata =
            new SingleLiveEvent<>();
    public SingleLiveEvent<List<MineStoreEntity.ResultBean.DataBean.GoodsBean>> mineshopMoredata =
            new SingleLiveEvent<>();
    private ObservableField<MineStoreEntity.ResultBean> result =new ObservableField<>();
    private List<MineStoreEntity.ResultBean.DataBean.GoodsBean> totalList = new ArrayList();


    public void setMessageCode(){
        int userMessage = model.getUserMessage();
        KLog.a("当前的消息数量"+userMessage);
        if(userMessage<=0){
            binding.ivMessageNumber.setVisibility(View.GONE);
        }else {
            binding.ivMessageNumber.setText(String.valueOf(userMessage));
            binding.ivMessageNumber.setVisibility(View.VISIBLE);
            binding.ivMessageNumber.invalidate();
        }

    }

    public void requestwork(String shopid, String keywords, final int page, SmartRefreshLayout refreshLayout, final boolean isRefresh){
        this.mRefreshLayout=refreshLayout;
        if(page==1){
            isShowDialog(true);
        }else {
            isShowDialog(false);
        }
        addSubscribe(model.postMineShopHome(model.getUserToken(),shopid,keywords,page),new ParseDisposableTokenErrorObserver<MineStoreEntity>(){
            @Override
            public void onResult(MineStoreEntity o) {
                super.onResult(o);
                //刷新  加载
                if (o != null) {
                    if (o.getResult() != null) {
                        if (o.getResult().getData() != null && o.getResult().getData().getGoods().size() > 0) {
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(o.getResult().getData().getGoods());
                                result.set(o.getResult());
                                mineshopdata.setValue(result.get());
                                mineshopMoredata.setValue(totalList);
                            }/*else if (page==1&&o.getResult() == null ||
                                    o.getResult().getData().getGoods().size()==0) {//空页面处理
                                mBackResultNull(o, page);
                                mRefreshLayout.setEnableRefresh(false);
                                mRefreshLayout.setEnableAutoLoadmore(false);
                                mRefreshLayout.setEnableLoadmore(false);
                                return;
                            } else {
                                mBackResultNull(o, page);
                                return;
                            }*/
                            if (isRefresh && mRefreshLayout != null) { //刷新
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.setLoadmoreFinished(false);
                            } else if (mRefreshLayout != null&&page!=1) {  //加载更多

                                if (o.getResult() != null && o.getResult().getData().getGoods().size() == 0) {
                                    totalList.clear();
                                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                                }
                                int status = o.getResult().getStatus();
                                if (status == 200 || status == 300){
                                    totalList.addAll(o.getResult().getData().getGoods());
                                    result.set(o.getResult());
                                    mRefreshLayout.finishLoadmore();
                                    mineshopMoredata.setValue(totalList);
                                }
                                int currentCountSize = o.getResult().getData().getGoods().size();
                                if (currentCountSize < 10 ) {
                                    if (mRefreshLayout != null) {
                                        mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                                    }
                                }
                            }


                        }else if(page==1&&o.getResult() == null ||
                                o.getResult().getData().getGoods().size()==0){
                            mBackResultNull(o, page);
//                            mRefreshLayout.setEnableRefresh(false);
                            mRefreshLayout.setEnableAutoLoadmore(false);
                            mRefreshLayout.setEnableLoadmore(false);
                            return;
                        }



                    }
                }
                dismissDialog();
//                mineshopdata.setValue(o.getResult());

            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                loadError(isRefresh);
                KLog.a("错误信息"+errarMessage);
                finish();

            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
             startActivity(LoginVertifyActivity.class);
            }
        });
    }



    private void mBackResultNull(MineStoreEntity mineStoreEntity, int page) {
        if (page == 1 && mineStoreEntity.getResult().getData()==null) {
            //todo 隐藏列表
//            binding.rv.setVisibility(View.GONE);
//            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
//            binding.rv.setVisibility(View.VISIBLE);
//            binding.emptyView.setVisibility(View.GONE);
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


    public BindingCommand onClickGoTop =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //一键置顶
            binding.recyclerView.scrollToPosition(0);


        }
    });


}
