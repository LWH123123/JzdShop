package com.jzd.jzshop.ui.mine.merch;


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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMerchNextBinding;
import com.jzd.jzshop.entity.EventFile;
import com.jzd.jzshop.entity.MerchEntity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.photo_album.FileUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchNextFragment extends BaseFragment<FragmentMerchNextBinding, MerchNextViewModel> {


    private MerchNextAdapter merchNextAdapter;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private MerchNextFragment merchNextFragment;
    private MerchEntity.ResultBean merchEntity;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_merch_next;
    }

    @Override
    public int initVariableId() {
        return BR.merchnextVM;
    }


    @Override
    public MerchNextViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MerchNextViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setTool();
        Bundle arguments = getArguments();
        merchNextFragment = MerchNextFragment.this;
        if (arguments != null) {
            merchEntity = getArguments().getParcelable(Constants.MerchFirst);
            List<MerchEntity.ResultBean.FieldsBean> parcelableArray = getArguments().getParcelableArrayList(Constants.MerchFirstList);
            merchNextAdapter = new MerchNextAdapter();
            final FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(),
                    2, GridLayoutManager.VERTICAL, false);
            merchNextAdapter.setList(parcelableArray);
            binding.recyclerView.setLayoutManager(manager);
            binding.recyclerView.setAdapter(merchNextAdapter);
            getUpdatepic();
        }
        viewModel.setbind(binding);


    }
    private int index;

    private void getUpdatepic() {
        merchNextAdapter.setOnitemClick(new MerchNextAdapter.OnitemClick() {
            @Override
            public void OnitemClick(int position, MerchEntity.ResultBean.FieldsBean fieldsBean) {
                index=position;
                showChoosePicDialog();
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.next.observe(MerchNextFragment.this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                List<MerchEntity.ResultBean.FieldsBean> adapterList = merchNextAdapter.getAdapterList();
                for (MerchEntity.ResultBean.FieldsBean fieldsBean : adapterList) {
                    int tp_must = fieldsBean.getTp_must();
                    if(tp_must==1){
                        if(TextUtils.isEmpty(fieldsBean.getTp_value())) {
                            if (fieldsBean.getFile() == null) {
                                ToastUtils.showLong("请上传" + fieldsBean.getTp_name());
                                return;
                            }
                        }
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.MerchSeconed,merchEntity);

                ArrayList<EventFile.FileBean> filelist = new ArrayList<>();

                for (MerchEntity.ResultBean.FieldsBean fieldsBean : adapterList) {
                    EventFile.FileBean eventFile = new EventFile.FileBean();
                    if(fieldsBean.getFile()!=null){
                      eventFile.setKey(fieldsBean.getTp_key());
                      eventFile.setFile(fieldsBean.getFile());
                        filelist.add(eventFile);
                    }
                }
                EventBus.getDefault().postSticky(filelist);
                bundle.putParcelableArrayList(Constants.MerchSeconedList, (ArrayList<? extends Parcelable>) adapterList);
                startContainerActivity(MerchFinallyFragment.class.getCanonicalName(),bundle);
            }
        });
    }

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
                    .show((getActivity()).getSupportFragmentManager());     //显示dialog
        }catch (IllegalStateException e){
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
        merchNextFragment.startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private boolean checkPermission(String permission, int requestCode) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED)
            flag = true;
        else
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        return flag;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(getActivity(), permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(getActivity(), permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                merchNextFragment.requestPermissions(permissions, 1);
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
            tempUri = FileProvider.getUriForFile(getActivity(), "com.jzd.jzshop.img.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        merchNextFragment.startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) { // 如果返回码是可以用的
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
                        setImageToView(); // 让刚才选择裁剪得到的图片显示在界面上
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
        intent.putExtra("crop", "false");
        // aspectX aspectY 是宽高的比例
        /*intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);*/
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    private File getPicFile(String fileName) {
        File picDir = new File(getActivity().getExternalFilesDir(null), "pics");
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
    protected void setImageToView() {
        String imagePath = FileUtils.getFilePathByUri(getContext(), tempUri);
        try {
            uploadPic(imagePath);
        } catch (Exception e) {
        }
    }

    private void uploadPic(String imagePath) {
        if (imagePath != null) {
            File file = new File(imagePath);
            if(file.exists()){
                List<MerchEntity.ResultBean.FieldsBean> adapterList = merchNextAdapter.getAdapterList();
                adapterList.get(index).setFile(file);
                merchNextAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            takePicture();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(getContext(), "需要存储权限", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 10003) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choiceImage();
            }
        }
    }


}
