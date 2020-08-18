package com.jzd.jzshop.ui.home.local_life.search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jzd.jzshop.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class SearchKeyWordItemChildAdapter extends SuperAdapter<String> {
    private int position = 0;
    private Context mContext;
    private List<String> dataList;
    private int layoutPosition;

    public SearchKeyWordItemChildAdapter(Context context, List<String> items, int layoutPosition, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
        this.layoutPosition = layoutPosition;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, String item) {
        holder.setText(R.id.tv_name, item);
        ImageView iv_hot = holder.findViewById(R.id.iv_hot);
        if (layoutPosition == 1) { //热搜显示
            iv_hot.setVisibility(View.VISIBLE);
        } else {
            iv_hot.setVisibility(View.GONE);
        }
    }

}
