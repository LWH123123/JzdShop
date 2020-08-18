package com.jzd.jzshop.ui.home.news;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.HomeBannerEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeMenuEntity;
import com.jzd.jzshop.entity.HomeNoticeEntity;
import com.jzd.jzshop.entity.ShopSpecialEntity;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author LXB
 * @description:
 * @date :2020/1/3 16:28
 */

public class HomePagerRecycleAdapter extends RecyclerView.Adapter{
    private static final String TAG = HomePagerRecycleAdapter.class.getSimpleName();
    private Context mContext;
    private HomePageViewModel viewModel;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private CustomStaggerGrildLayoutManger customStager;
    private List<Integer> mHeights = new ArrayList<>();
    private int mTotal = 0;
    private int mCurrent = 0;
    //viewType
//    menu 显示的type 组合结果只有三种情况  1、单列（最少列数3 最大列数 5） 2、多行（最大行数8 最大列数 5）
//    public static final int TYPE_MENU_SINGLE_PAGE = 1;//菜单  单页布局
//    public static final int TYPE_MENU_MULTI_PAGE = 2;//菜单  分页布局
    public static final int TYPE_BANNER = 1;//banner添加
    public static final int TYPE_MENU = 2;//菜单  单页布局
    public static final int TYPE_JUNZHI_QUAN = 3; //君子权广告位
    public static final int TYPE_SHOP = 4;//商品
    public static final int TYPE_SHOP_SPECIAL = 5; //商品专题
    public static final int TYPE_HOT_NOTICE = 6; //热点公告
    //setdata
    private List<HomeBannerEntity> banner;
    private List<HomeEntity.ResultBean.DataBeanX> mHomeShopEntities;
    private List<HomeMenuEntity> mHomeMenuEntities;
    private List<HomeNoticeEntity> homeHotNoticeEntities;
    private List<HomeEntity.ResultBean.DataBeanX> homeJunZhiQEntities;
    private List<ShopSpecialEntity> homeshopSpecialEntities;
    private ArrayList<Integer> types = new ArrayList<>();
    private Map<Integer, Integer> mPosition = new HashMap<>();


