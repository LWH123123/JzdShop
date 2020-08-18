package com.jzd.jzshop.utils.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.jzd.jzshop.R;

/**
 * @author LXB
 * @description: 自定义一键置顶scrollview
 * @date :2019/12/4 15:27
 */
public class GoTopScrollView extends ScrollView implements View.OnClickListener {

    private ImageView goTopBtn;
    private int screenHeight;

    public GoTopScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollListener(ImageView goTopBtn) {
        this.goTopBtn = goTopBtn;
        this.goTopBtn.setOnClickListener(this);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        /**
         * 滑动距离超过500px,出现向上按钮,可以做为自定义属性
         */
        if (t >= 800) {
            goTopBtn.setVisibility(View.VISIBLE);
        } else {
            goTopBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ftb) {
            this.smoothScrollTo(0, 0);
        }
    }

}
