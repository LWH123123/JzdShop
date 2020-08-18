package com.jzd.jzshop.ui.home.local_life.search;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.SearchKeyWordEntity;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class SearchKeyWordAdapter extends SuperAdapter<SearchKeyWordEntity> {
    private Context mContext;
    private List<SearchKeyWordEntity> dataList;
    private String keyWord;

    public SearchKeyWordAdapter(Context context, List<SearchKeyWordEntity> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final SearchKeyWordEntity item) {
        holder.setText(R.id.tv_tip, item.getTip());
        ImageView iv_tip = holder.findViewById(R.id.iv_tip);
        if (layoutPosition == 0){
            iv_tip.setBackgroundResource(R.mipmap.ic_search_history);
            holder.findViewById(R.id.iv_clear_history).setVisibility(View.VISIBLE);
        }else if (layoutPosition == 1){
            iv_tip.setBackgroundResource(R.mipmap.ic_search_hot);
            holder.findViewById(R.id.iv_clear_history).setVisibility(View.GONE);
        }else {
        }
        //清空搜索历史
        holder.findViewById(R.id.iv_clear_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPosition ==0){
//                    dataList.get(layoutPosition).getKeys().clear();
//                    notifyDataSetChanged();
                }else {}

            }
        });


        //--------------key word recycleview-------------start--------------------
        RecyclerView recycleChild = holder.findViewById(R.id.recycle);
        final List<String> keys = item.getKeys();
        if (keys != null && keys.size() > 0) {
            recycleChild.setVisibility(View.VISIBLE);
            final SearchKeyWordItemChildAdapter adapter = new SearchKeyWordItemChildAdapter(
                    mContext, keys,layoutPosition,R.layout.item_rv_search_keyword_child_list_item);
            recycleChild.setAdapter(adapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            recycleChild.setLayoutManager(gridLayoutManager);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int viewType, int position) {//搜索关键词
                    keyWord = keys.get(position);
                }
            });
        } else {
            recycleChild.setVisibility(View.GONE);
        }
        //--------------key word recycleview-------------end--------------------
    }
}
