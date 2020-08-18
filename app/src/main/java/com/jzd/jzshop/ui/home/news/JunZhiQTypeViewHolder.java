package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.ui.base.viewholder.TypeAbstractViewHolder;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LXB
 * @description: 君子权 广告位
 * @date :2020/3/20 15:22
 */
class JunZhiQTypeViewHolder extends TypeAbstractViewHolder<HomeEntity.ResultBean.DataBeanX> {
    @BindView(R.id.tv_junzhiNumber)
    TextView tv_junzhiNumber;
    @BindView(R.id.notice)
    MarqueeView notice;
    @BindView(R.id.consl_junzhiq)
    ConstraintLayout consl_junzhiq;

    public JunZhiQTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(HomeEntity.ResultBean.DataBeanX dataBeanX, Context mContext) {
        mScrollTxtBanner(mContext);
        tv_junzhiNumber.setText(dataBeanX.getPercent());
        consl_junzhiq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     *  君子权 广告位 文字滚动
     * @param mContext
     */
    private void mScrollTxtBanner(Context mContext) {
        SpannableString spannableString = new SpannableString(mContext.getResources().getString(R.string.home_noice_tip));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D90804"));
        spannableString.setSpan(colorSpan,0,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        Typeface textTypeface = Typeface.createFromAsset(AppApplication.getInstance().getAssets(), "fonts/Lobster-1.4.otf");
        notice.setTypeface(textTypeface);
        List messages= new ArrayList<String>();
        for (int i = 0; i < 1; i++) {
            messages.add(spannableString);
        }
        notice.startWithList(messages);
    }
}
