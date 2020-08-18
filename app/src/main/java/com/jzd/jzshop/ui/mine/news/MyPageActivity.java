package com.jzd.jzshop.ui.mine.news;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMyPageBinding;
import com.jzd.jzshop.entity.MemberEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.entity.MyPagerEntity;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.message.MessageCenterActivity;
import com.jzd.jzshop.ui.mine.assets.AssetsAty;
import com.jzd.jzshop.ui.mine.creditsign.SignRecordFragment;
import com.jzd.jzshop.ui.mine.setting.SetFragment;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.ui.website.WebSiteActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.photo_album.FileUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shehuan.niv.NiceImageView;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description:
 * @date :2020/1/20 16:33
 */
public class MyPageActivity extends BaseActivity<ActivityMyPageBinding, MyPageViewModel> implements OnRefreshListener {
    // 拍照相处选图-------------start
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private Bitmap bitmap;
    // 拍照相处选图-------------end
    private NavigationController build;
    private ArrayList<MyPagerEntity.DataBean> listOptionsItem; //构建实体
    private boolean isShow = true;
    private NiceImageView iv_avatar;
    private List<MyPagerEntity> dataList;
    private TextView tv_not_login;
    private int page = 1;   //分页页码
    private TextView iv_message_number;
    private int total_noread;
    private MyPagerRecycleAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_page;

    }

    @Override
    public int initVariableId() {
        return BR.myVM;
    }

    @Override
    public MyPageViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MyPageViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        initBottomTab();
        viewModel.setBinding(binding, this, binding.mallRefreshLayout);

        // TODO: 2020/1/20  我的页面数据是否写活，暂时本地构建模拟数据，如果写活，直接修改数据源即可
        dataList = new ArrayList<>();
        List<MyPagerEntity.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i == 1) { //全部订单
                mCommonMethod(dataList, R.array.my_order_txt, R.mipmap.pays, R.mipmap.pay, R.mipmap.car, R.mipmap.people,
                        R.mipmap.finish);
            } else if (i == 2) { //我的功能
                mFunctionMethod(dataList, R.array.my_function_txt, R.mipmap.ic_tuiguang, R.mipmap.integralsignin, R.mipmap.coupon,
                        R.mipmap.ic_invite_friend, R.mipmap.ic_my_team, R.mipmap.collect, R.mipmap.address,
                        /*R.mipmap.ruzhu,*/R.mipmap.quan, R.mipmap.ic_my_shop);/*R.mipmap.ruzhu,*/
            } else if (i == 3) { //我的服务
                mServiceMethod(dataList, R.mipmap.ic_online_phone, R.mipmap.ic_service_phone, R.mipmap.ic_feedback);
            }
        }
   
        viewModel.requestWork(dataList);
        initMallRefresh(); //配置刷新

        Messenger.getDefault().register(this, MyPageViewModel.TOKEN_VIEWMODEL_REFRESH, new BindingAction() {
            @Override
            public void call() {//刷新我的数据
                ToastUtils.showLong("充值成功！");
                binding.mallRefreshLayout.autoRefresh();
            }
        });
        //登录后，获取消息数
        if (viewModel.user.get() != null) {
            String userToken = viewModel.user.get().getToken();
            if (!TextUtils.isEmpty(userToken)) {
                viewModel.requestMessageData(viewModel.refresh,page);//获取消息总数
            } else {
            }
        } else {
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.myData.observe(this, new Observer<MemberEntity>() {
            @Override
            public void onChanged(@Nullable MemberEntity member) {
                UserInfo userInfo = new UserInfo();
                userInfo.setNickname(member.getResult().getNickname());
                userInfo.setRecommand_name(member.getResult().getRecommand_name()); //推荐人
                userInfo.setInvit_id(member.getResult().getInvit_id()); //邀请码
                userInfo.setMoney2(member.getResult().getMoney2()); //君子资产
                userInfo.setLevel(member.getResult().getLevel()); //等级
                userInfo.setUsericon(member.getResult().getAvatar());
                userInfo.setPoints(member.getResult().getPoints());
                userInfo.setMoney(member.getResult().getMoney());

                userInfo.setDfk_num(member.getResult().getDot0());
                userInfo.setDfh_num(member.getResult().getDot1());
                userInfo.setDshh_num(member.getResult().getDot2());
                userInfo.setThh_num(member.getResult().getDot4());

                userInfo.setIsLogin(1);
                KLog.a("更新用户信息");
                initRecycleView(dataList, userInfo);
                viewModel.requestMessageData(viewModel.refresh,page);//获取消息总数
            }
        });
        //设置头像
        viewModel.uc.icon.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                iv_avatar.setImageResource(R.mipmap.ic_launcher_round);
                iv_avatar.invalidate();
                notLoginDefaultData(dataList);
            }
        });
        //获取消息总数
        viewModel.uc.mMessageLiveData.observe(this, new Observer<MessageCenterEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable MessageCenterEntity.ResultBean resultBean) {
                total_noread = resultBean.getTotal_noread();
                if (total_noread == 0) {
                    iv_message_number.setVisibility(View.GONE);
                } else {
                    iv_message_number.setText(String.valueOf(total_noread));
                    iv_message_number.setVisibility(View.VISIBLE);
                    iv_message_number.invalidate();
                }
            }
        });
    }

    /**
     * 未登录 设置默认数据
     *
     * @param dataList
     */
    public void notLoginDefaultData(List<MyPagerEntity> dataList) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname("");
        userInfo.setUsericon("");
        userInfo.setRecommand_name(""); //推荐人
        userInfo.setInvit_id(0); //邀请码
        userInfo.setMoney2(0.00); //君子资产
        userInfo.setLevel(""); //等级
        userInfo.setPoints(0.00);
        userInfo.setMoney(0.00);

        userInfo.setDfk_num(0);
        userInfo.setDfh_num(0);
        userInfo.setDshh_num(0);
        userInfo.setThh_num(0);

        userInfo.setIsLogin(0);
        userInfo.setToken("");
        initRecycleView(dataList, userInfo);
    }

    private void initHeaderData(View headView, UserInfo userInfo) {
        String assets = MoneyFormatUtils.keepTwoDecimal(userInfo.getMoney2());
        String money = MoneyFormatUtils.keepTwoDecimal(userInfo.getMoney()); //余额
        final MyPagerEntity.DataBeanX headData = new MyPagerEntity.DataBeanX(userInfo.getNickname(), userInfo.getUsericon(),
                userInfo.getRecommand_name(), String.valueOf(userInfo.getInvit_id()), userInfo.getLevel(),
                assets, money, String.valueOf(userInfo.getShowPoints()));
        iv_avatar = headView.findViewById(R.id.iv_avatar);
        TextView tv_nick_name = headView.findViewById(R.id.tv_nick_name);
        TextView tv_level = headView.findViewById(R.id.tv_level);
        TextView tv_reference = headView.findViewById(R.id.tv_reference);
        TextView tv_invitation_code = headView.findViewById(R.id.tv_invitation_code);
        final TextView tv_totalAssets = headView.findViewById(R.id.tv_totalAssets);
        final TextView tv_balance = headView.findViewById(R.id.tv_balance);
        final TextView tv_integral = headView.findViewById(R.id.tv_integral);
        final ConstraintLayout constl_level = headView.findViewById(R.id.constl_level);
        iv_message_number = headView.findViewById(R.id.iv_message_number);
        tv_not_login = headView.findViewById(R.id.tv_not_login);
        if (userInfo.getIsLogin() == 0) {
            tv_not_login.setVisibility(View.VISIBLE);
            iv_avatar.setBackgroundResource(R.mipmap.ic_avatar_default);
        } else {
            tv_not_login.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(headData.getLogo()).into(iv_avatar);
        if (TextUtils.isEmpty(headData.getLevel())) {
            constl_level.setVisibility(View.GONE);
        } else {
            constl_level.setVisibility(View.VISIBLE);
        }
        tv_level.setText(headData.getLevel());
        tv_nick_name.setText(headData.getNickName());
        if (TextUtils.isEmpty(headData.getTuijin())) {
            tv_reference.setVisibility(View.GONE);
        } else {
            tv_reference.setVisibility(View.VISIBLE);
        }
        tv_reference.setText("推荐人：".concat(headData.getTuijin()));
        if (!TextUtils.isEmpty(headData.getInvitecode())){
            if (headData.getInvitecode().equals("0")) {
                tv_invitation_code.setVisibility(View.GONE);
            } else {
                tv_invitation_code.setVisibility(View.VISIBLE);
            }
            tv_invitation_code.setText("邀请码：".concat(headData.getInvitecode()));
        }
        tv_totalAssets.setText(headData.getAsset());
        tv_balance.setText(headData.getBalanc());
        tv_integral.setText(headData.getIntrgral());
        if (userInfo.getIsLogin() == 0) {
            iv_message_number.setVisibility(View.GONE);
        } else {
            if (total_noread == 0){
                iv_message_number.setVisibility(View.GONE);
            }else {
                iv_message_number.setVisibility(View.VISIBLE);
                iv_message_number.setText(String.valueOf(total_noread));
            }
        }
        //明文密文
        final ImageView iv_show_hide = headView.findViewById(R.id.iv_show_hide);
        //设置金额的展示样式
        boolean memberMoneyStatus = viewModel.getMemberMoneyStatus();
        isShow=memberMoneyStatus;
        if(memberMoneyStatus){
            iv_show_hide.setBackgroundResource(R.mipmap.ic_eye_open);
            tv_totalAssets.setText(headData.getAsset());
            tv_balance.setText(headData.getBalanc());
            tv_integral.setText(headData.getIntrgral());
        }else {
            iv_show_hide.setBackgroundResource(R.mipmap.ic_eye_close);
            tv_totalAssets.setText("*****");
            tv_balance.setText("*****");
            tv_integral.setText("*****");
        }
        iv_show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShow) {
                    isShow = true;
                    iv_show_hide.setBackgroundResource(R.mipmap.ic_eye_open);
                    tv_totalAssets.setText(headData.getAsset());
                    tv_balance.setText(headData.getBalanc());
                    tv_integral.setText(headData.getIntrgral());
                } else {
                    isShow = false;
                    iv_show_hide.setBackgroundResource(R.mipmap.ic_eye_close);
                    tv_totalAssets.setText("*****");
                    tv_balance.setText("*****");
                    tv_integral.setText("*****");
                }
                viewModel.saveMemberMoneyStatus(isShow);
            }
        });

    }


    private void setHeaderClickEvent(View headView) {
        headView.findViewById(R.id.tv_not_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //点击登陆
                SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);

            }
        });
        headView.findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //设置
                if (viewModel.user.get() != null) {
                    String userToken = viewModel.user.get().getToken();
                    if (!TextUtils.isEmpty(userToken)) {
                        startContainerActivity(SetFragment.class.getCanonicalName());
                    } else {
                        SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                    }
                } else {
                    SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                }
            }
        });
        headView.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //登陆
                if (viewModel.user.get() != null) {
                    String userToken = viewModel.user.get().getToken();
                    if (!TextUtils.isEmpty(userToken)) {
                        KLog.d(TAG, "更换头像----->>>>");
                        showChoosePicDialog();
                    } else {
                        SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                    }
                } else {
                    SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                }
            }
        });
        headView.findViewById(R.id.tv_see).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //立即查看
                if (viewModel.user.get() != null) {
                    String userToken = viewModel.user.get().getToken();
                    if (!TextUtils.isEmpty(userToken)) {
                        startActivity(AssetsAty.class);
                    } else {
                        SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                    }
                } else {
                    SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                }

            }
        });
        headView.findViewById(R.id.constrl_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //余额
                viewModel.seeBalance();

            }
        });
        headView.findViewById(R.id.constrl_integral).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //积分
