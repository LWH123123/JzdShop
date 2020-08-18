package com.jzd.jzshop.ui.goods;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityGoodsBinding;
import com.jzd.jzshop.databinding.PopItemActivityFlowBinding;
import com.jzd.jzshop.databinding.PopItemCouponFlowBinding;
import com.jzd.jzshop.databinding.PoplayoutParameterBinding;
import com.jzd.jzshop.databinding.PoplayoutSpecBinding;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.GoodsEntity;
import com.jzd.jzshop.utils.widget.CouponPop;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.BaseWebviewUtils;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.QQUtils;
import com.jzd.jzshop.utils.SetTablayout;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.jessyan.autosize.utils.ScreenUtils;

/**
 * 商品详情
 */

public class GoodsActivity extends BaseActivity<ActivityGoodsBinding, GoodsViewModel> implements XBanner.OnItemClickListener {
    PopupWindow popupWindow;
    int screen[];
    private List<BannEntity> bannerList;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods;
    }

    @Override
    public int initVariableId() {
        return BR.goodsVM;
    }


    @Override
    public GoodsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GoodsViewModel.class);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(false);
    }

    private String gid, openFlag,shopid,goodid;

    @Override
    public void initParam() {
        super.initParam();
        getIntentData();
        screen = ScreenUtils.getScreenSize(this);

    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gid = bundle.getString(Constants.GOODS_KEY_GID);
            shopid=bundle.getString(Constants.SHOP_GOODS);
            KLog.a("商品ID:===>>>", gid);
            openFlag = bundle.getString(Constants.GOODS_OPEN_FLAG);
            goodid= bundle.getString(Constants.GOODLIST_GOODS);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        viewModel.badgenumber.notifyChange();
    }



    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.network.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.rvGood.setVisibility(View.VISIBLE);
                binding.agSmarttab.setVisibility(View.VISIBLE);
                binding.agNestedscroll.setVisibility(View.VISIBLE);
                binding.agBottom.setVisibility(View.VISIBLE);
                binding.noWork.setVisibility(View.GONE);
            }
        });
        binding.agXbanner.setOnItemClickListener(this);

        viewModel.uc.updateCouponText.observe(this, new Observer<List<GoodsEntity.ResultBean.CouponDataBean>>() {
            @Override
            public void onChanged(@Nullable List<GoodsEntity.ResultBean.CouponDataBean> beans) {
                binding.agCouponLayout.setAdapter(new TagAdapter<GoodsEntity.ResultBean.CouponDataBean>(beans) {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodsEntity.ResultBean.CouponDataBean o) {
                        PopItemCouponFlowBinding flowBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.agCouponLayout.getContext()),
                                R.layout.pop_item_coupon_flow, binding.agCouponLayout, false);
                        flowBinding.setCouponIVM(o);
                        return flowBinding.getRoot();
                    }
                });
              /*
                for (GoodsEntity.ResultBean.CouponDataBean couponDataBean : beans) {
                    PopItemCouponFlowBinding flowBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.agCouponLayout.getContext()),
                            R.layout.pop_item_coupon_flow, binding.agCouponLayout, false);
                    flowBinding.setCouponIVM(couponDataBean);
                    binding.agCouponLayout.addView(flowBinding.getRoot());
                }*/
            }
        });
        viewModel.uc.updateActivityText.observe(this, new Observer<List<GoodsEntity.ResultBean.ActivityBean>>() {
            @Override
            public void onChanged(@Nullable List<GoodsEntity.ResultBean.ActivityBean> beans) {
                if(beans.size()==0){
                    binding.groupActivity.setVisibility(View.GONE);
                    return;
                }
                if (beans != null && beans.size() > 0) {
                    ArrayList<GoodsEntity.ResultBean.ActivityBean.DataBean> dataBeans = new ArrayList<>();
                    for (GoodsEntity.ResultBean.ActivityBean activityBean : beans) {
                        for (GoodsEntity.ResultBean.ActivityBean.DataBean dataBean : activityBean.getData()) {
                            dataBean.setId(activityBean.getId());
                            dataBeans.add(dataBean);
                        }
                    }
                        binding.agActivityLayout.setAdapter(new TagAdapter<GoodsEntity.ResultBean.ActivityBean.DataBean>(dataBeans) {
                            @Override
                            public View getView(FlowLayout parent, int position, GoodsEntity.ResultBean.ActivityBean.DataBean o) {
                                PopItemActivityFlowBinding flowBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.agActivityLayout.getContext()),
                                        R.layout.pop_item_activity_flow, binding.agActivityLayout, false);
                                flowBinding.setActivityIVM(o);
                                KLog.a("当前的活动详情有===>>>"+o.getActivityText(o.getId(),viewModel.unit));
                                flowBinding.piafText.setText(o.getActivityText(o.getId(), viewModel.unit));
                                return flowBinding.getRoot();
                            }
                        });
                }
            }
        });

        viewModel.uc.updateSpecText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                if(text.length()!=0) {
                    binding.agSpec.setText(text);
                }else{
                    binding.agSpec.setText("暂无规格");
                    binding.agSpec.setGravity(Gravity.RIGHT);
                }
            }
        });
        viewModel.uc.updateParamText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                if(text.length()!=0) {
                    binding.agParam.setText(text);
                }else {
                    binding.agParam.setText("暂无参数");
                    binding.agParam.setGravity(Gravity.RIGHT);
                }
            }
        });
        viewModel.uc.xbannerRefreshing.observe(this, new Observer<List<BannEntity>>() {
            @Override
            public void onChanged(@Nullable List<BannEntity> bean) {
                bannerList = bean;
                binding.agXbanner.setBannerData(bean);
                binding.agXbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        BannEntity ban = (BannEntity) model;
                        Glide.with(getApplication()).load(ban.getXBannerUrl()).into((ImageView) view);
                    }
                });
                binding.agXbanner.startAutoPlay();
            }
        });
        viewModel.uc.webviewRefreshing.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String html) {
                initWebView();
                //加载商品详情图片
                if (!TextUtils.isEmpty(html)) {
                    String htmlData = BaseWebviewUtils.getHtmlData(html);
                    binding.agGoodsDetails.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
//                    binding.agGoodsDetails.addJavascriptInterface(new JavaScriptInterface(AMapUtils.getContext()), "imagelistner");//这个是给图片设置点击监听的
                }
            }
        });
        viewModel.uc.showCouponPopwindow.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String type) {
                CouponPop gouponPop = new CouponPop(GoodsActivity.this, viewModel.pg_coupon_ob);
                KLog.a("优惠券列表===>>>"+viewModel.pg_coupon_ob.size());
                if(viewModel.pg_coupon_ob.size()==0){
                    gouponPop.setNoData();
                }
                gouponPop.showAtLocation(binding.agShop, Gravity.BOTTOM, 0, 0);
            }
        });
        viewModel.uc.showSpecPopwindow.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String type) {
                showPopwindow(type);
            }
        });
        viewModel.uc.showParamPopwindow.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String type) {
                showPopwindow(type);
            }
        });
        viewModel.uc.backTop.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.agNestedscroll.fullScroll(NestedScrollView.FOCUS_UP);
            }
        });
        viewModel.uc.closePopwindow.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (popupWindow!=null&&popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        });

        viewModel.uc.lovegood.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Drawable goodlove = getResources().getDrawable(R.mipmap.goodlove);
                Drawable love = getResources().getDrawable(R.mipmap.love);
                if (integer.intValue() == 0) {
                    binding.tvCollect.setText("收藏");
                    love.setBounds(0, 22, love.getMinimumWidth(), love.getMinimumHeight() + 24);
                    binding.imCollect.setImageDrawable(love);
                } else {
                    goodlove.setBounds(0, 15, goodlove.getMinimumWidth(), goodlove.getMinimumHeight() + 14);
                    binding.imCollect.setImageDrawable(goodlove);
                    binding.tvCollect.setText("已收藏");
                }
            }
        });
        viewModel.uc.qqservice.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                MerchantCustomerService();
            }
        });


    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initWebView() {
        binding.agGoodsDetails.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.agGoodsDetails.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName('img'); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "var img = objs[i];   " +
                        "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                        "}" +
                        "})()");
                // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
                binding.agGoodsDetails.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].οnclick=function()  " +
                        "    {  "
                        + "        window.imagelistner.openImage(this.src);  " +
                        "    }  " +
                        "}" +
                        "})()");
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
        });

        WebSettings settings = binding.agGoodsDetails.getSettings();
        // 是否支持Javascript，默认值false
        settings.setJavaScriptEnabled(true);
        //设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录。
        settings.setAppCachePath(Utils.getContext().getCacheDir().getAbsolutePath());
        //设置是否启用缓存，不过需要设置好缓存路径，默认false
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true); //设置自适应屏幕
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextZoom(100); //WebView里的字体就不会随系统字体大小设置发生变化,图片超出手机屏幕

