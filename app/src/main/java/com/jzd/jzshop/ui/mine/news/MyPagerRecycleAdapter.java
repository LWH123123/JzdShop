package com.jzd.jzshop.ui.mine.news;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MyPagerEntity;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.home.invite_friends.InviteFriendsActivity;
import com.jzd.jzshop.ui.home.news.RecyclerViewSpacesItemDecorationUtils;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressFragment;
import com.jzd.jzshop.ui.mine.creditsign.CreditSignNewActivity;
import com.jzd.jzshop.ui.mine.feedback.FeedBackAty;
import com.jzd.jzshop.ui.mine.mineshop.MineShopActivity;
import com.jzd.jzshop.ui.mine.mineteam.MineTeamActivity;
import com.jzd.jzshop.ui.mine.promotion.PromotCommissionActivity;
import com.jzd.jzshop.ui.order.mineorder.MineOrderFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.QQUtils;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author LXB
 * @description:
 * @date :2020/1/20 17:04
 */
public class MyPagerRecycleAdapter extends SuperAdapter<MyPagerEntity> implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = MyPagerRecycleAdapter.class.getSimpleName();
    public final static int REQUEST_CODE_CALL_PHONE = 222;
    String[] perms = {Manifest.permission.CALL_PHONE};
    private Context context;
    private MyPageViewModel viewModel;
    private List<MyPagerEntity> dataLists;
    private GridLayoutManager gridLayoutManager;
    private UserInfo userInfo;

    public MyPagerRecycleAdapter(Context context, UserInfo userInfo, MyPageViewModel model, List<MyPagerEntity> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
        this.userInfo = userInfo;
        this.viewModel = model;
        this.dataLists = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, MyPagerEntity item) {
        holder.setText(R.id.tv_title, item.getTitle());
        List<MyPagerEntity.DataBean> list = item.getList();
        RecyclerView recycleChild = holder.findViewById(R.id.recycle);
        if (list != null && list.size() > 0) {
            if (layoutPosition == 1) {
                gridLayoutManager = new GridLayoutManager(context, 5);
                recycleChild.setLayoutManager(gridLayoutManager);
            } else {
                gridLayoutManager = new GridLayoutManager(context, 4);
            }
            recycleChild.setLayoutManager(gridLayoutManager);
            MyPageRecycleChildAdapter adapter = new MyPageRecycleChildAdapter(context, userInfo, layoutPosition, list, R.layout.item_my_page_recyc_list_item);
            recycleChild.setAdapter(adapter);
            setSpace(recycleChild);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int viewType, int position) {
                    Log.d("onItemClick", "MyPageRecycleChildAdapter===" + position);
                    if (layoutPosition == 1) {  //我的订单
                        skipOrderPage(position);
                    } else if (layoutPosition == 2) { //我的功能
                        skipFunctionPage(position);
                    } else if (layoutPosition == 3) { //我的服务
                        skipServicePage(position);
                    } else {
                    }
                }
            });
        } else {
        }
    }

    /**
     * 跳转  我的订单
     *
     * @param position
     */
    private void skipOrderPage(int position) {
        if (viewModel.user.get() != null) {
            String userToken = viewModel.user.get().getToken();
            if (TextUtils.isEmpty(userToken)) {
                viewModel.startActivity(LoginVertifyActivity.class);
            } else {
                Bundle bundle = new Bundle();
                if (position == 3) {  //退换货
                    bundle.putInt(Constants.MyOrder, 4);
                } else if (position == 4) { //已完成
                    bundle.putInt(Constants.MyOrder, 3);
                } else {
                    bundle.putInt(Constants.MyOrder, position);
                }
                viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(), bundle);
            }
        } else {
            viewModel.startActivity(LoginVertifyActivity.class);
        }
    }

    /**
     * 跳转  我的服务
     *
     * @param position
     */
    private void skipServicePage(int position) {
        if (position == 0) { //在线客服
            mMerchantCustomerService();
        } else if (position == 1) {//电话客服
            final String phone = context.getResources().getString(R.string.storemobile);
            MessageDialog.showCancelAndSureDialog(context, phone, "",
                    R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                        @Override
                        public void onCancelClickListener() {
                        }

                        @Override
                        public void onSureClickListener() {
                            requestPermissions(phone);
                        }
                    });
        } else { //意见反馈
            viewModel.startActivity(FeedBackAty.class);
        }
    }

    /**
     * 跳转  我的功能   原生
     *
     * @param position
     */
    private void skipFunctionPage(int position) {
        // TODO: 2020/2/9 是否必须登陆
        if (viewModel.user.get() != null) {
            String userToken = viewModel.user.get().getToken();
            if (TextUtils.isEmpty(userToken)) {
                viewModel.startActivity(LoginVertifyActivity.class);
            } else {
                if (position == 0) { //推广中心
                    //第一版skip h5
//                    if (viewModel.memberEntity.get() != null) {
//                        goToBaseWebview(viewModel.memberEntity.get().getCommission_url(), userToken);
//                    }
                    //第二版skip h5
//                    String commission_url = URLEncoder.encode(Constants.MY_PAGE_COMMISSION); //url参数转义
//                    goToBaseWebviewNew(commission_url,userToken);
                    viewModel.startActivity(PromotCommissionActivity.class);
                } else if (position == 1) { //积分签到
                    viewModel.startActivity(CreditSignNewActivity.class);
                } else if (position == 2) { //我的优惠券
                    String commission_url = URLEncoder.encode(Constants.MY_PAGE_SALE_COUPON_MY);
                    goToBaseWebviewNew(commission_url, userToken);
                } else if (position == 3) { //邀请好友
                    viewModel.startActivity(InviteFriendsActivity.class);
                } else if (position == 4) { //我的团队
                  /*  if (viewModel.memberEntity.get() != null) {
                        goToBaseWebview(viewModel.memberEntity.get().getGroup_url(), userToken);
                    }*/
                    //TODO 我的团队改版原生
                    viewModel.startActivity(MineTeamActivity.class);
                } else if (position == 5) { //我的收藏
                    String commission_url = URLEncoder.encode(Constants.MY_PAGE_MEMBER_FAVORITE);
                    goToBaseWebviewNew(commission_url, userToken);
                } else if (position == 6) { // 收货地址
                    viewModel.startContainerActivity(ReceiptAddressFragment.class.getCanonicalName());
                }/* else if (position == 7) { //商家入驻暂时隱藏
                    viewModel.startActivity(MerchantEntryAty.class);
                } */ else if (position == 7) { //领券中心
                    String commission_url = URLEncoder.encode(Constants.MY_PAGE_SALE_COUPON);
                    goToBaseWebviewNew(commission_url, userToken);
//                    viewModel.startContainerActivity(CouponCenterFragment.class.getCanonicalName());
                } else { //我的小店
                    viewModel.startActivity(MineShopActivity.class);
                }
            }
        } else {
            viewModel.startActivity(LoginVertifyActivity.class);
        }
    }

    private void setSpace(RecyclerView recycleChild) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.RIGHT_DECORATION, 16);//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.LEFT_DECORATION, 16);//left间距
        recycleChild.addItemDecoration(new RecyclerViewSpacesItemDecorationUtils(stringIntegerHashMap));
    }


    /**
     * 统一 跳转  我的功能   对应一期 （奖金&服务）
     *
     * @param url
     * @param userToken
     */
    public void goToBaseWebview(String url, String userToken) {
        int netState = NetworkUtils.getNetWorkState(context);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        if (viewModel.memberEntity.get() != null && !TextUtils.isEmpty(userToken)) {
            bundle.putString(Constants.BUNDLE_KEY_URL, url + userToken);
            viewModel.startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }


    /**
     * H5统一url链接
     *
     * @param returnUrl
     */
    public void goToBaseWebviewNew(String returnUrl, String userToken) {
        int netState = NetworkUtils.getNetWorkState(context);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        if (!TextUtils.isEmpty(userToken)) {
            String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + returnUrl + "&user_token=" + userToken;
            Log.d(TAG, "goToBaseWebviewNew:" + url);
            bundle.putString(Constants.BUNDLE_KEY_URL, url);
            viewModel.startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }

    private void mMerchantCustomerService() {
        if (QQUtils.installedApp(context, "com.tencent.mobileqq") || QQUtils.installedApp((context), "com.tencent.tim")) {
            QQUtils.openPersonalQQ(context, context.getResources().getString(R.string.online_service_qq));
        } else {
            ToastUtils.showShort("本机未安装QQ应用");
            return;
        }
    }

    private void requestPermissions(String phoneNum) {
        if (EasyPermissions.hasPermissions(context, perms)) {
            callPhone(phoneNum);
        } else {
            EasyPermissions.requestPermissions((MyPageActivity) context,
                    "拨打电话需要如下权限：",
                    REQUEST_CODE_CALL_PHONE,
                    perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(context, "已获取" + perms + "权限", Toast.LENGTH_SHORT).show();
        final String phone = context.getResources().getString(R.string.storemobile);
        callPhone(phone);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(context, "你拒绝了" + perms + "权限", Toast.LENGTH_SHORT).show();
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, (MyPageActivity) context);
    }
}