//                String encode = URLEncoder.encode(Constants.MY_PAGE_SIGN_RECORD); //url参数转义
//                viewModel.goToBaseWebviewNew(encode);
                if (!TextUtils.isEmpty(viewModel.getUserToken())) {
                    startContainerActivity(SignRecordFragment.class.getCanonicalName());
                } else {
                    SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                }
            }
        });
        headView.findViewById(R.id.iv_message).setOnClickListener(new View.OnClickListener() {//消息中心
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(viewModel.getUserToken())) {
                    startActivity(MessageCenterActivity.class);
                } else {
                    SystemUtils.openActivityByAnimation(MyPageActivity.this,mContext,LoginVertifyActivity.class);
                }
            }
        });

    }

    private void initRecycleView(List<MyPagerEntity> dataList, UserInfo userInfo) {
        if (dataList != null && dataList.size() > 0) {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new MyPagerRecycleAdapter(mContext, userInfo, viewModel, dataList, R.layout.item_recy_my_page);
            binding.recyclerView.setAdapter(mAdapter);
            View headView = LayoutInflater.from(mContext).inflate(R.layout.item_my_page_head_view, null);
            if (mAdapter.hasHeaderView()){
            }else {
                mAdapter.addHeaderView(headView);
            }
            initHeaderData(headView, userInfo);
            setHeaderClickEvent(headView);
        } else {
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        build.setMessageNumber(3, anInt);
        build.setSelect(4);
        //未登录状态
        int netState = NetworkUtils.getNetWorkState(MyPageActivity.this);
        if(netState==-1){
            notLoginDefaultData(dataList);
        }else {
            if (viewModel.getUserInfo() != null) {
                initRecycleView(dataList, viewModel.getUserInfo());
            }else {
                notLoginDefaultData(dataList);
            }
        }
//        binding.mallRefreshLayout.autoRefresh();
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
                startActivity(ShoppingCarActivity.class);
                break;
            case 4:
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build.hideBottomLayout();
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
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
//        mallRefreshLayout.setOnLoadmoreListener()
        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestWork(dataList);
        viewModel.requestMemberNetWork(viewModel.refresh);
        viewModel.requestMessageData(viewModel.refresh,page);//获取消息总数
    }


    /**
     * 全部订单
     *
     * @param dataList
     * @param my_order_txt
     * @param pays
     * @param pay
     * @param car
     * @param people
     * @param finish
     */
    private void mCommonMethod(List<MyPagerEntity> dataList, int my_order_txt, int pays, int pay, int car, int people, int finish) {
        List<String> listTitle = Arrays.asList(getResources().getStringArray(my_order_txt));
        List<Integer> listImage = Arrays.asList(pays, pay, car, people, finish);
        listOptionsItem = new ArrayList<MyPagerEntity.DataBean>();
        for (int j = 0; j < listTitle.size(); j++) {
            listOptionsItem.add(new MyPagerEntity.DataBean(listTitle.get(j), listImage.get(j)));
        }
        dataList.add(new MyPagerEntity("全部订单", listOptionsItem));
    }

    /**
     * 全部功能
     *
     * @param dataList
     * @param my_function_txt
     * @param tuiguang
     * @param integralsignin
     * @param coupon
     * @param friend
     * @param team
     * @param collect
     * @param address
     * @param quan
     * @param shop
     */
    private void mFunctionMethod(List<MyPagerEntity> dataList, int my_function_txt, int tuiguang, int integralsignin, int coupon,
                                 int friend, int team,
                                 int collect, int address,/* int ruzhu,*/ int quan, int shop) { /* int ruzhu,商家入驻 暂时隐藏*/
        List<String> listTitle = Arrays.asList(getResources().getStringArray(my_function_txt));
        List<Integer> listImage = Arrays.asList(tuiguang, integralsignin, coupon, friend, team, collect, address,/*ruzhu,*/quan, shop);/*ruzhu*/
        listOptionsItem = new ArrayList<MyPagerEntity.DataBean>();
        for (int j = 0; j < listTitle.size(); j++) {
            listOptionsItem.add(new MyPagerEntity.DataBean(listTitle.get(j), listImage.get(j)));
        }
        dataList.add(new MyPagerEntity("全部功能", listOptionsItem));
    }

    /**
     * 服务实体
     *
     * @param dataList
     * @param ic_online_phone
     * @param ic_service_phone
     * @param ic_feedback
     */
    private void mServiceMethod(List<MyPagerEntity> dataList, int ic_online_phone, int ic_service_phone, int ic_feedback) {
        List<String> listTitle = Arrays.asList(getResources().getStringArray(R.array.my_service_txt));
        List<Integer> listImage = Arrays.asList(ic_online_phone, ic_service_phone, ic_feedback);
        listOptionsItem = new ArrayList<MyPagerEntity.DataBean>();
        for (int j = 0; j < listTitle.size(); j++) {
            listOptionsItem.add(new MyPagerEntity.DataBean(listTitle.get(j), listImage.get(j)));
        }
        dataList.add(new MyPagerEntity("全部服务", listOptionsItem));
    }


    //====================================相册选图拍照============================================

    /**
     * 相册选图/拍照
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    if (data != null) {
                        startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    }
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        String imageName = System.currentTimeMillis() + ".jpg";
        File imageFile = getPicFile(imageName);
        tempUri = Uri.fromFile(imageFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    private File getPicFile(String fileName) {
        File picDir = new File(getExternalFilesDir(null), "pics");
        if (!picDir.exists()) {
            picDir.mkdir();
        }
        return new File(picDir, fileName);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            Log.d("TAG", "setImageToView:" + photo);
//            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
//            imgUserIcon.setImageBitmap(photo);
//            uploadPic(photo);
//        }
        String imagePath = FileUtils.getFilePathByUri(this, tempUri);
        try {
            uploadPic(imagePath);
        } catch (Exception e) {
        }
    }

    private void uploadPic(String imagePath) {
        this.bitmap = bitmap;
        if (imagePath != null) {
            File file = new File(imagePath);
            Glide.with(MyPageActivity.this).load(file).into(iv_avatar);
            iv_avatar.setImageBitmap(bitmap);
            viewModel.updateAvatar(file, imagePath);
            iv_avatar.invalidate();
            Log.d("TAG", "imagePath:" + imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            takePicture();
        } else {
            if (requestCode == MyPagerRecycleAdapter.REQUEST_CODE_CALL_PHONE) { //拨打电话权限申请，不提示
            } else {
                // 没有获取 到权限，从新请求，或者关闭app
                Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 10003) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choiceImage();
            }
        }
    }


    //         =============================更换头像=========================================

    /**
     * 显示修改头像的对话框
     */
    public void showChoosePicDialog() {
        try {
            NiceDialog.init()
                    .setLayoutId(R.layout.pop_choose_pic)     //设置dialog布局文件
                    .setConvertListener(new ViewConvertListener() {     //进行相关View操作的回调
                        @Override
                        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                            View tv_take_photo = holder.getView(R.id.tv_take_photo);//照相
                            View tv_album = holder.getView(R.id.tv_album);//相册
                            View tv_cancel = holder.getView(R.id.tv_cancel);//取消
                            setPopListener(dialog, tv_take_photo, tv_album, tv_cancel);
                        }
                    })
                    .setDimAmount(0.5f)//调节灰色背景透明度[0-1]，默认0.5f
                    .setShowBottom(true)//是否在底部显示dialog，默认flase
                    .setOutCancel(true)     //点击dialog外是否可取消，默认true
                    .setAnimStyle(R.style.PopupAnimation_Up_Down)     //设置dialog进入、退出的动画style(底部显示的dialog有默认动画)
                    .show(getSupportFragmentManager());     //显示dialog
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void setPopListener(final BaseNiceDialog dialog, View tv_take_photo, View tv_album, View tv_cancel) {
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                dialog.dismiss();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceImageFromGallery();
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void choiceImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 10003)) {
                choiceImage();
            }
        } else {
            choiceImage();
        }
    }

    /**
     * 相册选图
     */
    public void choiceImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private boolean checkPermission(String permission, int requestCode) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
            flag = true;
        else
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        return flag;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(this, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(this, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1);
            } else {
                takePicture();
            }
        } else {
            takePicture();
        }
    }

    /**
     * 拍照
     */
    public void takePicture() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(this, "com.jzd.jzshop.img.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
}
