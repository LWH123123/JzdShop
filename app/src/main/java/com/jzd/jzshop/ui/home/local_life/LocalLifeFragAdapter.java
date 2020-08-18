package com.jzd.jzshop.ui.home.local_life;

import android.content.Context;

import com.jzd.jzshop.entity.LocalLifeEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class LocalLifeFragAdapter extends SuperAdapter<LocalLifeEntity.ResultBean.AssetsRecordBean> {
    private Context mContext;
    private List<LocalLifeEntity.ResultBean.AssetsRecordBean> dataList;

    public LocalLifeFragAdapter(Context context, List<LocalLifeEntity.ResultBean.AssetsRecordBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, LocalLifeEntity.ResultBean.AssetsRecordBean item) {

    }
}
