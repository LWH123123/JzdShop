package com.jzd.jzshop.ui.home.creditsstore.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CreditsStoreEntity;
import com.jzd.jzshop.entity.bean.CreditsStoreEntityLocal;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreChildAdapter;
import com.jzd.jzshop.ui.home.news.RecyclerViewSpacesItemDecorationUtils;
import com.jzd.jzshop.utils.Constants;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author LXB
 * @description:
 * @date :2020/5/8 17:31
 */
public class CreditsStoreRecycleAdapter extends SuperAdapter<CreditsStoreEntityLocal> {
    private static final String TAG = CreditsStoreRecycleAdapter.class.getSimpleName();
    private GridLayoutManager gridLayoutManager;
    private Context context;
    private CreditsStoreViewModel viewModel;
    private List<CreditsStoreEntityLocal> dataList;
    public CreditsStoreChildAdapter adapter;


    public CreditsStoreRecycleAdapter(Context context, CreditsStoreViewModel viewModel, List<CreditsStoreEntityLocal> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
        this.viewModel = viewModel;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CreditsStoreEntityLocal item) {
        holder.setText(R.id.tv_title, item.getTitle());
        List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> list = item.getList();
        RecyclerView recycleChild = holder.findViewById(R.id.recycle);
        if (list != null && list.size() > 0) {
            if (item.getTitle().equals("积分兑换券")) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycleChild.setLayoutManager(linearLayoutManager);
                CreditsStoreChildTypeTwoAdapter adapter = new CreditsStoreChildTypeTwoAdapter(context, item.getTitle(),
                        list, R.layout.item_credits_store_child_linearlayout);
                recycleChild.setAdapter(adapter);
                setSpace(recycleChild);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int viewType, int position) {
                        String gid = list.get(position).getGid();
                        Log.d(TAG,"积分兑换券 商品gid===>>>:"+ gid);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.CREDIT_GOODS_DETAIL,gid);
                        viewModel.startActivity(CreditGoodsDetailsActivity.class,bundle);
                    }
                });
            } else {
                gridLayoutManager = new GridLayoutManager(context,2);
                recycleChild.setLayoutManager(gridLayoutManager);
                adapter = new CreditsStoreChildAdapter(context, item.getTitle(),list, R.layout.item_credits_store_child);
                recycleChild.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int viewType, int position) {
                        if(onitemClick!=null){
                            CreditsStoreEntity.ResultBean.DataBeanX.DataBean dataBean = item.getList().get(position);
                            onitemClick.OnitemClick(dataBean);
                        }
                    }
                });
//                setSpace(recycleChild);
            }
        } else {
        }


    }

    private void setSpace(RecyclerView recycleChild) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.RIGHT_DECORATION, 16);//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecorationUtils.LEFT_DECORATION, 16);//left间距
        recycleChild.addItemDecoration(new RecyclerViewSpacesItemDecorationUtils(stringIntegerHashMap));
    }


    public interface OnitemClick {
        void OnitemClick(CreditsStoreEntity.ResultBean.DataBeanX.DataBean dataBean);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }


}
