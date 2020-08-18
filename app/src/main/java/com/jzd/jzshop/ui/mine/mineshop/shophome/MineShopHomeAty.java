package com.jzd.jzshop.ui.mine.mineshop.shophome;

import android.animation.ArgbEvaluator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Build;
import android.service.autofill.SaveCallback;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMineShopHomeBinding;
import com.jzd.jzshop.entity.MineStoreEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.home.invite_friends.InviteFriendsActivity;
import com.jzd.jzshop.ui.message.MessageCenterActivity;
import com.jzd.jzshop.ui.mine.mineshop.MineShopActivity;
import com.jzd.jzshop.ui.mine.mineshop.regist.MineShopRegiestFragment;
import com.jzd.jzshop.ui.mine.promotion.PromotCommissionActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MineShopHomeAty extends BaseActivity<ActivityMineShopHomeBinding,MineShopHomeViewModel>
        implements OnRefreshListener, OnLoadmoreListener {

    private NavigationController build;
    private boolean isrefresh=true;
    private int page=1;
    private String storeid;
    private MineShopHomeAdapter mineShopHomeAdapter;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_mine_shop_home;
    }

    @Override
    public int initVariableId() {
        return BR.mineshophomeVM;
    }


    @Override
    public MineShopHomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MineShopHomeViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        getIntentData();
        initMallRefresh();
        initViewData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarColor(MineShopHomeAty.this, Color.parseColor("#141414"));
        viewModel.setbind(this,binding);
        viewModel.setMessageCode();
