package com.jzd.jzshop.ui.home.news;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityHomeNewBinding;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.HomeBannerEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeGoodsEntity;
import com.jzd.jzshop.entity.HomeMenuEntity;
import com.jzd.jzshop.entity.HomeNoticeEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.entity.ShopSpecialEntity;
import com.jzd.jzshop.jpush.ExampleUtil;
import com.jzd.jzshop.jpush.JpushBean;
import com.jzd.jzshop.jpush.NotifyClickReceiver;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListFragment;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.message.MessageCenterActivity;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.ui.website.WebSiteActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.notification.NotificationsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author LXB
 * @description:
 * @date :2020/1/4 15:45
 */
public class HomePageActivity extends BaseActivity<ActivityHomeNewBinding, HomePageViewModel> implements OnRefreshListener,
        View.OnClickListener, OnLoadmoreListener {
    private NavigationController build;
    private HomePagerRecycleAdapter homeMenuAdapter;    //menu
    private int page_message = 1;
    private int page = 1;   //分页页码
    private int pagesize = 20;   //每页的条数
    private boolean isRefresh = true; //是否是刷新
    //for receive customer msg from jpush server---------------start
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.jzd.jzshop.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    //for receive customer msg from jpush server---------------end
    public static final String CLICK_NOTIFY_ACTION = "com.jzd.jzshop.RECEIVER.CLICK.NOTIFY";
    private Notification notification;
    private HomeGoodsAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_home_new;
    }

    @Override
    public int initVariableId() {
        return BR.homeVM;
    }

    @Override
    public HomePageViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomePageViewModel.class);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        KLog.a("购物车", anInt);
        build.setMessageNumber(3, anInt);
        build.setSelect(0);
