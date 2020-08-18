package com.jzd.jzshop.ui.mine.address;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentReceiptAddressBinding;
import com.jzd.jzshop.entity.AddressListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ReceiptAddressViewModel extends ToolbarViewModel<Repository> {

    public static final String TOKEN_RECEIPTADDRESSVIEWMODEL_REFRESH = "token_receiptaddressviewmodel_refresh";
    // --------------刷新字段
    public int page = 1;
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout smartRefreshLayout;
    private int mPage;
    private boolean mIsRefresh;
    private List<AddressListEntity.ResultBean.DataBean > totalList = new ArrayList();




    // --------------刷新字段

    public ObservableField<Integer> sign = new ObservableField<>();
    public ObservableList<ReceiptAddressItemViewModel> receiptlist = new ObservableArrayList<>();
    public ItemBinding<ReceiptAddressItemViewModel> receipt = ItemBinding.of(com.jzd.jzshop.BR.receiptItemVM, R.layout.item_address);


    public ReceiptAddressViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    @Override
    public void onResume() {
//        requestWork(smartRefreshLayout);
    }

    public void initToolbar() {
        setRightIconVisible(View.GONE);
        setRightTextVisible(View.VISIBLE);
        setTitleText("收货地址");
    }



    public void requestWork(final SmartRefreshLayout refreshLayout, final int page, final boolean isRefresh) {
        smartRefreshLayout = refreshLayout;
        this.mPage = page;
        this.mIsRefresh = isRefresh;
        if (receiptlist.size() != 0) {
            receiptlist.clear();
        }

        addSubscribe(model.postManageAddress(model.getUserToken(), page,pagesize), new ParseDisposableTokenErrorObserver<AddressListEntity>() {
            @Override
            public void onResult(AddressListEntity o) {
                isShowDialog(false);
                dismissDialog();
                List<AddressListEntity.ResultBean.DataBean> dataList = new ArrayList();
                List<AddressListEntity.ResultBean.DataBean> data = o.getResult().getData();
                if(data!=null&&data.size()!=0){
                    binding.mallRefreshLayout.setVisibility(View.VISIBLE);
                    binding.noaddress.setVisibility(View.GONE);
                }else {
                    binding.mallRefreshLayout.setVisibility(View.GONE);
                    binding.noaddress.setVisibility(View.VISIBLE);
                    return;
                }
                Log.i("地址列表", "onResult:  数据长度：" + data.size());
                for (AddressListEntity.ResultBean.DataBean reslist : data) {
                    dataList.add(reslist);
                    ReceiptAddressItemViewModel receipt = new ReceiptAddressItemViewModel(ReceiptAddressViewModel.this, reslist, smartRefreshLayout);
                        receiptlist.add(receipt);
                }

                if (isRefresh && refreshLayout!=null){ //刷新
                    if (receiptlist.size() == 0) {
                        return;
                    }
                    if (page>1){
                        receiptlist.clear();
                    }
                    refreshLayout.finishRefresh();
                }else if (refreshLayout!=null){  //加载更多
                    if (dataList!=null && dataList.size()==0){
                        totalList.clear();
                        refreshLayout.finishLoadmoreWithNoMoreData();
                    }
                    totalList.addAll(dataList);
                    receiptlist.clear();
                    for (AddressListEntity.ResultBean.DataBean vmData: totalList){
                        receiptlist.add(new ReceiptAddressItemViewModel(ReceiptAddressViewModel.this, vmData,smartRefreshLayout));
                    }
                    refreshLayout.finishLoadmore();
                }
                int currentCountSize = receiptlist.size();
                if (currentCountSize < 10 || currentCountSize == 0) {
                    if (refreshLayout != null) {
                        refreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                        refreshLayout.setLoadmoreFinished(false);
                    }
                }

            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                Log.i("地址列表", "onError: " + errarMessage);
                loadError(isRefresh,refreshLayout);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
            }
        });
    }


    /**
     * 网络请求，加载失败
     */
    public void loadError(boolean isRefresh, SmartRefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            if (isRefresh) {
                refreshLayout.finishRefresh(false);  //结束刷新（刷新失败）
                refreshLayout.setLoadmoreFinished(false);
            } else {
                refreshLayout.finishLoadmore(false); //结束加载（加载失败）
            }
        }
    }



    public void requestSetDefaultAddress(final String add_id, final SmartRefreshLayout refreshLayout) {
        showDialog();
        addSubscribe(model.postSetDefaultAddress(model.getUserToken(), add_id), new ParseDisposableObserver() {
            @Override
            public void onResult(Object o) {
                dismissDialog();
                ReceiptAddressItemViewModel address = null;
                for (ReceiptAddressItemViewModel receiptAddressItemViewModel : receiptlist) {
                    String addr_ids = receiptAddressItemViewModel.entity.get().getAddr_id();
                    if (!add_id.equals(addr_ids)) {
                        //地址刷新数据的时候  出现慢的问题
                        address = receiptAddressItemViewModel;
                        address.entity.get().setIsdefault(0);
                        address.entity.notifyChange();
                    }
                }
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                refreshLayout.finishRefresh();
            }
        });
    }


    public BindingCommand onAddClickAddress = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("compile", "新增收货地址");
            startContainerActivity(CompileAddressFragment.class.getCanonicalName(), bundle);
        }
    });





    public void deleteAddress(String addr_id, final SmartRefreshLayout refreshLayout) {
        MessageDialog.showCancelAndSureDialog(context, "确认删除该地址吗？", "",
                R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                    @Override
                    public void onCancelClickListener() {
                    }

                    @Override
                    public void onSureClickListener() {
                        addSubscribe(model.postDeleteAddress(model.getUserToken(), addr_id), new ParseDisposableObserver<BaseResponse>() {
                            @Override
                            public void onResult(BaseResponse o) {
                                dismissDialog();
                                requestWork(refreshLayout, page, mIsRefresh);
                            }

                            @Override
                            public void onError(String errarMessage) {
                                dismissDialog();
                                smartRefreshLayout.finishRefresh();
                            }
                        });
                    }
                });






    }

    public void selectAddress(String addr_id) {
        Bundle bundle = new Bundle();
        if (sign != null && sign.get() != null) {
            if (!TextUtils.isEmpty(addr_id)) {
                model.saveAddress(addr_id);
                bundle.putString(Constants.ADDRESS_SELECR, addr_id);
                bundle.putInt(Constants.TYPE, sign.get());
//                startActivity(FirmOrderActivity.class, bundle);
                Messenger.getDefault().send(bundle, ReceiptAddressViewModel.TOKEN_RECEIPTADDRESSVIEWMODEL_REFRESH); //发送通知消息
                finish();
            }
        } else {
            return;
        }
    }

    private FragmentReceiptAddressBinding binding;
    private Context context;
    public void setBind(FragmentReceiptAddressBinding binding, Context context) {
        this.binding=binding;
        this.context=context;
    }
}
