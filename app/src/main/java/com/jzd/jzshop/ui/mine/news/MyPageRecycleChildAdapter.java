package com.jzd.jzshop.ui.mine.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MyPagerEntity;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.utils.widget.CornerNumberView;
import com.jzd.jzshop.utils.widget.MarkedImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/20 18:12
 */
public class MyPageRecycleChildAdapter extends SuperAdapter<MyPagerEntity.DataBean> {
    private Context mContext;
    private List<MyPagerEntity.DataBean> dataList;
    private int layoutPosition;
    private  UserInfo userInfo;

    public MyPageRecycleChildAdapter(Context context, UserInfo userInfo,int layoutPosition, List<MyPagerEntity.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.userInfo = userInfo;
        this.layoutPosition = layoutPosition;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, MyPagerEntity.DataBean item) {
        holder.setText(R.id.tv_menu, item.getName());
        ImageView iv_menu = holder.findViewById(R.id.iv_menu);
        CornerNumberView message = holder.findViewById(R.id.cor_number);
        Glide.with(mContext).load(item.getIcon()).into(iv_menu);
        if (layoutPosition == 1){ //我的订单 设置  新订单数
            if (position == 0){
//                iv_menu.setMessageNumber(userInfo.getDfk_num());
                message.setMessage(userInfo.getDfk_num());
            }else if (position == 1){
//                iv_menu.setMessageNumber(userInfo.getDfh_num());
                message.setMessage(userInfo.getDfh_num());
            }else if (position == 2){
//                iv_menu.setMessageNumber(userInfo.getDshh_num());
                message.setMessage(userInfo.getDshh_num());
            }else if (position == 3){
//                iv_menu.setMessageNumber(userInfo.getThh_num());
                message.setMessage(userInfo.getThh_num());
            }else {
            }
        }else {}
    }
}
