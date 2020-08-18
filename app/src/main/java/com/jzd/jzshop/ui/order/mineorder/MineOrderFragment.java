package com.jzd.jzshop.ui.order.mineorder;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMineOrderBinding;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.ui.order.mineorder.adpter.MineOrderAdapter;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailAty;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SetTablayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineOrderFragment extends BaseFragment<FragmentMineOrderBinding, MineOrderViewModel> implements OnRefreshListener, OnLoadmoreListener {

    private MineOrderAdapter mineOrderAdapter;
    private MineOrderAdapter adapter;
    //从其他页面传过来的标志
    private int anInt;

    //订单页面真正请求数据用的id
    private int index;

    //页数
    private int page=1;

    private boolean isRefresh = true; //是否是刷新


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine_order;
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));
        viewModel.initToolBar();
        initMallRefresh();
        initTabItem();
        adapter = new MineOrderAdapter(viewModel);
        binding.rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void initTabItem() {
        final TabLayout mtab = binding.tab;
        SetTablayout.setIndicatorWidth(mtab, 30);
        viewModel.setBinding(binding, MineOrderFragment.this);
        if (getArguments() != null) {
            KLog.a("我是INITDATA方法()");
            anInt = getArguments().getInt(Constants.MyOrder, 0);
            if (anInt != 4) {
                mtab.addTab(mtab.newTab().setText(R.string.all));
                mtab.addTab(mtab.newTab().setText(R.string.waitPay));
                mtab.addTab(mtab.newTab().setText(R.string.waitSend));
                mtab.addTab(mtab.newTab().setText(R.string.waitRecycle));
                mtab.addTab(mtab.newTab().setText(R.string.finish));
                viewModel.setStatus(String.valueOf(anInt));
//                viewModel.requestWord(binding.mallRefreshLayout,String.valueOf(anInt),page,true);
                mtab.getTabAt(anInt + 1).select();
            } else {
                SetTablayout.setIndicatorWidth(mtab, 100);
                mtab.addTab(mtab.newTab().setText(R.string.allorder));
                mtab.addTab(mtab.newTab().setText(R.string.salesReturn));
                viewModel.setStatus(String.valueOf(anInt));
//                viewModel.requestWord(binding.mallRefreshLayout,String.valueOf(anInt),page,true);
                mtab.getTabAt(1).select();
            }
        }
        mtab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                upDateData(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        Messenger.getDefault().register(this, OrderDetailViewModel.TOKEN_ORDERDETAILVIEWMODEL_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//刷新
                if (state.equals("cancle_order")) {
//
                } else if (state.equals("delete_order")) {
                    KLog.a("删除订单", "我的标志" + state);
//
                } else if (state.equals("confirm_receipt")) {
                    if (anInt == 4) {//从退换货进来的
                        binding.tab.getTabAt(0).select();
                    } else {
                        binding.tab.getTabAt(4).select();
                    }
                } else {
                }
            }
        });
        // 确认收到换货物品
        Messenger.getDefault().register(this, OrderDetailViewModel.TOKEN_VIEWMODEL_REFUND_RECEIVE_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//关闭详情页 刷新订单列表
                if (state.equals("refund_receive")) {
                    //todo 刷新为 全部订单
                    if (anInt == 4) {//从退换货进来的
                        binding.tab.getTabAt(0).select();
                    } else {
                        binding.tab.getTabAt(4).select();
                    }

                } else {
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.a("当前加载的STATUS"+viewModel.status);
        if(adapter!=null){
            adapter.clearData();
        }
        viewModel.requestWord(binding.mallRefreshLayout,viewModel.status,page,true);
    }

    public void upDateData(int position) {
        index = position;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(adapter!=null) {
                    adapter.clearData();
                }
                page=1;
                if (index == 0) {
                    viewModel.requestWord(binding.mallRefreshLayout,null,page,true);
                    return;
                }
                if (anInt == 4 && index == 1) {
                    viewModel.requestWord(binding.mallRefreshLayout,String.valueOf(4),page,true);
                    return;
                }
                index--;
                viewModel.requestWord(binding.mallRefreshLayout,String.valueOf(index),page,true);
            }
        };
        runnable.run();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.binddata.observe(this, new Observer<MineOrderEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable final MineOrderEntity.ResultBean resultBean) {
                if(adapter==null){
                    adapter= new MineOrderAdapter(viewModel);
                }
                if (adapter.getData() == null || !viewModel.isSetAdapter()) {
                    adapter.clearData();
                    binding.rvOrder.setAdapter(adapter);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setList(resultBean.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
                adapter.setOnitemClick(new MineOrderAdapter.OnitemClick() {
                    @Override
                    public void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean) {
                        if (bean.getOrderid() != null && !TextUtils.isEmpty(bean.getOrderid())) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.ORDER_ID, bean.getOrderid());
//                            bundle.putString(Constants.ORDER_PRICE, bean.getAllprice());
                            startActivity(OrderDetailAty.class, bundle);
                        } else {
                            ToastUtils.showLong("此订单号不存在！" + bean.getOrderid());
                            return;
                        }
                    }
                });

                adapter.setonStoreClick(new MineOrderAdapter.OnitemStoreClick() {
                    @Override
                    public void onitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean) {
                        /*if (!TextUtils.isEmpty(dataBean.getMerch_id())) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.MINE_SHOP, dataBean.getMerch_id());
                            bundle.putString("title", dataBean.getMerch_name());
                            startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
                        } else {
                            return;
                        }*/
                    }
                });


            }
        });
        viewModel.nofity.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if(anInt!=4){
                    binding.tab.getTabAt(4).select();
                }
            }
        });


    }

    @Override
    public int initVariableId() {
        return BR.mineorderVM;
    }

    @Override
    public MineOrderViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineOrderViewModel.class);
    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(true);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
        binding.mallRefreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }


    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
            isRefresh=false;
            page++;
            viewModel.requestWord(binding.mallRefreshLayout,viewModel.status,page,isRefresh);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page=1;
        isRefresh=true;
        if(adapter!=null) {
            adapter.clearData();
        }
        viewModel.requestWord(binding.mallRefreshLayout,viewModel.status,page,isRefresh);
    }
}
