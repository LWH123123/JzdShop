package com.jzd.jzshop.ui.goods;

import android.os.Bundle;

import com.jzd.jzshop.R;
import com.previewlibrary.GPreviewActivity;

import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 预览大图
 * @date :2020/1/1 17:52
 */
public class ImageLookActivity extends GPreviewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    /**
     *  重写此方法，自定义自己的布局
     * @return
     */
    @Override
    public int setContentLayout() {
        return R.layout.activity_image_look;
    }

}
