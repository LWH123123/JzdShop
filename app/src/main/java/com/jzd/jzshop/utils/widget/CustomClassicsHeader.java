package com.jzd.jzshop.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.jzd.jzshop.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.internal.pathview.PathsDrawable;

/**
 * @author LXB
 * @description: 自定义下拉刷新头样式
 * @date :2019/12/6 14:06
 */
public class CustomClassicsHeader extends ClassicsHeader {

    private TypedArray ta;

    public CustomClassicsHeader(Context context) {
        super(context);
    }

    public CustomClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomClassicsHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setTitleTextColors(int colors) {
        mTitleText.setTextColor(getResources().getColor(R.color.white));
    }

    public void setTitleTimeColors(int colors) {
        mLastUpdateText.setTextColor(getResources().getColor(R.color.white));
    }

    public void setmArrowViewWhite() {
        PathsDrawable mArrowDrawable = new PathsDrawable();
        mArrowDrawable.parserColors(0xfffffafa);
        mArrowDrawable.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
        mArrowView.setImageDrawable(mArrowDrawable);
    }

    public ProgressDrawable getmProgressView() {
        ProgressDrawable mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xfffffafa);
        return mProgressDrawable;
    }
}
