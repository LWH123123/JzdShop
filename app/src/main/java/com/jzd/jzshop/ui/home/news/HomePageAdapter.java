package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeMenuEntity;
import com.jzd.jzshop.utils.widget.ZQImageViewRoundOval;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:  home module three
 * @date :2020/1/3 13:45
 */
public class HomePageAdapter extends SuperAdapter<HomeEntity.ResultBean.DataBeanX.DataBean> {
    private Context mContext;
    private  List<HomeEntity.ResultBean.DataBeanX.DataBean> dataList;

    public HomePageAdapter(Context context, List<HomeEntity.ResultBean.DataBeanX.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, HomeEntity.ResultBean.DataBeanX.DataBean item) {
        holder.setText(R.id.tv_menu, item.getText());
        ImageView logo = holder.findViewById(R.id.iv_menu);
        Glide.with(mContext).load(item.getImgurl()).into(logo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("item" + layoutPosition + " 被点击了");
            }
        });

    }

}
