package com.jzd.jzshop.ui.home.local_life.location;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.ChoiceCityEntity;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import me.goldze.mvvmhabit.bus.Messenger;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class SearchChoiceCityAdapter extends SuperAdapter<ChoiceCityEntity> {
    private Context mContext;
    private List<ChoiceCityEntity> dataList;
    private  ChoiceCityViewModel viewModel;
    private String hotCity;

    public SearchChoiceCityAdapter(Context context, ChoiceCityViewModel viewModel, List<ChoiceCityEntity> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        this.mContext = context;
        this.viewModel = viewModel;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ChoiceCityEntity item) {
        holder.setText(R.id.tv_tip, item.getTip());
        //--------------key word recycleview-------------start--------------------
        RecyclerView recycleChild = holder.findViewById(R.id.recycle);
        final List<String> keys = item.getKeys();
        if (keys != null && keys.size() > 0) {
            recycleChild.setVisibility(View.VISIBLE);
            final ChoiceCityItemChildAdapter adapter = new ChoiceCityItemChildAdapter(
                    mContext, keys, R.layout.item_rv_choice_city_child_list_item);
            recycleChild.setAdapter(adapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            recycleChild.setLayoutManager(gridLayoutManager);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int viewType, int position) {//热门城市
                    hotCity = keys.get(position);
                    Messenger.getDefault().send(hotCity, ChoiceCityViewModel.TOKEN_VIEWMODEL_CHOICE_CITY_REFRESH);
                    viewModel.finish();

                }
            });
        } else {
            recycleChild.setVisibility(View.GONE);
        }
        //--------------key word recycleview-------------end--------------------
    }
}
