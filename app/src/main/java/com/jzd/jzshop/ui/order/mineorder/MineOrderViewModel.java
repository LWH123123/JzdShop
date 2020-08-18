package com.jzd.jzshop.ui.order.mineorder;

import android.app.Application;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentMineOrderBinding;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.QQUtils;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MineOrderViewModel extends ToolbarViewModel<Repository> {
    public ObservableField<MineOrderEntity.ResultBean> order = new ObservableField<>();

    public MineOrderViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public SingleLiveEvent<MineOrderEntity.ResultBean> binddata = new SingleLiveEvent<>();
    public SingleLiveEvent nofity = new SingleLiveEvent<>();

    public void initToolBar() {
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
        setIvBackWhiteIsVisible(View.VISIBLE);
        setIvBackIsVisible(View.GONE);
        setTitleText("我的订单");
    }

    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    private boolean isReSetAdapter;

    public boolean isSetAdapter() {
        return isReSetAdapter;
    }

    public void requestWord(final SmartRefreshLayout refreshLayout, String status, int page, final boolean isrefresh) {

        isShowDialog(false);
        if (this.status != null && !this.status.equals(status)) {
            isReSetAdapter = false;
        } else {
            isReSetAdapter = true;
        }
        setStatus(status);
        addSubscribe(model.postMyOrder(model.getUserToken(), status, page, 5), new ParseDisposableTokenErrorObserver<MineOrderEntity>() {
            @Override
            public void onResult(MineOrderEntity o) {
                super.onResult(o);
                dismissDialog();
                /*order.set(o.getResult());
                binddata.setValue(o.getResult());*/
                if(o!=null) {
                    if(o.getResult().getData()!=null&&o.getResult().getData().size()>0) {
                        if (page == 1) {
                            order.set(o.getResult());
                            binddata.setValue(o.getResult());
                        }
                        if(isrefresh&&refreshLayout!=null){//刷新
                            refreshLayout.finishRefresh();
                            refreshLayout.setLoadmoreFinished(false);
                        }else if(refreshLayout!=null&&page!=1){//加載更多
                            if(o.getResult()!=null&&o.getResult().getData()==null&&o.getResult().getData().size()==0){
                                refreshLayout.finishLoadmore();
                                refreshLayout.finishLoadmoreWithNoMoreData();
                                return;
                            }
                            refreshLayout.finishLoadmore();
                            order.set(o.getResult());
                            binddata.setValue(o.getResult());
                            int currentCountSize = o.getResult().getData().size();
                            if (currentCountSize < 5 ) {
                                KLog.a("关闭加载更多逻辑");
                                refreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                            }
                        }
                    }else if(page==1&&o.getResult().getData().size()==0){
//                        refreshLayout.setEnableRefresh(false);
//                        refreshLayout.setEnableAutoLoadmore(false);
//                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishLoadmoreWithNoMoreData();
                    }else {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishLoadmoreWithNoMoreData();
                    }

//                    binddata.setValue(o.getResult());
                }

            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
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

    /*取消订单*/
    public void cancleOrder(String order_id) {
        addSubscribe(model.postCancleOrder(model.getUserToken(), order_id), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });


    }


    /*确认订单*/
    public void onfirmGoods(final String order) {
        MessageDialog.showCancelAndSureDialog(min.getContext(), "确认已收到货了吗？","", R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
            @Override
            public void onCancelClickListener() {
            }

            @Override
            public void onSureClickListener() {
                addSubscribe(model.postConfirmGoods(model.getUserToken(), order), new ParseDisposableTokenErrorObserver() {
                    @Override
                    public void onResult(Object o) {
                        dismissDialog();
                        super.onResult(o);
                        nofity.call();
                    }

                    @Override
                    public void onError(String errarMessage) {
                        super.onError(errarMessage);
                        dismissDialog();
                    }

                    @Override
                    protected void onTokenError() {
                        super.onTokenError();
                        dismissDialog();
                    }

                });

            }
        });


    }

    /*删除订单*/

    public void deleteOrder(String order) {
        addSubscribe(model.postDeleteOrder(model.getUserToken(), order), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }
        });
    }

    private FragmentMineOrderBinding binding;
    private MineOrderFragment min;

    public void setBinding(FragmentMineOrderBinding binding, MineOrderFragment mineOrderFragment) {
        this.min = mineOrderFragment;
        this.binding = binding;
    }


    public void MerchantCustomerService() {
        if (QQUtils.installedApp(min.getContext(), "com.tencent.mobileqq") || QQUtils.installedApp((min.getContext()), "com.tencent.tim")) {
            QQUtils.openPersonalQQ(min.getContext(), min.getResources().getString(R.string.online_service_qq));
        } else {
            ToastUtils.showShort("本机未安装QQ应用");
            return;
        }
    }


    public void saveFlag() {
        model.saveOpenFlag("0");//保存打开标识
    }

}