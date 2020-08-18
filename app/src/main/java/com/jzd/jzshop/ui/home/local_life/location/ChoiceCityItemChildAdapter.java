package com.jzd.jzshop.ui.home.local_life.location;

import android.content.Context;

import com.jzd.jzshop.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class ChoiceCityItemChildAdapter extends SuperAdapter<String> {
    private Context mContext;
    private List<String> dataList;

    public ChoiceCityItemChildAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
