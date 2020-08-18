package com.jzd.jzshop.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.jzd.jzshop.R;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LWH
 * @description:
 * @date :2020/1/15 12:00
 */
public class PictureSelectorUtils {
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private Fragment fragment;
    private int maxnum;
    private int requestCode;

    public PictureSelectorUtils(Fragment fragment, int maxnum,int requestCode) {
        this.fragment = fragment;
        this.maxnum = maxnum;
        this.requestCode=requestCode;
    }

    private Activity activity;

    public PictureSelectorUtils(Activity activity, int maxnum,int requestCode) {
        this.activity = activity;
        this.maxnum = maxnum;
        this.requestCode=requestCode;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public GridImageAdapter.onAddPicClickListener getOnAddPicClickListener() {
        return onAddPicClickListener;
    }

    public void setOnAddPicClickListener(GridImageAdapter.onAddPicClickListener onAddPicClickListener) {
        this.onAddPicClickListener = onAddPicClickListener;
    }



    public void requestpermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(activity, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(activity, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 1);
            }
        }
    }
    public void requestpermission(Fragment fragment){
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(fragment.getActivity(), permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(fragment.getActivity(), permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(permissions, 1);
            }
        }
    }
    /*fragment*/
    public GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int select) {
            PictureSelector p = null;
            if (fragment != null) {
                p = PictureSelector.create(fragment);
            } else if (activity != null) {
                p = PictureSelector.create(activity);
            }else {
                return;
            }
            p.openGallery(PictureMimeType.ofImage())
                    .theme(R.style.picture_WeChat_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                    .compress(true)
                    .cropWH(300, 300)
                    .enableCrop(true)
                    .freeStyleCropEnabled(true)
                    .maxSelectNum(select)
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步false或异步true 压缩 默认同步
                    .freeStyleCropEnabled(true)//裁剪框是否可拖 拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(requestCode);//结果回调onActivityResult code
        }
    };
}
