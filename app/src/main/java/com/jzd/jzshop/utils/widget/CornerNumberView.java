package com.jzd.jzshop.utils.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzd.jzshop.R;


/**
 * @author LWH
 * @description:自定义小红点
 * @date :2020/1/10 14:12
 */
public class CornerNumberView extends LinearLayout {

   private TextView number;
   private LinearLayout li_message;


    public CornerNumberView(Context context) {
        super(context);
    }

    public CornerNumberView(Context context, @Nullable  AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_corner_include_number, this, true);
            number = inflate.findViewById(R.id.tv_message);
            li_message = inflate.findViewById(R.id.li_message);
            li_message.setVisibility(View.GONE);
        }
    }


    public CornerNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setMessage(int message) {
        li_message.setVisibility(View.VISIBLE);
        String s = String.valueOf(message);
        if(!TextUtils.isEmpty(s)&&!s.equals("0")){
            number.setText(s);
            if(s.length()>2){
                number.setText("99+");
            }
        }else {
            li_message.setVisibility(View.GONE);
        }
    }
}