//        String appVersionCode = SystemUtils.getVersion(mContext);
//        viewModel.toAppUpdate(appVersionCode);
        mJpushLogcat();       //jpush 推送日志
        requestPermissions();  //悬浮窗权限
        viewModel.requestMessageData(page_message);//消息实时变化

    }

    private void requestPermissions() {

    }

    private void mJpushLogcat() {
        String appKey = ExampleUtil.getAppKey(getApplicationContext());
        if (null == appKey) appKey = "AppKey异常";
        String udid = ExampleUtil.getImei(getApplicationContext(), "");
        if (null != udid) udid = "udid 空";
        String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        if (!rid.isEmpty()) {
            Log.d(TAG, "RegId:" + rid);
        } else {
            Log.d(TAG, "Get registration fail, JPush init failed!");
        }
        Log.e(TAG, "<<Jpush 推送日志>>：" +
                "\n AppKey: " + appKey + "\n IMEI: " + udid
                + "\n deviceId:" + deviceId + "\n RegId:" + rid);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        //----------------------V2.3 以前，即商品组未做分页前使用----------------lxb 标记---------------
/*      initRecycleView();  a//初始化RecycleView
        mFromCacheHomeData();
        viewModel.requestHomeDataNetWork(binding.mallRefreshLayout);*/
        //----------------------V2.3 以前，即商品组未做分页前使用----------------lxb 标记---------------
        viewModel.requestHomeShopData(binding.mallRefreshLayout, page, pagesize, isRefresh, binding); //请求商品组 分页数据
        initMallRefresh(); //配置刷新
        initBottomTab();
        mKeywordSearch();
        registerMessageReceiver();  // used for receive msg
        viewModel.requestMessageData(page_message);//获取消息总数
        //刷新消息总数
        Messenger.getDefault().register(this, HomePageViewModel.TOKEN_VIEWMODEL_HOME_MESSAGENUMBER_REFRESH,
                String.class, new BindingConsumer<String>() {
                    @Override
                    public void call(String state) {// 刷新消息状态
                        if (state.equals("message_num")) {
                            viewModel.requestMessageData(page);
                        } else {
                        }
                    }
                });
    }

    /**
     * 初始化RecycleView数据
     *
     * @param recyclerView
     */
    private void initRecycleView(RecyclerView recyclerView) {
        homeMenuAdapter = new HomePagerRecycleAdapter(mContext, viewModel);
        recyclerView.setAdapter(homeMenuAdapter);
        recyclerView.setLayoutManager(new CustomStaggerGrildLayoutManger(mContext, 2, StaggeredGridLayoutManager.VERTICAL));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false); //避免recycleview重新创建ViewHolder
        viewModel.setBinding(this, binding, homeMenuAdapter);
    }

    /**
     * 从缓冲取 首页数据
     */
    private void mFromCacheHomeData() {
        ArrayList<BannEntity> bannerData = (ArrayList<BannEntity>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_BANNER_DATA);
        ArrayList<HomeBannerEntity> bannerList = new ArrayList<>();
        bannerList.add(new HomeBannerEntity(bannerData));
        ArrayList<HomeMenuEntity> homeMenuEntities = (ArrayList<HomeMenuEntity>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_MENU_DATA);
        ArrayList<HomeEntity.ResultBean.DataBeanX> homeShopEntities = (ArrayList<HomeEntity.ResultBean.DataBeanX>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_SHOP_DATA);
        ArrayList<HomeEntity.ResultBean.DataBeanX> homeJunZhiQEntities = (ArrayList<HomeEntity.ResultBean.DataBeanX>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_JUNZHIQ_DATA);
        ArrayList<ShopSpecialEntity> homeshopSpecialEntities = (ArrayList<ShopSpecialEntity>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_SHOPSPECIAL_DATA);
        ArrayList<HomeNoticeEntity> homeNoticeEntities = (ArrayList<HomeNoticeEntity>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_NOTICE_DATA);
        /*KLog.json(TAG, "mFromCacheHomeData--->>" + homeMenuEntities.toString() + homeShopEntities.toString() + homeJunZhiQEntities.toString()
                + homeshopSpecialEntities.toString() + homeNoticeEntities.toString());*/
        if (homeJunZhiQEntities != null && homeShopEntities != null && homeMenuEntities != null &&
                homeshopSpecialEntities != null && homeNoticeEntities != null) {
            homeMenuAdapter.addList(bannerList, homeMenuEntities, homeShopEntities, homeJunZhiQEntities, homeshopSpecialEntities, homeNoticeEntities);
            homeMenuAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //版本更新
        viewModel.uc.appUpdate.observe(this, new Observer<AppUpdateEntity>() {
            @Override
            public void onChanged(@Nullable AppUpdateEntity appUpdateEntity) {
                if (appUpdateEntity != null) {
                    if (appUpdateEntity.getResult() != null) {
                        if (appUpdateEntity.getResult().getUrl() != null && !TextUtils.isEmpty(appUpdateEntity.getResult().getUrl())) {
                            mCommonMethod(appUpdateEntity.getResult().getUrl());
                        }
                    }
                }
            }
        });
        //获取消息总数
        viewModel.uc.mMessageLiveData.observe(this, new Observer<MessageCenterEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable MessageCenterEntity.ResultBean resultBean) {
                int total_noread = resultBean.getTotal_noread();
                if (total_noread == 0) {
                    binding.ivMessageNumber.setVisibility(View.GONE);
                    binding.ivMessageNumber.setText(String.valueOf(total_noread));
                } else {
                    binding.ivMessageNumber.setVisibility(View.VISIBLE);
                    if (total_noread >=99){
                        binding.ivMessageNumber.setText("99+");
                    }else {
                        binding.ivMessageNumber.setText(String.valueOf(total_noread));
                    }
                }
            }
        });

        viewModel.uc.mAllLiveData.observe(this, new Observer<List<HomeGoodsEntity.ResultBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<HomeGoodsEntity.ResultBean.DataBean> dataBeans) {
                if (page == 1 && dataBeans.size() == 0) {
                    binding.mallRefreshLayout.setEnableRefresh(false);
                    binding.mallRefreshLayout.setEnableAutoLoadmore(false);
                    binding.mallRefreshLayout.setEnableLoadmore(false);
                } else {
                    binding.mallRefreshLayout.setEnableRefresh(true);
                    binding.mallRefreshLayout.setEnableAutoLoadmore(true);
                }
                initShopRecyViewData(dataBeans);
            }
        });


    }

    /**
     * set shop data
     *
     * @param dataBeans
     */
    private void initShopRecyViewData(List<HomeGoodsEntity.ResultBean.DataBean> dataBeans) {
        if (dataBeans != null && dataBeans.size() > 0) {
            binding.rcyShop.setVisibility(View.VISIBLE);
//        binding.emptyView.setVisibility(View.GONE);
            if (adapter == null) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
                binding.rcyShop.setLayoutManager(gridLayoutManager);
                adapter = new HomeGoodsAdapter(mContext, dataBeans, R.layout.item_home_goods_page);
                binding.rcyShop.setAdapter(adapter);
                View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_home_goods_page_header_view, null);
                initHeaderData(headerView);
                adapter.addHeaderView(headerView);
            } else {
                adapter.notifyDataSetChanged();
            }
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int viewType, int position) {
                    if (position - 1 >= dataBeans.size()) return;
                    String gid = dataBeans.get(position - 1).getGid();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GOODS_KEY_GID, gid);
                    bundle.putString(Constants.GOODS_OPEN_FLAG, "0");
                    startActivity(GoodsActivity.class, bundle);
                }
            });
            binding.rcyShop.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    //获取RecyclerView当前顶部显示的第一个条目对应的索引
                    int position = layoutManager.findFirstVisibleItemPosition();
                    //根据索引来获取对应的itemView
                    View firstVisiableChildView = layoutManager.findViewByPosition(position);
                    //获取当前显示条目的高度
                    int itemHeight = firstVisiableChildView.getHeight();
                    //获取当前Recyclerview 偏移量
                    int flag = (position) * itemHeight - firstVisiableChildView.getTop();
                    //注意事项：recyclerView不要设置padding
                    if (flag == 0)
                        binding.ivGoTop.setVisibility(View.GONE);
                    else
                        binding.ivGoTop.setVisibility(View.VISIBLE);
                }
            });
        } else {
            binding.rcyShop.setVisibility(View.GONE);
        }
    }

    /**
     * set home  头部 banner noice  zhuanti  menu data
     *
     * @param headerView
     */
    private void initHeaderData(View headerView) {
        RecyclerView recyclerView = headerView.findViewById(R.id.recyclerView);
        initRecycleView(recyclerView);
        mFromCacheHomeData();
        viewModel.requestHomeDataNetWork(binding.mallRefreshLayout);
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
                break;
            case 1:
                startActivity(CategoryActivity.class);
                break;
            case 2:
                startActivity(WebSiteActivity.class);
                break;
            case 3:
                startActivity(ShoppingCarActivity.class);
                break;
            case 4:
                startActivity(MyPageActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    /**
     * 关键词搜索
     */
    private void mKeywordSearch() {
        binding.ivSearch.setOnClickListener(this);
        binding.etSearchShop.setOnClickListener(this);
        binding.rlSearch.setOnClickListener(this);
        binding.ivMessage.setOnClickListener(this);
        binding.ivMessageNumber.setOnClickListener(this);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestHomeDataNetWork(binding.mallRefreshLayout);
        viewModel.requestMessageData(page);//获取消息总数
        page = 1;
        isRefresh = true;
        viewModel.requestHomeShopData(binding.mallRefreshLayout, page, pagesize, isRefresh, binding);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isRefresh = false;
        viewModel.requestHomeShopData(binding.mallRefreshLayout, page, pagesize, isRefresh, binding);
    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setTitleTextColors(R.color.white);
        binding.mallRefreshHeader.setTitleTimeColors(R.color.white);
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setmArrowViewWhite();
        ProgressDrawable progressDrawable = binding.mallRefreshHeader.getmProgressView();
        binding.mallRefreshHeader.setProgressDrawable(progressDrawable);
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(48);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(true);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
        binding.mallRefreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
            case R.id.et_search_shop:
            case R.id.rl_search:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_KEYWORDS, "");
                startContainerActivity(GoodsListFragment.class.getCanonicalName());
//                boolean fastClick = SystemUtils.isFastClick();  //防止重复点击
                break;
            case R.id.iv_message:
            case R.id.iv_message_number:
                if (!TextUtils.isEmpty(viewModel.getUserToken())) {
                    startActivity(MessageCenterActivity.class);
                } else {
                    SystemUtils.openActivityByAnimation(HomePageActivity.this, mContext, LoginVertifyActivity.class);
                }
                break;
            default:
                break;
        }
    }

    private void mCommonMethod(String url) {
        try {
            Intent intent = new Intent();    //调web浏览器
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    //jpush ---------------自定义消息、tag/alia 推送回调处理逻辑-------------start-----------------
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(messge, extras);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 接受到 推送的自定义消息，在这里处理相关业务逻辑
     *
     * @param messge
     * @param extras
     */
    private void setCostomMsg(String messge, String extras) {
        Log.d(TAG, "MessageReceiver setCostomMsg：＝＝＝\n messge：" + messge + "\nextras:" + extras);
        KLog.json("<EXTRA_EXTRA> ========服务器推送下来的附加字段:", extras);

        Gson gson = new Gson();
        JpushBean jpushBean = gson.fromJson(extras, JpushBean.class);
        int type = -1;
        if (jpushBean != null) {
            String param1 = jpushBean.getParam1();
            String param2 = jpushBean.getParam2();
            Log.d(TAG, "param2：＝＝＝：" + param2);
            //会员
            if (param1.equals("app:order.payed")) {//付款成功通知
                type = 0;
            } else if (param1.equals("app:order.refund")) {//退款申请通知
                type = 1;
            } else if (param1.equals("app:order.send")) {//发货通知
                type = 2;
            } else if (param1.equals("app:order.end")) {//收货通知
                type = 3;
            } else if (param1.equals("app:order. app:order.close")) {//会员订单（关闭通知）
                type = 4;
            } else if (param1.equals("app:order.refund.nosend")) {//会员订单（维权通过后，会员待发货通知）
                type = 5;
            } else if (param1.equals("app:order.refund.send")) {//会员订单（换货后商家发货通知）
                type = 6;
            } else if (param1.equals("app:order.refund.ends")) {//会员订单（退款成功通知）
                type = 7;
            } else if (param1.equals("app:order.refund.refuse")) {//会员订单（维权被驳回通知）
                type = 8;
            }
            //商户
            if (param1.equals("app:merch.order.send")) {//商户订单（发货通知）
                type = 9;
            } else if (param1.equals("app:merch.refund")) {//商户维权订单（申请通知）
                type = 10;
            } else if (param1.equals("app:merch.order.end")) {//商户订单（收货通知）
                type = 11;
            } else if (param1.equals("app:merch.refund.send")) {//商户维权订单（发货通知）
                type = 12;
            } else {
            }
            //（客服对话）咨询、回复通知
            if (param1.equals("app:chat.message")) {
                type = 13;
            } else {
            }

            // 判断通知权限是否打开
//            NotificationManagerCompat notification = NotificationManagerCompat.from(this);
//            final boolean isEnabled = notification.areNotificationsEnabled();
            boolean isEnabled = NotificationsUtils.isNotificationEnabled(mContext);
            if (isEnabled) {
                showInspectorRecordNotification(messge, param2, type);
            } else {
                //方案二:通知权限是否开启判断
                //  为了不每次强制跳转打开通知的页面  通过弹窗提示用户需要打开通知
                showNotification();
//                NotificationsUtils.requestNotify(mContext);
//                isNotificationEnabled(isEnabled);

            }
        }

    }


    private void showNotification() {
        MessageDialog.showCancelAndSureDialog(HomePageActivity.this, "温馨提示",
                "您还未开启系统通知，将影响消息的接收，要去开启吗？", R.color.textColor, R.color.textColor,
                new MessageDialog.DialogClick() {
                    @Override
                    public void onSureClickListener() {
                        NotificationsUtils.requestNotify(HomePageActivity.this);
                    }

                    @Override
                    public void onCancelClickListener() {
                    }
                });
    }


    private void showInspectorRecordNotification(String messge, String param2, int type) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //android 8.0 适配     需要配置 通知渠道NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "order";
            String channelName = "订单消息";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;// 通知优先级 IMPORTANCE_HIGH  IMPORTANCE_DEFAULT
            createNotificationChannel(channelId, channelName, importance);
//            channelId = "subscribe";
//            channelName = "公告消息";
//            importance = NotificationManager.IMPORTANCE_DEFAULT;
//            createNotificationChannel(channelId, channelName, importance);

            //管理通知渠道
            NotificationChannel channel = nm.getNotificationChannel("order");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                ToastUtils.showLong("请手动将通知权限打开");
                return;
            }

        }
        //添加自定义视图  activity_notification
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_view_custom);
        mRemoteViews.setTextViewText(R.id.title, getResources().getString(R.string.app_name));
        mRemoteViews.setTextViewText(R.id.text, messge);
        int notify_id = (int) System.currentTimeMillis(); //防止消息通知覆盖只显示一条
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this, "order")
                    .setContent(mRemoteViews)
                    .setTicker("来自" + getResources().getString(R.string.app_name) + "的通知")
