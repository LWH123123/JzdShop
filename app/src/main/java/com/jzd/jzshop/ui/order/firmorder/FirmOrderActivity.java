package com.jzd.jzshop.ui.order.firmorder;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityFirmOrderBinding;
import com.jzd.jzshop.databinding.FirmorderCouponBinding;
import com.jzd.jzshop.entity.FirmOrderEntity;
import com.jzd.jzshop.entity.OrderDataEntity;
import com.jzd.jzshop.ui.mine.address.CompileAddressFragment;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressViewModel;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DecimalFormat;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * 确认订单
 */
public class FirmOrderActivity extends BaseActivity<ActivityFirmOrderBinding, FirmOrderViewModel> implements OnRefreshListener {
    private static final String TAG = FirmOrderActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_firm_order;
    }

    @Override
    public int initVariableId() {
        return com.jzd.jzshop.BR.firmOrderVM;
    }

    @Override
    public FirmOrderViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(FirmOrderViewModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        return;
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this,true);
        viewModel.setContext(binding, FirmOrderActivity.this);
        viewModel.initToolbar();
        getIntentData();
        viewModel.requestNetWork(binding.mallRefreshLayout);
        initMallRefresh(); //配置刷新
        Messenger.getDefault().register(this, ReceiptAddressViewModel.TOKEN_RECEIPTADDRESSVIEWMODEL_REFRESH, Bundle.class, new BindingConsumer<Bundle>() {
            @Override
            public void call(Bundle bundle) {//选择地址
                Log.d(TAG, "选择地址成功");
                int type = bundle.getInt(Constants.TYPE);
                String address = bundle.getString(Constants.ADDRESS_SELECR);
                viewModel.setRequestInfo(type, address);
            }
        });
        // TODO: 2019/12/16
//        binding.setAdapter(new CustomBindingRecyclerViewAdapter());
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int type = bundle.getInt(Constants.TYPE);
            String address = bundle.getString(Constants.ADDRESS_SELECR);
            KLog.a("类型", type);
            viewModel.setRequestInfo(type, address);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mallRefreshLayout.autoRefresh();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.updataAddress.observe(this, new Observer<FirmOrderEntity.ResultBean.AddressBean>() {
            @Override
            public void onChanged(@Nullable FirmOrderEntity.ResultBean.AddressBean bean) {
                if(bean!=null){
                    if(bean.getAddress()!=null){
                        binding.afoAddAddress.setVisibility(View.GONE);
                        binding.afoAddressGroup.setVisibility(View.VISIBLE);
                    }else {
                        binding.afoAddAddress.setVisibility(View.VISIBLE);
                        binding.afoAddressGroup.setVisibility(View.GONE);
                        binding.setaddress.setClickable(false);
                        binding.afoAddAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("compile", "添加地址");
                                startContainerActivity(CompileAddressFragment.class.getCanonicalName(), bundle);
                            }
                        });
                    }
                }
            }
        });
        /*viewModel.uc.updataMoneyText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.afoMoney.setText(viewModel.updataMoney());
            }
        });*/

        viewModel.uc.updateData.observe(this, new Observer<OrderDataEntity>() {
            @Override
            public void onChanged(@Nullable OrderDataEntity orderDataEntity) {
                if (orderDataEntity != null) {
                    if (orderDataEntity.getGoodnumber() == 0) {
                    } else {
                        binding.couponnumber.setText(orderDataEntity.getCouponnumber() + "张优惠券");
                    }
                    DecimalFormat fnums = new DecimalFormat("##0.00");
                    String formats = fnums.format(orderDataEntity.getTatol());
                    binding.total.setText("￥" + formats);
                    binding.freights.setText("+￥" + orderDataEntity.getFreight());
                    binding.shopnumber.setText(String.valueOf(orderDataEntity.getGoodnumber()));
                }
            }
        });

        viewModel.uc.coupon.observe(FirmOrderActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                initCoupon(s);
            }
        });
        binding.linRootView.setFocusable(true);
        binding.linRootView.setFocusableInTouchMode(true);
    }


    //初始化当前订单的优惠券信息
    private void initCoupon(String s) {
        FirmorderCouponBinding inflate = DataBindingUtil.inflate(getLayoutInflater(), R.layout.firmorder_coupon, null, false);
        inflate.setFirmcouponVM(viewModel);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        };
        inflate.ppClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewModel.unUserCoupon();
                popupWindow.dismiss();
            }
        });
        inflate.unuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.isuser.set(0);
                viewModel.unUserCoupon();
                popupWindow.dismiss();
            }
        });

        //点击优惠券列表中的确认使用按钮
        inflate.usecoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用相应的优惠券
                viewModel.useCoupon();
                popupWindow.dismiss();
            }
        });
        if (s.equals("0")) {
            inflate.ppParamRecycler.setVisibility(View.GONE);
            inflate.nocoupon.setVisibility(View.VISIBLE);
        }
        showPopwindows(inflate.getRoot());
    }

    private PopupWindow popupWindow;

    private void showPopwindows(View root) {
        popupWindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.myPopwindowAnimStyle);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
//                ((Activity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
//        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        if (Build.VERSION.SDK_INT < 24) {
            //nexus5 不显示
            if (Build.MODEL.equals("Nexus 5") || Build.VERSION.SDK_INT == 23) {
                popupWindow.showAtLocation(binding.afoBottomLayout, Gravity.BOTTOM, 0, 0);
            } else {
                popupWindow.showAsDropDown(binding.afoBottomLayout);
            }
        } else {
            popupWindow.showAtLocation(binding.afoBottomLayout, Gravity.BOTTOM, 0, 0);
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        //确认订单不需要加载更多
        /*binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）*/

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
//        mallRefreshLayout.setOnLoadmoreListener()
        binding.mallRefreshLayout.setEnableLoadmore(false);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestNetWork(binding.mallRefreshLayout);
        getIntentData();
        binding.afoRecycler.clearFocus(); //刷新抢占焦点导致闪退  java.lang.IllegalArgumentException: parameter must be a descendant of this view
    }

}
