package com.jzd.jzshop.ui.shoppingcar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityShoppingcarBinding;
import com.jzd.jzshop.utils.widget.CouponPop;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.website.WebSiteActivity;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class ShoppingCarActivity extends BaseActivity<ActivityShoppingcarBinding, ShoppingCarViewModel> {
    private NavigationController build;
    private int anInt;
    private int anInt1;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shoppingcar;
    }

    @Override
    public int initVariableId() {
        return BR.shopcarVM;
    }

    @Override
    public ShoppingCarViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ShoppingCarViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            anInt = extras.getInt(Constants.GOODS_CAR);
        }*/
        initMallRefresh();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        viewModel.setBinding(binding, this);
        binding.pagerBottomTab.setFocusable(true);
        binding.pagerBottomTab.setFocusableInTouchMode(true);
        binding.pagerBottomTab.requestFocus();
        initBottomTab();
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.getcoupon.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                CouponPop couponPop = new CouponPop(ShoppingCarActivity.this, viewModel.coupon_ob);
                if(viewModel.coupon_ob.size()==0){
                    couponPop.setNoData();
                }
                couponPop.showAtLocation(binding.pagerBottomTab, Gravity.BOTTOM, 0, 0);
            }
        });

        viewModel.uc.nodata.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                int i = integer.intValue();
                if (i == 0) {
                    binding.mallRefreshLayout.setVisibility(View.GONE);
                    binding.shopdata.setVisibility(View.GONE);
                    binding.imageView26.setVisibility(View.VISIBLE);
                    binding.text45.setVisibility(View.VISIBLE);
                    binding.goshop.setVisibility(View.VISIBLE);
                    binding.checkBox.setChecked(false);
                    binding.checkBox.setEnabled(false);
                    binding.manage.setClickable(false);
                    return;
                }
                binding.shopdata.setVisibility(View.VISIBLE);
                binding.imageView26.setVisibility(View.GONE);
                binding.text45.setVisibility(View.GONE);
                binding.goshop.setVisibility(View.GONE);

            }
        });


        viewModel.uc.bagenumber.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                build.setMessageNumber(3, integer.intValue());
            }
        });
        //更新导航栏的数据
        viewModel.uc.message.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                //Sp修改购物车的数据
                if(build!=null){
                     if(integer==1){
                       anInt1++;
                       build.setMessageNumber(3,anInt1);
                       SPUtils.getInstance().put(Constants.MINE_GOOD,anInt1);
                   }else {
                         if(anInt1!=0) {
                             anInt1--;
                             build.setMessageNumber(3, anInt1);
                             SPUtils.getInstance().put(Constants.MINE_GOOD,anInt1);
                         }
                     }
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO  之前商品详情跳转购物车  购物车添加返回按钮  现在不用了
        /*if (anInt == 3) {
            binding.backleft.setVisibility(View.VISIBLE);
            binding.backleft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            binding.backleft.setVisibility(View.GONE);
        }*/
        viewModel.requestNetWork();
        viewModel.entrys.set(false);
        viewModel.entrys.notifyChange();
        anInt1 = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        if (anInt1 != 0) {
            build.setMessageNumber(3, anInt1);
        }
        build.setSelect(3);
    }



    public void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).init(binding.pagerBottomTab, new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                startOtherActivity(index);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    private void startOtherActivity(int index) {
        switch (index) {
            case 0:
                startActivity(HomePageActivity.class);
                break;
            case 1:
                startActivity(CategoryActivity.class);
                break;
            case 2:
                startActivity(WebSiteActivity.class);
                break;
            case 3:
                break;
            case 4:
                startActivity(MyPageActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }




    private void initMallRefresh() {

//        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
//        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
//        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
//        binding.mallRefreshLayout.setOnRefreshListener(this);
//        mallRefreshLayout.setOnLoadmoreListener()
        binding.mallRefreshLayout.setEnableLoadmore(false);

    }

    /*    *//*
     * 优惠明细
     * *//*
    @SuppressLint("ClickableViewAccessibility")
    public void showPopCouponDetail(CouponDetailEntity couponDetailEntity){
        LinearLayout mConstrain = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.pop_discount_money,null);
        PopDiscountMoneyBinding bind = DataBindingUtil.bind(mConstrain);
        bind.setShopCarPopVM(viewModel);
        final PopupWindow popupWindow = new PopupWindow(mConstrain, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        TextView total = mConstrain.findViewById(R.id.total);
        TextView coupon = mConstrain.findViewById(R.id.coupon);
        TextView allconpon = mConstrain.findViewById(R.id.allconpon);
        TextView money = mConstrain.findViewById(R.id.money);
        total.setText(couponDetailEntity.getTotal());
        coupon.setText(couponDetailEntity.getCoupon());
        allconpon.setText(couponDetailEntity.getAllcoupon());
        money.setText(couponDetailEntity.getMoney());
//        binding.arrows.setImageDrawable(getResources().getDrawable(R.mipmap.up));
        popupWindow.setAnimationStyle(R.style.myPopwindowAnimStyle);
        WindowManager.LayoutParams params=(this).getWindow().getAttributes();
        params.alpha=0.9f;
        (this).getWindow().setAttributes(params);
        (this).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                binding.arrows.setImageDrawable(getResources().getDrawable(R.mipmap.down));
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });

        binding.pagerBottomTab.setBackgroundColor(getResources().getColor(R.color.white));

        mConstrain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                binding.arrows.setImageDrawable(getResources().getDrawable(R.mipmap.down));
                WindowManager.LayoutParams params =getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                return false;
            }
        });
        int height = binding.constrain.getHeight();
        int height1 = binding.pagerBottomTab.getHeight();

        popupWindow.showAtLocation(binding.constrain, Gravity.BOTTOM,0,height+height1);

    }*/


}