    public HomePagerRecycleAdapter(Context context, HomePageViewModel viewModel) {
        this.mContext = context;
        this.viewModel = viewModel;
        inflater = LayoutInflater.from(mContext);
        //init module data
        banner= new ArrayList<>();//banner
        mHomeMenuEntities = new ArrayList<>(); //menu
        mHomeShopEntities = new ArrayList<>(); //shop
        homeHotNoticeEntities = new ArrayList<>(); //hotnotice
        homeJunZhiQEntities = new ArrayList<>(); //junzhiquan
        homeshopSpecialEntities = new ArrayList<>(); //shopSpecial

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder---->>>" + viewType);
        switch (viewType) {
            case TYPE_BANNER://BANNER布局
                View inflate = inflater.inflate(R.layout.item_home_headview_banner, parent, false);
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) inflate.getLayoutParams();
                params.setFullSpan(true);
                inflate.setLayoutParams(params);
                return new BannerTYpeViewHolder(inflate);
            case TYPE_MENU: //menu布局
                View viewMenu = inflater.inflate(R.layout.item_home_menu_dynamic, parent, false);
                StaggeredGridLayoutManager.LayoutParams params1 = (StaggeredGridLayoutManager.LayoutParams) viewMenu.getLayoutParams();
                params1.setFullSpan(true);
                viewMenu.setLayoutParams(params1);
                return new MenuTypeViewHolder(viewMenu);
            case TYPE_JUNZHI_QUAN:
                View junzhiBanner = inflater.inflate(R.layout.item_home_junzhi_quan, parent, false);
                StaggeredGridLayoutManager.LayoutParams params2 = (StaggeredGridLayoutManager.LayoutParams) junzhiBanner.getLayoutParams();
                params2.setFullSpan(true);
                junzhiBanner.setLayoutParams(params2);
                return new JunZhiQTypeViewHolder(junzhiBanner);
            case TYPE_SHOP:   //商品布局
                View viewShop = inflater.inflate(R.layout.item_home_shop, parent, false);
                //------方案二-----商品item 通过嵌套recycleview 写---------------
//            View view = inflater.inflate(R.layout.item_home_shop_list, parent, false);
                CustomStaggerGrildLayoutManger.LayoutParams params3 = (CustomStaggerGrildLayoutManger.LayoutParams) viewShop.getLayoutParams();
                params3.setFullSpan(false);
                viewShop.setLayoutParams(params3);
                return new ShopTypeViewHolder(viewShop);
            case TYPE_SHOP_SPECIAL://商品专题  //item_home_shop_special
                View viewShopSpecial = inflater.inflate(R.layout.item_home_shop_special_dynamic, parent, false);
                CustomStaggerGrildLayoutManger.LayoutParams params4 = (CustomStaggerGrildLayoutManger.LayoutParams) viewShopSpecial.getLayoutParams();
                params4.setFullSpan(true);
                viewShopSpecial.setLayoutParams(params4);
                return new ShopSpecialTypeViewHolder(viewShopSpecial);
            case TYPE_HOT_NOTICE: //热点公告
                View hotNotice = inflater.inflate(R.layout.item_home_hot_notice, parent, false);
                StaggeredGridLayoutManager.LayoutParams params5 = (StaggeredGridLayoutManager.LayoutParams) hotNotice.getLayoutParams();
                params5.setFullSpan(true);
                hotNotice.setLayoutParams(params5);
                return new HotNoticeTypeViewHolder(hotNotice);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        int realPosition  = position - mPosition.get(viewType);
        Log.e(TAG, "viewType---->>>" + viewType);
        Log.e(TAG, "realPosition---->>>" + realPosition);
        switch (viewType) {
            case TYPE_BANNER://banner
                if(banner.size()!=0){
                    initBannerData((BannerTYpeViewHolder) holder,banner);
                }
            break;
            case TYPE_MENU:
                if (mHomeMenuEntities.size() != 0) {
                    if (realPosition >= mHomeMenuEntities.size() || realPosition < 0) {
                        return;
                    }
//                    ((MenuTypeViewHolder)holder).bindHolder(mHomeMenuEntities.get(realPosition).getData(),mContext);
                    List<HomeEntity.ResultBean.DataBeanX.DataBean> data = mHomeMenuEntities.get(realPosition).getData();
                    //todo item 动态添加recycleview list 导致外层recycleview 总条目数计算有问题，导致数据重复 动态添加的recy 相当于占用 1 个item
                    // 2、 切记 postion position > 0  代表 非动态添加的两层嵌套 item, position ==0  代表 是

                    if (position > 1) return;
                    initMenuData((MenuTypeViewHolder) holder, data);               //动态布局 菜单
                }
                break;
            case TYPE_JUNZHI_QUAN:
                if (homeJunZhiQEntities.size() != 0) {
                    if (realPosition >= homeJunZhiQEntities.size() || realPosition < 0) {
                        return;
                    }
                    ((JunZhiQTypeViewHolder) holder).bindHolder(homeJunZhiQEntities.get(realPosition), mContext);
                }
                break;
            case TYPE_SHOP:
                if (mHomeShopEntities.size() != 0) {
                    if (realPosition >= mHomeShopEntities.size() || realPosition < 0) {
                        return;
                    }
//                    HomeEntity.ResultBean.DataBeanX dataBeanX = mHomeShopEntities.get(realPosition);
//                    initShopData((ShopTypeViewHolder) holder, dataBeanX);
                    ((ShopTypeViewHolder) holder).bindHolder(mHomeShopEntities.get(realPosition), mContext);
                }
                break;
            case TYPE_SHOP_SPECIAL:
                if (homeshopSpecialEntities.size() != 0) {
                    if (realPosition >= homeshopSpecialEntities.size() || realPosition < 0) {
                        return;
                    }
                    // TODO: 2020/3/29 动态布局写活，需要装修 橱窗样式暂不支持，需后期实现
                    List<HomeEntity.ResultBean.DataBeanX.DataBean> data = homeshopSpecialEntities.get(realPosition).getData();
                    //-------------position != 4  4代表的是 专题模块所在的position位置----------------------------
                    if (position != 4) return;
                    initShopSpecialData((ShopSpecialTypeViewHolder) holder, data); //动态布局
                    // TODO: 2020/3/30   撰写 橱窗样式, 通过 recycleview  直接对 item 操作不可行，此方案失败！！
//                    ((ShopSpecialTypeViewHolder) holder).bindHolder(homeshopSpecialEntities.get(realPosition), mContext,viewModel);
                }
                break;
            case TYPE_HOT_NOTICE:
                if (homeHotNoticeEntities.size() != 0) {
                    if (realPosition >= homeHotNoticeEntities.size() || realPosition < 0) {
                        return;
                    }
                    ((HotNoticeTypeViewHolder) holder).bindHolder(homeHotNoticeEntities.get(realPosition), mContext,viewModel);
                }
                break;
        }

    }