//        settings.setBlockNetworkImage(true);  //先阻塞加载图片 先加载文本
//        settings.setBlockNetworkImage(false);     //页面加载好以后，在放开图片
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);      //提高渲染等级
    }


    /**
     * android webview加载html图片自适应手机屏幕大小&点击查看大图 - smileiam的专栏 - CSDN博客
     * https://blog.csdn.net/smileiam/article/details/72123227
     */
    public static class JavaScriptInterface {

        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }


        //点击图片回调方法
        //必须添加注解,否则无法响应
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void openImage(String img) {
            KLog.i("TAG", "响应点击事件!");
            ToastUtils.showShort(img);
//            Intent intent = new Intent();
//            intent.putExtra("image", img);
//            intent.setClass(context, BigImageActivity.class);//BigImageActivity查看大图的类，自己定义就好
//            context.startActivity(intent);
        }

    }

    @Override
    public void initData() {
        binding.rvGood.setVisibility(View.GONE);
        //要忘记了, 在当前activity onCreate中设置 取消padding,  因为这个padding 我们用代码实现了,不需要系统帮我
//        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        viewModel.setGid(gid, openFlag,shopid,goodid);
        viewModel.setBinding(binding);
        initTab();
        binding.agNestedscroll.smoothScrollTo(0, 0);
        binding.agNestedscroll.setScrollListener(binding.ftb);

        TextPaint paint = binding.agTitle.getPaint();
        TextPaint paint1 = binding.takegood.getPaint();
        TextPaint paint2 = binding.storename.getPaint();
        paint.setFakeBoldText(true);
        paint1.setFakeBoldText(true);
        paint2.setFakeBoldText(true);
        binding.agProductprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  //商品原价
        int netState = NetworkUtils.getNetWorkState(GoodsActivity.this);
        if (netState == -1) {
            binding.rvGood.setVisibility(View.VISIBLE);
            binding.agSmarttab.setVisibility(View.GONE);
            binding.agNestedscroll.setVisibility(View.GONE);
            binding.agBottom.setVisibility(View.GONE);
            binding.noWork.setVisibility(View.VISIBLE);
            return;
        }
        viewModel.requestNetWork(gid);

        //一键置顶

    }

    public void initTab() {
        SetTablayout.setIndicatorWidth(binding.agSmarttab, 60);
        binding.agSmarttab.addTab(binding.agSmarttab.newTab().setText("商品"));
        binding.agSmarttab.addTab(binding.agSmarttab.newTab().setText("详情"));
        binding.agSmarttab.addTab(binding.agSmarttab.newTab().setText("评价"));
        binding.agSmarttab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                binding.agNestedscroll.post(new Runnable() {
                    @Override
                    public void run() {
                        switch (tab.getPosition()) {
                            case 0:
                                binding.agNestedscroll.fullScroll(NestedScrollView.FOCUS_UP);
                                break;
                            case 1:
                                binding.agNestedscroll.smoothScrollTo(0, binding.agGoodsDetails.getTop());
                                break;
                            case 2:
                                binding.agNestedscroll.smoothScrollTo(0, binding.agCommentAll.getTop());
                                break;
                        }
                    }
                });
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
    }

    /**
     * 显示popupWindow
     */
    public View initPopwindowLayout(String type) {
        //加载弹出框的布局
        if (type.equals(Constants.GOODS_KEY_PARAM)) {
            return initParamLayout();
        } else if (type.equals(Constants.GOODS_KEY_SPEC) ||
                type.equals(Constants.GOODS_KEY_BUYNOW) ||
                type.equals(Constants.GOODS_KEY_SHOPPING_CAR)) {
            return initSpecLayout(type);
        }
        return null;
    }


    //参数列表
    private View initParamLayout() {
        final PoplayoutParameterBinding popBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.poplayout_parameter, null, false);
        popBinding.setParamVM(viewModel);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        };
        popBinding.ppOkBtn.setOnClickListener(listener);
        popBinding.ppClose.setOnClickListener(listener);
        return popBinding.getRoot();
    }

    //规格
    private View initSpecLayout(String type) {
        final PoplayoutSpecBinding popBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.poplayout_spec, null, false);
        popBinding.setSpecVM(viewModel);
        popBinding.setSpecEntity(viewModel.specEntity.get());
        PopGoodsSpecAdapter adapter = new PopGoodsSpecAdapter();
        popBinding.setAdapter(adapter);
        adapter.setOnitemClick(new PopGoodsSpecAdapter.OnitemClick() {
            @Override
            public void OnitemClick() {
                viewModel.updateShop();
            }
        });
    popBinding.pgClosePopwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        /***========================页面逻辑处理===*/
        if (type.equals(Constants.GOODS_KEY_SPEC)) {
            popBinding.psAddShoppingcar.setVisibility(View.VISIBLE);
            popBinding.psBuynow.setVisibility(View.VISIBLE);
        } else if (type.equals(Constants.GOODS_KEY_BUYNOW)) {
            popBinding.psAddShoppingcar.setVisibility(View.GONE);
            popBinding.psBuynow.setText("确定");
        } else if (type.equals(Constants.GOODS_KEY_SHOPPING_CAR)) {
            popBinding.psAddShoppingcar.setVisibility(View.VISIBLE);
            popBinding.psAddShoppingcar.setText("确定");
            popBinding.psAddShoppingcar.setBackgroundColor(getResources().getColor(R.color.colorred));
            popBinding.psBuynow.setVisibility(View.GONE);
        }
        /***========================页面逻辑处理===*/
        return popBinding.getRoot();
    }

    private int marginHeight(final View root) {
        // 第三种获取宽高的方式
        ViewTreeObserver vtb1 = root.getViewTreeObserver();
        vtb1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                KLog.d("使用OnGlobalLayoutListener 获取宽高：width:" + root.getHeight());
                root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (root.getHeight() > screen[1] * .85) {
                    popupWindow.setHeight((int) (screen[1] * .85));
                }
            }
        });
        return 0;
    }

    public void showPopwindow(String type) {
        View root = initPopwindowLayout(type);
//        marginHeight(root);
//        popupWindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screen[1]*.85));
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
                popupWindow.showAtLocation(binding.agShop, Gravity.BOTTOM, 0, 0);
            } else {
                popupWindow.showAsDropDown(binding.agShop);
            }
        } else {
            popupWindow.showAtLocation(binding.agShop, Gravity.BOTTOM, 0, 0);
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
//        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //不添加这个偶尔会出现 背景重叠的问题
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    private void MerchantCustomerService() {
        if (QQUtils.installedApp(mContext, "com.tencent.mobileqq") || QQUtils.installedApp((mContext), "com.tencent.tim")) {
            QQUtils.openPersonalQQ(GoodsActivity.this, getResources().getString(R.string.online_service_qq));
        } else {
            ToastUtils.showShort("本机未安装QQ应用");
            return;
        }
        /*String url3521 = "mqqwpa://im/chat?chat_type=wpa&uin=3126614869";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url3521)));*/
    }

    @Override
    public void onItemClick(XBanner banner, Object model, View view, int position) {
        BannEntity bannEntity = (BannEntity) model;
        AppIdentityJumpUtils.previewLargePicGoods(GoodsActivity.this,bannerList,position);
    }


}