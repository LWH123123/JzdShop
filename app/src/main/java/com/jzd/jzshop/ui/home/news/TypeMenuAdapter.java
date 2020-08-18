package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/1/4 17:49
 */
public class TypeMenuAdapter extends RecyclerView.Adapter<TypeMenuAdapter.TypeMenuHolder> {
    private static final String TAG = TypeMenuAdapter.class.getSimpleName();
    private Context mContext;
    private HomePageViewModel viewModel;
    private LayoutInflater inflater;
    private List<HomeEntity.ResultBean.DataBeanX.DataBean> mHomeMenu;


    public TypeMenuAdapter(Context mContext, List<HomeEntity.ResultBean.DataBeanX.DataBean> data) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mHomeMenu = data;
    }

    public TypeMenuAdapter(Context mContext, HomePageViewModel viewModel, List<HomeEntity.ResultBean.DataBeanX.DataBean> data) {
        this.mContext = mContext;
        this.viewModel = viewModel;
        inflater = LayoutInflater.from(mContext);
        this.mHomeMenu = data;
    }

    public void addList(List<HomeEntity.ResultBean.DataBeanX.DataBean> data){
        this.mHomeMenu = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TypeMenuHolder(inflater.inflate(R.layout.item_home_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TypeMenuHolder holder, final int position) {
        final HomeEntity.ResultBean.DataBeanX.DataBean dataBean = mHomeMenu.get(position);
        holder.tvMenu.setText(dataBean.getText());
        Glide.with(mContext).load(dataBean.getImgurl()).into(holder.ivMenu);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLinkUrl(dataBean,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mHomeMenu == null ? 0 : mHomeMenu.size();
    }

    static class TypeMenuHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_menu)
        ImageView ivMenu;
        @BindView(R.id.tv_menu)
        TextView tvMenu;
        @BindView(R.id.consl_root)
        ConstraintLayout constraintLayout;

        public TypeMenuHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     *  menu 跳转
     * @param dataBean
     * @param index
     */
    public void getLinkUrl(HomeEntity.ResultBean.DataBeanX.DataBean dataBean, int index) {
        int netState = NetworkUtils.getNetWorkState(mContext);
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        String url =dataBean.getLinkurl().toString();
        AppIdentityJumpUtils.homeMenujumpLinkUrl(url,viewModel,mContext);
    }
}