//        viewModel.requestwork(null,null,1,binding.mallRefreshLayout,true);
        viewModel.requestwork(storeid,null,1,binding.mallRefreshLayout,isrefresh);
        initBottomTab();
    }

    private void initViewData() {
        mWordSearch(binding.etSearchShop);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MessageCenterActivity.class);
            }
        });

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent!=null){
            storeid = intent.getStringExtra(Constants.MINE_SMALL_STORE);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.mineshopdata.observe(this, new Observer<MineStoreEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable MineStoreEntity.ResultBean resultBean) {
                if(resultBean.getStatus()==100){//未申请 或  被驳回
                    startActivity(MineShopActivity.class);
                    return;
                }
                if(resultBean.getStatus()==400){//申请已提交 审核中
                    startContainerActivity(MineShopRegiestFragment.class.getCanonicalName());
                    return;
                }
                if(resultBean.getStatus()==200||resultBean.getStatus()==300) {//拥有小店
                    initRecycleview(resultBean);
                    return;
                }
            }
        });

        viewModel.mineshopMoredata.observe(this,
                new Observer<List<MineStoreEntity.ResultBean.DataBean.GoodsBean>>() {
            @Override
            public void onChanged(@Nullable List<MineStoreEntity.ResultBean.DataBean.GoodsBean> goodsBeans) {
                initRecycleviewMoreData(goodsBeans);
            }
        });

    }

    private void initRecycleviewMoreData(List<MineStoreEntity.ResultBean.DataBean.GoodsBean> goodsBeans) {
        mineShopHomeAdapter.clear();
        mineShopHomeAdapter.addAll(goodsBeans);
        mineShopHomeAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(0);
//        viewModel.requestwork(null,null,1,binding.mallRefreshLayout,true);
    }

    //初始化Recycle1View
    private void initRecycleview(MineStoreEntity.ResultBean resultBean) {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                2, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        if(mineShopHomeAdapter==null) {
         mineShopHomeAdapter = new MineShopHomeAdapter(
                 this, resultBean.getData().getGoods(), viewModel, R.layout.item_home_shop);
        }else {
//            mineShopHomeAdapter.addAll(resultBean.getData().getGoods());
            mineShopHomeAdapter.notifyDataSetChanged();
        }
        binding.recyclerView.setAdapter(mineShopHomeAdapter);
        mineShopHomeAdapter.setShopid(resultBean.getData().getShop_id());
        if(!mineShopHomeAdapter.hasHeaderView()){
            View headView = LayoutInflater.from(mContext).inflate(R.layout.item_mine_shop_head_view, null);
            mineShopHomeAdapter.addHeaderView(headView);
        }
        initHeadData(mineShopHomeAdapter.getHeaderView(),resultBean);
        //RecycleView监听滑动得距离来处理标题栏的颜色
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               //第一个item的位置
                int position = manager.findFirstVisibleItemPosition();
                //根据position找到这个Item
                View firstVisiableChildView = manager.findViewByPosition(position);
                if(firstVisiableChildView==null){
                    return;
                }
                //获取Item的高
                int itemHeight = firstVisiableChildView.getHeight();
                //算出该Item还未移出屏幕的高度
                int itemTop = firstVisiableChildView.getTop();
                //position移出屏幕的数量*高度得出移动的距离
                int iposition = position * itemHeight;
                //减去该Item还未移出屏幕的部分可得出滑动的距离
                int  iResult = iposition - itemTop;
                if(iResult==0){
                   binding.ivGoTop.setVisibility(View.GONE);
                }else {
                    binding.ivGoTop.setVisibility(View.VISIBLE);
                }
                setReachColor(iResult);
            }
        });
    }

    /**
     * 设置标题栏和状态栏的颜色渐变
    * */
    public void setReachColor(int position){
        float  fraction = Math.abs(position * 1.0f) / 500;
        //转化的颜色值是ARGB类型的
        //这儿是ARGB 类型的颜色值 需要装化为16进制的颜色值
        int  color = changeAlpha(getResources().getColor(R.color.red), fraction);
        if(position==0){
            StatusBarUtil.setRootViewFitsSystemWindows(MineShopHomeAty.this, false);
            StatusBarUtil.getStatusBarHeight(MineShopHomeAty.this);
            StatusBarUtil.setTranslucentStatus(MineShopHomeAty.this);
            binding.conReasch.setBackgroundColor(Color.parseColor("#141414"));
        }else if(position>500){
            StatusBarUtil.setStatusBarDarkTheme(MineShopHomeAty.this, false);
            StatusBarUtil.setStatusBarColor(MineShopHomeAty.this, Color.parseColor("#D90804"));
            binding.conReasch.setBackgroundColor(Color.parseColor("#D90804"));
        }else {
            StatusBarUtil.setStatusBarDarkTheme(MineShopHomeAty.this, false);
            StatusBarUtil.setStatusBarColor(MineShopHomeAty.this, color);
            binding.conReasch.setBackgroundColor(color);
        }
    }


    /**
     * 根据百分比修改颜色透明度
     */
    int   changeAlpha(int color, Float fraction){
        int  red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha =((int)(Color.alpha(color) * fraction));
        return Color.argb(alpha, red, green, blue);
    }


    /**
     * 初始化RecycleView的头部
     * @param headView
     * @param resultBean
     */
    private void initHeadData(View headView, MineStoreEntity.ResultBean resultBean) {

        TextView name = headView.findViewById(R.id.tv_name);
        ImageView icon = headView.findViewById(R.id.iv_icon);
        ImageView ivsigns = headView.findViewById(R.id.iv_signs);
//        ImageView ivback = headView.findViewById(R.id.iv_back);
//        ImageView iv_message = headView.findViewById(R.id.iv_message);
        ImageView iv_code = headView.findViewById(R.id.iv_code);


//        RelativeLayout rlsearch = headView.findViewById(R.id.rl_search);


/*        EditText search = headView.findViewById(R.id.et_search_shop);
        mWordSearch(search);*/
        name.setText(resultBean.getData().getName());
        Glide.with(mContext).load(resultBean.getData().getLogo()).into(icon);
        Glide.with(mContext).load(resultBean.getData().getSigns()).into(ivsigns);





/*        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MessageCenterActivity.class);
            }
        });
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        iv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(InviteFriendsActivity.class);
            }
        });

    }

    private void mWordSearch(final EditText search) {
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideKeyboard(search);//点击搜索的时候隐藏软键盘
                    String keyWord = search.getText().toString().trim();
                    viewModel.requestwork(null,keyWord,1,binding.mallRefreshLayout,true);
                    return true;
                }
                return false;
            }
        });
    }


    public void initBottomTab() {
        build=  InitBottomTab.getInstance(Utils.getContext()).initShop(binding.pagerBottomTab, new OnTabItemSelectedListener() {
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
                startActivity(MineSelectActivity.class);
                finish();
                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MINE_PROMOT,"VISIBLE");
                startActivity(PromotCommissionActivity.class,bundle);
                finish();
                break;
            case 3:
                Intent intent = new Intent(this,MineShopActivity.class);
                Intent set_shop = intent.putExtra(Constants.MINE_SET, "SET_SHOP");
                startActivityForResult(set_shop,100);
                break;

        }
        overridePendingTransition(0, 0);
    }

    //配置刷新
    private void initMallRefresh() {

        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）
        binding.mallRefreshHeader.setTitleTextColors(R.color.white);
        binding.mallRefreshHeader.setTitleTimeColors(R.color.white);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==100){
            binding.mallRefreshLayout.autoRefresh();
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        isrefresh=true;
        page=1;
        viewModel.requestwork(null,null,1,binding.mallRefreshLayout,isrefresh);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        isrefresh=false;
        viewModel.requestwork(null,null,page,binding.mallRefreshLayout,isrefresh);
    }
}
