package com.jzd.jzshop.ui.base.bottom_tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.R;

import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP： 自定义为了修改底部导航栏的  字体大小
 */
public class CustomNormalItemView extends NormalItemView {
    public TextView mTitle;
    public CustomNormalItemView(Context context) {
        this(context,null);
    }

    public CustomNormalItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomNormalItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTitle = findViewById(R.id.title);
    }

    @Override
    public void initialize(int drawableRes, int checkedDrawableRes, String title) {
        super.initialize(drawableRes, checkedDrawableRes, title);
        mTitle.setTextSize(11);

    }
}
