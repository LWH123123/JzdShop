package com.jzd.jzshop.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityStartingBinding;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeMenuEntity;
import com.jzd.jzshop.entity.HomeNoticeEntity;
import com.jzd.jzshop.entity.ShopSpecialEntity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.dialog.Dialog4AppUpdate;
import com.jzd.jzshop.utils.dialog.DialogAppForceUpdate;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.photo_album.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author LXB
 * @description: 1、apk 下载
 * 2、首页数据量大，可以在启动页预先加载数据，避免一进入首页页面卡顿
 * 3、一些耗时操作在这里处理
 * @date :2020/1/4 15:45
 */
public class SplashActivity extends BaseActivity<ActivityStartingBinding, SplashViewModel> implements EasyPermissions.PermissionCallbacks {
    private static final int SPLASH = 3;
    private AppUpdateEntity.ResultBean result;
    private final static int REQUEST_CODE_FILE_DOWNLOAD_APK = 222;
    private String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private ProgressBar pgressBar;
    private AlertDialog downLoadDialog;
    private String url_download_apk;
    //---------------------apk download-------------start------------------
    private String fileSavePath;
    private static final int DOWN = 1;// 用于区分正在下载
    private static final int DOWN_FINISH = 0;// 用于区分下载完成
    private int progress;
    private boolean cancelUpdate = false;
    private File finalApkFile;
    //---------------------apk download-------------end------------------
    private File file;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // app 版本更新
            final String appVersionCode = SystemUtils.getVersion(mContext);
            viewModel.toAppUpdate(appVersionCode);
//            startActivity(HomePageActivity.class);
//            finish();
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) return;
            switch ((Integer) msg.obj) {
                case DOWN:
                    pgressBar.setProgress(progress);
                    break;
                case DOWN_FINISH:
                    prepareInstall();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_starting;
    }

    @Override
    public int initVariableId() {
        return BR.startVM;
    }

    @Override
    public SplashViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SplashViewModel.class);
    }


    @Override
    public void initData() {
        viewModel.setBinding(this, binding);
        mHandler.sendEmptyMessageDelayed(SPLASH, 2000);
        mFromCacheHomeData();
    }


    /**
     * 不获取最新数据，避免吗每次请求启动页过慢
     */
    private void mFromCacheHomeData() {
        ArrayList<BannEntity> bannerData = (ArrayList<BannEntity>) SPUtils.getInstance()
                .getSerializableEntity(Constants.SP.CACHE_HOME_BANNER_DATA);
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
        KLog.json(TAG, "mFromCacheHomeData--->>" + homeMenuEntities + homeShopEntities + homeJunZhiQEntities
                + homeshopSpecialEntities + homeNoticeEntities);
        if (homeJunZhiQEntities != null && homeShopEntities != null && homeJunZhiQEntities != null &&
                homeshopSpecialEntities != null && homeNoticeEntities != null && bannerData != null) {
        } else {
            viewModel.requestHomeDataNetWork();
        }

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.appUpdate.observe(this, new Observer<AppUpdateEntity>() {
            @Override
            public void onChanged(@Nullable AppUpdateEntity appUpdateEntity) {
                if (appUpdateEntity != null) {
                    if (appUpdateEntity.getResult() != null) {
                        showUpdateVersionDialog(appUpdateEntity);
                    }
                }
            }
        });

        viewModel.uc.nowork.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                startActivity(HomePageActivity.class);
                finish();
            }
        });


    }


    /**
     * 更新提示框
     *
     * @param appUpdateEntity
     */
    private void showUpdateVersionDialog(final AppUpdateEntity appUpdateEntity) {
        String version = SystemUtils.getVersion(mContext);
        if (appUpdateEntity.getResult() != null) {
            result = appUpdateEntity.getResult();
            int status = appUpdateEntity.getResult().getStatus();//是否强更
            if (appUpdateEntity.getResult().getVersion() != null && appUpdateEntity.getResult().getDesc() != null
                    && appUpdateEntity.getResult().getSize() != null) {
                String newVersion = appUpdateEntity.getResult().getVersion();
                String size = appUpdateEntity.getResult().getSize();
                String depict = appUpdateEntity.getResult().getDesc();
                if (status == 0) {// 0 无需更新
                    startActivity(HomePageActivity.class);
                    finish();
                } else if (status == 1) {//不强制更新
                    showNormalUpdateDialog(appUpdateEntity, newVersion, size, depict, version);
                } else if (status == 2) {  //2 强制更新
                    DialogAppForceUpdate dialog = new DialogAppForceUpdate(mContext, "当前版本V" + version
                            + ",是否升级到版本V" + newVersion + "?新版本大小:" + size, "新版本功能 \n" + depict.replace("\\n", "\n"));
                    dialog.setCancelEnable(false);
                    dialog.setListener(new DialogAppForceUpdate.onClickSureBtnListener() {
                        @Override
                        public void onClickSure() {
                            if (appUpdateEntity.getResult().getUrl() != null && !TextUtils.isEmpty(appUpdateEntity.getResult().getUrl())) {
                                url_download_apk = appUpdateEntity.getResult().getUrl();
                                //执行下载操作
                                startDownloading(appUpdateEntity.getResult().getUrl());
                            }
                        }
                    });
                    if (!isFinishing()) {
                        dialog.show();
                    }
                }
            }
        }
    }

    private void startDownloading(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.hasPermissions(this, perms)) {
                showDownloadDialog(url);
            } else {
                EasyPermissions.requestPermissions(this,
                        "下载app需要如下权限：\n存储权限",
                        REQUEST_CODE_FILE_DOWNLOAD_APK,
                        perms);
            }
        } else {
            showDownloadDialog(url);
        }
    }


    private void showDownloadDialog(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 下载对话框
        builder.setTitle("正在更新");
        final LayoutInflater inflater = LayoutInflater.from(this);  // 给下载对话框增加进度条
        View v = inflater.inflate(R.layout.dialog_download_progress, null);
        pgressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
        builder.setView(v);
        builder.setCancelable(false);
        downLoadDialog = builder.create();
        downLoadDialog.show();
        downLoadDialog.setCanceledOnTouchOutside(false);
        downloadApk(url); //下载文件
    }

    private void downloadApk(String downloadUrl) {
        //1:下载最新版的apk
        //2:进度条的更新
        //3:下载完成之后 替换原有的apk
        //--------------下载偶然会 android HttpURLConnection java.net.SocketTimeoutException: timeout------------------
//        new DownloadApkThread(downloadUrl).start();
        //-------------------------------------------------------------------------------------------------------------
        //判断sdk是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String sdpath = Environment.getExternalStorageDirectory() + "/";    // 获得存储卡的路径
            fileSavePath = sdpath + "download";
            Log.i(TAG, "fileSavePath：" + fileSavePath);
        }else {
            Log.d(TAG,"手机存储卡不可用");
        }
        OkHttpUtils
                .get()
                .url(String.valueOf(downloadUrl))
                .build()
                .execute(new FileCallBack(fileSavePath, "krsc" + ".apk") {
                    @Override
                    public void onResponse(File response, int id) {
                        Log.e(TAG, "onResponse :" + response.getAbsolutePath());
                        file = response;
                        Log.d(TAG, "apk文件下载成功 路径为：" + file.getAbsolutePath() + ".apk");
                        // 下载完成
                        downLoadDialog.dismiss();     // 取消下载对话框显示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                prepareInstallApk(file);
                            }
                        });
                        cancelUpdate = true;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "下载出错:" + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showLong("下载出错啦，再试试呗...");
                                downLoadDialog.dismiss();
                            }
                        });
                        if (file!=null){
                            if (!file.exists()) {
                                file.delete();
                            }
                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.d(TAG, "inProgress:======" + 100 * progress);
                        pgressBar.setProgress((int) (100 * progress));
                    }
                });

    }

    /**
     *  HttpURLConnection 下载文件
     */
    public class DownloadApkThread extends Thread {
        String downloadUrl;

        public DownloadApkThread(String url) {
            this.downloadUrl = url;
        }

        @Override
        public void run() {
            super.run();
            // 判断SD卡是否存在，并且是否具有读写权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String sdpath = Environment.getExternalStorageDirectory() + "/";    // 获得存储卡的路径
//                String sdpath = getFilesDir().getPath() + "/";
//                File externalFile = SplashActivity.this.getExternalFilesDir(null);
//                File[] externalFile = ContextCompat.getExternalFilesDirs(SplashActivity.this, null);
                fileSavePath = sdpath + "download";
                Log.i(TAG, "fileSavePath：" + fileSavePath);
                try {
                    URL url = new URL(downloadUrl);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 10 * 1000 );// 设置超时时间  One Minute
                    conn.setReadTimeout(6 * 10 * 1000);// 设置超时时间
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charset", "GBK,utf-8;q=0.7,*;q=0.3");
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(fileSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(fileSavePath, "krsc" + ".apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        Message message = new Message();
                        message.obj = DOWN;
                        handler.sendMessage(message);
                        if (numread <= 0) {
                            // 下载完成
                            // 取消下载对话框显示
                            downLoadDialog.dismiss();
                            Message message2 = new Message();
                            message2.obj = DOWN_FINISH;
                            handler.sendMessage(message2);
                            cancelUpdate = true;
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void prepareInstallApk(File apkfile) {
        if (!apkfile.exists()) {
            return;
        }
        if (result == null) {
            return;
        }
        String md5 = result.getHashMD5();
        String fileMd5 = FileUtils.getMd5(apkfile);
        Log.d(TAG, "fileMd5 :----->>>" + fileMd5);
        if (!md5.equals(fileMd5)) {
            ToastUtils.showLong("安装包下载错误，请重新下载");
            return;
        }
        ToastUtils.showShort("文件下载完成,正在安装更新");
        installApk(apkfile);
    }

    private void prepareInstall() {
        File apkfile = new File(fileSavePath, "krsc" + ".apk");
        if (!apkfile.exists()) {
            return;
        }
        if (result == null) {
            return;
        }
        String md5 = result.getHashMD5();
        String fileMd5 = FileUtils.getMd5(apkfile);
        Log.d(TAG, "fileMd5 :----->>>" + fileMd5);
        if (!md5.equals(fileMd5)) {
            ToastUtils.showLong("安装包下载错误，请重新下载");
            return;
        }
        ToastUtils.showShort("文件下载完成,正在安装更新");
        installApk(apkfile);
    }

    private void installApk(File apkFile) {
        finalApkFile = apkFile;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = isHasInstallPermissionWithO();
            if (!hasInstallPermission) {
                startInstallPermissionSettingActivity();
                ToastUtils.showLong("请允许安装未知来源应用权限,否则无法正常安装！");
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(this,
                    "com.jzd.jzshop.img.fileProvider", apkFile);
            grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃
//        apkFile.delete(); //  提示是否删除本地文件
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO() {
        return getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }

    private final int REQUEST_CODE_APP_INSTALL = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_APP_INSTALL:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = isHasInstallPermissionWithO();
                    if (hasInstallPermission) {
                        if (finalApkFile != null) {
                            installApk(finalApkFile);
                        }
                    } else { // 用户可能不知道 点击允许，返回后再次更新安装app 会安装失败，通过弹窗提示处理
                        MessageDialog.showWarningDialog(mContext, "提示",
                                "安装App需要打开未知来源应用权限,\n请去手机设置中找到跨融商城APP\n开启允许安装应用权限开关.",
                                new MessageDialog.MessageDialogClick() {
                                    @Override
                                    public void onSureClickListener() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //兼容8.0
                                            boolean hasInstallPermission = isHasInstallPermissionWithO();
                                            if (!hasInstallPermission) {
                                                startInstallPermissionSettingActivity();
                                            } else {
                                                //再次执行安装流程，包含权限判等
                                                if (finalApkFile != null) {
                                                    //再次启动安装流程
                                                    installApk(finalApkFile);
                                                }
                                            }
                                        }
                                    }
                                });
//                        ToastUtils.showLong("请开启安装未知来源应用权限后再重装安装");
                    }
                }
                break;
//            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
//                boolean ischeckPermissions = EasyPermissions.hasPermissions(mContext, perms);
//                if (ischeckPermissions) {
//                    installApk(finalApkFile);
//                }else {
//                    ToastUtils.showLong("请开启安装未知来源应用权限后再重装安装");
//                }
//                break;
        }
    }


    /**
     * 非强制更新 弹窗提示
     *
     * @param appUpdateEntity
     * @param newVersion
     * @param size
     * @param depict
     * @param version
     */
    private void showNormalUpdateDialog(final AppUpdateEntity appUpdateEntity, String newVersion, String size, String depict, String version) {
        final Dialog4AppUpdate dialog = new Dialog4AppUpdate(mContext, "当前版本V" + version
                + ",是否升级到版本V" + newVersion + "?新版本大小:" + size, "新版本功能 \n" + depict.replace("\\n", "\n"));
        dialog.setCancelEnable(false);
        dialog.setListener(new Dialog4AppUpdate.onClickSureBtnListener() {
            @Override
            public void onClickSure() {
                if (appUpdateEntity.getResult().getUrl() != null && !TextUtils.isEmpty(appUpdateEntity.getResult().getUrl())) {
                    url_download_apk = appUpdateEntity.getResult().getUrl();
                    startDownloading(appUpdateEntity.getResult().getUrl());//执行下载操作
                }
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                if (!isFinishing()) {
                    dialog.dismiss();
                }
                startActivity(HomePageActivity.class);
                finish();
            }
        });
        if (!isFinishing()) {
            dialog.show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.i(TAG, "获取成功的权限有：" + perms);
        showDownloadDialog(url_download_apk);
        ToastUtils.showLong("获取权限成功!");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "你拒绝了" + perms + "权限");
        ToastUtils.showLong("你拒绝了App安装权限，无法下载安装!");
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this).build().show();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && cancelUpdate) {
            showCancleDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showCancleDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("确认退出");
        builer.setMessage("是否要放弃下载？");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelUpdate = false;
                finish();
            }
        });
        builer.setCancelable(false);
        //当点取消按钮时进行登录
        builer.setNegativeButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                installApk(finalApkFile);
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

}


