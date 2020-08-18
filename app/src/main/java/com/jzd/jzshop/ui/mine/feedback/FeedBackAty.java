package com.jzd.jzshop.ui.mine.feedback;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityFeedbackBinding;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * Created by lxb on 2020/2/10.
 * 邮箱：2072301410@qq.com
 * TIP： 意见反馈
 */
public class FeedBackAty extends BaseActivity<ActivityFeedbackBinding, FeedBackViewModel> {
    private GridImageAdapter imageAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_feedback;
    }

    @Override
    public int initVariableId() {
        return BR.feedbackVM;
    }

    @Override
    public FeedBackViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(FeedBackViewModel.class);
    }

    public List<LocalMedias> localMedias = new ArrayList<>();

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.initToolBar(getResources().getString(R.string.feedback));
        getFeedBackImage();
        // TODO: 2020/2/10  构建本地 问题分类数据,若后台返回，替换数据源即可
        List<String> dataList = Arrays.asList(getResources().getStringArray(R.array.feedback_quest_name));
        viewModel.setQuestClassData(dataList);
        viewModel.requestData();
        requestpermission(this);

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

    private void getFeedBackImage() {
        RecyclerView addimage = binding.rvAddimage;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(FeedBackAty.this,
                4, GridLayoutManager.VERTICAL, false);
        addimage.setLayoutManager(manager);
        PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(FeedBackAty.this, 5, PictureConfig.MULTIPLE);
        imageAdapter = new GridImageAdapter(addimage.getContext(), pictureSelectorUtils.onAddPicClickListener);
        imageAdapter.setSelectMax(5);
        addimage.setAdapter(imageAdapter);
        viewModel.setBinding(mContext, binding, imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.MULTIPLE:
                    List<LocalMedia> loca = PictureSelector.obtainMultipleResult(data);
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
                    imageAdapter.setList(localMedias);
                    imageAdapter.notifyDataSetChanged();
                    break;
            }

        }

    }
}
