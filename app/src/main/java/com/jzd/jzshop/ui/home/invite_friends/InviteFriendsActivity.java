package com.jzd.jzshop.ui.home.invite_friends;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityInviteFriendsBinding;
import com.jzd.jzshop.entity.bean.PosterBean;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.photo_album.SavePicToGalleryUtils;
import com.jzd.jzshop.utils.widget.ZQImageViewRoundOval;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author LXB
 * @description: 邀请好友
 * @date :2020/1/9 12:17
 */
public class InviteFriendsActivity extends BaseActivity<ActivityInviteFriendsBinding, InviteFriendsViewModel> implements EasyPermissions.PermissionCallbacks {
    //是否下载海报
    private boolean isDownloadPoster = false;
    private final static int REQUEST_CODE_FILE_CHOOSER = 101;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File file;

    private Bitmap bitmapPic;
    private String imagePath; //要下载的图片url
    private List dataList;
    private List<String> bannerList;
    public static final int MSG_ONCLICK_SAVE_PIC_UI = 100;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage( Message msg) {
            super.handleMessage(msg);
            if (msg ==null)return;
            switch (msg.what) {
                case MSG_ONCLICK_SAVE_PIC_UI:
                    bitmapPic = (Bitmap) msg.obj;
                    ToastUtils.showShort("保存成功");
                    saveToSystemGallery(mContext, file, file.getName());  //把文件插入到系统图库
                    break;
            }

        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_invite_friends;
    }

    @Override
    public int initVariableId() {
        return BR.inviteFriendVM;
    }

