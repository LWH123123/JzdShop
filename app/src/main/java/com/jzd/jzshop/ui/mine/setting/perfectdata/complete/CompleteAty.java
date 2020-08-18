package com.jzd.jzshop.ui.mine.setting.perfectdata.complete;


import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCompleteBinding;
import com.jzd.jzshop.entity.PerFectEntity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.CompleteAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.photo_album.FileUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**LWH
 * 完善个人信息
 * A simple {@link Fragment} subclass.
 */
public class CompleteAty extends BaseActivity<ActivityCompleteBinding,CompleteViewModel> {

    private Uri tempUri;
    private Bitmap bitmap;

    private ImageView nice;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_complete;
    }

    @Override
    public int initVariableId() {
        return BR.completeVM;
    }

    public CompleteAdapter adapter;

    @Override
    public CompleteViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CompleteViewModel.class);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Messenger.getDefault().register(this, Constants.BINDMOBILE, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//刷新
                if(state!=null&&!TextUtils.isEmpty(state)){
                    viewModel.mobile.set(state);
                    viewModel.mobile.notifyChange();
                }
            }
        });
    }
    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
//        StatusBarUtil.getStatusBarHeight(getActivity());
        StatusBarUtil.setTranslucentStatus(this);
//        requestpermission(this);
        nice=binding.niceIcon;
        viewModel.setbind(binding,this);
        viewModel.request();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.data.observe(this, new Observer<PerFectEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable PerFectEntity.ResultBean resultBean) {
                adapter=new CompleteAdapter();
                adapter.setData(CompleteAty.this,resultBean.getFields());
                binding.rlView.setAdapter(adapter);
                binding.rlView.setLayoutManager(new LinearLayoutManager(CompleteAty.this));
                adapter.notifyDataSetChanged();
                viewModel.setAdapter(adapter);
            }
        });
    }

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public void requestpermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(activity, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(activity, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 1);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> loca = PictureSelector.obtainMultipleResult(data);
                    List<LocalMedias> localMedias = new ArrayList<>();
                    for (int i = 0; i < loca.size(); i++) {
                        LocalMedias httpMedia = new LocalMedias();
                        httpMedia.setPath(loca.get(i).getPath());
                        httpMedia.setChecked(loca.get(i).isChecked());
                        httpMedia.setCompressPath(loca.get(i).getCompressPath());
                        httpMedia.setCut(loca.get(i).isCut());
                        httpMedia.setCutPath(loca.get(i).getCutPath());
                        httpMedia.setDuration(loca.get(i).getDuration());
                        httpMedia.setPosition(loca.get(i).getPosition());
                        httpMedia.setCompressed(loca.get(i).isCompressed());
                        LocalMedia localMedia = loca.get(i);
                        File file = new File(localMedia.getCutPath());
                        httpMedia.setFile(file);
                        localMedias.add(httpMedia);
                    }
                    adapter.addpicture(localMedias);
                    break;
                case CompleteViewModel.TAKE_PICTURE:
                    startPhotoZoom(viewModel.tempUri); // 开始对图片进行裁剪处理
                    break;
                case CompleteViewModel.CHOOSE_PICTURE:
                    if (data != null) {
                        startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    }
                    break;
                case CompleteViewModel.CROP_SMALL_PICTURE:
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
        CompleteViewModel.tempUri = tempUri;
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
        startActivityForResult(intent, CompleteViewModel.CROP_SMALL_PICTURE);
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
        String imagePath = FileUtils.getFilePathByUri(this, tempUri);
        try {
            uploadPic(imagePath);
        } catch (Exception e) {
        }
    }

    private void uploadPic(String imagePath) {
        this.bitmap = bitmap;
        KLog.a("文件的原型",imagePath);
        if (imagePath != null) {
            File file = new File(imagePath);
            boolean exists = file.exists();
            KLog.a("文件是否存在",exists);
            viewModel.setFile(file);
            RequestOptions requestOptions1 = new RequestOptions().centerCrop().placeholder(R.color.app_color_f6).diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(this).load(file).into(nice);
            Log.d(TAG, "imagePath:" + imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            viewModel.takePicture();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            ToastUtils.showLong("需要存储权限");
        }
        if (requestCode == 10003) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.choiceImage();
            }
        }
    }


}
