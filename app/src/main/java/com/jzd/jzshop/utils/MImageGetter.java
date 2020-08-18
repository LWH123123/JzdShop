package com.jzd.jzshop.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.TypedValue;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lwh
 * @description :
 * @date 2020/4/10.
 */
public class MImageGetter implements Html.ImageGetter{
    private Context c;
    private TextView container;

    public MImageGetter(TextView text, Context c) {
        this.c = c;
        this.container = text;
    }

    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable = null;
        InputStream is = null;
        //source便是图片的路径，如果图片在本地，可以这样做
        try {
            is = c.getResources().getAssets().open(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TypedValue typedValue = new TypedValue();
            typedValue.density = TypedValue.DENSITY_DEFAULT;
            drawable = Drawable.createFromResourceStream(null, typedValue, is, "src");
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            return drawable;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