    @Override
    public InviteFriendsViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(InviteFriendsViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, false);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FE5D53"));
        viewModel.initToolBar(getResources().getString(R.string.invite_friends));
        viewModel.setBinding(mContext, binding);
        viewModel.requestData();
        initBanner();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initBanner() {
        binding.xbanner.setAutoPlayAble(false);
        binding.xbanner.setHandLoop(true);
        binding.xbanner.setAllowUserScrollable(true);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.xbannerLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> bannEntities) {
                bannerList = bannEntities;
                if (bannEntities != null && bannEntities.size() > 0) {
                    dataList = new ArrayList<PosterBean>();
                    for (int i = 0; i < bannEntities.size(); i++) {
                        dataList.add(new PosterBean(bannEntities.get(i)));
                    }
                    if (dataList != null && dataList.size() > 0) {
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        binding.xbanner.setAutoPlayAble(false);
                        binding.xbanner.setIsClipChildrenMode(true);//是否开启一屏显示多个模式
                        binding.xbanner.setBannerData(R.layout.custom_view_share_poster, dataList);
                    }
                } else {
                }
            }
        });

        //查看海报
        binding.xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Log.d(TAG, "点击了第" + (position + 1) + "图片");
                //返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                PosterBean posterBean = (PosterBean) model;
                AppIdentityJumpUtils.previewLargePic(InviteFriendsActivity.this, bannerList, position);
            }
        });
        binding.xbanner.loadImage(new XBanner.XBannerAdapter() { //设置海报
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                PosterBean posterBean = (PosterBean) model;
                ZQImageViewRoundOval imageViewRoundOval = (ZQImageViewRoundOval) view;
                imageViewRoundOval.setType(ZQImageViewRoundOval.TYPE_ROUND);
                imageViewRoundOval.setRoundRadius(9);//矩形凹行大小
                Glide.with(mContext).load(posterBean.getImgUrl()).into(imageViewRoundOval);
            }
        });
        //保存海报
        viewModel.uc.btnPicClick.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                isDownloadPoster = true;
                int bannerCurrentItem = binding.xbanner.getBannerCurrentItem();
                if (dataList != null) {
                    PosterBean posterBean = (PosterBean) dataList.get(bannerCurrentItem);
                    if (posterBean != null) {
                        try {
                            imagePath = posterBean.getImgUrl();
                            mDownloadPosterPic(imagePath);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        //分享海报
        viewModel.uc.btnshareWXClick.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                int bannerPosiont = binding.xbanner.getBannerCurrentItem();
                if (dataList != null) {
                    PosterBean poster = (PosterBean) dataList.get(bannerPosiont);
                    if (poster != null) {
                        imagePath = poster.getImgUrl();
                        try {
                            isDownloadPoster = false;
                            mDownloadPosterPic(poster.getImgUrl());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    /**
     * 将获取到的网络图片下载下来，通过流转成Bitmap
     *
     * @param picturePath 网络图片文件路径
     * @return
     */
    private void mDownloadPosterPic(final String picturePath) throws MalformedURLException {
        requestPermissions(picturePath);
    }

    private void requestPermissions(String picturePath) throws MalformedURLException {
        if (EasyPermissions.hasPermissions(this, perms)) {
            downloadPoster();
        } else {
            EasyPermissions.requestPermissions(this,
                    "保存图片到相册需要如下权限：",
                    REQUEST_CODE_FILE_CHOOSER,
                    perms);
        }
    }


    private void downloadPoster() throws MalformedURLException {
        URL url = new URL(imagePath);
        OkHttpUtils
                .get()
                .url(String.valueOf(url))
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "app_poster.jpg") {
                    @Override
                    public void onResponse(File response, int id) {
                        Log.e(TAG, "onResponse :" + response.getAbsolutePath());
                        file = response;
                        Log.d(TAG, "文件下载成功 路径为：" + file.getAbsolutePath() + ".jpg");
                        if (isDownloadPoster) {
                            try {
                                Bitmap bitmap = mToBitmap(file);
                                Message message = Message.obtain();     //下面这是把图片携带在Message里面
                                message.what = MSG_ONCLICK_SAVE_PIC_UI;
                                message.obj = bitmap;
                                mHandler.sendMessage(message);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {//将图片文件转为bitmap
                                Bitmap bitmap = mToBitmap(file);
                                if (bitmap != null) {
                                    sendToFriends(imagePath, bitmap);
                                } else {
                                    Log.e(TAG, "下载推广海报失败!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "下载出错:" + e.getMessage());
                        ToastUtils.showShort("下载出错啦，再试试呗...");
                        if (file!=null){
                            String pdfFilePath = Environment.getExternalStorageDirectory() + "/temp" + file.getName();
                            File file = new File(pdfFilePath);
                            if (!file.exists()) {
                                file.delete();
                            }
                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.d(TAG, "inProgress:======" + 100 * progress);
                    }
                });
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//已授权
        try {
            downloadPoster();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {//如果拒绝，提示
        Toast.makeText(this, "你拒绝了" + perms + "权限", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 将图片文件转为bitmap
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private Bitmap mToBitmap(File file) throws FileNotFoundException {
        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            return bitmap;
        } else {
            return null;
        }
    }


    /**
     * 发送给好友
     */
    private void sendToFriends(final String imgUrl, final Bitmap bitmap) {
        final OnekeyShare oks = new OnekeyShare();//关闭sso授权
        oks.disableSSOWhenAuthorize();
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(imgUrl);//只分享图片
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams shareParams) {
                if (platform.getName().equals(Wechat.NAME)) {// 分享到微信朋友/朋友圈
                    shareParams.setShareType(Platform.SHARE_IMAGE);
                    shareParams.setUrl(imgUrl);
                    shareParams.setImageData(bitmap);
                }
            }
        });
        // 启动分享GUI
        oks.show(this);
    }


    /**
     * 保存图片到本地,其次把文件插入到系统图库
     * 方案一：发送广播通知系统更新相册（相册更新很慢，部分手机无法更新）
     * 方案二：MediaScanner
     * 方案三：仿照 微信保存图片到系统相册
     * @param context
     * @param file
     * @param fileName
     */
    public static void saveToSystemGallery(Context context, File file, String fileName) {
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(),fileName + ".jpg", null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        //方案三：仿照 微信保存图片到系统相册
        SavePicToGalleryUtils.saveToSystemGallery(context,file,fileName);

    }

}
