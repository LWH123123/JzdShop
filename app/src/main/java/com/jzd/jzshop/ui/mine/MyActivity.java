package com.jzd.jzshop.ui.mine;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMyBinding;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.photo_album.FileUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MyActivity extends BaseActivity<ActivityMyBinding, MyViewModel> implements OnRefreshListener {
    private NavigationController build;
    private Uri tempUri;
    private Bitmap bitmap;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my;
    }

    @Override
    public int initVariableId() {
        return BR.myVM;
    }

    @Override
    public MyViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MyViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setTranslucentStatus(this);
        initBottomTab();
        viewModel.setbind(binding, this, binding.mallRefreshLayout);
        viewModel.requestWork();
        initMallRefresh(); //配置刷新
        Messenger.getDefault().register(this, MyViewModel.TOKEN_VIEWMODEL_REFRESH, new BindingAction() {
            @Override
            public void call() {//刷新我的数据
                ToastUtils.showLong("充值成功！");
                binding.mallRefreshLayout.autoRefresh();
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.loginWX.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

/**
 * Auth.withWX(context)
 *         .setAction(Auth.Pay)
 *         .payNonceStr("")
 *         .payPackageValue("")
 *         .payPartnerId("")
 *         .payPrepayId("")
 *         .paySign("")
 *         .payTimestamp("")
 *         .build(mCallback);
 */
            }
        });
        viewModel.uc.icon.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.icon.setImageResource(R.mipmap.ic_launcher_round);
                binding.icon.invalidate();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        build.setMessageNumber(2, anInt);
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
                startActivity(ShoppingCarActivity.class);
                break;
            case 3:
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
        viewModel.requestWork();
        viewModel.requestMemberNetWork(viewModel.refresh);
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
                case MyViewModel.TAKE_PICTURE:
                    startPhotoZoom(viewModel.tempUri); // 开始对图片进行裁剪处理
                    break;
                case MyViewModel.CHOOSE_PICTURE:
                    if (data != null) {
                        startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    }
                    break;
                case MyViewModel.CROP_SMALL_PICTURE:
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
        MyViewModel.tempUri = tempUri;
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.tempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, MyViewModel.CROP_SMALL_PICTURE);
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
            Glide.with(MyActivity.this).load(file).into(binding.icon);
            binding.icon.setImageBitmap(bitmap);
            viewModel.updateAvatar(file, imagePath);
            binding.icon.invalidate();
            Log.d("TAG", "imagePath:" + imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            viewModel.takePicture();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 10003) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.choiceImage();
            }
        }
    }
}
