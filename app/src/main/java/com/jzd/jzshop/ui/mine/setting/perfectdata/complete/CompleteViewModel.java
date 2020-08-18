package com.jzd.jzshop.ui.mine.setting.perfectdata.complete;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;

import com.jzd.jzshop.databinding.ActivityCompleteBinding;
import com.jzd.jzshop.entity.PerFectEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.login.bindmobile.BindMobileFragment;
import com.jzd.jzshop.ui.mine.setting.perfectdata.AnalysisData;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.CompleteAdapter;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

public class CompleteViewModel extends BaseViewModel<Repository> {
    public CompleteViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    public File file;

    public void setFile(File file) {
        this.file = file;
    }
    private CompleteAdapter adapter;
    public void setAdapter(CompleteAdapter adapter) {
        this.adapter=adapter;
    }
    public ActivityCompleteBinding binding;
    private CompleteAty context;
    public void setbind(ActivityCompleteBinding binding,CompleteAty completeAty) {
        this.binding=binding;
        this.context=completeAty;
    }
    public ObservableField<PerFectEntity.ResultBean> result =new ObservableField<>();
    public SingleLiveEvent<PerFectEntity.ResultBean> data=new SingleLiveEvent<>();
    public ObservableField<String> mobile=new ObservableField<>();




    public void request(){

        if (TextUtils.isEmpty(model.getUserToken())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CAR_LOGIN, "CAR_LOGIN");
            startActivity(LoginVertifyActivity.class, bundle);
            return;
        }
        addSubscribe(model.postPerfect(model.getUserToken()), new ParseDisposableTokenErrorObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse baseResponse) {
                dismissDialog();
                Gson gson = new Gson();
                String s = gson.toJson(baseResponse.getResult());
                PerFectEntity.ResultBean resultBean = new AnalysisData().initDatas("{\"result\": " + s + "}");
                result.set(resultBean);
                mobile.set(resultBean.getMobile());

                data.setValue(resultBean);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
            }
        });

    }
    //更换绑定
    public BindingCommand onCLickChangeBind =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(BindMobileFragment.class.getCanonicalName());
        }
    });


    //更换头像
    public BindingCommand onClickIcon =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setIcon();

        }
    });


    //提交资料
    public BindingCommand onSubmitClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            submitData();

        }
    });


    public BindingCommand onClickBackListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    private void submitData() {
        if(adapter==null){
            return;
        }
        boolean b = adapter.mustInport();
        if(!b){
            ToastUtils.showLong("请填写必填项!");
            return;
        }
        String s = binding.edNickname.toString();
        if(TextUtils.isEmpty(s)){
            ToastUtils.showLong("请填写昵称!");
            return;
        }
        if(TextUtils.isEmpty(mobile.get())){
            ToastUtils.showLong("请绑定手机号");
            return;
        }
        HashMap<String, String> hash = new HashMap<>();
        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        if(adapter.hashMap!=null){
           hash.putAll(adapter.hashMap);
        }
        if(adapter!=null&&adapter.gridImageAdapter!=null){
           GridImageAdapter image= adapter.gridImageAdapter;
           if(!TextUtils.isEmpty(image.httpImg())&&!TextUtils.isEmpty(image.key)){
               KLog.a("图片====>>"+image.httpImg());
               hash.put(image.key+"addimage",image.httpImg());
           }
            if(image.getList()!=null&&image.getList().size()!=0){
//            List<LocalMedias> list = image.getList();
                List<File> files = image.locaImgFile();
                for (int i = 0; i <files.size() ; i++) {
                    if (files.get(i) != null) {
                        KLog.a("上传的图片:", files.get(i).getPath());
                        postFormBuilder.addFile( image.key+ "_" + i, files.get(i).getName(), files.get(i));
                    }
                }
            }
        }
        hash.put("nickname",binding.edNickname.getText().toString());
        hash.put("terminal", "Android");

        Map<String, String> params = new HashMap<>();
        String postBodyString = ParameterToJsonUtils.createJsonString(model.getUserToken(), AppApplication.myDeviceID,
                hash);
        String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
        String sign = ParameterToJsonUtils.encrypSign(postBodyString);
        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);



        if (file != null && file.exists()) {
            postFormBuilder.addFile("avatar", file.getName(), file);
        }
//                .files(tp_key, filesmap)
        postFormBuilder
                .addHeader("Content-Type", "multipart/form-data;")
                .url(RetrofitClient.baseUrl + Constants.URL.submitUserInfo)
                .params(params)
                .build()
                .execute(new CompleteViewModel.MyStringCallback());
    }



    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onResponse(String response, int id) {
            ToastUtils.showShort("提交资料成功！");
            finish();
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }
    }



    //更换头像
    private void setIcon() {
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
                    .setOutCancel(true)     //点击dialog外是否可取消，默认true
                    .setAnimStyle(R.style.PopupAnimation_Up_Down)     //设置dialog进入、退出的动画style(底部显示的dialog有默认动画)
                    .setShowBottom(true)//是否在底部显示dialog，默认flase
                    .show((context).getSupportFragmentManager());     //显示dialog
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
        context.startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private boolean checkPermission(String permission, int requestCode) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
            flag = true;
        else
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
        return flag;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(context, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(context, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                ((context)).requestPermissions(permissions, 1);
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
            tempUri = FileProvider.getUriForFile(context, "com.jzd.jzshop.img.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        context.startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
}
