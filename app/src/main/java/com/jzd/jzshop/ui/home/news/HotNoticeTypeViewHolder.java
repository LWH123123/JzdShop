package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.entity.HomeNoticeEntity;
import com.jzd.jzshop.ui.base.viewholder.TypeAbstractViewHolder;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.NetworkUtils;
import com.sunfusheng.marqueeview.IMarqueeItem;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * @author LXB
 * @description: 首页 热点公告
 * @date :2020/3/25 17:55
 */

class HotNoticeTypeViewHolder extends TypeAbstractViewHolder<HomeNoticeEntity> {
    @BindView(R.id.hotNotice)
    MarqueeView hotNotice;
    @BindView(R.id.iv_tip)
    android.support.v7.widget.AppCompatImageView iv_tip;
    private Context context;
    private HomePageViewModel viewModel;

    public HotNoticeTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void bindHolder(final HomeNoticeEntity model, Context mContext, Object viewModel) {
        super.bindHolder(model, mContext, viewModel);
        this.context = mContext;
        this.viewModel = (HomePageViewModel) viewModel;
        Glide.with(mContext).load(model.getIconurl()).into(iv_tip);
        Typeface textTypeface = Typeface.createFromAsset(AppApplication.getInstance().getAssets(), "fonts/Lobster-1.4.otf");
        hotNotice.setTypeface(textTypeface);
        List<CustomModel> messages = new ArrayList<>();
        if (model.getData() != null && model.getData().size() > 0) {
            for (int i = 0; i < model.getData().size(); i++) {
                if (!TextUtils.isEmpty(model.getData().get(i).getTitle())) {
                    messages.add(new CustomModel(model.getData().get(i).getTitle(), model.getData().get(i).getLinkurl()));
                }
            }
            hotNotice.startWithList(messages);
            hotNotice.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                @Override
                public void onItemClick(int position, TextView textView) {
                    String linkurl = model.getData().get(position).getLinkurl();
                    Log.d(TAG, "hotNotice:　onItemClick ---->>>" + linkurl);
                    getLinkUrl(linkurl);
                }
            });
        }
    }

    @Override
    public void bindHolder(final HomeNoticeEntity model, Context mContext) {

    }

    //自定义的Model数据类型
    public class CustomModel implements IMarqueeItem {
        private String name;
        private String url;

        public CustomModel(String title, String linkurl) {
            this.name = title;
            this.url = linkurl;
        }

        @Override
        public CharSequence marqueeMessage() {
            return name;
        }
    }


    /**
     * menu 跳转
     *
     * @param linkUrl
     */
    public void getLinkUrl(String linkUrl) {
        int netState = NetworkUtils.getNetWorkState(context);
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        AppIdentityJumpUtils.homeMenujumpLinkUrl(linkUrl,viewModel,context);
    }
}