    /**
     * banner 数据
     */
    private void initBannerData(BannerTYpeViewHolder holder, List<HomeBannerEntity> banner) {
        HomeBannerEntity homeBannerEntity = banner.get(0);

        holder.xbanner.setBannerData(homeBannerEntity.getBannEntities());
        holder.xbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                BannEntity ban = (BannEntity) model;
                Glide.with(mContext).load(ban.getXBannerUrl()).into((ImageView) view);
            }
        });
        holder.xbanner.startAutoPlay();
        holder.xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                BannEntity xBannerData = (BannEntity) model;
                Bundle bundle = new Bundle();
                if (xBannerData != null) {
                    String linkurl = xBannerData.getUrl();
                    Log.d(TAG, "linkUrl:" + linkurl);
                    AppIdentityJumpUtils.homeMenujumpLinkUrl(linkurl, viewModel, mContext);
                } else {
                    SystemUtils.openActivityByAnimation((Activity)mContext, mContext, LoginVertifyActivity.class);
                }


            }
        });

    }

    //初始化Banner数据
   /* private void initBannerData(BannerTypeViewHolder holder, List<BannEntity> banner) {
        holder.xBanner.setBannerData(banner);
        holder.xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                BannEntity ban = (BannEntity) model;
                Glide.with(mContext).load(ban.getXBannerUrl()).into((ImageView) view);
            }
        });
        holder.xBanner.startAutoPlay();
    }*/


    /**
     *  set shopSpecial data
     * @param holder
     * @param data
     */
    private void initShopSpecialData(ShopSpecialTypeViewHolder holder, List<HomeEntity.ResultBean.DataBeanX.DataBean> data) {
        LinearLayout linearLayout = holder.dynamicRecy;
        linearLayout.removeAllViews();

        for (int i = 0; i < homeshopSpecialEntities.size(); i++) {
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mContext);
            HorizontalScrollView.LayoutParams layoutParams1 =
                    new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            horizontalScrollView.setLayoutParams(layoutParams1);
            horizontalScrollView.setScrollBarSize(0);
            horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            horizontalScrollView.setVerticalScrollBarEnabled(false);
            horizontalScrollView.setHorizontalScrollBarEnabled(false);

            LinearLayout linearLayout1 = new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams2 =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setLayoutParams(layoutParams2);

            RecyclerView dyRecyclerView = new RecyclerView(mContext);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dyRecyclerView.setLayoutParams(layoutParams);

            linearLayout1.addView(dyRecyclerView);
            horizontalScrollView.addView(linearLayout1);
            linearLayout.addView(horizontalScrollView);

            if (homeshopSpecialEntities.get(i).getShowtype() == 0) {//单页
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, homeshopSpecialEntities.get(i).getRownum());
                dyRecyclerView.setLayoutManager(gridLayoutManager);
                dyRecyclerView.setBackgroundColor(Color.parseColor(homeshopSpecialEntities.get(i).getBackground()));
                TypeShopSpecialAdapter centerAdapter = new TypeShopSpecialAdapter(mContext, homeshopSpecialEntities.get(i).getData());
                dyRecyclerView.setAdapter(centerAdapter);
                mSlidingMode(horizontalScrollView, false);
                if (homeshopSpecialEntities.get(i).getRownum() >= 5) { //后台档  3 、4、5 一行最多5
                    setRecySpace(dyRecyclerView, 26);
                } else if (homeshopSpecialEntities.get(i).getRownum() == 4) {
                    setRecySpace(dyRecyclerView, 12);
                } else if (homeshopSpecialEntities.get(i).getRownum() == 3) {
                    setRecySpaceLeft$Right(dyRecyclerView, 77, 77);
                } else {
                }
            } else { //多页
                mSlidingMode(horizontalScrollView, false);
                dyRecyclerView.setLayoutManager(new GridLayoutManager(mContext, homeshopSpecialEntities.get(i).getPagenum()));
                if (homeshopSpecialEntities.get(i).getPagenum() >= 5) {
                    if (homeshopSpecialEntities.get(i).getPagenum() > 10) {
                        dyRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 10));
                    } else if (homeshopSpecialEntities.get(i).getPagenum() == 5) {
                        setRecySpace(dyRecyclerView,11);
                    } else {
                    }
                } else {//mHomeMenuEntities.get(i).getRownum() >0 <=4  考虑适配问题，一行固定显示四个，否则item 填充不满
                    mSlidingMode(horizontalScrollView, true);
                    if (homeshopSpecialEntities.get(i).getPagenum() == 4) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, homeshopSpecialEntities.get(i).getPagenum());
                        dyRecyclerView.setLayoutManager(gridLayoutManager);
                        setRecySpaceLeft$Right(dyRecyclerView, 40, 40);
                    } else if (homeshopSpecialEntities.get(i).getPagenum() == 3) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, homeshopSpecialEntities.get(i).getPagenum());
                        dyRecyclerView.setLayoutManager(gridLayoutManager);
                        setRecySpaceLeft$Right(dyRecyclerView,82, 82);
                    } else {
                    }
                }
        }
            dyRecyclerView.setBackgroundColor(Color.parseColor(homeshopSpecialEntities.get(i).getBackground()));
            TypeShopSpecialAdapter centerAdapter = new TypeShopSpecialAdapter(mContext, viewModel, homeshopSpecialEntities.get(i).getData());
            dyRecyclerView.setAdapter(centerAdapter);

        }
    }

    /**
     * set shop data
     *
     * @param holder
     * @param dataBeanX
     */
    private void initShopData(ShopTypeViewHolder holder, HomeEntity.ResultBean.DataBeanX dataBeanX) {
        holder.shopeName.setText(dataBeanX.getTitle());
//        holder.shopePrice.setText(MoneyFormatUtils.keepTwoDecimal(dataBeanX.getPrice()));
        holder.shopePrice.setText(dataBeanX.getPrice());
        if ((dataBeanX.getSeckill() == 0)) holder.seckills.setVisibility(View.GONE);
        else holder.seckills.setVisibility(View.VISIBLE); //秒杀
        if ((dataBeanX.getEnoughs() == 0)) holder.fullReduction.setVisibility(View.GONE);
        else holder.fullReduction.setVisibility(View.VISIBLE); //满减
        if ((dataBeanX.getSalegit() == 0)) holder.fullGift.setVisibility(View.GONE);
        else holder.fullGift.setVisibility(View.VISIBLE); //满赠
        Glide.with(mContext).load(dataBeanX.getThumb()).into(holder.shopIcon);
        holder.shopIcon.setTag(dataBeanX.getThumb());
//----------------------------------商品展示为瀑布流样式---------------------
//        if (mHeights.size() <= getItemCount() + 2) {
//            //这里只是随机数模拟瀑布流， 实际过程中， 应该根据图片高度来实现瀑布流
//            mHeights.add((int) (500 + Math.random() * 400));
//        }
//        ViewGroup.LayoutParams layoutParams = holder.shopIcon.getLayoutParams();
//        if (mHeights.size() > position)
//            //此处取得随机数，如果mheight里面有数则取， 没有则邹走else
//            layoutParams.height = mHeights.get(position);
//        else layoutParams.height = 589;
//        holder.shopIcon.setLayoutParams(layoutParams);
//        holder.shopIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        //----------------------------------商品展示为瀑布流样式---------------------

//        holder.rvShop.setLayoutManager(new CustomStaggerGrildLayoutManger(mContext, 1, StaggeredGridLayoutManager.VERTICAL));

//        holder.rvShop.setLayoutManager(new GridLayoutManager(mContext, 6));
//        TypeShopAdapter typeShopAdapter = new TypeShopAdapter(mContext, dataBeanX);
//        holder.rvShop.setAdapter(typeShopAdapter);

    }

    /**
     * set menu data
     *
     * @param holder
     * @param data
     */
    private void initMenuData(MenuTypeViewHolder holder, List<HomeEntity.ResultBean.DataBeanX.DataBean> data) {
        /**
         *  menu 组合显示情况  单页   多页（可滑动）
         *  1、单页
         *   一行 最少显示3个，最多5个
         *   todo : 网格布局 行数控制
         *  2、多页（分页可滑动）
         *     2.1  行数 大于 页数时，以页数判断
         *          行数 小于 页数时，以行数判断
         *   todo : 网格布局 页数优先控制，行数其次
         *
         */
        LinearLayout linearLayout = holder.dynamicRecy;
        linearLayout.removeAllViews();
        for (int i = 0; i < mHomeMenuEntities.size(); i++) {
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mContext);
            HorizontalScrollView.LayoutParams layoutParams1 =
                    new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            horizontalScrollView.setLayoutParams(layoutParams1);
            horizontalScrollView.setScrollBarSize(0);
            horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            horizontalScrollView.setVerticalScrollBarEnabled(false);
            horizontalScrollView.setHorizontalScrollBarEnabled(false);

            LinearLayout linearLayout1 = new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams2 =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout1.setPadding(20,0,0,0);
            linearLayout1.setGravity(Gravity.CENTER);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setLayoutParams(layoutParams2);

            RecyclerView dyRecyclerView = new RecyclerView(mContext);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dyRecyclerView.setLayoutParams(layoutParams);

            linearLayout1.addView(dyRecyclerView);
            horizontalScrollView.addView(linearLayout1);
            linearLayout.addView(horizontalScrollView);


            if (mHomeMenuEntities.get(i).getShowtype() == 0) {//单页
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mHomeMenuEntities.get(i).getRownum());
                dyRecyclerView.setLayoutManager(gridLayoutManager);
                dyRecyclerView.setBackgroundColor(Color.parseColor(mHomeMenuEntities.get(i).getBackground()));
                TypeMenuAdapter centerAdapter = new TypeMenuAdapter(mContext, mHomeMenuEntities.get(i).getData());
                dyRecyclerView.setAdapter(centerAdapter);
                mSlidingMode(horizontalScrollView, false);
                if (mHomeMenuEntities.get(i).getRownum() >= 5) { //后台档  3 、4、5 一行最多5
                    setRecySpace(dyRecyclerView, 26);
                } else if (mHomeMenuEntities.get(i).getRownum() == 4) {
                    setRecySpace(dyRecyclerView, 12);
                } else if (mHomeMenuEntities.get(i).getRownum() == 3) {
                    setRecySpaceLeft$Right(dyRecyclerView, 77, 77);
                } else {
                }
            } else { //多页
                mSlidingMode(horizontalScrollView, false);
                dyRecyclerView.setLayoutManager(new GridLayoutManager(mContext, mHomeMenuEntities.get(i).getPagenum()));
                if (mHomeMenuEntities.get(i).getPagenum() >= 5) {
                    if (mHomeMenuEntities.get(i).getPagenum() > 10) {
                        dyRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 10));
                    } else if (mHomeMenuEntities.get(i).getPagenum() == 5) {
                        setRecySpace(dyRecyclerView,11);
                    } else {
                    }
                } else {//mHomeMenuEntities.get(i).getRownum() >0 <=4  考虑适配问题，一行固定显示四个，否则item 填充不满
                    mSlidingMode(horizontalScrollView, true);
                    if (mHomeMenuEntities.get(i).getPagenum() == 4) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mHomeMenuEntities.get(i).getPagenum());
                        dyRecyclerView.setLayoutManager(gridLayoutManager);
                        setRecySpaceLeft$Right(dyRecyclerView, 40, 40);
                    } else if (mHomeMenuEntities.get(i).getPagenum() == 3) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mHomeMenuEntities.get(i).getPagenum());
                        dyRecyclerView.setLayoutManager(gridLayoutManager);
                        setRecySpaceLeft$Right(dyRecyclerView, 82, 82);
                    } else {
                    }
                }

                //方案一
                // 水平分页布局管理器