//                .setContentTitle("收到一条聊天消息")
//                .setContentText("今天中午吃什么？")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.jpush_notification_icon_small)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jpush_notification_icon))
                    .setAutoCancel(true)//点击让通知自动取消
                    .setNumber(1)
//                .setDefaults(Notification.DEFAULT_ALL) //三种全部提醒 (声音、闪灯和震动效果)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND) //设置默认或者自定义的铃声来提醒
                    .setContentIntent(createIntent(messge, param2, type)) //利用PendingIntent来包装我们的intent对象,使其延迟跳转 设置通知栏点击意图
                    .build();
            notification.headsUpContentView = mRemoteViews;//设置横幅通知
//            nm.notify(notify_id, notification);   //更新Notification
        } else {
            notification = new Notification.Builder(this)
                    .setContent(mRemoteViews)
                    .setTicker("来自" + getResources().getString(R.string.app_name) + "的通知")
                    .setSmallIcon(R.drawable.jpush_notification_icon_small)
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jpush_notification_icon))
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND) //设置默认或者自定义的铃声来提醒
                    .setContentIntent(createIntent(messge, param2, type))
                    .setNumber(1)
                    .build();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0 引入锁屏/横幅通知
                notification.headsUpContentView = mRemoteViews;//设置横幅通知
            }
        }
        nm.notify(notify_id, notification);

    }

    /**
     * 方案二：通知权限是否开启判断  （在广播中添加 弹窗失效，可用需要吹弹窗失效问题）
     * 解决方案参考：https://blog.csdn.net/monkin2011/article/details/78016124
     *
     * @param isEnabled
     */
    private void isNotificationEnabled(boolean isEnabled) {
        if (!isEnabled) {//未打开通知
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请在“通知”中打开通知权限")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", mContext.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", mContext.getPackageName());
                                intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                            }
                            startActivity(intent);
                            dialog.cancel();
                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }
    }

    /**
     * 参阅博文：https://www.jianshu.com/p/8abc37e4f097
     * 获取PendingIntent 方式：
     * 1、getActivity(Context context, int requestCode, Intent intent, int flags)
     * 2、ggetService(Context context, int requestCode, Intent intent, int flags)
     * 3、getBroadcast(Context context, int requestCode, Intent intent, int flags)
     *
     * @param messge
     * @param param2
     * @return
     */
    private PendingIntent createIntent(String messge, String param2, int type) {
        // getActivity()
//        Intent intent = new Intent(this, OrderDetailActivity.class);
//        intent.putExtra("Notifiction",true);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        // getBroadcast()
        Intent intent;
        PendingIntent contentIntent = null;
        Bundle mBundle = new Bundle();
        intent = new Intent();
        mBundle.putString("content", messge);
        mBundle.putString("param2", param2);
        mBundle.putInt("type", type);
        intent.putExtras(mBundle);
        intent.setClass(mContext, NotifyClickReceiver.class);
        intent.setAction(CLICK_NOTIFY_ACTION);
        contentIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return contentIntent;
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true); //config  通知 显示未读角标
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


}