//                pagerGridLayoutManager = new PagerGridLayoutManager(/*mHomeMenuEntities.get(i).getRownum()*/2,
//                        /*mHomeMenuEntities.get(i).getPagenum()*/3, PagerGridLayoutManager.HORIZONTAL);
//                pagerGridLayoutManager.setPageListener(this);    // 设置页面变化监听器
//                dyRecyclerView.setLayoutManager(pagerGridLayoutManager);

            }

            dyRecyclerView.setBackgroundColor(Color.parseColor(mHomeMenuEntities.get(i).getBackground()));
            TypeMenuAdapter centerAdapter = new TypeMenuAdapter(mContext, viewModel, mHomeMenuEntities.get(i).getData());
            dyRecyclerView.setAdapter(centerAdapter);

        }

    }




    /**
     * 多种布局时候至关重要的方法
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
//
//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        //----------------------------------商品展示为瀑布流样式---------start------------
//        this.recyclerView = recyclerView;
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            customStager = ((CustomStaggerGrildLayoutManger) layoutManager);
//        }
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == 2) {     //如果快速滑动， 不加载图片
//                    Glide.with(AppApplication.getInstance()).pauseRequests();
//                } else {
//                    Glide.with(AppApplication.getInstance()).resumeRequests();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            }
//        });
        //----------------------------------商品展示为瀑布流样式---------end------------
//    }


    //-----------------------------------------------根据type 创建各自的viewHolder-------------------------------------------------
    static class MenuTypeViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.rv_menu)
//        RecyclerView rvMenu;
        @BindView(R.id.fl_dynamic_recy)
        LinearLayout dynamicRecy;

        public MenuTypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    static class BannerTYpeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.xbanner)
        XBanner xbanner;
        public BannerTYpeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }





//    static class ShopTypeViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.pic)
//        ImageView shopIcon;
//        @BindView(R.id.viewSeckills)
//        View seckills;
//        @BindView(R.id.name)
//        TextView shopeName;
//        @BindView(R.id.price)
//        TextView shopePrice;
//        @BindView(R.id.tv_full_reduction)
//        TextView fullReduction;
//        @BindView(R.id.tv_full_gift)
//        TextView fullGift;
////        @BindView(R.id.rv_shop)
////        RecyclerView rvShop;
//
//        public ShopTypeViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//
//        }
//    }

    static class ShopSpecialTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fl_dynamic_recy)
        LinearLayout dynamicRecy;

        public ShopSpecialTypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


    //-----------------------------------------------setData-------------------------------------------------

    /**
     * 创建一个方法供外面操作此数据
     *
     * @param list1
     * @param list2
     * @param list3
     */
    public void addList(List<HomeBannerEntity> list, List<HomeMenuEntity> list1, ArrayList<HomeEntity.ResultBean.DataBeanX> list2,
                        List<HomeEntity.ResultBean.DataBeanX> list3, List<ShopSpecialEntity> list4,
                        List<HomeNoticeEntity> list5) {
        banner.clear();
        mHomeMenuEntities.clear();
        mHomeShopEntities.clear();
        homeJunZhiQEntities.clear();
        homeshopSpecialEntities.clear();
        homeHotNoticeEntities.clear();
        types.clear();
        mPosition.clear();

        //=============切记 这里才是真正的  item 类型显示位置  ===========
        addListByType(TYPE_BANNER,(ArrayList) list);
        addListByType(TYPE_MENU, (ArrayList) list1);
        addListByType(TYPE_HOT_NOTICE, (ArrayList) list5);
        addListByType(TYPE_JUNZHI_QUAN, (ArrayList) list3);
        addListByType(TYPE_SHOP_SPECIAL, (ArrayList) list4);
        addListByType(TYPE_SHOP, (ArrayList) list2);

        this.banner=list;
        this.mHomeMenuEntities = list1;
        this.homeJunZhiQEntities = list3;
        this.mHomeShopEntities = list2;
        this.homeshopSpecialEntities = list4;
        this.homeHotNoticeEntities = list5;
    }


    private void addListByType(int type, ArrayList list) {
        if(list==null){
            return;
        }
        mPosition.put(type, types.size());
        for (int i = 0; i < list.size(); i++) {
            types.add(type);
        }
    }

    /**
     * RecyclerView 设置item右间距
     *
     * @param dyRecyclerView
     * @param spce
     */
    private void setRecySpace(RecyclerView dyRecyclerView, int spce) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.RIGHT_DECORATION, spce);//右间距
        dyRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecorationUtils(stringIntegerHashMap));
    }

    /**
     * 设置item左右间距
     *
     * @param dyRecyclerView
     * @param left
     * @param right
     */
    private void setRecySpaceLeft$Right(RecyclerView dyRecyclerView, int left, int right) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.RIGHT_DECORATION, right);//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.LEFT_DECORATION, left);//右间距
        dyRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecorationUtils(stringIntegerHashMap));
    }

    /**
     * @param horizontalScrollView
     * @param scrlllMode           true  不可滑   false  可滑动
     */
    private void mSlidingMode(HorizontalScrollView horizontalScrollView, final boolean scrlllMode) {
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return scrlllMode;//可滑动
            }
        });
    }


}
